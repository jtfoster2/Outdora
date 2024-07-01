package com.esep.outdoora

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView


@Controller
class ExampleController {
    @GetMapping("/")
    fun root(
        model: Model,
        @RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient,
        @AuthenticationPrincipal oauth2User: OAuth2User
    ): String {
        // See other fields with authDetails endpoint.
        model.addAttribute("name", oauth2User.attributes.get("given_name"))
        return "home"
    }

    @GetMapping("/hello")
    fun hello(model: Model): String {
        return "hello"
    }

    @GetMapping("/help")
    fun help(model: Model): String {
        return "help"
    }
}

@ControllerAdvice
class MyGlobalExceptionHandler {
    val logger = LoggerFactory.getLogger(MyGlobalExceptionHandler::class.java)
    @ExceptionHandler(Exception::class)
    @Throws(Exception::class)
    fun defaultErrorHandler(req: HttpServletRequest, e: Exception?): ModelAndView {
        val mav: ModelAndView = ModelAndView()
        mav.addObject("exception", e)
        mav.addObject("url", req.getRequestURL())
        mav.setViewName("error")
        logger.error("yikes", e)
        return mav
    }
}