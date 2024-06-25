package com.esep.outdoora

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class LandingPageController {
    @GetMapping("/landing")
    fun landingPage(): String {
        return "landing"
    }
}