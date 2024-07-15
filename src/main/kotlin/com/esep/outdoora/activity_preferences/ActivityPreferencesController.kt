package com.esep.outdoora.activity_preferences

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
    var activityPreferencesRepository: ActivityPreferencesRepository,
    var userRepository: UserRepository,
) {
    @PostMapping("/editActivityPreferences")
    fun updateActivityPreferencesDetails(
        @RequestParam skiingSkillLevel: SkillLevel,
        @RequestParam skiingAttitude: Attitude,
        @RequestParam backpackingSkillLevel: SkillLevel,
        @RequestParam backpackingAttitude: Attitude,
        @RequestParam travelSkillLevel: SkillLevel,
        @RequestParam travelAttitude: Attitude,
        @RequestParam hikingSkillLevel: SkillLevel,
        @RequestParam hikingAttitude: Attitude,
        @RequestParam holidateSkillLevel: SkillLevel,
        @RequestParam holidateAttitude: Attitude,
        session: HttpSession
    ) : String {
        val userId = session.getAttribute("userId") as Long
        val activity = activityPreferencesRepository.findByUserId(userId = userId)?.apply {
            this.skiingSkillLevel = skiingSkillLevel
            this.skiingAttitude = skiingAttitude
            this.backpackingSkillLevel = backpackingSkillLevel
            this.backpackingAttitude = backpackingAttitude
            this.travelSkillLevel = travelSkillLevel
            this.travelAttitude = travelAttitude
            this.hikingSkillLevel = hikingSkillLevel
            this.hikingAttitude = hikingAttitude
            this.holidateSkillLevel = holidateSkillLevel
            this.holidateAttitude = holidateAttitude
        } ?: ActivityPreferences(
            id = null,
            skiingSkillLevel = skiingSkillLevel,
            skiingAttitude = skiingAttitude,
            backpackingSkillLevel = backpackingSkillLevel,
            backpackingAttitude = backpackingAttitude,
            travelSkillLevel = travelSkillLevel,
            travelAttitude = travelAttitude,
            hikingSkillLevel = hikingSkillLevel,
            hikingAttitude = hikingAttitude,
            holidateSkillLevel = holidateSkillLevel,
            holidateAttitude = holidateAttitude,
            user = userRepository.findById(userId).getOrNull()!!
        )

        activityPreferencesRepository.save(activity)

        return "redirect:/activityPreferences"
    }


    @GetMapping("/activityPreferences")
    fun viewActivityPreferences(
        model: Model,
        session: HttpSession,
    ): String {
        val userId = session.getAttribute("userId") as Long
        val activity = activityPreferencesRepository.findByUserId(userId = userId)
        model.addAttribute("preferencesExists", activity?.let {true} ?: false)
        activity?.also { model.addAttribute("activity", activity) }?:
        model.addAttribute("activity", ActivityPreferences(userId, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, User()))
        return "activityPreferences"
    }

    @GetMapping("/editActivityPreferences")
    fun editActivityPreferences(
        model: Model,
        session: HttpSession
    ): String {
        val userId = session.getAttribute("userId") as Long
        val activity = activityPreferencesRepository.findByUserId(userId = userId)
        model.addAttribute("preferencesExists", activity?.let {true} ?: false)
        activity?.also{ model.addAttribute("activity", activity) }?:
        model.addAttribute("activity", ActivityPreferences(userId, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, SkillLevel.BEGINNER, Attitude.IMMERSION, User()))
        return "editActivityPreferences"
    }
}