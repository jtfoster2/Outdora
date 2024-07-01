package com.esep.outdoora

import com.esep.outdoora.oauth2.ProviderDetails
import com.esep.outdoora.oauth2.ProviderDetailsRepository
import com.esep.outdoora.user.User
import com.esep.outdoora.user.UserRepository
import com.esep.outdoora.user.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class OAuth2LoginSuccessHandler(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val providerDetailsRepository: ProviderDetailsRepository
) : AuthenticationSuccessHandler {

    @Throws(IOException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: org.springframework.security.core.Authentication
    ) {
        val oauth2User = authentication.principal as DefaultOAuth2User
        val authorizedClient = (authentication as OAuth2AuthenticationToken).authorizedClientRegistrationId
        val email: String = oauth2User.getAttribute("email")!!
        email.length

        val user = userRepository.findByEmail(email) ?: userService.createNewUser(email)

        providerDetailsRepository.findByUserId(userId = user.id!!)
            ?: providerDetailsRepository.save(ProviderDetails(
                    user = user,
                    providerName = authorizedClient,
                    providerUserId = oauth2User.name
                ))

        request.getSession(false)?.setAttribute("userId", user.id)

        response.sendRedirect("/")
    }
}