package com.esep.outdoora.messaging

import com.esep.outdoora.MyGlobalExceptionHandler
import com.esep.outdoora.user.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpSession
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate

@Controller
class ConversationController(
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository,
    private val messagingTemplate: SimpMessagingTemplate,
) {
    val logger = LoggerFactory.getLogger(ConversationController::class.java)
    @Transactional
    @GetMapping("/conversation/{user}/{recipient}")
    fun showConversation(@PathVariable user: String?, @PathVariable recipient: String?, model: Model): String {
        model.addAttribute("user", user)
        model.addAttribute("recipient", recipient)

        userRepository.findById(recipient?.toLong()!!).ifPresent {
            model.addAttribute("recipientName", it.email)
        }

        chatRepository.findChat(user?.toLong()!!, recipient.toLong())?.let {
            val mapper = ObjectMapper()
            val chatMessages = mapper.readValue(it.chat, Array<MessageDetail>::class.java).toList()
            model.addAttribute("chat", chatMessages)
        }
        return "messaging/conversation"
    }

    @GetMapping("/conversation")
    fun showConversationFromSubmit(@RequestParam user: String?, @RequestParam recipient: String?, model: Model): String {
        return "redirect:/conversation/$user/$recipient"
    }

    @GetMapping("/conversation/selector")
    fun showConversationSelector(
        model: Model,
        session: HttpSession,
    ): String {
        val userId = (session.getAttribute("userId") as Long)
        model.addAttribute(
            "conversations",
            chatRepository.findAll().filter {
                it.userAId == userId || it.userBId == userId
            }.map {
                if (it.userAId == userId) it.userBId else it.userAId
            }
        )
        model.addAttribute("userId", userId)

        return "messaging/conversationSelector"
    }

    @Transactional
    @MessageMapping("/chat/{userId}/{recipientId}")
    fun send(
        @DestinationVariable
        userId : Long,
        @DestinationVariable
        recipientId: Long,
        message: String,
        headerAccessor: SimpMessageHeaderAccessor
    ) {
        logger.info("Received message from $userId to $recipientId: $message")

        val userSessionId = headerAccessor.sessionAttributes?.get("userId") as? Long
        if (userSessionId != userId) {
            throw IllegalArgumentException("Sender ID does not match authenticated user")
        }
        val messageDetail = MessageDetail(userSessionId, LocalDate.now().toString(), message)
        val chat = chatRepository.findChat(userSessionId, recipientId)?.let {
            val chatMessages = it.chat
            val mapper = ObjectMapper()
            val chatMessageList = mapper.readValue(chatMessages, Array<MessageDetail>::class.java).toMutableList()
            chatMessageList.add(messageDetail)
            it.chat = mapper.writeValueAsString(chatMessageList)
            it
        } ?: run {
            val chatMessages = mutableListOf(messageDetail)
            val chat = Chat(
                userAId = userSessionId,
                userBId = recipientId,
                chat = ObjectMapper().writeValueAsString(chatMessages)
            )
            chat
        }
        chatRepository.save(chat)
        messagingTemplate.convertAndSend("/topic/chat/${userId}/${recipientId}", messageDetail)
        messagingTemplate.convertAndSend("/topic/chat/${recipientId}/${userId}", messageDetail)
//        messagingTemplate.convertAndSend("/app/chat/${userId}/${recipientId}", messageDetail)
//        messagingTemplate.convertAndSend("/app/chat/${recipientId}/${userId}", messageDetail)
    }
}