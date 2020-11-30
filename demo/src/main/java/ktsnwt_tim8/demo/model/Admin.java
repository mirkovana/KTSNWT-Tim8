package ktsnwt_tim8.demo.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ROLE_ADMIN")
public class Admin extends User {

	@Column
	private String phoneNumber;
	
	public Admin() {
		super();
	}

	
	
	public Admin(String phoneNumber) {
		super();
		this.phoneNumber = phoneNumber;
	}



	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
	
}
