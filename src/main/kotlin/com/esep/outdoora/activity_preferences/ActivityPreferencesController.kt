package com.esep.outdoora.activity_preferences

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
        @RequestParam activityList: List<Activity>,
        session: HttpSession
    ) : String {
        val userId = session.getAttribute("userId") as Long
        val activity = activityPreferencesRepository.findByUserId(userId = userId)?.apply {
            this.activityList = activityList
        } ?: ActivityPreferences(
            id = null,
            activityList = activityList,
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
        activity?.also{
            // Pass the activityList to the HTML to display
            model.addAttribute("activityList", activity.activityList)
        }
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
        activity?.also{
            // Pass the activityList to the HTML to display
            model.addAttribute("activityList", activity.activityList)
        }
        return "editActivityPreferences"
    }
}