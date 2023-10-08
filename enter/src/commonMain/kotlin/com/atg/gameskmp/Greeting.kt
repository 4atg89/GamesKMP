package com.atg.gameskmp

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "${platform.name}!"
    }
}

@Composable fun Enter() {
    Text(text = "Hi from ${Greeting().greet()}")
}