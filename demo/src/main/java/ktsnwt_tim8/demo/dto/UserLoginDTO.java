package ktsnwt_tim8.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

//DTO za login
public class UserLoginDTO {

	@NotBlank(message = "Email cannot be empty.")
	@Email(message = "Email format is not valid.")
	private String username;
	
	@NotBlank(message = "Password cannot be empty.")
	private String password;

	public UserLoginDTO() {
	}

	public UserLoginDTO(@NotBlank(message = "Email cannot be empty.") @Email(message = "Email format is not valid.") String username, @NotBlank(message = "Password cannot be empty.") String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String email) {
		this.username = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
