package ktsnwt_tim8.demo.dto;

import javax.validation.constraints.NotBlank;

public class CategoryDTO {


	@NotBlank(message = "Name cannot be empty.")
	private String name;
	private Long ID;

	

	public String getName() {
		return name;
	}

	public CategoryDTO() {
	 
	}
	
	public CategoryDTO(@NotBlank(message = "Name cannot be empty.") String name, Long ID) {
		 this.name=name;
		 this.ID=ID;
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
