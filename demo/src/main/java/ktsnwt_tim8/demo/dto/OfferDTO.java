package ktsnwt_tim8.demo.dto;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Subcategory;

public class OfferDTO {
	private Long ID;
	private String title;
	private String description;
	private double avgRating;
	private int nmbOfRatings;
	private double lat;
	private double lon;
	private SubcategoryDTO subcategory;
	
	public OfferDTO() {}
	
	public OfferDTO(Offer offer) {
		this.ID = offer.getID();
		this.title = offer.getTitle();
		this.description = offer.getDescription();
		this.avgRating = offer.getAvgRating();
		this.nmbOfRatings = offer.getNmbOfRatings();
		this.lat = offer.getLat();
		this.lon = offer.getLon();
		this.subcategory = new SubcategoryDTO(offer.getSubcategory());
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
	public SubcategoryDTO getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(SubcategoryDTO subcategory) {
		this.subcategory = subcategory;
	}
}
