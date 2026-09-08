package com.example.vehiclecare.core.common

import android.content.Context
import androidx.annotation.StringRes

sealed interface UiText {
    data class Dynamic(val value: String) : UiText
    data class Resource(@StringRes val id: Int, val args: List<Any> = emptyList()) : UiText
    fun asString(context: Context): String = when (this) {
        is Dynamic -> value
        is Resource -> context.getString(id, *args.toTypedArray())
    }
}
