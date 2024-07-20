package com.esep.outdoora.profile

import com.esep.outdoora.IntegrationTest
import com.esep.outdoora.user.User
import com.esep.outdoora.user.UserRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.ui.Model
import org.springframework.web.multipart.MultipartFile
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerTestDb(
    @Autowired val profileRepository: ProfileRepository,
    @Autowired val profileController: ProfileController,
) : IntegrationTest() {
    @Test
    fun `test updateProfileDetails`() {
        // create the user and then profile
        val user = userRepository.save(User(email = "blah"))
        profileRepository.save(Profile(name = "oldName", age = 25, description = "old description", user = user))
        // mock the session
        val session = mockk<HttpSession>()
        every { session.getAttribute("userId") } returns user.id
        // then call the updateProfileDetails method
        val multipartFile = mockk<MultipartFile>().also {
            every { it.bytes } returns "image".toByteArray()
        }
        profileController.updateProfileDetails("newName", 30, "new description", multipartFile, session)
        // then check that the profile is different in the repository
        val profile = profileRepository.findByUserId(user.id!!)
        profile!!.name shouldBe "newName"
        profile.age shouldBe 30
        profile.description shouldBe "new description"
    }

    @Test
    fun `test viewProfile`() {
        // create the user and then profile
        val user = userRepository.save(User(email = "blah"))
        profileRepository.save(Profile(name = "oldName", age = 25, description = "old description", user = user))
        // mock the session
        val session = mockk<HttpSession>()
        every { session.getAttribute("userId") } returns user.id
        // then call the viewProfile method
        val model = mockk<Model>(relaxed = true)

        profileController.viewProfile(model, session)

        // check the model
        verify { model.addAttribute("profileExists", true) }
        verify { model.addAttribute("name", "oldName") }
        verify { model.addAttribute("age", 25) }
        verify { model.addAttribute("description", "old description") }
    }

    @Test
    fun `test editProfile`() {
        // create the user and then profile
        val user = userRepository.save(User(email = "blah"))
        profileRepository.save(
            Profile(
                name = "oldName",
                age = 25,
                description = "old description",
                image = null,
                user = user
            )
        )
        // mock the session
        val session = mockk<HttpSession>()
        every { session.getAttribute("userId") } returns user.id
        // then call the viewProfile method
        val model = mockk<Model>(relaxed = true)

        profileController.viewProfile(model, session)

        // check the model
        verify { model.addAttribute("profileExists", true) }
        verify { model.addAttribute("name", "oldName") }
        verify { model.addAttribute("age", 25) }
        verify { model.addAttribute("description", "old description") }
    }
}