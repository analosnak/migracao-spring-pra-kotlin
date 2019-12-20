package br.com.alura.forum.security

import br.com.alura.forum.security.jwt.TokenManager
import br.com.alura.forum.security.service.UsersService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
        private val tokenManager: TokenManager,
        private val usersService: UsersService
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val jwt = getTokenFromRequest(request)

        if (tokenManager.isValid(jwt)) {
            val userId = tokenManager.getUserIdFromToken(jwt)
            val userDetails = usersService.loadUserById(userId)

            val authenticationToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

            SecurityContextHolder.getContext().authentication = authenticationToken
        }

        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")

        if (!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7)
        return null
    }
}