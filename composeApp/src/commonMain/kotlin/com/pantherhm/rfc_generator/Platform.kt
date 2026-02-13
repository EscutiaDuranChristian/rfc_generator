package com.pantherhm.rfc_generator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform