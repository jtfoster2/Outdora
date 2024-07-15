package com.esep.outdoora.recommendationengine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpSession

@Controller
class RecommendationController() {
    @Autowired
    lateinit var recommendEngine: RecommendEngine

    @GetMapping("/recommendations")
    fun recommendation(
        model: Model,
        session: HttpSession
    ): String {
        val userId = session.getAttribute("userId") as Long
        val recommandedProfiles = recommendEngine.basicRecommendProfileTo(userId)
        model.addAttribute("profiles", recommandedProfiles)

        return "recommendation"
    }

    @PostMapping("/likeprofile")
    fun likeProfile(
        @RequestParam LikedProfileId: Long,
        session: HttpSession
    ): String {
        val userId = session.getAttribute("userId") as Long

        return "redirect:/recommendations"
    }
}
