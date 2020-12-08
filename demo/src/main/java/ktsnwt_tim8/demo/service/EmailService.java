package ktsnwt_tim8.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
	        
	        
	        
	        javaMailSender.send(email);	//ovde pukne
			System.out.println("Email poslat!");
		
		
	}
}
