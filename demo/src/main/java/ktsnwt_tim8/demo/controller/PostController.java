package ktsnwt_tim8.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import ktsnwt_tim8.demo.helper.PostMapper;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Post;
import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.repository.PostRepository;
import ktsnwt_tim8.demo.service.EmailService;
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
	
	@Autowired
	private EmailService emailService;
	
	private static PostMapper mapper = new PostMapper();

	/* ISPISIVANJE SVIH POSTOVA ZA PONUDU */
	@GetMapping(value = "/{idOffer}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ResponseEntity<List<PostDTO>> getAllPosts(@PathVariable Long idOffer, Pageable pageable) {
		Offer offer = offerService.get(idOffer);
		Page<Post> posts = service.findAllByOffer1(offer, pageable);
		List<PostDTO> postsDTO = new ArrayList<PostDTO>();
		
		for (Post p: posts) {
			postsDTO.add(mapper.toDto(p));
		}
		
		Page<PostDTO> postsPageDTO = new PageImpl<>(postsDTO, posts.getPageable(), posts.getTotalElements());
		List<PostDTO> lista = new ArrayList<PostDTO>();
		for(PostDTO p : postsPageDTO) {
			lista.add(p);
		}
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
//	public Page<Post> getAllByOffer(@PathVariable Long idOffer) {
//		Offer offer = offerService.get(idOffer);
//
//		
//		
//		return service.findAllByOffer1(offer);
//	}

	/* DODAVANJE NOVOG POSTA */
	@PostMapping(value = "/{idOffer}",consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PostDTO> savePost(@PathVariable Long idOffer,@Valid @RequestBody PostDTO postDTO) throws Exception {

		Date date = new Date();
		Post post = new Post();
		 
		if(postDTO.getContent().isEmpty()) {
			//throw new Exception("Content cannot be empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		post.setContent(postDTO.getContent());
		post.setDate(date);
		Offer offer = offerService.get(idOffer);
		post.setOffer(offer); 
		
		
		if(postDTO.getTitle().isEmpty()) {
			//throw new Exception("Title cannot be empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		post.setTitle(postDTO.getTitle());
		
		post = service.save(post);
		
		for (RegisteredUser user : offer.getUsers()) {
			emailService.sendEmailNotification(user.getEmail(), post);
			System.out.println("USAO");
		
		}
		return new ResponseEntity<>(new PostDTO(post), HttpStatus.CREATED);
	}

	/* BRISANJE POSTA */
	@DeleteMapping(value = "/{idPost}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> deletePost(@PathVariable Long idPost) {

		Post post = service.get(idPost);
		List<Post> posts = service.listAll();

		posts.remove(post);
		try {
		service.delete(idPost);
		}
		catch  (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		 return new ResponseEntity<>(HttpStatus.OK);
		//return posts;
	}
	
	/*IZMENA POSTA*/
	@PutMapping(value = "/{idPost}", consumes = "application/json")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PostDTO> updatePost(@PathVariable Long idPost,@Valid @RequestBody PostDTO postUpdated)
			throws NotFoundException, Exception{
		
		Date date = new Date();
		if(postUpdated.getContent().isEmpty()) {
			//throw new Exception("Content cannot be empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if(postUpdated.getTitle().isEmpty()) {
			//throw new Exception("Title cannot be empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Post post = repository.getOne(idPost);
		post.setDate(date);
		post.setContent(postUpdated.getContent());
		post.setTitle(postUpdated.getTitle());
		repository.save(post);
		
		return new ResponseEntity<>(new PostDTO(post), HttpStatus.OK);
		/*return repository.findById(idPost).map(post -> {
			post.setDate(date);
			post.setContent(postUpdated.getContent());
			post.setTitle(postUpdated.getTitle());
			return repository.save(post);
		}).orElseThrow(() -> new NotFoundException("Post not found with id " + idPost));*/

	}
}
