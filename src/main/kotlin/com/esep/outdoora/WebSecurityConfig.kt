package com.esep.outdoora

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    @Autowired
    private lateinit var clientRegistrationRepository: ClientRegistrationRepository

    private val googleLogoutUri: String = "https://accounts.google.com/Logout"

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/landing").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login { oauth2Login ->

            }
            .logout { logout ->
                logout
                    .logoutSuccessUrl("/logged-out")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessHandler { request, response, authentication ->
                        val logoutUrl = "$googleLogoutUri"
                        response.sendRedirect(logoutUrl)
                    }
            }

        return http.build()
    }
}