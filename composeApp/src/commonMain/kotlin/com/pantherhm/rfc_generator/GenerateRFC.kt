package com.pantherhm.rfc_generator

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.toLowerCase


class GenerateRFC {
    companion object {
        var errors = mutableListOf<FoundError>()

        fun calculate(
            apellidos: MutableState<String>,
            nombres: MutableState<String>,
            fechaNac: MutableState<String>
        ): String {

            errors = mutableListOf<FoundError>()
            var rfc = ""
            //Process names
            val a = processName(apellidos.value, 0)
            val n = processName(nombres.value, 1)
            val f = processDate(fechaNac.value, 2)

            //Build rfc
            if(a == null) rfc += "XXX"
            else
            {
                if(a[0].length > 1) {
                    rfc += "${a[0][0]}${a[0].getFirstInnerVocal() ?: 'X'}"
                    if (a.size > 1) rfc += a[1][0]
                    else rfc += "X"
                }
            }

            if(n == null) rfc += "X"
            else {
                var useName = n[0]
                if(useName in StaticLists.commonNames && n.size > 1)
                    useName = n[1]
                if(rfc + useName[0] in StaticLists.badStart)
                    rfc += "X"
                else rfc += useName[0]
            }

            rfc += f

            return rfc
        }
    }
}