package br.com.alura.forum.repository

import br.com.alura.forum.model.User
import org.springframework.data.repository.Repository

interface UserRepository : Repository<User, Long> {
    fun findByEmail(email: String?): User?
    fun findById(userId: Long): User?
    fun save(user: User): User
}