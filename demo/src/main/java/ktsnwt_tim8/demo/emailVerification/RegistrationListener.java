package ktsnwt_tim8.demo.emailVerification;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.model.VerificationToken;
import ktsnwt_tim8.demo.service.EmailService;
import ktsnwt_tim8.demo.service.VerificationTokenService;

@Component 
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private VerificationTokenService tokenService;
	@Autowired
	private EmailService emailService;

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);

	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
	
			RegisteredUser user = event.getUser();
			System.out.println("aaaaaaaaaaaaaaaaaaaaa" + user.getEmail());
			String token = UUID.randomUUID().toString();

			VerificationToken newUserToken = new VerificationToken(token, user);

			tokenService.save(newUserToken);

			String recipient = user.getEmail();
			String subject = "Potvrda registracije";
			String url = event.getAppUrl() +  token;

			emailService.sendNotificaitionAsync(url, recipient, subject);

		

	}

}