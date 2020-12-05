package ktsnwt_tim8.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ktsnwt_tim8.demo.dto.UserDTO;
import ktsnwt_tim8.demo.dto.UserLoginDTO;
import ktsnwt_tim8.demo.dto.UserTokenStateDTO;
import ktsnwt_tim8.demo.emailVerification.OnRegistrationCompleteEvent;
import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.model.User;
import ktsnwt_tim8.demo.model.VerificationToken;
import ktsnwt_tim8.demo.security.TokenUtils;
import ktsnwt_tim8.demo.service.AuthorityService;
import ktsnwt_tim8.demo.service.CustomUserDetailsService;
import ktsnwt_tim8.demo.service.UserService;
import ktsnwt_tim8.demo.service.VerificationTokenService;

//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	@Lazy
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private VerificationTokenService verificationTokenService;

	// private UserMapper userMapper;

	// Prvi endpoint koji pogadja korisnik kada se loguje.
	// Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
	@PostMapping("/log-in")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody UserLoginDTO authenticationRequest,
			HttpServletResponse response) {

		//dobavimo usera preko tog userdto 
		//proverimo da li je aktivan 
		//ako jeste nastavi dalje kao sto si  do sad
		//ako nije onda ona poruka 
		User userReg =  userService.findByUsername(authenticationRequest.getUsername());
		if(userReg.isActive()) {

			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));

			// Ubaci korisnika u trenutni security kontekst
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// Kreiraj token za tog korisnika
			User user = (User) authentication.getPrincipal();
			String jwt = tokenUtils.generateToken(user.getUsername()); // prijavljujemo se na sistem sa email adresom
			int expiresIn = tokenUtils.getExpiredIn();

			// Vrati token kao odgovor na uspesnu autentifikaciju
			return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
			
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/logout")
	public ResponseEntity logOut(HttpServletRequest request) {
		SecurityContextHolder.getContext().setAuthentication(null);
		return new ResponseEntity(HttpStatus.OK);
	}

	// Endpoint za registraciju novog korisnika
	@PostMapping("/sign-up")
	public ResponseEntity<?> addUser(@RequestBody UserDTO usersDTO, HttpServletRequest request) throws Exception {

		User existUser = this.userService.findByUsername(usersDTO.getUsername());
		if (existUser != null) {
			throw new Exception("Username already exists");
		}
		RegisteredUser user = new RegisteredUser();
		try {

			user.setName(usersDTO.getName());

			if (usersDTO.getPassword().isEmpty()) {
				throw new Exception("Password cannot be empty");
			}
			user.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
			user.setSurname(usersDTO.getSurname());
			user.setEmail(usersDTO.getUsername());
			user.setActive(false);
			if (usersDTO.getUsername().isEmpty()) {
				throw new Exception("Username cannot be empty");
			}
			user.setUsername(usersDTO.getUsername());
			//user.setAuthorities(authorityService.findByName("ROLE_USER")); //OVO TEK KAD POTVRDI MEJL ADRESU
			user = (RegisteredUser) userService.save(user);
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user,
					request.getLocale(), request.getContextPath()));
		} catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
	}

	
	@PostMapping(value="/validateEmail/{token}", consumes = "application/json")
	@PreAuthorize("permitAll()")
	public String confirmRegistration(@PathVariable String token){
       
		VerificationToken verificationToken = verificationTokenService.findByToken(token);
		RegisteredUser user = verificationToken.getRegisteredUser();
		user.setActive(true);
		user.setAuthorities(authorityService.findByName("ROLE_USER"));
		userService.save(user);
		
		return token;
	}
	
	// U slucaju isteka vazenja JWT tokena, endpoint koji se poziva da se token
	// osvezi
	@PostMapping(value = "/refresh")
	public ResponseEntity<UserTokenStateDTO> refreshAuthenticationToken(HttpServletRequest request) {

		String token = tokenUtils.getToken(request);
		String username = this.tokenUtils.getUsernameFromToken(token);
		User user = (User) this.userDetailsService.loadUserByUsername(username);

		if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
			String refreshedToken = tokenUtils.refreshToken(token);
			int expiresIn = tokenUtils.getExpiredIn();

			return ResponseEntity.ok(new UserTokenStateDTO(refreshedToken, expiresIn));
		} else {
			UserTokenStateDTO userTokenState = new UserTokenStateDTO();
			return ResponseEntity.badRequest().body(userTokenState);
		}
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
		userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

		Map<String, String> result = new HashMap<>();
		result.put("result", "success");
		return ResponseEntity.accepted().body(result);
	}

	static class PasswordChanger {
		public String oldPassword;
		public String newPassword;
	}

	/*
	 * public AuthenticationController() { userMapper = new UserMapper(); }
	 */
}
