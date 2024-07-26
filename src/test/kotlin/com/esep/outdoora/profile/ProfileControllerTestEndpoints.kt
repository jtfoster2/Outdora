package com.esep.outdoora.profile

import com.esep.outdoora.IntegrationTest
import io.mockk.mockk
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
import org.springframework.ui.Model

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ProfileControllerTestEndpoints(
    @Autowired val mockMvc: MockMvc,
    @Autowired val profileRepository: ProfileRepository,
    @Autowired val shareController: ShareController,
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

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `test share endpoint`() {
        val author = "testAuthor"
        val text = "testText"
        val model = mockk<Model>(relaxed = true)
        shareController.getShare(author, text, model)
        mockMvc.perform(get("/share?author=${author}&text=${text}").session(session))
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isOk)
            .andExpect(content().string(containsString("""<div class="popup-header">Share Text</div>""")))
            .andExpect(content().string(containsString("""<p>Author: <span>testAuthor</span></p>""")))
            .andExpect(content().string(containsString("""<p>Text: <span>testText</span></p>""")))
            .andExpect(content().string(containsString("<a href=\"https://www.facebook.com/sharer/sharer.php?u=https://outdoora.com&quot;e=testText - testAuthor\" class=\"btn-share\" target=\"_blank\">Share</a>")))
            .andExpect(content().string(containsString("<a href=\"https://twitter.com/intent/tweet?text=testText - testAuthor\" class=\"btn-share\" target=\"_blank\">Share</a>")))
            .andExpect(content().string(containsString("<a href=\"https://www.linkedin.com/sharing/share-offsite/?url=https://outdoora.com&amp;title=testText&amp;summary=testText - testAuthor\" class=\"btn-share\" target=\"_blank\">Share</a>")))
            .andExpect(content().string(containsString("<a href=\"https://api.whatsapp.com/send?text=testText - testAuthor\" class=\"btn-share\" target=\"_blank\">Share</a>")))
            .andExpect(content().string(containsString("<a href=\"https://t.me/share/url?url=https://outdoora.com&amp;text=testText - testAuthor\" class=\"btn-share\" target=\"_blank\">Share</a>")))

    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `test facebook endpoint`() {
        mockMvc.perform(get("https://www.facebook.com/sharer/sharer.php?u=https://outdoora.com%22e=Text%20-%20testAuthor")
            .session(session))
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `test twitter endpoint`() {
        mockMvc.perform(get("https://twitter.com/intent/tweet?text=Text%20-%20testAuthor")
            .session(session))
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `test linkedin endpoint`() {
        mockMvc.perform(get("https://www.linkedin.com/sharing/share-offsite/?url=https://outdoora.com&title=Text&summary=Text%20-%20testAuthor")
            .session(session))
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `test whatsapp endpoint`() {
        mockMvc.perform(get("https://api.whatsapp.com/send?text=Text%20-%20testAuthor")
            .session(session))
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isOk)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `test telegram endpoint`() {
        mockMvc.perform(get("https://t.me/share/url?url=https://outdoora.com&text=Text%20-%20testAuthor")
            .session(session))
            .andDo { println(it.response.contentAsString) }
            .andExpect(status().isOk)
    }
}