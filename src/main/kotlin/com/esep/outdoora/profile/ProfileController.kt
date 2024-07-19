package com.esep.outdoora.profile

import com.esep.outdoora.user.UserRepository
import jakarta.servlet.http.HttpSession
import jakarta.transaction.Transactional
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.Base64Utils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.security.Principal
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Controller
class ProfileController(
    val profileRepository: ProfileRepository,
    val userRepository: UserRepository,
) {
    @Transactional
    @PostMapping("/editProfile")
    fun updateProfileDetails(
        @RequestParam name: String,
        @RequestParam age: Int,
        @RequestParam description: String,
        @RequestParam image: MultipartFile,
        session: HttpSession
    ) : String {
        val userId = session.getAttribute("userId") as Long
        val profile = profileRepository.findByUserId(userId = userId)?.apply {
            this.name = name
            this.age = age
            this.description = description
            this.image = image.bytes
        } ?: Profile(
            id = null,
            name = name,
            age = age,
            description = description,
            image = image.bytes,
            user = userRepository.findById(userId).getOrNull()!!
        )

        profileRepository.save(profile)

        return "redirect:/profile"
    }

    @Transactional
    @GetMapping("/profile")
    fun viewProfile(model: Model, session: HttpSession): String {
        val userId = session.getAttribute("userId") as Long
        val profile = profileRepository.findByUserId(userId = userId)
        model.addAttribute("profileExists", profile != null)
        profile?.let {
            model.addAttribute("name", it.name)
            model.addAttribute("age", it.age)
            model.addAttribute("description", it.description)
            // Use java.util.Base64 for encoding
            it.image
                ?.let { image -> Base64.getEncoder().encodeToString(image) }
                ?.also { imageBase64 -> model.addAttribute("imageBase64", imageBase64) }
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