package br.com.alura.forum.controller.dto.input

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

class LoginInputDto(private val email: String,
                    private val password: String) {
    fun build() = UsernamePasswordAuthenticationToken(this.email, this.password)
}