package com.esep.outdoora

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException


/**
 * This filter ensures that the loopback IP `127.0.0.1` is used to access the
 * application so that the sample works correctly, due to the fact that redirect URIs with
 * "localhost" are rejected by the Spring Authorization Server, because the OAuth 2.1
 * draft specification states:
 *
 * <pre>
 * While redirect URIs using localhost (i.e.,
 * "http://localhost:{port}/{path}") function similarly to loopback IP
 * redirects described in Section 10.3.3, the use of "localhost" is NOT
 * RECOMMENDED.
</pre> *
 *
 * @author Steve Riesenberg
 * @see [Loopback Redirect
 * Considerations in Native Apps](https://tools.ietf.org/html/draft-ietf-oauth-v2-1-01.section-9.7.1)
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class LoopbackIpRedirectFilter : OncePerRequestFilter() {
    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.serverName == "localhost" && request.getHeader("host") != null) {
            val uri = UriComponentsBuilder.fromHttpRequest(ServletServerHttpRequest(request))
                .host("127.0.0.1")
                .build()
            response.sendRedirect(uri.toUriString())
            return
        }
        filterChain.doFilter(request, response)
    }
}