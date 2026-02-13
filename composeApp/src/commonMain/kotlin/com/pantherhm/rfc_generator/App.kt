package com.pantherhm.rfc_generator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import rfc_generator.composeapp.generated.resources.Chambeador

import rfc_generator.composeapp.generated.resources.Res

import rfc_generator.composeapp.generated.resources.compose_multiplatform

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import rfc_generator.composeapp.generated.resources.OIP


@Composable
@Preview
fun App() {
    val inputText = listOf(
        remember { mutableStateOf("") },
        remember { mutableStateOf("") },
        remember { mutableStateOf("") },
    )
    val errors = listOf(
        remember { mutableStateOf<InputError?>(null) },
        remember { mutableStateOf<InputError?>(null) },
        remember { mutableStateOf<InputError?>(null) },
    )

    var rfc by remember { mutableStateOf("XXXXXXXXXX") }

    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var showButton by remember { mutableStateOf(true) }

        val focusRequesters = List(inputText.size) { FocusRequester() }

        Column(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize().safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AnimatedVisibility(visible = showButton) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    @OptIn(ExperimentalResourceApi::class)
                    Image(
                        painter = painterResource(Res.drawable.OIP),
                        contentDescription = "RFC Logo",
                        modifier = Modifier
                            .padding(0.dp, 10.dp)
                            .fillMaxWidth(0.3f)
                    )

                    Button(
                        onClick = { showContent = !showContent; showButton = false },
                        modifier = Modifier
                            .padding(0.dp, 10.dp)
                            .fillMaxWidth(0.8f)
                    ) {
                        Text("¡Empezar!")
                    }
                }
            }
            AnimatedVisibility(showContent) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.8f).background(Color.White).padding(0.dp, 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "¡Rellena los campos para calcular tu RFC!",
                        Modifier.padding(0.dp, 10.dp)
                    )
                    for (i in inputText.indices) {
                        if (errors[i].value != null) {
                            val error = errors[i].value
                            Text(
                                text = error?.desc() ?: "",
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                        OutlinedTextField(
                            value = inputText[i].value,
                            onValueChange = {
                                inputText[i].value = it;
                                rfc = GenerateRFC.calculate(
                                    inputText[0],
                                    inputText[1],
                                    inputText[2]
                                );
                                for(i in errors.indices) {
                                    errors[i].value = null
                                }
                                for(i in GenerateRFC.errors.indices)
                                {
                                    val e = GenerateRFC.errors[i]
                                    errors[e.input].value = e.error
                                }
                            },
                            modifier = Modifier.fillMaxWidth(0.8f).
                                        padding(0.dp, 10.dp).
                                        focusRequester(focusRequesters[i])
                                        .onPreviewKeyEvent { event ->
                                            if (event.type == KeyEventType.KeyDown &&
                                                (event.key == Key.Enter || event.key == Key.Tab)
                                            ) {
                                                val nextIndex = (i + 1) % focusRequesters.size
                                                focusRequesters[nextIndex].requestFocus()
                                                true
                                            } else {
                                                false
                                            }
                                        },
                            label = {
                                Text(
                                    listOf(
                                        "Apellidos", "Nombre",
                                        "Fecha Nac (YY/MM/DD)"
                                    )[i]
                                )
                            },
                        )
                    }
                    Text(rfc)
                }
            }
        }
    }
}


