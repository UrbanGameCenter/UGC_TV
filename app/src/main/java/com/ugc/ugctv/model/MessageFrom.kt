package com.ugc.ugctv.model

class MessageFrom {
    var emitter: String
    var message: String
    private var date: String
    var isServerMessage : Boolean = false

    constructor(nickname: String, message: String, date : String) {
        this.emitter = nickname
        this.message = message
        this.date = date
    }

    fun setIsServerMessage(isServerMessage: Boolean): MessageFrom {
        this.isServerMessage = isServerMessage
        return this
    }

    fun getFormatedDate() : String{
        return date
    }


}