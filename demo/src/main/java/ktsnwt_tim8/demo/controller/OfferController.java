package ktsnwt_tim8.demo.controller;

import java.util.ArrayList;
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
import org.springframework.web.server.ResponseStatusException;

import javassist.NotFoundException;
import ktsnwt_tim8.demo.dto.FilterDTO;
import ktsnwt_tim8.demo.dto.OfferDTO;
import ktsnwt_tim8.demo.helper.OfferMapper;
import ktsnwt_tim8.demo.model.Comment;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.OfferImage;
import ktsnwt_tim8.demo.model.Post;
import ktsnwt_tim8.demo.model.Rating;
import ktsnwt_tim8.demo.model.Subcategory;
import ktsnwt_tim8.demo.repository.OfferRepository;
import ktsnwt_tim8.demo.service.CommentService;
import ktsnwt_tim8.demo.service.OfferImageService;
import ktsnwt_tim8.demo.service.OfferService;
import ktsnwt_tim8.demo.service.PostService;
import ktsnwt_tim8.demo.service.RatingService;
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
	private CommentService serviceComment;
	
	@Autowired
	private RatingService serviceRating;

	@Autowired
	private SubcategoryService serviceSubcategory;

	@Autowired
	private UserService serviceUser;

	private static OfferMapper mapper = new OfferMapper();

	/* ISPISIVANJE SVIH PONUDA SA PAGINACIJOM */
	/*
	 * @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	 * 
	 * @GetMapping public Page<Offer> findAllPageable(Pageable page){ return
	 * service.findAllPageable(page); }
	 */

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	@GetMapping
	public ResponseEntity<List<OfferDTO>> getAllOffers(Pageable pageable) {
		Page<Offer> offers = service.findAllPageable(pageable);
		List<OfferDTO> offersDTO = new ArrayList<OfferDTO>();

		for (Offer o : offers) {
			offersDTO.add(mapper.toDto(o));
		}

		Page<OfferDTO> pageOffersDTO = new PageImpl<>(offersDTO, offers.getPageable(), offers.getTotalElements());
		List<OfferDTO> lista = new ArrayList<OfferDTO>();
		for(OfferDTO o : pageOffersDTO) {
			lista.add(o);
		}
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	/* ISPISIVANJE SVIH PONUDA */
	/*
	 * @GetMapping public List<Offer> getAllOffers() { List<Offer> listOffers =
	 * service.listAll(); return listOffers; }
	 */

	/* DODAVANJE NOVE PONUDE */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/{idSubcategory}", consumes = "application/json")
	public ResponseEntity<OfferDTO> saveOffer(@PathVariable Long idSubcategory, @Valid @RequestBody OfferDTO offerDTO)
			throws Exception {
		Offer offer = new Offer();
		if (offerDTO.getDescription().isEmpty()) {
			//throw new Exception("Description cannot be empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		offer.setDescription(offerDTO.getDescription());
		offer.setLat(offerDTO.getLat());
		offer.setLon(offerDTO.getLon());
		if (offerDTO.getTitle().isEmpty()) {
			//throw new Exception("Title cannot be empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		offer.setTitle(offerDTO.getTitle());
		
		if (offerDTO.getPlace().isEmpty()) {
			//throw new Exception("Place cannot be empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		offer.setPlace(offerDTO.getPlace());
		
		Subcategory subcategory = serviceSubcategory.get(idSubcategory);
		offer.setSubcategory(subcategory);
		offer.setAvgRating(0);
		offer.setNmbOfRatings(0);

		offer = service.save(offer);
		return new ResponseEntity<>(new OfferDTO(offer.getID(), offer.getTitle(), offer.getDescription(),
				offer.getAvgRating(), offer.getNmbOfRatings(), offer.getLat(), offer.getLon(), offer.getPlace()), HttpStatus.CREATED);
	}

	/* IZMENA PONUDE */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/{idOffer}", consumes = "application/json")
	public ResponseEntity<OfferDTO> updateOffer(@PathVariable Long idOffer, @Valid @RequestBody OfferDTO offerUpdated)
			throws NotFoundException, Exception {
		if (offerUpdated.getDescription().isEmpty()) {
			//throw new Exception("Description cannot be empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (offerUpdated.getTitle().isEmpty()) {
			//throw new Exception("Title cannot be empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (offerUpdated.getPlace().isEmpty()) {
			//throw new Exception("Place cannot be empty");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	    Offer offer = repository.getOne(idOffer);
		offer.setDescription(offerUpdated.getDescription());
		offer.setPlace(offerUpdated.getPlace());
		offer.setTitle(offerUpdated.getTitle());
		repository.save(offer);
		return new ResponseEntity<>(new OfferDTO(offer.getID(), offer.getTitle(), offer.getDescription(),
				offer.getAvgRating(), offer.getNmbOfRatings(), offer.getLat(), offer.getLon(), offer.getPlace()), HttpStatus.OK);
		/*return repository.findById(idOffer).map(offer -> {
			// offer.setAvgRating(offerUpdated.getAvgRating());
			offer.setDescription(offerUpdated.getDescription());
			// offer.setLat(offerUpdated.getLat());
			// offer.setLon(offerUpdated.getLon());
			// offer.setNmbOfRatings(offerUpdated.getNmbOfRatings());
			offer.setPlace(offerUpdated.getPlace());
			offer.setTitle(offerUpdated.getTitle());
			return repository.save(offer);
		}).orElseThrow(() -> new NotFoundException("Offer not found with id " + idOffer));*/

	}

	/* BRISANJE PONUDE */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{idOffer}")
	public ResponseEntity<Void>  deleteOffer(@PathVariable Long idOffer) {
		Offer offer = service.get(idOffer);
		List<Offer> offers = service.listAll();

		offers.remove(offer);
		// brisanje slika vezanih za ponudu i postova
		List<Post> posts = servicePost.findAllByOffer(offer);
		for (Post p : posts) {
			servicePost.delete(p.getID());
		}
		List<OfferImage> offerimages = serviceOfferImage.findAllByOffer(offer);
		for (OfferImage oi : offerimages) {
			try {
				serviceOfferImage.deleteImage(oi.getID());
				//IZMENJENO SA delete na deleteImage
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		for (Comment com: offer.getComments()) {
			serviceComment.deleteCommentById(com);
		}
		for (Rating r: offer.getRatings()) {
			serviceRating.deleteRatingById(r.getID());
		}
		try {
		service.delete(idOffer);
		}		catch  (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		 return new ResponseEntity<>(HttpStatus.OK);
		//return offers;
	}

	/* SUBSCRIBING TO OFFER */
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value = "/subscribe/{idOffer}")
	public ResponseEntity<OfferDTO> subscribeUser(@PathVariable Long idOffer) throws Exception {
		Offer offer;
		try {
			offer = service.subscribe(idOffer);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

//		service.save(offer);
		OfferDTO ret = new OfferDTO(offer);
//		
//		Offer off = service.get(idOffer);
//		System.out.println(off.getUsers().size());

		return new ResponseEntity<>(ret, HttpStatus.CREATED);
	}

	/* CANCELING OFFER SUBSCRIPTION */
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping(value = "/unsubscribe/{idOffer}")
	public ResponseEntity<Void> unsubscribeUser(@PathVariable Long idOffer) {
		Offer offer;
		try {
			offer = service.deleteSubscriber(idOffer);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		service.save(offer);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/filter")
	public ResponseEntity<Page<OfferDTO>> filter(@RequestBody FilterDTO filterDTO, Pageable pageable) {
		
		Page<Offer> offers = service.filter(filterDTO, pageable);
		
		List<OfferDTO> offersDTO = new ArrayList<OfferDTO>();

		for (Offer o : offers) {
			offersDTO.add(mapper.toDto(o));
		}

		Page<OfferDTO> pageOffersDTO = new PageImpl<>(offersDTO, offers.getPageable(), offers.getTotalElements());

		return new ResponseEntity<>(pageOffersDTO, HttpStatus.OK);
	}

}
