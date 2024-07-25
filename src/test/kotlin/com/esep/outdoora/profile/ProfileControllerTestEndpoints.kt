package com.esep.outdoora.profile

import com.esep.outdoora.IntegrationTest
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProfileControllerTestEndpoints(
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
    fun `test viewProfile endpoint`() {
        profileRepository.save(Profile(name = "oldName", age = 25, description = "old description", user = testUsers[0]))

        mockMvc.perform(get("/profile").session(session))
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("""<p>Welcome to your profile!</p>""")))
            .andExpect(content().string(containsString("""<p>Your name is: <span>oldName</span></p>""")))
            .andExpect(content().string(containsString("""<p>Your age is: <span>25</span></p>""")))
            .andExpect(content().string(containsString("""<p>Your description is: <span>old description</span></p>""")))
            .andExpect(content().string(containsString("""<a href="/editProfile">Edit Profile</a>""")))
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
            .andExpect(status().is3xxRedirection)
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