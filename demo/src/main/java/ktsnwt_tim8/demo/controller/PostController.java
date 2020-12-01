package ktsnwt_tim8.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import ktsnwt_tim8.demo.dto.PostDTO;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Post;
import ktsnwt_tim8.demo.repository.PostRepository;
import ktsnwt_tim8.demo.service.OfferService;
import ktsnwt_tim8.demo.service.PostService;

@RestController
@RequestMapping(value = "/api/posts")
public class PostController {
	@Autowired
	private PostService service;
	
	@Autowired
	private PostRepository repository;

	@Autowired
	private OfferService offerService;

	/* ISPISIVANJE SVIH POSTOVA ZA PONUDU */
	@GetMapping(value = "/{idOffer}")
	public Page<Post> getAllByOffer(@PathVariable Long idOffer) {
		Offer offer = offerService.get(idOffer);

		
		
		return service.findAllByOffer1(offer);
	}

	/* DODAVANJE NOVOG POSTA */
	@PostMapping(value = "/{idOffer}",consumes = "application/json")
	public ResponseEntity<PostDTO> savePost(@PathVariable Long idOffer, @RequestBody PostDTO postDTO) {

		Post post = new Post();
		post.setContent(postDTO.getContent());
		post.setDate(postDTO.getDate());
		Offer offer = offerService.get(idOffer);
		post.setOffer(offer); 
		post.setTitle(postDTO.getTitle());
		
		post = service.save(post);
		return new ResponseEntity<>(new PostDTO(post), HttpStatus.CREATED);
	}

	/* BRISANJE POSTA */
	@DeleteMapping(value = "/{idPost}")
	public List<Post> deletePost(@PathVariable Long idPost) {

		Post post = service.get(idPost);
		List<Post> posts = service.listAll();

		posts.remove(post);
		service.delete(idPost);

		return posts;
	}
	
	/*IZMENA POSTA*/
	@PutMapping(value = "/{idPost}", consumes = "application/json")
	public Post updatePost(@PathVariable Long idPost, @RequestBody PostDTO postUpdated)
			throws NotFoundException {
		return repository.findById(idPost).map(post -> {
			post.setContent(postUpdated.getContent());
			post.setTitle(postUpdated.getTitle());
			return repository.save(post);
		}).orElseThrow(() -> new NotFoundException("Post not found with id " + idPost));

	}
}
