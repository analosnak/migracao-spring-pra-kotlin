package br.com.alura.forum.model

import br.com.alura.forum.model.topic.domain.Topic
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
data class User private constructor(@Column(nullable = false, unique = true) val email: String): UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    var name: String = ""
    private set

    private lateinit var password: String

    @ManyToMany(fetch = FetchType.EAGER)
    val authorities = mutableListOf<Role>()

    constructor(name: String,
                email: String,
                password: String) : this(email) {
        this.name = name
        this.password = password
    }

    fun isOwnerOf(topic: Topic) = this == topic.owner

    fun isAdmin() = authorities.any { role -> role.authority == Role.ADMIN }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun getUsername() = email

    override fun getPassword() = password

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true
}