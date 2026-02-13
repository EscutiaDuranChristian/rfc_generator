package com.pantherhm.rfc_generator

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "rfc_generator",
    ) {
        App()
    }
}