package ktsnwt_tim8.demo.dto;

import java.util.Date;

import ktsnwt_tim8.demo.model.Post;

public class PostDTO {
	private String title;
	private String content;
	private Date date;
	
	public PostDTO(Post post) {
		super();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.date = post.getDate();
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
}
