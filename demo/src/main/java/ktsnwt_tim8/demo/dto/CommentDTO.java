package ktsnwt_tim8.demo.dto;

import java.util.Date;



public class CommentDTO {

	private Long ID;
	
	private String text;
	
	private Date date;

	private String imageBase64;
	
	private String username;
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public CommentDTO() {}
	
	public CommentDTO(Long ID, String text, Date date, String imageBase64, String username) {
		this.ID = ID;
		this.text = text;
		this.date = date;
		this.imageBase64 = imageBase64;
		this.username = username;
	}

	public CommentDTO(String text) {
		this.text = text;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}

	

	
	
}
