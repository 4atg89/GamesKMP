package com.atg.gameskmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform