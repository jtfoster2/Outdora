package com.esep.outdoora.messaging

import com.esep.outdoora.user.User
import com.esep.outdoora.user.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpSession
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.ui.Model
import java.util.*

class ConversationControllerUnitTest {
    @Test
    fun testShowConversation() {
        val user = "1"
        val recipient = "2"
        val model = mockk<Model>()

        val chatRepository = mockk<ChatRepository>()
        val userRepository = mockk<UserRepository>()
        val messagingTemplate = mockk<SimpMessagingTemplate>()

        val conversationController = ConversationController(chatRepository, userRepository, messagingTemplate)

        every { model.addAttribute("user", "1") } returns model
        every { model.addAttribute("recipient", "2") } returns model

        every { userRepository.findById(2) } returns Optional.of(
            User(id = 2, email = "blah@gmail.com")
        )

        every { model.addAttribute("recipientName", "blah@gmail.com" ) } returns model

        every { chatRepository.findByUserAIdAndUserBId(1, 2) } returns Chat(
            id = 1,
            userAId = 1,
            userBId = 2,
            chat = "[]"
        )

        every { model.addAttribute("chat", emptyList<Unit>()) } answers { model }

        val result = conversationController.showConversation(user, recipient, model)

        assertEquals("messaging/conversation", result)
    }

    @Test
    fun testShowConversation_noExistingChats() {
        val user = "1"
        val recipient = "2"
        val model = mockk<Model>()

        val chatRepository = mockk<ChatRepository>()
        val userRepository = mockk<UserRepository>()
        val messagingTemplate = mockk<SimpMessagingTemplate>()

        val conversationController = ConversationController(chatRepository, userRepository, messagingTemplate)

        every { model.addAttribute("user", "1") } returns model
        every { model.addAttribute("recipient", "2") } returns model

        every { userRepository.findById(2) } returns Optional.of(
            User(id = 2, email = "blah@gmail.com")
        )

        every { model.addAttribute("recipientName", "blah@gmail.com" ) } returns model

        every { chatRepository.findByUserAIdAndUserBId(1, 2) } returns null
        every { chatRepository.findByUserAIdAndUserBId(2, 1) } returns null

        val result = conversationController.showConversation(user, recipient, model)

        verify(exactly = 0) { model.addAttribute("chat", emptyList<Unit>()) }

        assertEquals("messaging/conversation", result)
    }

    @Test
    fun testShowConversationFromSubmit() {
        val user = "1"
        val recipient = "2"
        val model = mockk<Model>()


        val chatRepository = mockk<ChatRepository>()
        val userRepository = mockk<UserRepository>()
        val messagingTemplate = mockk<SimpMessagingTemplate>()

        val conversationController = ConversationController(chatRepository, userRepository, messagingTemplate)

        val result = conversationController.showConversationFromSubmit(user, recipient, model)

        assertEquals("redirect:/conversation/1/2", result)
    }

    @Test
    fun testShowConversationSelector() {
        val model = mockk<Model>()
        val session = mockk<HttpSession>()

        val chatRepository = mockk<ChatRepository>()
        val userRepository = mockk<UserRepository>()
        val messagingTemplate = mockk<SimpMessagingTemplate>()

        val conversationController = ConversationController(chatRepository, userRepository, messagingTemplate)

        every { session.getAttribute("userId") } returns 1

        every { model.addAttribute("conversations", emptyList<Unit>()) } returns model
        every { model.addAttribute("userId", 1L) } returns model

        every { chatRepository.findAll() } returns emptyList()

        val result = conversationController.showConversationSelector(model, session)

        assertEquals("messaging/conversationSelector", result)
    }

    @Test
    fun testShowConversationSelector_existingConvos() {
        val model = mockk<Model>()
        val session = mockk<HttpSession>()

        val chatRepository = mockk<ChatRepository>()
        val userRepository = mockk<UserRepository>()
        val messagingTemplate = mockk<SimpMessagingTemplate>()

        val conversationController = ConversationController(chatRepository, userRepository, messagingTemplate)

        every { session.getAttribute("userId") } returns 1

        every { model.addAttribute("userId", 1L) } returns model

        every { chatRepository.findAll() } returns listOf(
            Chat(
                id = 1,
                userAId = 1,
                userBId = 2,
                chat = "[]"
            )
        )
        every { model.addAttribute("conversations", listOf(2L)) } returns model
        every { model.addAttribute("conversations", listOf(1L)) } returns model

        val result = conversationController.showConversationSelector(model, session)

        assertEquals("messaging/conversationSelector", result)
    }

    @Test
    fun testSend() {
        val userId = 1L
        val recipientId = 2L
        val message = "Hello"
        val headerAccessor = mockk<SimpMessageHeaderAccessor>()

        val chatRepository = mockk<ChatRepository>()
        val userRepository = mockk<UserRepository>()
        val messagingTemplate = mockk<SimpMessagingTemplate>()

        val conversationController = ConversationController(chatRepository, userRepository, messagingTemplate)

        every { headerAccessor.sessionAttributes } returns mutableMapOf("userId" to userId as Any)
        every { chatRepository.findByUserAIdAndUserBId(any(), any()) } returns null
        every { chatRepository.save(any()) } returns Chat(
            id = 1,
            userAId = userId,
            userBId = recipientId,
            chat = "[]"
        )
        every { messagingTemplate.convertAndSend("/topic/chat/1/2", any<MessageDetail>()) } returns Unit
        every { messagingTemplate.convertAndSend("/topic/chat/2/1", any<MessageDetail>()) } returns Unit

        conversationController.send(userId, recipientId, message, headerAccessor)

        verify { messagingTemplate.convertAndSend("/topic/chat/1/2", any<MessageDetail>()) }
        verify { messagingTemplate.convertAndSend("/topic/chat/2/1", any<MessageDetail>()) }
    }

    @Test
    fun testSend_UserMismatch() {
        val userId = 1L
        val recipientId = 2L
        val message = "Hello"
        val headerAccessor = mockk<SimpMessageHeaderAccessor>()

        val chatRepository = mockk<ChatRepository>()
        val userRepository = mockk<UserRepository>()
        val messagingTemplate = mockk<SimpMessagingTemplate>()

        val conversationController = ConversationController(chatRepository, userRepository, messagingTemplate)

        every { headerAccessor.sessionAttributes } returns mutableMapOf("userId" to userId as Any)

        shouldThrow<IllegalArgumentException> {
            conversationController.send(
                3L,
                recipientId,
                message,
                headerAccessor
            )
        }
    }
}