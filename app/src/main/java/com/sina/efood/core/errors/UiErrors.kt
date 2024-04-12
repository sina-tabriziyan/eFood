package com.sina.efood.core.errors

import com.sina.efood.R
import com.sina.efood.core.ui.UiText

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StaticString(R.string.this_is_full)
        DataError.Network.REQUEST_TIMEOUT -> UiText.StaticString(R.string.request_timeout)
        DataError.Network.TOO_MANY_REQUEST -> UiText.StaticString(R.string.too_many_requests)
        DataError.Network.NO_INTERNET -> UiText.StaticString(R.string.not_network)
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StaticString(R.string.payload_too_large)
        DataError.Network.SERVER_ERROR -> UiText.StaticString(R.string.server_error)
        DataError.Network.SERIALIZATION -> UiText.StaticString(R.string.serialization_error)
        DataError.Network.UNKNOWN -> UiText.StaticString(R.string.unknown_error)
        DataError.Local.DiskIO -> UiText.StaticString(R.string.unknown_error)
        DataError.Local.DataCorruption -> UiText.StaticString(R.string.unknown_error)
        DataError.Local.OperationNotPermitted -> UiText.StaticString(R.string.unknown_error)
        DataError.Local.InsufficientResources -> UiText.StaticString(R.string.unknown_error)
        DataError.Local.OutOfMemory -> UiText.StaticString(R.string.unknown_error)
        DataError.Local.Deadlock -> UiText.StaticString(R.string.unknown_error)
        DataError.Local.Unknown -> UiText.StaticString(R.string.unknown_error)
    }
}
