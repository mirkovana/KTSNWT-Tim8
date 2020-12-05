package ktsnwt_tim8.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Category> getAllCategories() {
		List<Category> listCategories = service.listAll();
		return listCategories;
	}

	/* DODAVANJE NOVE KATEGORIJE */
	@PostMapping(consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO) throws Exception {

		Category category = new Category();

		if (categoryDTO.getName().isEmpty()) {
			throw new Exception("Name of category cannot be empty");
		}

		category.setName(categoryDTO.getName());
		try {
			category = service.save(category);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(new CategoryDTO(category.getName()), HttpStatus.CREATED);
	}

	/* BRISANJE KATEGORIJE */
	@DeleteMapping(value = "/{idCategory}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Category> deleteCategory(@PathVariable Long idCategory) {

		Category cat = service.get(idCategory);
		List<Category> categories = service.listAll();

		categories.remove(cat);
		service.delete(idCategory);

		return categories;
	}

}
