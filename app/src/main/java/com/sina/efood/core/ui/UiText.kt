package com.sina.efood.core.ui

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class DynamicString(val values: String) : UiText()

    class StaticString(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> values
            is StaticString -> context.getString(id, *args)
        }
    }
}