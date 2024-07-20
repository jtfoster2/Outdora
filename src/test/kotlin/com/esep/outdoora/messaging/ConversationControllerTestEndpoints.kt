package com.esep.outdoora.messaging

import com.esep.outdoora.IntegrationTest
import com.esep.outdoora.profile.Profile
import com.esep.outdoora.profile.ProfileRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ConversationControllerTestEndpoints(
    @Autowired val mockMvc: MockMvc,
    @Autowired val profileRepository: ProfileRepository,
) : IntegrationTest() {
    lateinit var session: MockHttpSession

    @BeforeEach
    fun setup() {
        session = MockHttpSession()
        session.setAttribute("userId", testUsers[0].id)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `test showConversation endpoint`() {
        mockMvc.perform(get("/conversation/1/2")
            .session(session))
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `test conversation request params redirect`() {
        mockMvc.perform(get("/conversation?user=1&recipient=2")
            .session(session))
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `test showConversationSelector endpoint`() {
        mockMvc.perform(get("/conversation/selector")
            .session(session))
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isOk)
    }

}