package com.esep.outdoora.activity_preferences

import com.esep.outdoora.user.User
import com.esep.outdoora.user.UserRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpSession
import org.junit.jupiter.api.Test
import org.springframework.ui.Model
import java.util.*


class ActivityPreferencesControllerTest {
    val activityPreferencesRepository = mockk<ActivityPreferencesRepository>()
    val userRepository = mockk<UserRepository>()
    val session = mockk<HttpSession>()
    val controller = ActivityPreferencesController(activityPreferencesRepository, userRepository)

    @Test
    fun updateExistingActivityPreferences(){
        every { session.getAttribute("userId") } returns 1010L
        val user = User(
            id = 1010,
            email = "testemail@gmail.com"
        )
        val existingPreferences = ActivityPreferences(
            id = 1L,
            skiing = true,
            backpacking = false,
            travel = true,
            hiking = false,
            holidate = true,
            user = user
        )

        every { activityPreferencesRepository.findByUserId(1010L) } returns existingPreferences
        every { userRepository.findById(1010L) } returns Optional.of(user)
        every { activityPreferencesRepository.save(any()) } answers { firstArg() }

        controller.updateActivityPreferencesDetails(skiing = false,backpacking = true,travel = false,hiking = true,holidate = false, session)

        val activityPreferencesCaptor = mutableListOf<ActivityPreferences>()
        verify { activityPreferencesRepository.save(capture(activityPreferencesCaptor)) }
        val updatedPreferences = activityPreferencesCaptor[0]

        updatedPreferences.id shouldBe 1L
        updatedPreferences.skiing shouldBe false
        updatedPreferences.backpacking shouldBe true
        updatedPreferences.travel shouldBe false
        updatedPreferences.hiking shouldBe true
        updatedPreferences.holidate shouldBe false
        updatedPreferences.user shouldBe user
    }

    @Test
    fun createActivityPreferences() {
        val user = User(
            id = 1010,
            email = "testemail@gmail.com"
        )

        every { session.getAttribute("userId") } returns 1010L
        every { activityPreferencesRepository.findByUserId(1010L) } returns null
        every { userRepository.findById(1010L) } returns Optional.of(user)
        every { activityPreferencesRepository.save(any()) } answers { firstArg() }

        controller.updateActivityPreferencesDetails(skiing = false,backpacking = false,travel = false,hiking = false,holidate = false, session)

        val activityPreferencesCaptor = mutableListOf<ActivityPreferences>()
        verify { activityPreferencesRepository.save(capture(activityPreferencesCaptor)) }
        val newPreferences = activityPreferencesCaptor[0]

        newPreferences.id shouldBe null
        newPreferences.skiing shouldBe false
        newPreferences.backpacking shouldBe false
        newPreferences.travel shouldBe false
        newPreferences.hiking shouldBe false
        newPreferences.holidate shouldBe false
        newPreferences.user shouldBe user
    }

    @Test
    fun viewActivityPreferencesNotCreated(){
        val activityPreferencesRepository = mockk<ActivityPreferencesRepository>()
        val userRepository = mockk<UserRepository>()
        val session = mockk<HttpSession>()
        val model = mockk<Model>(relaxed = true)

        val controller = ActivityPreferencesController(activityPreferencesRepository, userRepository)

        every { session.getAttribute("userId") } returns 1010L
        every { activityPreferencesRepository.findByUserId(1010L) } returns null

        val result = controller.viewActivityPreferences(model, session)

        result shouldBe "activityPreferences"
        verify(exactly = 0) { model.addAttribute("skiing", any()) }
        verify(exactly = 0) { model.addAttribute("hiking", any()) }
        verify(exactly = 0) { model.addAttribute("backpacking", any()) }
        verify(exactly = 0) { model.addAttribute("travel", any()) }
        verify(exactly = 0) { model.addAttribute("holidate", any()) }
    }

    @Test
    fun viewActivityPreferences(){
        val activityPreferencesRepository = mockk<ActivityPreferencesRepository>()
        val userRepository = mockk<UserRepository>()
        val session = mockk<HttpSession>()
        val model = mockk<Model>(relaxed = true)

        val controller = ActivityPreferencesController(activityPreferencesRepository, userRepository)

        val user = User(
            id = 1010,
            email = "testemail@gmail.com"
        )
        val existingPreferences = ActivityPreferences(
            id = 1L,
            skiing = true,
            backpacking = false,
            travel = true,
            hiking = false,
            holidate = true,
            user = user
        )

        every { session.getAttribute("userId") } returns 1010L
        every { activityPreferencesRepository.findByUserId(1010L) } returns existingPreferences

        val result = controller.viewActivityPreferences(model, session)

        result shouldBe "activityPreferences"
        verify{ model.addAttribute("preferences",
            ActivityPreferences(id = existingPreferences.id, skiing = existingPreferences.skiing,
                backpacking = existingPreferences.backpacking, travel = existingPreferences.travel,
            hiking = existingPreferences.hiking, holidate = existingPreferences.holidate, user = existingPreferences.user))}
    }

    @Test
    fun editActivityPreferences(){
        val activityPreferencesRepository = mockk<ActivityPreferencesRepository>()
        val userRepository = mockk<UserRepository>()
        val session = mockk<HttpSession>()
        val model = mockk<Model>(relaxed = true)

        val controller = ActivityPreferencesController(activityPreferencesRepository, userRepository)

        val user = User(
            id = 1010,
            email = "testemail@gmail.com"
        )
        val existingPreferences = ActivityPreferences(
            id = 1L,
            skiing = true,
            backpacking = false,
            travel = true,
            hiking = false,
            holidate = true,
            user = user
        )

        every { session.getAttribute("userId") } returns 1010L
        every { activityPreferencesRepository.findByUserId(1010L) } returns existingPreferences

        val result = controller.editActivityPreferences(model, session)

        result shouldBe "editActivityPreferences"
        verify{ model.addAttribute("preferences",
            ActivityPreferences(id = existingPreferences.id, skiing = existingPreferences.skiing,
                backpacking = existingPreferences.backpacking, travel = existingPreferences.travel,
                hiking = existingPreferences.hiking, holidate = existingPreferences.holidate, user = existingPreferences.user))}
    }
}