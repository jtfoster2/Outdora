package com.esep.outdoora.activity_preferences

import com.esep.outdoora.IntegrationTest
import com.esep.outdoora.profile.Profile
import com.esep.outdoora.profile.ProfileController
import com.esep.outdoora.profile.ProfileRepository
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
import kotlin.test.Test

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ActivityPreferencesControllerTestDB(
    @Autowired val activityRepository: ActivityRepository,
    @Autowired val activityController: ActivityPreferencesController,
) : IntegrationTest() {
    @Test
    fun `test updateProfileDetails`() {
        // create the user and then activityPreferences
        val user = userRepository.save(User(email = "blah"))
        activityRepository.save(ActivityPreferences(skiing = true, backpacking = true, travel = true, hiking = true, holidate = true, user = user))
        // mock the session
        val session = mockk<HttpSession>()
        every { session.getAttribute("userId") } returns user.id
        // then call the updateActivityPreferencesDetails method
        activityController.updateActivityPreferencesDetails(skiing = false, backpacking = true, travel = false, hiking = true, holidate = false, session)
        // then check that the activityPreference is different in the repository
        val activityPreferences = activityRepository.findByUserId(user.id!!)
        activityPreferences!!.skiing shouldBe false
        activityPreferences.backpacking shouldBe true
        activityPreferences.travel shouldBe false
        activityPreferences.hiking shouldBe true
        activityPreferences.holidate shouldBe false
    }

    @Test
    fun `test viewProfile`() {
        // create the user and then profile
        val user = userRepository.save(User(email = "blah"))
        activityRepository.save(ActivityPreferences(skiing = true, backpacking = true, travel = true, hiking = true, holidate = true, user = user))
        // mock the session
        val session = mockk<HttpSession>()
        every { session.getAttribute("userId") } returns user.id
        // then call the viewProfile method
        val model = mockk<Model>(relaxed = true)

        activityController.viewActivityPreferences(model, session)

        // check the model
        verify{ model.addAttribute("preferences", any())}
    }

}