package com.esep.outdoora.TwoFactorAuth

import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class TwoFactorController(val twoFactorAutService: TwoFactorAuthenticationService) {
    val phoneNumber: String = ""
    val actualCode: String = ""

    @GetMapping("/phoneform")
    fun codeForm(): String {
        return "phone-form"
    }

    @PostMapping("/confirmCode")
    fun confirmingCode(
        @RequestParam code: String,
        session: HttpSession
    ): String {
        val phoneNumber = session.getAttribute("phoneNumber")
        val valide = twoFactorAutService.verifyCode(phoneNumber.toString(), code, actualCode)
        if (!valide) {
            return "redirect:"
        }
        return "redirect:landing"
    }

    @PostMapping("/sendcode")
    fun smscodesender(
        @RequestParam phone: String,
        session: HttpSession
    ): String {
        //twoFactorAutService.sendVerificationCode(phone)
        session.setAttribute("phoneNumber", phone)
        return "redirect:/code-form"
    }


}