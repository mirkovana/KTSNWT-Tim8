package ktsnwt_tim8.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ktsnwt_tim8.demo.model.Category;
import ktsnwt_tim8.demo.repository.CategoryRepository;
@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repo;
		
	@JsonIgnore
	public List<Category> listAll() {
		return repo.findAll();
	}

	public Category save(Category category) {
		return repo.save(category);

	}

	public Category get(Long id) {
		return repo.findById(id).get();
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
}
