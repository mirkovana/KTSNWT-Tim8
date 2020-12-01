package ktsnwt_tim8.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ktsnwt_tim8.demo.dto.CommentDTO;
import ktsnwt_tim8.demo.dto.RatingDTO;
import ktsnwt_tim8.demo.helper.RatingMapper;
import ktsnwt_tim8.demo.model.Comment;
import ktsnwt_tim8.demo.model.Rating;
import ktsnwt_tim8.demo.service.RatingService;

@RestController
@RequestMapping(value = "/api/ratings")
public class RatingController {

	@Autowired
	private RatingService ratingService;
	
	private static RatingMapper mapper = new RatingMapper();
	
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<RatingDTO>> getAllRatings(Pageable page){
		Page<Rating> ratings = ratingService.findAll(page);
		List<RatingDTO> ratingsDTO = new ArrayList<RatingDTO>();
		for (Rating r: ratings) {
			ratingsDTO.add(mapper.toDto(r));
		}
		Page<RatingDTO> pageRatingsDTO = new PageImpl<>(ratingsDTO, ratings.getPageable(), ratings.getTotalElements());
        return new ResponseEntity<>(pageRatingsDTO, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Page<RatingDTO>> getRatingsFromPost(@PathVariable Long id, Pageable page){
		
		Page<Rating> ratings = ratingService.findAllByOfferID(id, page);
		List<RatingDTO> ratingsDTO = new ArrayList<RatingDTO>();
		
		for (Rating r: ratings) {
			ratingsDTO.add(mapper.toDto(r));
		}
		
		Page<RatingDTO> pageRatingsDTO = new PageImpl<>(ratingsDTO, ratings.getPageable(), ratings.getTotalElements());

        return new ResponseEntity<>(pageRatingsDTO, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<RatingDTO> addRating(@PathVariable Long id, @RequestBody RatingDTO ratingDTO){
		
		// da li je string
		
		Rating rating = mapper.toEntity(ratingDTO);
		
		Rating r;
		try {
			r = ratingService.create(id, rating);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		
        return new ResponseEntity<>(mapper.toDto(r), HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value="/{ratingId}", method=RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<RatingDTO> updateRating(@PathVariable Long ratingId, @RequestBody RatingDTO ratingDTO){
		
		Rating r = mapper.toEntity(ratingDTO);
		try {
			r = ratingService.updateRating(ratingId, r);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
		
        return new ResponseEntity<>(mapper.toDto(r), HttpStatus.OK);
	}
	
	
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
