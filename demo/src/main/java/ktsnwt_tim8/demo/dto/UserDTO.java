package ktsnwt_tim8.demo.dto;

import ktsnwt_tim8.demo.model.RegisteredUser;

public class UserDTO {
	private String username;
	private String password;
	private String name;
	private String surname;

	public UserDTO() {
		
	}
	
	public UserDTO(RegisteredUser user) {
		this.password=user.getPassword();
		this.name=user.getName();
		this.surname=user.getSurname();
		this.username=user.getUsername();
		
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
