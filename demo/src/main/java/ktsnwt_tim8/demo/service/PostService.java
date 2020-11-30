package ktsnwt_tim8.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Post;
import ktsnwt_tim8.demo.repository.PostRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository repo;
	
	
	@JsonIgnore
	public List<Post> listAll() {
		return repo.findAll();
	}

	public Post save(Post post) {
		return repo.save(post);

	}

	public Post get(Long id) {
		return repo.findById(id).get();
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
	
	public List<Post> findAllByOffer(Offer offer) {
		return repo.findAllByOffer(offer);
	}
}
