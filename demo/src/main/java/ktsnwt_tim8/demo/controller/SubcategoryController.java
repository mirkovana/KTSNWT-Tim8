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

import ktsnwt_tim8.demo.dto.SubcategoryDTO;
import ktsnwt_tim8.demo.model.Subcategory;
import ktsnwt_tim8.demo.service.SubcategoryService;

@RestController
@RequestMapping(value = "/api/subcategories")
public class SubcategoryController {
	@Autowired
	private SubcategoryService service;
	
	
	/* ISPISIVANJE SVIH POD KATEGORIJA */
	@GetMapping
	public List<Subcategory> getAllSubcategories() {
		List<Subcategory> listSubcategories = service.listAll();
		return listSubcategories;
	}
	
	
	/*DODAVANJE NOVE KATEGORIJE*/
	
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<SubcategoryDTO> saveSubcategory(@RequestBody SubcategoryDTO subcategoryDTO) {

		Subcategory subcategory = new Subcategory();
		subcategory.setName(subcategoryDTO.getName());
		 
		subcategory = service.save(subcategory);
		return new ResponseEntity<>(new SubcategoryDTO(subcategory), HttpStatus.CREATED);
	}
	
	/*BRISANJE KATEGORIJE*/
		@DeleteMapping(value = "/{idSubcategory}")
		public List<Subcategory> deleteSubcategory(@PathVariable Long idSubcategory) {
	

			Subcategory subcat = service.get(idSubcategory);
			List<Subcategory> subcategories = service.listAll();
			
			subcategories.remove(subcat);
			service.delete(idSubcategory);

			return subcategories;
		}
}
