package ktsnwt_tim8.demo.dto;


public class RatingDTO {

	private Long ID;
	

	private int rating;

	public RatingDTO() {}
	
	
	
	public RatingDTO(Long iD, int rating) {
		super();
		ID = iD;
		this.rating = rating;
	}




	public Long getID() {
		return ID;
	}


	public void setID(Long iD) {
		ID = iD;
	}


	public int getRating() {
		return rating;
	}


	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
	
	
}
