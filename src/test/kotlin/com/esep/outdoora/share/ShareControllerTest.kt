package com.esep.outdoora.share

import com.esep.outdoora.profile.ShareController
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpSession
import org.junit.jupiter.api.Test
import org.springframework.ui.Model

class ShareControllerTest {
    val controller = ShareController()
    @Test
    fun `test shareFacebook`(){
        val model = mockk<Model>(relaxed = true)

        val author = "testAuthor"
        val message = "testMessage"

        val getShareResult = controller.getShare("testAuthor", "testMessage", model)

        getShareResult shouldBe "shareTemplate"
        verify { model.addAttribute("author", author)}
        verify { model.addAttribute("text", message)}
    }
    @Test
    fun `test shareTwitter`(){
        val model = mockk<Model>(relaxed = true)

        val author = "testAuthor"
        val message = "testMessage"

        val getShareResult = controller.getShare("testAuthor", "testMessage", model)

        getShareResult shouldBe "shareTemplate"
        verify { model.addAttribute("author", author)}
        verify { model.addAttribute("text", message)}
    }
    @Test
    fun `test shareLinkedIn`(){
        val model = mockk<Model>(relaxed = true)

        val author = "testAuthor"
        val message = "testMessage"

        val getShareResult = controller.getShare("testAuthor", "testMessage", model)

        getShareResult shouldBe "shareTemplate"
        verify { model.addAttribute("author", author)}
        verify { model.addAttribute("text", message)}
    }
    @Test
    fun `test shareWhatsApp`(){
        val model = mockk<Model>(relaxed = true)

        val author = "testAuthor"
        val message = "testMessage"

        val getShareResult = controller.getShare("testAuthor", "testMessage", model)

        getShareResult shouldBe "shareTemplate"
        verify { model.addAttribute("author", author)}
        verify { model.addAttribute("text", message)}
    }
    @Test
    fun `test shareTelegram`(){
        val model = mockk<Model>(relaxed = true)

        val author = "testAuthor"
        val message = "testMessage"

        val getShareResult = controller.getShare("testAuthor", "testMessage", model)

        getShareResult shouldBe "shareTemplate"
        verify { model.addAttribute("author", author)}
        verify { model.addAttribute("text", message)}

    }

}