package br.com.alura.forum.configuration

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.ServerSetup
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Profile("dev")
@Configuration
class GreenMailLocalSmtpConfiguration(
        @Value("\${spring.mail.host}")
        private val hostAddress: String,
        @Value("\${spring.mail.port}")
        private val port: String,
        @Value("\${spring.mail.username}")
        private val username: String,
        @Value("\${spring.mail.password}")
        private val password: String
) {

    lateinit var smtpServer: GreenMail

    @PostConstruct fun setup() {
        val serverSetup = ServerSetup(port.toInt(), hostAddress, "smtp")

        smtpServer = GreenMail(serverSetup)
        smtpServer.setUser(username, username, password)
        smtpServer.start()
    }

    @PreDestroy fun destroy() { smtpServer.stop() }
}