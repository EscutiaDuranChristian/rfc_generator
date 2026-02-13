package com.pantherhm.rfc_generator

import com.pantherhm.rfc_generator.GenerateRFC.Companion.errors

val minLength = 3
val nameRegex = "[a-zA-Z ]{$minLength,}"
val dateRegex = Regex(
    "^(0[1-9]|[1-9][0-9])/(02/(0[1-9]|1[0-9]|2[0-8])|" +
            "(04|06|09|11)/(0[1-9]|[1-2][0-9]|30)|" +
            "(01|03|05|07|08|10|12)/(0[1-9]|1[0-9]|2[0-9]|3[01]))$"
)
fun processName(name : String, input: Int) : List<String>? {
    if (!name.isEmpty()) {
        //Error Control
        if(name.length < minLength) {
            errors.add(FoundError(input, InputError.InsufficientLength))
            return null
        }
        else if(!name.trim().matches(Regex(nameRegex))) {
            errors.add(FoundError(input, InputError.BadFormat))
            return null
        }
        val list = name.uppercase().split(" ").filter { it.isNotEmpty() }
        for(i in list.indices) {
            val v = list[i].getFirstInnerVocal()
            if(v == null) {
                errors.add(FoundError(input, InputError.BadFormat))
                return null
            }
        }
        return list
    }
    errors.add(FoundError(input, InputError.EmptyInput))
    return  null
}
fun processDate(date : String, input: Int) : String
{
    if (!date.isEmpty()) {
        //Error Control
        if(!dateRegex.matches(date)) {
            errors.add(FoundError(input, InputError.InvalidDate))
            return "XXXXXX"
        }
        return date.replace("/","")
    }
    else errors.add(FoundError(input, InputError.EmptyInput))
    return  "XXXXX"
}

fun String.getFirstInnerVocal() : Char?
{
    for(i in this.indices)
    {
        if(this[i] in "AEIOU" && i != 0)
            return this[i]
    }
    return null
}