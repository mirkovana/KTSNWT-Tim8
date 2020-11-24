package ktsnwt_tim8.demo.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	
	@Column(nullable = false)
	private String title;
	
	@Column
	private String description;
	
	@Column
	private double avgRating;
	
	@Column
	private int nmbOfRatings;
	
	@Column(nullable = false)
	private double lat;
	
	@Column(nullable = false)
	private double lon;
	
	@OneToMany(mappedBy = "offer")
	private Set<Post> posts;
	
	@OneToMany(mappedBy = "offer")
	private Set<OfferImage> images;
	
	@OneToMany(mappedBy = "offer")
	private Set<Comment> comments;
	
	@OneToMany(mappedBy = "offer")
	private Set<Rating> ratings;
	@ManyToMany
	@JoinTable(name = "user_offer", 
    		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
    		inverseJoinColumns = @JoinColumn(name = "offer_id", 
    		referencedColumnName = "id"))
	private Set<RegisteredUser> users;
	
	@ManyToOne
	@JoinColumn(name = "subcategory_id")
	private Subcategory subcategory;
	
	public Offer() {
		super();
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
