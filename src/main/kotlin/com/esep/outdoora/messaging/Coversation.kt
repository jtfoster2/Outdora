package com.esep.outdoora.messaging

data class Conversation(
    val messages: List<MessageDetail>
)

data class MessageDetail(
    val senderId: Long = 0L,
    val timestamp: String = "", // ISO 8601 format
    val content: String = ""
)