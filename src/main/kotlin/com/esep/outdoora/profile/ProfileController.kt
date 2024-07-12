package com.esep.outdoora.profile

import com.esep.outdoora.user.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import kotlin.jvm.optionals.getOrNull

@Controller
class ProfileController(
    private val profileRepository: ProfileRepository,
    private val userRepository: UserRepository,
    private val profileDeletionService: ProfileDeletionService
) {
    @PostMapping("/editProfile")
    fun updateProfileDetails(
        @RequestParam name: String,
        @RequestParam age: Int,
        @RequestParam description: String,
        session: HttpSession
    ) : String {
        val userId = session.getAttribute("userId") as Long
        val profile = profileRepository.findByUserId(userId = userId)?.apply {
            this.name = name
            this.age = age
            this.description = description
        } ?: Profile(
            id = null,
            name = name,
            age = age,
            description = description,
            user = userRepository.findById(userId).getOrNull()!!
        )

        profileRepository.save(profile)

        return "redirect:/profile"
    }

    @GetMapping("/profile") // context
    fun viewProfile(
        model: Model,
        session: HttpSession,
    ): String {
        val userId = session.getAttribute("userId") as Long
        val profile = profileRepository.findByUserId(userId = userId)
        model.addAttribute("profileExists", profile?.let { true } ?: false)
        profile?.also {
            model.addAttribute("name", profile.name)
            model.addAttribute("age", profile.age)
            model.addAttribute("description", profile.description)
        }
        return "profile"
    }

    @GetMapping("/editProfile")
    fun editProfile(
        model: Model,
        session: HttpSession
    ): String {
        val userId = session.getAttribute("userId") as Long
        val profile = profileRepository.findByUserId(userId = userId)
        model.addAttribute("profileExists", profile?.let { true } ?: false)
        profile?.also {
            model.addAttribute("name", profile.name)
            model.addAttribute("age", profile.age)
            model.addAttribute("description", profile.description)
        }
        return "editProfile"
    }

    @GetMapping("/deleteProfileConfirmation")
    fun deleteProfileConfirmation(
        model: Model,
        session: HttpSession,
        redirectAttributes: RedirectAttributes
    ): String {
        val userId = session.getAttribute("userId") as Long
        val profile = profileRepository.findByUserId(userId = userId)
        if (profile == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Profile not found.")
            return "redirect:/"
        }

        profile.also {
            model.addAttribute("name", profile.name)
            model.addAttribute("description", profile.description)
        }
        return "deleteProfileConfirmation"
    }


    @GetMapping("/deleteProfile")
    fun deleteProfile(
        session: HttpSession,
        redirectAttributes: RedirectAttributes
    ): String {
        val userId = session.getAttribute("userId") as Long
        val profile = profileRepository.findByUserId(userId = userId)
        if (profile == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Profile not found.")
            return "redirect:/"
        }

        return if (profileDeletionService.deleteProfile(userId)) {
            redirectAttributes.addFlashAttribute("successMessage", "Profile successfully deleted.")
            "redirect:/"
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error occurred while deleting profile.")
            "redirect:/"
        }
    }
}