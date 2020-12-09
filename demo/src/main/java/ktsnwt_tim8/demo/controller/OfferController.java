package ktsnwt_tim8.demo.controller;

import java.util.ArrayList;

import java.util.Iterator;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.context.SecurityContextHolder;

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
import ktsnwt_tim8.demo.dto.OfferDTO;
import ktsnwt_tim8.demo.dto.UserDTO;
import ktsnwt_tim8.demo.helper.OfferMapper;
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
	public ResponseEntity<Page<OfferDTO>> getAllOffers(Pageable pageable) {
		Page<Offer> offers = service.findAllPageable(pageable);
		List<OfferDTO> offersDTO = new ArrayList<OfferDTO>();

		for (Offer o : offers) {
			offersDTO.add(mapper.toDto(o));
		}

		Page<OfferDTO> pageOffersDTO = new PageImpl<>(offersDTO, offers.getPageable(), offers.getTotalElements());

		return new ResponseEntity<>(pageOffersDTO, HttpStatus.OK);
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
			throw new Exception("Description cannot be empty");
		}
		offer.setDescription(offerDTO.getDescription());
		offer.setLat(offerDTO.getLat());
		offer.setLon(offerDTO.getLon());
		if (offerDTO.getTitle().isEmpty()) {
			throw new Exception("Title cannot be empty");
		}
		offer.setTitle(offerDTO.getTitle());
		Subcategory subcategory = serviceSubcategory.get(idSubcategory);
		offer.setSubcategory(subcategory);

		offer = service.save(offer);
		return new ResponseEntity<>(new OfferDTO(offer.getID(), offer.getTitle(), offer.getDescription(),
				offer.getAvgRating(), offer.getNmbOfRatings(), offer.getLat(), offer.getLon()), HttpStatus.CREATED);
	}

	/* IZMENA PONUDE */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/{idOffer}", consumes = "application/json")
	public Offer updateOffer(@PathVariable Long idOffer, @Valid @RequestBody OfferDTO offerUpdated)
			throws NotFoundException, Exception {
		if (offerUpdated.getDescription().isEmpty()) {
			throw new Exception("Description cannot be empty");
		}
		if (offerUpdated.getTitle().isEmpty()) {
			throw new Exception("Title cannot be empty");
		}
		return repository.findById(idOffer).map(offer -> {
			// offer.setAvgRating(offerUpdated.getAvgRating());
			offer.setDescription(offerUpdated.getDescription());
			// offer.setLat(offerUpdated.getLat());
			// offer.setLon(offerUpdated.getLon());
			// offer.setNmbOfRatings(offerUpdated.getNmbOfRatings());
			offer.setTitle(offerUpdated.getTitle());
			return repository.save(offer);
		}).orElseThrow(() -> new NotFoundException("Offer not found with id " + idOffer));

	}

	/* BRISANJE PONUDE */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/{idOffer}")
	public List<Offer> deleteOffer(@PathVariable Long idOffer) {
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
			serviceOfferImage.delete(oi.getID());
		}
		service.delete(idOffer);

		return offers;
	}

	/* SUBSCRIBING TO OFFER */
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value = "/subscribe/{idOffer}")
	public ResponseEntity<UserDTO> subscribeUser(@PathVariable Long idOffer) throws Exception {
		Offer offer;
		try {
			offer = service.subscribe(idOffer);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

		service.save(offer);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/* CANCELING OFFER SUBSCRIPTION */
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping(value = "/unsubscribe/{idOffer}")
	public ResponseEntity<UserDTO> unsubscribeUser(@PathVariable Long idOffer) {
		Offer offer;
		try {
			offer = service.deleteSubscriber(idOffer);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		service.save(offer);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
