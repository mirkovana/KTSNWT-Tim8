package ktsnwt_tim8.demo.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import ktsnwt_tim8.demo.model.Post;

public class PostDTO {
	
	private Long ID;
	@NotBlank(message = "Title cannot be empty.")
	private String title;
	@NotBlank(message = "Content cannot be empty.")
	private String content;
	private Date date;

	public PostDTO(Post post) {
		super();
		this.ID=post.getID();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.date = post.getDate();
	}

	public PostDTO(Long iD, @NotBlank(message = "Title cannot be empty.") String title, @NotBlank(message = "Content cannot be empty.") String content, Date date) {

		this.ID = iD;
		this.title = title;
		this.content = content;
		this.date = date;
	}

	public PostDTO() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}
}
