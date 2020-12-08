package ktsnwt_tim8.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.User;
import ktsnwt_tim8.demo.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository repo;
	
	
	@JsonIgnore
	public List<User> listAll() {
		return repo.findAll();
	}

	public User save(User user) {
		return repo.save(user);

	}

	public User get(Long id) {
		return repo.findById(id).get();
	}
	
	public User findByUsername(String username) {
		return repo.findByUsername(username);
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}

}
