package com.kimurashin.sdk.extension

import java.math.RoundingMode

val Double.roundedString
    get() = toBigDecimal().setScale(2, RoundingMode.FLOOR).toString()