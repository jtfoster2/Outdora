package com.esep.outdoora.recommendationengine

import com.esep.outdoora.profile.ProfileRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class RecommendationController(
    val profileRepository: ProfileRepository,
    val recommendEngine: RecommendEngine
) {
    @GetMapping("/recommendations")
    fun recommendation(
        model: Model,
        session: HttpSession
    ): String {

        val recommandedProfiles = recommendEngine.basicRecommendProfileTo(44)
        model.addAttribute("profiles", recommandedProfiles)

        return "profile-recommend"
    }

    @PostMapping("/likeprofile")
    fun likeProfile(
        @RequestParam LikedProfileId: Long,
        session: HttpSession
    ): String {
        val userId = session.getAttribute("user_id") as Long


        return "redirect:/recommendations"
    }
}
