package com.esep.outdoora.config

import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.support.MessageHeaderAccessor

@Component
class UserSubscriptionInterceptor : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)!!
        if (StompCommand.SUBSCRIBE == accessor.command) {
            val userId = accessor.sessionAttributes?.get("userId") as? Long
            val destination = accessor.destination
            if (userId == null) {
                return null // This effectively denies the subscription request
            }

            // Assuming the destination should contain the userId for valid subscriptions
            if (destination != null && !destination.contains("/$userId/")) {
                return null // This effectively denies the subscription request
            }
        }
        return message
    }
}