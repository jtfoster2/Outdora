package com.esep.outdoora.profile

import com.esep.outdoora.user.User
import com.esep.outdoora.user.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpSession
import org.junit.jupiter.api.Test
import org.springframework.ui.Model
import org.springframework.web.multipart.MultipartFile
import java.security.Principal
import java.util.*

class ProfileControllerTest {
    val profileRepository = mockk<ProfileRepository>()
    val userRepository = mockk<UserRepository>()
    val session = mockk<HttpSession>()

    val controller = ProfileController(profileRepository, userRepository)

    @Test
    fun `Update profile details bombs if the user repository comes back null`() {
        every { session.getAttribute("userId") } returns 1010L
        every { userRepository.findById(1010L) } returns Optional.empty()

        // create a mock multipart file
        val multipartFile = mockk<MultipartFile>()
        shouldThrow<Exception> {
            controller.updateProfileDetails("newName", 30, "new description", multipartFile, session)
        }

        verify(exactly = 0) { profileRepository.save(any()) }
    }

    @Test
    fun `Update existing profile details`() {
        every { session.getAttribute("userId") } returns 1010L

        val user = User(
            id = 1010,
            email = "testemail@gmail.com"
        )
        val existingProfile = Profile(
            id = 1L,
            name = "oldName",
            age = 25,
            description = "old description",
            user = user
        )

        every { profileRepository.findByUserId(1010L) } returns existingProfile
        every { userRepository.findById(1010L) } returns Optional.of(user)
        every { profileRepository.save(any()) } answers { firstArg() }

        val multipartFile = mockk<MultipartFile>()
        every { multipartFile.bytes } returns "image".toByteArray()

        controller.updateProfileDetails("newName", 30, "new description", multipartFile, session)

        val profileCaptor = mutableListOf<Profile>()
        verify { profileRepository.save(capture(profileCaptor)) }
        val updatedProfile = profileCaptor[0]

        updatedProfile.id shouldBe 1L
        updatedProfile.name shouldBe "newName"
        updatedProfile.age shouldBe 30
        updatedProfile.description shouldBe "new description"
        updatedProfile.user shouldBe user
    }

    @Test
    fun `create profile when it doesn't exist`() {
        val user = User(
            id = 1010,
            email = "testemail@gmail.com"
        )

        every { session.getAttribute("userId") } returns 1010L
        every { profileRepository.findByUserId(1010L) } returns null
        every { userRepository.findById(1010L) } returns Optional.of(user)
        every { profileRepository.save(any()) } answers { firstArg() }

        val multipartFile = mockk<MultipartFile>()
        every { multipartFile.bytes } returns "image".toByteArray()
        controller.updateProfileDetails("newName", 30, "new description", multipartFile, session)

        val profileCaptor: MutableList<Profile> = mutableListOf()
        verify { profileRepository.save(capture(profileCaptor)) }
        val newProfile: Profile = profileCaptor[0]

        newProfile.id shouldBe null
        newProfile.name shouldBe "newName"
        newProfile.age shouldBe 30
        newProfile.description shouldBe "new description"
        newProfile.user.email shouldBe "testemail@gmail.com"
    }

    @Test
    fun `test viewProfile - profile doesn't exist`() {
        val profileRepository = mockk<ProfileRepository>()
        val userRepository = mockk<UserRepository>()
        val session = mockk<HttpSession>()
        val model = mockk<Model>(relaxed = true)

        val controller = ProfileController(profileRepository, userRepository)

        every { session.getAttribute("userId") } returns 1010L
        every { profileRepository.findByUserId(1010L) } returns null

        val result = controller.viewProfile(model, session)

        result shouldBe "profile"
        verify { model.addAttribute("profileExists", false) }
        verify(exactly = 0) { model.addAttribute("name", any()) }
        verify(exactly = 0) { model.addAttribute("age", any()) }
        verify(exactly = 0) { model.addAttribute("description", any()) }
    }

    @Test
    fun `test viewProfile`() {
        // Arrange
        val profileRepository = mockk<ProfileRepository>()
        val userRepository = mockk<UserRepository>()
        val session = mockk<HttpSession>()
        val model = mockk<Model>(relaxed = true)

        val controller = ProfileController(profileRepository, userRepository)

        val user = User(
            id = 1010,
            email = "testemail@gmail.com"
        )
        val existingProfile = Profile(
            id = 1L,
            name = "oldName",
            age = 25,
            description = "old description",
            image = "image".toByteArray(),
            user = user
        )

        every { session.getAttribute("userId") } returns 1010L
        every { profileRepository.findByUserId(1010L) } returns existingProfile

        // Act
        val result = controller.viewProfile(model, session)

        // Assert
        result shouldBe "profile"
        verify { model.addAttribute("profileExists", true) }
        verify { model.addAttribute("name", existingProfile.name) }
        verify { model.addAttribute("age", existingProfile.age) }
        verify { model.addAttribute("description", existingProfile.description) }
    }

    @Test
    fun `test editProfile - profile doesn't exist`() {
        val profileRepository = mockk<ProfileRepository>()
        val userRepository = mockk<UserRepository>()
        val session = mockk<HttpSession>()
        val model = mockk<Model>(relaxed = true)

        val controller = ProfileController(profileRepository, userRepository)

        every { session.getAttribute("userId") } returns 1010L
        every { profileRepository.findByUserId(1010L) } returns null

        val result = controller.editProfile(model, session)

        result shouldBe "editProfile"
        verify { model.addAttribute("profileExists", false) }
        verify(exactly = 0) { model.addAttribute("name", any()) }
        verify(exactly = 0) { model.addAttribute("age", any()) }
        verify(exactly = 0) { model.addAttribute("description", any()) }
    }

    @Test
    fun `test editProfile`() {
        // Arrange
        val profileRepository = mockk<ProfileRepository>()
        val userRepository = mockk<UserRepository>()
        val session = mockk<HttpSession>()
        val model = mockk<Model>(relaxed = true)

        val controller = ProfileController(profileRepository, userRepository)

        val user = User(
            id = 1010,
            email = "testemail@gmail.com"
        )
        val existingProfile = Profile(
            id = 1L,
            name = "oldName",
            age = 25,
            description = "old description",
            user = user
        )

        every { session.getAttribute("userId") } returns 1010L
        every { profileRepository.findByUserId(1010L) } returns existingProfile

        // Act
        val result = controller.editProfile(model, session)

        // Assert
        result shouldBe "editProfile"
        verify { model.addAttribute("profileExists", true) }
        verify { model.addAttribute("name", existingProfile.name) }
        verify { model.addAttribute("age", existingProfile.age) }
        verify { model.addAttribute("description", existingProfile.description) }
    }
}