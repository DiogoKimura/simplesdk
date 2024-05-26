package com.kimurashin.sdk.extension

import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat

fun Resources.getDrawableRes(@DrawableRes res: Int, theme: Resources.Theme? = null) =
    ResourcesCompat.getDrawable(this, res, theme)