package ktsnwt_tim8.demo.dto;

import javax.validation.constraints.NotBlank;

import ktsnwt_tim8.demo.model.Category;

public class CategoryDTO {


	private String name;

	public String getName() {
		return name;
	}

	public CategoryDTO() {
	 
	}
	
	public CategoryDTO(String name) {
		 this.name=name;
	}


	public void setName(String name) {
		this.name = name;
	}
}
