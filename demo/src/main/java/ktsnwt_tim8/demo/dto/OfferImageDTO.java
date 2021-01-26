package ktsnwt_tim8.demo.dto;

public class OfferImageDTO {

	private Long ID;

	private String description;

	private String imageBase64;

	public OfferImageDTO() {

	}

	public OfferImageDTO(String desc) {
		this.description = desc;
	}

	public OfferImageDTO(String desc, Long id) {
		this.description = desc;
		this.ID = id;
	}

	public OfferImageDTO(Long iD, String description, String imageBase64) {
		super();
		ID = iD;
		this.description = description;
		this.imageBase64 = imageBase64;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}

}
