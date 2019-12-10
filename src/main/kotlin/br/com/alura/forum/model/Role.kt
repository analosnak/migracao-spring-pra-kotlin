package br.com.alura.forum.model

import org.springframework.security.core.GrantedAuthority
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Role(@Id private val authority: String) : GrantedAuthority {

    override fun getAuthority() = authority

    companion object {
        const val ROLE_ALUNO = "ROLE_ALUNO"
        const val ROLE_ADMIN = "ROLE_ADMIN"
    }
}