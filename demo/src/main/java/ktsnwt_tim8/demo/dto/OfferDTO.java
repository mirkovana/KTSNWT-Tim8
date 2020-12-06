package ktsnwt_tim8.demo.dto;

import javax.validation.constraints.NotBlank;

public class OfferDTO {
	private Long ID;
	
	@NotBlank(message = "Title cannot be empty.")
	private String title;
	
	@NotBlank(message = "Description cannot be empty.")
	private String description;
	private double avgRating;
	private int nmbOfRatings;
	private double lat;
	private double lon;
	
	public OfferDTO() {}
	
	//zbog validacije prilikom dodavanja novog i izmene 
	public OfferDTO(Long ID, @NotBlank(message = "Title cannot be empty.") String title, @NotBlank(message = "Description cannot be empty.") String description, double avgRating, int nmbOfRatings, double lat, double lon) {
		this.ID = ID;
		this.title = title;
		this.description = description;
		this.avgRating = avgRating;
		this.nmbOfRatings = nmbOfRatings;
		this.lat = lat;
		this.lon = lon;
	}
	
	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(double avgRating) {
		this.avgRating = avgRating;
	}
	public int getNmbOfRatings() {
		return nmbOfRatings;
	}
	public void setNmbOfRatings(int nmbOfRatings) {
		this.nmbOfRatings = nmbOfRatings;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
}
