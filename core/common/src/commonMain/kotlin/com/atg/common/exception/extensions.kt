package com.atg.common.exception

//@Throws
fun <T> tryOrThrow(value: () -> T, block: (Exception) -> Exception): T =
    try { value.invoke() } catch (e: Exception) { throw block(e) }
//@Throws
fun <T> tryOrNull(value: () -> T): T? =
    try { value.invoke() } catch (e: Exception) { null }
