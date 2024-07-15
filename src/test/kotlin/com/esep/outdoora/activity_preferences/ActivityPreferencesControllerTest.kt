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
            skiingSkillLevel = SkillLevel.ADVANCED,
            skiingAttitude = Attitude.CHALLENGE,
            backpackingSkillLevel = SkillLevel.ADVANCED,
            backpackingAttitude = Attitude.CHALLENGE,
            travelSkillLevel = SkillLevel.ADVANCED,
            travelAttitude = Attitude.CHALLENGE,
            hikingSkillLevel = SkillLevel.ADVANCED,
            hikingAttitude = Attitude.CHALLENGE,
            holidateSkillLevel = SkillLevel.ADVANCED,
            holidateAttitude = Attitude.CHALLENGE,
            user = user
        )

        every { activityPreferencesRepository.findByUserId(1010L) } returns existingPreferences
        every { userRepository.findById(1010L) } returns Optional.of(user)
        every { activityPreferencesRepository.save(any()) } answers { firstArg() }

        controller.updateActivityPreferencesDetails(SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, session)

        val activityPreferencesCaptor = mutableListOf<ActivityPreferences>()
        verify { activityPreferencesRepository.save(capture(activityPreferencesCaptor)) }
        val updatedPreferences = activityPreferencesCaptor[0]

        updatedPreferences.id shouldBe 1L
        updatedPreferences.skiingSkillLevel shouldBe SkillLevel.BEGINNER
        updatedPreferences.skiingAttitude shouldBe Attitude.IMMERSION
        updatedPreferences.backpackingSkillLevel shouldBe SkillLevel.BEGINNER
        updatedPreferences.backpackingAttitude shouldBe Attitude.IMMERSION
        updatedPreferences.travelSkillLevel shouldBe SkillLevel.BEGINNER
        updatedPreferences.travelAttitude shouldBe Attitude.IMMERSION
        updatedPreferences.hikingSkillLevel shouldBe SkillLevel.BEGINNER
        updatedPreferences.hikingAttitude shouldBe Attitude.IMMERSION
        updatedPreferences.holidateSkillLevel shouldBe SkillLevel.BEGINNER
        updatedPreferences.holidateAttitude shouldBe Attitude.IMMERSION


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

        controller.updateActivityPreferencesDetails(SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, session)

        val activityPreferencesCaptor = mutableListOf<ActivityPreferences>()
        verify { activityPreferencesRepository.save(capture(activityPreferencesCaptor)) }
        val newPreferences = activityPreferencesCaptor[0]

        newPreferences.id shouldBe null
        newPreferences.skiingSkillLevel shouldBe SkillLevel.BEGINNER
        newPreferences.skiingAttitude shouldBe Attitude.IMMERSION
        newPreferences.backpackingSkillLevel shouldBe SkillLevel.BEGINNER
        newPreferences.backpackingAttitude shouldBe Attitude.IMMERSION
        newPreferences.travelSkillLevel shouldBe SkillLevel.BEGINNER
        newPreferences.travelAttitude shouldBe Attitude.IMMERSION
        newPreferences.hikingSkillLevel shouldBe SkillLevel.BEGINNER
        newPreferences.hikingAttitude shouldBe Attitude.IMMERSION
        newPreferences.holidateSkillLevel shouldBe SkillLevel.BEGINNER
        newPreferences.holidateAttitude shouldBe Attitude.IMMERSION
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
        verify(exactly = 0) { model.addAttribute("activity", ActivityPreferences(1010L, SkillLevel.ADVANCED, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, User())) }
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
            skiingSkillLevel = SkillLevel.ADVANCED,
            skiingAttitude = Attitude.CHALLENGE,
            backpackingSkillLevel = SkillLevel.ADVANCED,
            backpackingAttitude = Attitude.CHALLENGE,
            travelSkillLevel = SkillLevel.ADVANCED,
            travelAttitude = Attitude.CHALLENGE,
            hikingSkillLevel = SkillLevel.ADVANCED,
            hikingAttitude = Attitude.CHALLENGE,
            holidateSkillLevel = SkillLevel.ADVANCED,
            holidateAttitude = Attitude.CHALLENGE,
            user = user
        )

        every { session.getAttribute("userId") } returns 1010L
        every { activityPreferencesRepository.findByUserId(1010L) } returns existingPreferences

        val result = controller.viewActivityPreferences(model, session)

        result shouldBe "activityPreferences"
        verify{ model.addAttribute("activity", any())}
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
            skiingSkillLevel = SkillLevel.ADVANCED,
            skiingAttitude = Attitude.CHALLENGE,
            backpackingSkillLevel = SkillLevel.ADVANCED,
            backpackingAttitude = Attitude.CHALLENGE,
            travelSkillLevel = SkillLevel.ADVANCED,
            travelAttitude = Attitude.CHALLENGE,
            hikingSkillLevel = SkillLevel.ADVANCED,
            hikingAttitude = Attitude.CHALLENGE,
            holidateSkillLevel = SkillLevel.ADVANCED,
            holidateAttitude = Attitude.CHALLENGE,
            user = user
        )

        every { session.getAttribute("userId") } returns 1010L
        every { activityPreferencesRepository.findByUserId(1010L) } returns existingPreferences

        val result = controller.editActivityPreferences(model, session)

        result shouldBe "editActivityPreferences"
        verify{ model.addAttribute("activity", any())}
    }
}