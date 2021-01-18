package ktsnwt_tim8.demo.dto;

import javax.validation.constraints.NotBlank;

import ktsnwt_tim8.demo.model.Subcategory;

public class SubcategoryDTO {
	@NotBlank(message = "Name cannot be empty.")
	private String name;

	private Long ID;

	private Long catID;
	
	public SubcategoryDTO() {

	}
	

	public SubcategoryDTO(@NotBlank(message = "Name cannot be empty.") String name, Long iD) {

		this.name = name;
		this.ID = iD;
	}

	
	public SubcategoryDTO(String name, Long iD, Long catId) {
		this.name = name;
		this.ID = iD;
		this.catID = catId;
	}

	
	public SubcategoryDTO(Subcategory subcategory) {
		this.name = subcategory.getName();
		this.ID=subcategory.getID();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}


	public Long getCatID() {
		return catID;
	}


	public void setCatID(Long catID) {
		this.catID = catID;
	}
	
	

}

