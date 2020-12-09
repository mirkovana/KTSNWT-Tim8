package ktsnwt_tim8.demo.model;

import static javax.persistence.FetchType.LAZY;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "verification_token")
public class VerificationToken {
	private static final int expirationTime = 24 * 60;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idVT;

	@Column(name = "token")
	private String token;

	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "id")
	@JsonBackReference
	private RegisteredUser registeredUser;

	@Column(name = "date_created")
	private Date dateCreated;

	@Column(name = "date_removed")
	private Date dateRemoved;

	public VerificationToken() {
		super();
	}

	public VerificationToken(String token, RegisteredUser registeredUser) {
		super();
		Calendar calendar = Calendar.getInstance();

		this.token = token;
		this.registeredUser = registeredUser;
		this.dateCreated = new Date(calendar.getTime().getTime());
		this.dateRemoved = setEndDate();
	}

	private Date setEndDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Timestamp(calendar.getTime().getTime()));
		calendar.add(Calendar.MINUTE, expirationTime);
		return new Date(calendar.getTime().getTime());
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	public Integer getIdVT() {
		return idVT;
	}

	public void setIdVT(Integer idVT) {
		this.idVT = idVT;
	}

	public RegisteredUser getRegisteredUser() {
		return registeredUser;
	}

	public void setRegisteredUser(RegisteredUser registeredUser) {
		this.registeredUser = registeredUser;
	}

	public static int getExpirationtime() {
		return expirationTime;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateRemoved() {
		return dateRemoved;
	}

	public void setDateRemoved(Date dateRemoved) {
		this.dateRemoved = dateRemoved;
	}

	
}