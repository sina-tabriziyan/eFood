package com.sina.efood.core.errors

sealed interface DataError : Error {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUEST,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN,
    }

    enum class Local : DataError {
        DISK_FULL,
        DiskIO,
        DataCorruption,
        OperationNotPermitted, InsufficientResources, OutOfMemory, Deadlock, Unknown
    }
}