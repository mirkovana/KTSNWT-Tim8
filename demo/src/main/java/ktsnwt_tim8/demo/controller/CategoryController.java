package ktsnwt_tim8.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ktsnwt_tim8.demo.dto.CategoryDTO;
import ktsnwt_tim8.demo.model.Category;
import ktsnwt_tim8.demo.service.CategoryService;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService service;
	
	
	/* ISPISIVANJE SVIH KATEGORIJA */
	@GetMapping
	public List<Category> getAllCategories() {
		List<Category> listCategories = service.listAll();
		return listCategories;
	}
	
	
	/*DODAVANJE NOVE KATEGORIJE*/
	
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO) {

		Category category = new Category();
		category.setName(categoryDTO.getName());
		 
		category = service.save(category);
		return new ResponseEntity<>(new CategoryDTO(category), HttpStatus.CREATED);
	}
	
	/*BRISANJE KATEGORIJE*/
		@DeleteMapping(value = "/{idCategory}")
		public List<Category> deleteCategory(@PathVariable Long idCategory) {
	

			Category cat = service.get(idCategory);
			List<Category> categories = service.listAll();
			
			categories.remove(cat);
			service.delete(idCategory);

			return categories;
		}
}
