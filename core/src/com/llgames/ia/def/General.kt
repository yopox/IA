package com.llgames.ia.def

import java.text.SimpleDateFormat
import java.util.*

object General {

    val BUILD_NB: String

    init {
        val formatter = SimpleDateFormat("yy.MM.dd", Locale.FRANCE)
        val today = Date()
        BUILD_NB = formatter.format(today)
    }

}