package br.com.alura.forum.security.configuration

import br.com.alura.forum.security.JwtAuthenticationFilter
import br.com.alura.forum.security.jwt.TokenManager
import br.com.alura.forum.security.service.UsersService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Order(2)
@Configuration
@EnableWebSecurity
class SecurityConfiguration(
        private val usersService: UsersService,
        private val tokenManager: TokenManager
) : WebSecurityConfigurerAdapter() {
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity) {
        http.antMatcher("/api/**")
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/topics/**").permitAll()
                    .antMatchers("/api/auth/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .cors()
                .and()
                    .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(JwtAuthenticationFilter(tokenManager, usersService),
                        UsernamePasswordAuthenticationFilter::class.java)
                .exceptionHandling()
                    .authenticationEntryPoint(JwtAuthenticationEntryPoint())
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(usersService)
                .passwordEncoder(BCryptPasswordEncoder())
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/**.html",  "/v2/api-docs", "/webjars/**",
                "/configuration/**", "/swagger-resources/**", "/css/**",
                "/**.ico", "/js/**")
    }

    class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
        override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
            logger.error("Um acesso não autorizado foi verificado. Mensagem: ${authException.message}")
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Você não está autorizado a acessar esse recurso.")
        }

        companion object {
            val logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint::class.java)
        }

    }
}