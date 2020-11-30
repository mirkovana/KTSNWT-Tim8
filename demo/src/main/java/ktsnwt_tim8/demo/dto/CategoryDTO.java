package ktsnwt_tim8.demo.dto;

import ktsnwt_tim8.demo.model.Category;

public class CategoryDTO {

	
	private String name;

	public String getName() {
		return name;
	}

	public CategoryDTO() {
	 
	}
	
	public CategoryDTO(Category category) {
		 this.name=category.getName();
	}


	public void setName(String name) {
		this.name = name;
	}
}
