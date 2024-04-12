package com.sina.efood.core.errors

sealed interface InputErrors : Error {
    enum class PasswordError : Error {
        TOO_SHORT,
        NO_UPPERCASE,
        NO_DIGITS
    }
}