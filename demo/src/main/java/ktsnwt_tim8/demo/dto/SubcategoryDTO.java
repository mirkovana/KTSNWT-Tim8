package ktsnwt_tim8.demo.dto;

import ktsnwt_tim8.demo.model.Subcategory;

public class SubcategoryDTO {
	private String name;
	private CategoryDTO category;	
	
	
	
	public SubcategoryDTO() {
		 
	}
	
	public SubcategoryDTO(Subcategory subcategory) {
		 this.name= subcategory.getName();
		 category= new CategoryDTO(subcategory.getCategory());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

}
