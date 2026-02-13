package com.pantherhm.rfc_generator

public enum class InputError
{
    BadFormat,
    EmptyInput,
    InsufficientLength,
    InvalidDate,
}

fun InputError.desc(): String = when (this) {
    InputError.BadFormat   -> "El formato es incorrecto."
    InputError.EmptyInput  -> "Campo obligatorio"
    InputError.InsufficientLength ->
        "La longitud es insuficiente, asegúrese de ingresar una cadena válida"
    InputError.InvalidDate -> "La fecha no es valida"
}

public data class FoundError(val input : Int, val error : InputError){}