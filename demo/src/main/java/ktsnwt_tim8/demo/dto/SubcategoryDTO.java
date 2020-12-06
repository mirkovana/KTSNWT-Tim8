package ktsnwt_tim8.demo.dto;

import javax.validation.constraints.NotBlank;

import ktsnwt_tim8.demo.model.Subcategory;

public class SubcategoryDTO {
	@NotBlank(message = "Name cannot be empty.")
	private String name;

	private Long ID;

	public SubcategoryDTO() {

	}

	public SubcategoryDTO(@NotBlank(message = "Name cannot be empty.") String name, Long iD) {

		this.name = name;
		this.ID = iD;
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

}
