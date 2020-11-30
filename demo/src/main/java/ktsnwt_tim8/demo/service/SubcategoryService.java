package ktsnwt_tim8.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ktsnwt_tim8.demo.model.Subcategory;
import ktsnwt_tim8.demo.repository.SubcategoryRepository;

@Service
public class SubcategoryService {
	@Autowired
	private SubcategoryRepository repo;
		
	@JsonIgnore
	public List<Subcategory> listAll() {
		return repo.findAll();
	}

	public Subcategory save(Subcategory subcategory) {
		return repo.save(subcategory);

	}

	public Subcategory get(Long id) {
		return repo.findById(id).get();
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
}
