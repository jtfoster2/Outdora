package com.esep.outdoora

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class LogoutController {

    @GetMapping("/logged-out")
    fun loggedOut(): String {
        // Display a logged-out confirmation page or redirect to the home page
        return "logged-out"
    }
}