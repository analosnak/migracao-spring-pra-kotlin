package br.com.alura.forum.service.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.Answer;
import br.com.alura.forum.model.topic.domain.Topic;

@Service
public class ForumMailService {
	private static final Logger logger = LoggerFactory.getLogger(ForumMailService.class);

	@Autowired
    private MailSender mailSender;
	
	@Async
	public void sendNewReplyMailAsync(Answer answer) {
		Topic answeredTopic = answer.getTopic();
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(answeredTopic.getOwnerEmail());
		message.setSubject("Novo comentário em: " + answeredTopic.getShortDescription());
		message.setText("Olá " + answeredTopic.getOwnerEmail() + "\n\n" +
                "Há uma nova mensagem no fórum! " + answer.getOwnerName() +
                " comentou no tópico: " + answeredTopic.getShortDescription());
		
		try {
			mailSender.send(message);
		} catch (MailException e) {
			logger.error("Não foi possível enviar email para " + answer.getTopic()
            .getOwnerEmail(), e.getMessage());
		}
	}
}
