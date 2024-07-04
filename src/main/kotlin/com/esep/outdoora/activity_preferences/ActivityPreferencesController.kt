package com.esep.outdoora.activity_preferences

import com.esep.outdoora.profile.Profile
import com.esep.outdoora.user.User
import com.esep.outdoora.user.UserRepository
import org.springframework.stereotype.Controller
import jakarta.servlet.http.HttpSession
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import kotlin.jvm.optionals.getOrNull

@Controller
class ActivityPreferencesController(
    var activityRepository: ActivityRepository,
    var userRepository: UserRepository,
) {
    @PostMapping("/editActivityPreferences")
    fun updateActivityPreferencesDetails(
        @RequestParam skiing: Boolean?,
        @RequestParam backpacking: Boolean?,
        @RequestParam travel: Boolean?,
        @RequestParam hiking: Boolean?,
        @RequestParam holidate: Boolean?,
        session: HttpSession
    ) : String {
        val userId = session.getAttribute("userId") as Long
        val activity = activityRepository.findByUserId(userId = userId)?.apply {
            this.skiing = skiing ?: false
            this.backpacking = backpacking ?: false
            this.travel = travel ?: false
            this.hiking = hiking ?: false
            this.holidate = holidate ?: false
        } ?: ActivityPreferences(
            id = null,
            skiing = skiing ?: false,
            backpacking = backpacking ?: false,
            travel = travel ?: false,
            hiking = hiking ?: false,
            holidate = holidate ?: false,
            user = userRepository.findById(userId).getOrNull()!!
        )

        activityRepository.save(activity)

        return "redirect:/activityPreferences"
    }

    @GetMapping("/activityPreferences")
    fun viewActivityPreferences(
        model: Model,
        session: HttpSession,
    ): String {
        val userId = session.getAttribute("userId") as Long
        val activity = activityRepository.findByUserId(userId = userId)
        model.addAttribute("preferences", activity ?: ActivityPreferences(user = User()))
        return "activityPreferences"
    }

    @GetMapping("/editActivityPreferences")
    fun editActivityPreferences(
        model: Model,
        session: HttpSession
    ): String {
        val userId = session.getAttribute("userId") as Long
        val activity = activityRepository.findByUserId(userId = userId)
        model.addAttribute("preferences", activity ?: ActivityPreferences(user = User()))
        return "editActivityPreferences"
    }
}