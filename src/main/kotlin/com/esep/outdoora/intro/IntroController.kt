package com.esep.outdoora.profile

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IntroController(
) {
    @GetMapping("/intro")
    fun getIntro(
        model: Model,
    ): String {
        return "intro"
    }

    @GetMapping("/home")
    fun getHome(
        model: Model,
    ): String {
        return "home"
    }
}