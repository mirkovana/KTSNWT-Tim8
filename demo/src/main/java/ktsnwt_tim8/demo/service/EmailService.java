package ktsnwt_tim8.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ktsnwt_tim8.demo.model.Post;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Async
	public void sendNotificaitionAsync(String url, String recipient, String subject) {
		System.out.println("Slanje emaila...");

		SimpleMailMessage email = new SimpleMailMessage();
		email.setText("Potvrdite vas email za aktivaciju profila");
		email.setTo(recipient);
		System.out.println(recipient + " rrrrrrrrrrrrrrrrrrrrrrr");

		email.setSubject(subject);
		System.out.println("subj" + subject);

		email.setText("http://localhost:8080/#" + url);
		System.out.println(url);

		javaMailSender.send(email); // ovde pukne
		System.out.println("Email poslat!");

	}

	@Async
	public void sendEmailNotification(String email, Post post) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject(post.getTitle());
		String message = post.getContent();
		message += "\n";
		message += "Vise o ovome pogledajte na : ..........";
		msg.setText(message);
		javaMailSender.send(msg);

	}
}
