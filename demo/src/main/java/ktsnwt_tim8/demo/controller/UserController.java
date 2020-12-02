package ktsnwt_tim8.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ktsnwt_tim8.demo.dto.UserDTO;
import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.service.UserService;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

	@Autowired
	private UserService service;

	/* REGISTRACIJA KORISNIKA*/
	@PostMapping(consumes = "application/json")
	public ResponseEntity<UserDTO> saveSubcategory(@RequestBody UserDTO usersDTO) throws Exception {

		RegisteredUser user = new RegisteredUser();
	
		user.setName(usersDTO.getName());

		if(usersDTO.getPassword().isEmpty()) {
			throw new Exception("Password cannot be empty");
		}
		user.setPassword(usersDTO.getPassword());
		user.setSurname(usersDTO.getSurname());

		if(usersDTO.getUsername().isEmpty()) {
			throw new Exception("Username cannot be empty");
		}
		user.setUsername(usersDTO.getUsername());
		
		
		user = (RegisteredUser) service.save(user);
		return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
	}

}
