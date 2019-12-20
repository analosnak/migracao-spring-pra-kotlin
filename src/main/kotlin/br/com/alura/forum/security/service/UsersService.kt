package br.com.alura.forum.security.service

import br.com.alura.forum.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UsersService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails =
            userRepository.findByEmail(username) ?: throw UsernameNotFoundException("Não foi possível encontrar usuário com email: $username")

    fun loadUserById(userId: Long): UserDetails =
            userRepository.findById(userId) ?: throw UsernameNotFoundException("Não foi possível encontrar o usuário com id: $userId")

}