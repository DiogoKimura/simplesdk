package com.kimurashin.sdk.extension

import java.math.RoundingMode

val Float.roundedString
    get() = toBigDecimal().setScale(2, RoundingMode.FLOOR).toString()
