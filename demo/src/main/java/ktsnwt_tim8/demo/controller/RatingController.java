package ktsnwt_tim8.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ktsnwt_tim8.demo.dto.RatingDTO;
import ktsnwt_tim8.demo.helper.RatingMapper;
import ktsnwt_tim8.demo.model.Rating;
import ktsnwt_tim8.demo.service.RatingService;

@RestController
@RequestMapping(value = "/api/ratings")
public class RatingController {

	@Autowired
	private RatingService ratingService;
	
	private static RatingMapper mapper = new RatingMapper();
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Page<RatingDTO>> getRatingsFromPost(@PathVariable Long id, Pageable page){
		
		Page<Rating> ratings;
		try {
			ratings = ratingService.findAllByOfferID(id, page);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		List<RatingDTO> ratingsDTO = new ArrayList<RatingDTO>();
		
		for (Rating r: ratings) {
			ratingsDTO.add(mapper.toDto(r));
		}
		
		Page<RatingDTO> pageRatingsDTO = new PageImpl<>(ratingsDTO, ratings.getPageable(), ratings.getTotalElements());

        return new ResponseEntity<>(pageRatingsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/rated/{offerId}", method=RequestMethod.GET)
	public ResponseEntity<RatingDTO> getRatingFromUser(@PathVariable Long offerId){
		Rating r;
		try{
			r = ratingService.findUsersRating(offerId);
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		RatingDTO rDTO = (r == null) ? null : mapper.toDto(r);
		return new ResponseEntity<>(rDTO, HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/{offerId}", method=RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<RatingDTO> addRating(@PathVariable Long offerId, @RequestBody RatingDTO ratingDTO){
			
		Rating r;
		try {
			r = ratingService.create(offerId, ratingDTO);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		
        return new ResponseEntity<>(mapper.toDto(r), HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/{ratingId}", method=RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<RatingDTO> updateRating(@PathVariable Long ratingId, @RequestBody RatingDTO ratingDTO){
		
		Rating r;
		try {
			r = ratingService.updateRating(ratingId, ratingDTO);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		
        return new ResponseEntity<>(mapper.toDto(r), HttpStatus.OK);
	}
	
	@CrossOrigin
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value="/{ratingId}", method=RequestMethod.DELETE)	
	public ResponseEntity<RatingDTO> deleteRating(@PathVariable Long ratingId){
		
		try {
			ratingService.deleteRating(ratingId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
        return new ResponseEntity<>(null, HttpStatus.OK);
	}

	
	
	
}
