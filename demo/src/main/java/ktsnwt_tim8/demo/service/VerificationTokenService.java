package ktsnwt_tim8.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.model.VerificationToken;
import ktsnwt_tim8.demo.repository.VerificationTokenRepository;
@Service
public class VerificationTokenService {
	
	@Autowired 
	private VerificationTokenRepository verificationTokenRepository;
	
	
	public VerificationToken save(VerificationToken token) {
		return verificationTokenRepository.save(token);
	}
	
	public VerificationToken findByToken(String token) {
		return verificationTokenRepository.findByToken(token);
	}
	public VerificationToken findByRegisteredUser(RegisteredUser registeredUser) {
		return verificationTokenRepository.findByRegisteredUser(registeredUser);
	}	
}
