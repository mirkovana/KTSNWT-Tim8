package ktsnwt_tim8.demo.dto;

import javax.validation.constraints.NotBlank;

import ktsnwt_tim8.demo.model.Offer;

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
	
	@NotBlank(message = "Place cannot be empty.")
	private String place;
	
	public OfferDTO() {}
	
	//zbog validacije prilikom dodavanja novog i izmene 
	public OfferDTO(Long ID, @NotBlank(message = "Title cannot be empty.") String title, @NotBlank(message = "Description cannot be empty.") String description, double avgRating, int nmbOfRatings, double lat, double lon,
			@NotBlank(message = "Place cannot be empty.") String place) {
		this.ID = ID;
		this.title = title;
		this.description = description;
		this.avgRating = avgRating;
		this.nmbOfRatings = nmbOfRatings;
		this.lat = lat;
		this.lon = lon;
		this.place = place;
	}
	
	public OfferDTO(Offer offer) {
		this.ID = offer.getID();
		this.title = offer.getTitle();
		this.description = offer.getDescription();
		this.avgRating = offer.getAvgRating();
		this.nmbOfRatings = offer.getNmbOfRatings();
		this.lat = offer.getLat();
		this.lon = offer.getLon();
		this.place = offer.getPlace();
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

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
}
