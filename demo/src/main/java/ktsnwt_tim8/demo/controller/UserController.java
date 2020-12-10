package ktsnwt_tim8.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ktsnwt_tim8.demo.dto.OfferDTO;
import ktsnwt_tim8.demo.dto.UserDTO;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.model.User;
import ktsnwt_tim8.demo.service.UserService;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

	@Autowired
	private UserService service;

	/* REGISTRACIJA KORISNIKA*/
	@PostMapping(consumes = "application/json")
	public ResponseEntity<UserDTO> saveSubcategory(@RequestBody UserDTO usersDTO) throws Exception {

		RegisteredUser user = new RegisteredUser();
	
		user.setName(usersDTO.getName());

		if(usersDTO.getPassword().isEmpty()) {
			throw new Exception("Password cannot be empty");
		}
		user.setPassword(usersDTO.getPassword());
		user.setSurname(usersDTO.getSurname());

		if(usersDTO.getUsername().isEmpty()) {
			throw new Exception("Username cannot be empty");
		}
		user.setUsername(usersDTO.getUsername());
		
		
		user = (RegisteredUser) service.save(user);
		return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
	}
	
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/getSubscriptions")
	public ResponseEntity<Page<OfferDTO>> getUserSubscriptions(Pageable page){//
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Set<Offer> subscriptions = ((RegisteredUser)user).getSubscriptions();
		List<OfferDTO> subs = new ArrayList<OfferDTO>();
		
		for (Offer offer : subscriptions) {
			System.out.println(offer.getID());
			OfferDTO off = new OfferDTO(offer.getID(), offer.getTitle(), offer.getDescription(), offer.getAvgRating(), offer.getNmbOfRatings(), offer.getLat(), offer.getLon(), offer.getPlace());
			subs.add(off);
		}
		
		int start = (int) page.getOffset();
		int end = (start + page.getPageSize()) > subs.size() ? subs.size() : (start + page.getPageSize());
		Page<OfferDTO> pages = new PageImpl<OfferDTO>(subs.subList(start, end), page, subs.size());
		return new ResponseEntity<>(pages, HttpStatus.OK);
	}

}
