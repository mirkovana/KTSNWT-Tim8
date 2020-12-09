package ktsnwt_tim8.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import ktsnwt_tim8.demo.model.RegisteredUser;

public class UserDTO {
	private Long ID;
	
	@NotBlank(message = "Email cannot be empty.")
	@Email(message = "Email format is not valid.")
	private String username;
	
	@NotBlank(message = "Password cannot be empty.")
	private String password;
	
	private String name;
	private String surname;
	private String email;
	
	public UserDTO() {
		
	}
	
	public UserDTO(RegisteredUser user) {
		this.ID=user.getID();
		this.password=user.getPassword();
		this.name=user.getName();
		this.surname=user.getSurname();
		this.username=user.getUsername();
		this.email=user.getUsername();
		
	}
	
	public UserDTO(Long iD, @NotBlank(message = "Email cannot be empty.") @Email(message = "Email format is not valid.") String username,@NotBlank(message = "Password cannot be empty.") String password, String name, String surname) {
		
		this.ID = iD;
		this.username = username;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = username;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
