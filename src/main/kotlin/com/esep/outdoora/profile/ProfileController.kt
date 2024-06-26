package com.esep.outdoora.profile

import com.esep.outdoora.user.UserRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal
import kotlin.jvm.optionals.getOrNull

@Controller
class ProfileController(
    val profileRepository: ProfileRepository,
    val userRepository: UserRepository,
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

    @GetMapping("/profile")
    fun viewProfile(
        model: Model,
        session: HttpSession,
        principal: Principal
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
}