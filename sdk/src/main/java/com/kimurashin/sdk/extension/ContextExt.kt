package com.kimurashin.sdk.extension

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToastLong(@StringRes res: Int) =
    Toast.makeText(this, resources.getString(res), Toast.LENGTH_LONG).show()

fun Context.showToastShort(@StringRes res: Int) =
    Toast.makeText(this, resources.getString(res), Toast.LENGTH_SHORT).show()