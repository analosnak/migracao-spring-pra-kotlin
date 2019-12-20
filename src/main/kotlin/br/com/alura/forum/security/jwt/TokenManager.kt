package br.com.alura.forum.security.jwt

import br.com.alura.forum.model.User
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenManager(
        @Value("\${alura.forum.jwt.secret}")
        private val secret: String,
        @Value("\${alura.forum.jwt.expiration}")
        private val expirationInMillis: Long
) {
    fun generateToken(authentication: Authentication): String {
        val user = authentication.principal as User

        val now = Date()
        val expiration = Date(now.time + this.expirationInMillis)

        return Jwts.builder()
                .setIssuer("Alura Fórum API")
                .setSubject(user.id.toString())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact()
    }

    fun isValid(jwt: String?) = try {
        Jwts.parser().setSigningKey(this.secret).parseClaimsJws(jwt)
        true
    } catch (e: JwtException) {
        false
    } catch (e: IllegalArgumentException) {
        false
    }

    fun getUserIdFromToken(jwt: String?): Long {
        val claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(jwt).body

        return claims.subject.toLong()
    }
}