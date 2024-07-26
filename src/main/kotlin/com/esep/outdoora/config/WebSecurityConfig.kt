package com.esep.outdoora.config

import com.esep.outdoora.OAuth2LoginSuccessHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    @Autowired
    private lateinit var clientRegistrationRepository: ClientRegistrationRepository

    @Autowired
    private lateinit var oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler

    private val googleLogoutUri: String = "https://accounts.google.com/Logout"

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("*")
                    .allowedHeaders("*")
            }
        }
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/landing").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login { oauth2Login ->
                oauth2Login.userInfoEndpoint(Customizer { })
                    .successHandler(oAuth2LoginSuccessHandler)
            }
            .logout { logout ->
                logout
                    .logoutSuccessUrl("/logged-out")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutSuccessHandler { _, response, _ ->
                        val logoutUrl = "$googleLogoutUri"
                        response.sendRedirect(logoutUrl)
                    }
            }
            .csrf { csrf -> csrf.ignoringRequestMatchers("/ws/**") } // Disable CSRF protection for WebSocket endpoints

        return http.build()
    }
}