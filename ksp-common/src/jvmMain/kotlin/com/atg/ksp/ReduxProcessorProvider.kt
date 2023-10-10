package com.atg.ksp

import com.atg.annotations.ReduxFeature
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.Variance

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.symbol.Variance.*
import com.google.devtools.ksp.validate
import java.io.OutputStream
import kotlin.reflect.KClass

class ReduxProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor = environment.run {
        ReduxProcessor(options = options, logger = logger, codeGenerator = codeGenerator)
    }
}

class ReduxProcessor(
    private val options: Map<String, String>,
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) : SymbolProcessor {
    operator fun OutputStream.plusAssign(str: String) {
        this.write(str.toByteArray())
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver
            .getSymbolsWithAnnotation(ReduxFeature::class.java.name)
            .filterIsInstance<KSClassDeclaration>()
        if (!symbols.iterator().hasNext()) return emptyList()

        // The generated file will be located at:
        // build/generated/ksp/main/kotlin/com/morfly/GeneratedFunctions.kt
        val file = codeGenerator.createNewFile(
            // Make sure to associate the generated file with sources to keep/maintain it across incremental builds.
            // Learn more about incremental processing in KSP from the official docs:
            // https://kotlinlang.org/docs/ksp-incremental.html
            dependencies = Dependencies(false, *resolver.getAllFiles().toList().toTypedArray()),
            packageName = "com.atg.annotations",
            fileName = "GeneratedReduxFeature"
        )
        file += "package com.atg.annotations\n"

        symbols.forEach { it.accept(Visitor(file), Unit) }

        file.close()

        return symbols.filterNot { it.validate() }.toList()
    }

    inner class Visitor(private val file: OutputStream) : KSVisitorVoid() {

        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            if (classDeclaration.classKind != ClassKind.CLASS && classDeclaration.classKind != ClassKind.INTERFACE) {
                logger.error("Only interface and class can be annotated with @ReduxFeature ${classDeclaration.classKind}", classDeclaration)
                return
            }

            val annotation: KSAnnotation = classDeclaration.annotations.first {
                it.shortName.asString() == ReduxFeature::class.simpleName
            }


            val classArgument: KSValueArgument = annotation.arguments
                .first { arg -> arg.name?.asString() == "clazz" }
            val nameArgument: KSValueArgument = annotation.arguments
                .first { arg -> arg.name?.asString() == "name" }
            // Getting the value of the 'name' argument.
            val functionClass = classArgument.value as KSType
            val functionName = nameArgument.value as String
            logger.warn("visitClassDeclaration ${ functionClass}", classDeclaration)

            // Getting the list of member properties of the annotated interface.
            val properties: Sequence<KSPropertyDeclaration> = classDeclaration.getAllProperties()
                .filter { it.validate() }

            // Generating function signature.
            file += "\n"
//            file += "import ${functionClass.arguments}.${functionClass}"
            file += "\n"
            if (properties.iterator().hasNext()) {
//                file += "fun ${functionClass}.$functionName(\n"
                file += "fun $functionName(\n"

                // Iterating through each property to translate them to function arguments.
                properties.forEach { prop ->
                    visitPropertyDeclaration(prop, Unit)
                }
                file += ") {\n"

            } else {
                // Otherwise, generating function with no args.
//                file += "fun ${functionClass}.$functionName() {\n"
                file += "fun $functionName() {\n"
            }

            // Generating function body.
            file += "    println(\"Hello from $functionClass.$functionName\")\n"
            file += "}\n"
        }

        override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
            // Generating argument name.
            val argumentName = property.simpleName.asString()
            file += "    $argumentName: "

            // Generating argument type.
            val resolvedType: KSType = property.type.resolve()
            file += resolvedType.declaration.qualifiedName?.asString() ?: run {
                logger.error("Invalid property type", property)
                return
            }

            // Generating generic parameters if any.
            val genericArguments: List<KSTypeArgument> = property.type.element?.typeArguments ?: emptyList()
            visitTypeArguments(genericArguments)

            // Handling nullability.
            file += if (resolvedType.nullability == Nullability.NULLABLE) "?" else ""

            file += ",\n"
        }

        private fun visitTypeArguments(typeArguments: List<KSTypeArgument>) {
            if (typeArguments.isNotEmpty()) {
                file += "<"
                typeArguments.forEachIndexed { i, arg ->
                    visitTypeArgument(arg, data = Unit)
                    if (i < typeArguments.lastIndex) file += ", "
                }
                file += ">"
            }
        }

        override fun visitTypeArgument(typeArgument: KSTypeArgument, data: Unit) {
            // Handling KSP options, specified in the consumer's build.gradle(.kts) file.
            if (options["ignoreGenericArgs"] == "true") {
                file += "*"
                return
            }

            when (val variance: Variance = typeArgument.variance) {
                // <*>
                STAR -> {
                    file += "*"
                    return
                }
                // <out ...>, <in ...>
                COVARIANT, CONTRAVARIANT -> {
                    file += variance.label
                    file += " "
                }
                INVARIANT -> {
                    // Do nothing.
                }
            }
            val resolvedType: KSType? = typeArgument.type?.resolve()
            file += resolvedType?.declaration?.qualifiedName?.asString() ?: run {
                logger.error("Invalid type argument", typeArgument)
                return
            }

            // Generating nested generic parameters if any.
            val genericArguments: List<KSTypeArgument> = typeArgument.type?.element?.typeArguments ?: emptyList()
            visitTypeArguments(genericArguments)

            // Handling nullability.
            file += if (resolvedType?.nullability == Nullability.NULLABLE) "?" else ""
        }
    }
}