package com.ugc.ugctv.model

class UgcError(val type : ErrorType, val message :String) {
}

enum class ErrorType() {
    BACKEND_ERROR,
    NETWORK_ERROR,
    UNEXCEPTED_ERROR
}