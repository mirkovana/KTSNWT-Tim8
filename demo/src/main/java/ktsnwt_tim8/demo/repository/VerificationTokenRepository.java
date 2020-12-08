package ktsnwt_tim8.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.model.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer>{
	
	
	// @Query(value = "SELECT * FROM verification_token WHERE token = ?1", nativeQuery = true)
	  VerificationToken findByToken(String token);
	  
	 // @Query(value = "SELECT * FROM verification_token WHERE lbo = ?1", nativeQuery = true)
	  VerificationToken findByRegisteredUser(RegisteredUser user);
}