package br.com.alura.forum.security.configuration

import br.com.alura.forum.security.service.UsersService
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Order(1)
@Configuration
@EnableWebSecurity
class AdminSecurityConfiguration(private val usersService: UsersService) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.antMatcher("/admin/**")
                    .authorizeRequests().anyRequest().hasRole("ADMIN")
                .and()
                    .httpBasic()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(usersService)
                .passwordEncoder(BCryptPasswordEncoder())
    }
}