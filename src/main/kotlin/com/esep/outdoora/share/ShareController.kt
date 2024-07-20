package com.esep.outdoora.profile

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class ShareController(
) {
    @GetMapping("/share")
    fun getShare(
        @RequestParam author: String,
        @RequestParam text: String,
        model: Model,
    ): String {
        model.addAttribute("author", author)
        model.addAttribute("text", text)
        return "shareTemplate"
    }
}