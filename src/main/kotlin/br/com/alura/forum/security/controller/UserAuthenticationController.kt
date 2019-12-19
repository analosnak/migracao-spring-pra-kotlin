package br.com.alura.forum.security.controller

import br.com.alura.forum.controller.dto.input.LoginInputDto
import br.com.alura.forum.controller.dto.output.AuthenticationTokenOutputDto
import br.com.alura.forum.security.jwt.TokenManager
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/auth")
class UserAuthenticationController(private val authManager: AuthenticationManager,
                                   private val tokenManager: TokenManager) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun authenticate(@RequestBody loginInfo: LoginInputDto): ResponseEntity<AuthenticationTokenOutputDto> {
        val authenticationToken = loginInfo.build()

        return try {
            val authentication = authManager.authenticate(authenticationToken)
            val jwtToken = tokenManager.generateToken(authentication)

            val tokenOutputDto = AuthenticationTokenOutputDto("Bearer", jwtToken)

            ResponseEntity.ok(tokenOutputDto)
        } catch (e: AuthenticationException) {

            ResponseEntity.badRequest().build()
        }
    }
}