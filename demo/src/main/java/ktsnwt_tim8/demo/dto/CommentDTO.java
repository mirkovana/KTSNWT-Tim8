package ktsnwt_tim8.demo.dto;

import java.util.Date;



public class CommentDTO {

	private Long ID;
	
	private String text;
	
	private Date date;

	
	public CommentDTO() {}
	
	public CommentDTO(Long ID, String text, Date date) {
		this.ID = ID;
		this.text = text;
		this.date = date;
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

	
	
}
