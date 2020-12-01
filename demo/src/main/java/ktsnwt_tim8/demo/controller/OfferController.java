package ktsnwt_tim8.demo.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import ktsnwt_tim8.demo.dto.OfferDTO;
import ktsnwt_tim8.demo.dto.PostDTO;
import ktsnwt_tim8.demo.dto.UserDTO;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.OfferImage;
import ktsnwt_tim8.demo.model.Post;
import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.model.Subcategory;
import ktsnwt_tim8.demo.model.User;
import ktsnwt_tim8.demo.repository.OfferRepository;
import ktsnwt_tim8.demo.service.OfferImageService;
import ktsnwt_tim8.demo.service.OfferService;
import ktsnwt_tim8.demo.service.PostService;
import ktsnwt_tim8.demo.service.SubcategoryService;
import ktsnwt_tim8.demo.service.UserService;

@RestController
@RequestMapping(value = "/api/offers")
public class OfferController {
	
	@Autowired
	private OfferRepository repository;
	
	@Autowired
	private OfferService service;
	
	@Autowired
	private PostService servicePost;
	
	@Autowired
	private OfferImageService serviceOfferImage;
	
	@Autowired
	private SubcategoryService serviceSubcategory;
	
	@Autowired
	private UserService serviceUser;
	
	/*ISPISIVANJE SVIH PONUDA SA PAGINACIJOM*/
	@GetMapping
	public Page<Offer> findAllPageable(){
		return service.findAllPageable();
	}
	
	/*ISPISIVANJE SVIH PONUDA*/
	/*@GetMapping
	public List<Offer> getAllOffers() {
		List<Offer> listOffers = service.listAll();
		return listOffers;
	}*/
	
	/*DODAVANJE NOVE PONUDE*/
	@PostMapping(value = "/{idSubcategory}", consumes = "application/json")
	public ResponseEntity<OfferDTO> saveOffer(@PathVariable Long idSubcategory, @RequestBody OfferDTO offerDTO) {
		Offer offer = new Offer();
		offer.setDescription(offerDTO.getDescription());
		offer.setLat(offerDTO.getLat());
		offer.setLon(offerDTO.getLon());
		offer.setTitle(offerDTO.getTitle());
		Subcategory subcategory = serviceSubcategory.get(idSubcategory);
		offer.setSubcategory(subcategory);
		
		offer = service.save(offer);
		return new ResponseEntity<>(new OfferDTO(offer), HttpStatus.CREATED);
	}
	
	/*IZMENA PONUDE*/
	@PutMapping(value = "/{idOffer}", consumes = "application/json")
	public Offer updateOffer(@PathVariable Long idOffer, @RequestBody OfferDTO offerUpdated)
			throws NotFoundException {
		return repository.findById(idOffer).map(offer -> {
			//offer.setAvgRating(offerUpdated.getAvgRating());
			offer.setDescription(offerUpdated.getDescription());
			//offer.setLat(offerUpdated.getLat());
			//offer.setLon(offerUpdated.getLon());
			//offer.setNmbOfRatings(offerUpdated.getNmbOfRatings());
			offer.setTitle(offerUpdated.getTitle());
			return repository.save(offer);
		}).orElseThrow(() -> new NotFoundException("Offer not found with id " + idOffer));

	}
	
	/*BRISANJE PONUDE*/
	@DeleteMapping(value = "/{idOffer}")
	public List<Offer> deleteOffer(@PathVariable Long idOffer) {
		Offer offer = service.get(idOffer);
		List<Offer> offers = service.listAll();

		offers.remove(offer);
		//brisanje slika vezanih za ponudu i postova
		List<Post> posts = servicePost.findAllByOffer(offer);
		for(Post p : posts) {
			servicePost.delete(p.getID());
		}
		List<OfferImage> offerimages = serviceOfferImage.findAllByOffer(offer);
		for(OfferImage oi : offerimages) {
			serviceOfferImage.delete(oi.getID());
		}
		service.delete(idOffer);

		return offers;
	}	
	
	@PostMapping(value = "/subscribe/{idOffer}", consumes = "application/json")
	public ResponseEntity<UserDTO> subscribeUser(@PathVariable Long idOffer, @RequestBody UserDTO subUser){
		Offer offer = service.get(idOffer);
		User user = serviceUser.findByUsername(subUser.getUsername());
		
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(offer == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		offer.getUsers().add((RegisteredUser) user);
		service.save(offer);
		
		return null;
	}
	
	
	
	
}
