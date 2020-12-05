package ktsnwt_tim8.demo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("ROLE_USER")
public class RegisteredUser extends User {

	@Column
	private String name;
	
	@Column
	private String surname;
	
	
	@ManyToMany(mappedBy = "users")
	private Set<Offer> subscriptions;
	
	@OneToMany(mappedBy = "user")
	private Set<Comment> comments;
	
	@OneToMany(mappedBy = "user")
	private Set<Rating> ratings;
	
	
	

	public RegisteredUser(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
	}

	public RegisteredUser() {
		super();
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
	
	
	
}
