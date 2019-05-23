package br.com.alura.forum.configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

@Profile("dev")
@Configuration
public class GreenMailLocalSmtpConfiguration {

	private GreenMail smtpServer;
	
    @Value("${spring.mail.host}")
    private String hostAddress;

    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;
    
    @PostConstruct
    public void setup() {
    	ServerSetup serverSetup = new ServerSetup(Integer.parseInt(this.port), this.hostAddress, "smtp");
    	
    	this.smtpServer = new GreenMail(serverSetup);
    	this.smtpServer.setUser(this.username, this.username, this.password);
    	this.smtpServer.start();
    }
    
    @PreDestroy
    public void destroy() {
    	this.smtpServer.stop();
    }
}
