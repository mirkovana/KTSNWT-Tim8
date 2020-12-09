package ktsnwt_tim8.demo.controller;

import java.util.List;

import javax.validation.Valid;

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

import ktsnwt_tim8.demo.dto.SubcategoryDTO;
import ktsnwt_tim8.demo.model.Category;
import ktsnwt_tim8.demo.model.Subcategory;
import ktsnwt_tim8.demo.service.CategoryService;
import ktsnwt_tim8.demo.service.SubcategoryService;

@RestController
@RequestMapping(value = "/api/subcategories")
public class SubcategoryController {
	@Autowired
	private SubcategoryService service;

	@Autowired
	private CategoryService serviceCategory;

	/* ISPISIVANJE SVIH POD KATEGORIJA */
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Subcategory> getAllSubcategories() {
		List<Subcategory> listSubcategories = service.listAll();
		return listSubcategories;
	}

	/* DODAVANJE NOVE POD KATEGORIJE */
	@PostMapping(value = "/{idCategory}", consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<SubcategoryDTO> saveSubcategory(@PathVariable Long idCategory,
			@Valid @RequestBody SubcategoryDTO subcategoryDTO) throws Exception {

		Category category = serviceCategory.get(idCategory);
		Subcategory subcategory = new Subcategory();
		if (subcategoryDTO.getName().isEmpty()) {
			throw new Exception("Name of subcategory cannot be empty");
		}

		subcategory.setName(subcategoryDTO.getName());
		subcategory.setCategory(category);
		subcategory = service.save(subcategory);
		return new ResponseEntity<>(new SubcategoryDTO(subcategory), HttpStatus.CREATED);
	}

	/* BRISANJE KATEGORIJE */
	@DeleteMapping(value = "/{idSubcategory}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Subcategory> deleteSubcategory(@PathVariable Long idSubcategory) {

		Subcategory subcat = service.get(idSubcategory);
		List<Subcategory> subcategories = service.listAll();

		subcategories.remove(subcat);
		service.delete(idSubcategory);

		return subcategories;
	}
}
