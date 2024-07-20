package com.esep.outdoora.recommendationengine

import com.esep.outdoora.profile.ProfileRepository
import com.esep.outdoora.user.User
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
        val userId = session.getAttribute("userId") as Long
        val recommandedProfiles = recommendEngine.basicRecommendProfileTo(userId)
        model.addAttribute("profiles", recommandedProfiles)

        return "profile-recommend"
    }

    @PostMapping("/likeprofile")
    fun likeProfile(
        @RequestParam LikedProfileId: Long,
        session: HttpSession
    ): String {
        val user = session.getAttribute("user_id") as User
        
        return "/recommendations"
    }
}
