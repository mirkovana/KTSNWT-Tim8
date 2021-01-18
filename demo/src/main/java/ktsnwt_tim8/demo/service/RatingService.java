package ktsnwt_tim8.demo.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ktsnwt_tim8.demo.dto.RatingDTO;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Rating;
import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.model.User;
import ktsnwt_tim8.demo.repository.OfferRepository;
import ktsnwt_tim8.demo.repository.RatingRepository;


@Service
public class RatingService {

	@Autowired
	private RatingRepository repo;
	
	@Autowired
	private OfferRepository offerRepo;
	
	
	public Page<Rating> findAllByOfferID(Long id, Pageable page) throws Exception {
		
		Optional<Offer> offer = offerRepo.findById(id);
		
		if (offer.isPresent()) {
			return repo.findAllByOfferID(id, page);
		}
		
		throw new Exception("Offer with the given ID does not exist.");
	}

	public Rating findUsersRating(Long offerId) throws Exception {
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Offer o = offerRepo.findOneByID(offerId);
		if (o == null) {
			throw new Exception("Bad request!");
		}
		Rating r = repo.findOneByUserAndOffer(u, o);
		return r;
	}

	public Rating create(Long offerId, RatingDTO ratingDTO) throws Exception {
		
		if (offerId == null) {
			throw new Exception("Offer ID cannot be null!");
		}
		if (ratingDTO.getRating() < 1 || ratingDTO.getRating() > 5) {
			throw new Exception("Rating out of range!");
		}
		
		Offer offer = offerRepo.findOneByID(offerId);
		
		if (offer == null) {
			throw new Exception("Offer with passed if does not exist");
		}
		
		Rating rating = new Rating();
		rating.setRating(ratingDTO.getRating());
		rating.setOffer(offer);
		
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Rating ratingOld = repo.findOneByUserIDAndOfferID(u.getID(), offer.getID());
		if (ratingOld != null) {
			throw new Exception("One offer cannot be rated twice by the same user");
		}
		
		int numOfRatings = offer.getNmbOfRatings();
		double oldAverage = offer.getAvgRating();
		
		double newAverage = (oldAverage * numOfRatings + rating.getRating()) / (numOfRatings + 1);
		
		offer.setNmbOfRatings(numOfRatings + 1);
		offer.setAvgRating(newAverage);
		
		rating.setUser((RegisteredUser) u);
		
		offerRepo.save(offer);
		
		return repo.save(rating);
		}


	public Rating updateRating(Long ratingId, RatingDTO ratingDTO) throws Exception {
		
		Optional<Rating> ratingOpt = repo.findById(ratingId);
		
		Rating rating;
		
		if (!ratingOpt.isPresent()) {
			throw new Exception("Rating with given id does not exist!");
		}
	
		rating = ratingOpt.get();
		
		if (ratingDTO.getRating() < 1 || ratingDTO.getRating() > 5) {
			throw new Exception("Rating out of range!");
		}
		
		
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (u.getID() != rating.getUser().getID()) {
			throw new Exception("You can only update your own rating!");			
		}
		
		
		Offer offer = rating.getOffer();
		
		int oldRating = rating.getRating();
		int newRating = ratingDTO.getRating();
		
		double oldAvg = offer.getAvgRating();
		int votes = offer.getNmbOfRatings();
		
		double newAvg = (oldAvg * votes - oldRating + newRating) / votes;
		
		offer.setAvgRating(newAvg);
				
		offerRepo.save(offer);
				
		rating.setRating(newRating);
		repo.save(rating);
		return rating;
	}


	public void deleteRating(Long ratingId) throws Exception {
		
		if (repo.existsById(ratingId)) {
			
			Rating r = repo.getOne(ratingId);
			
			User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (u.getID() != r.getUser().getID()) {
				throw new Exception("You can only delete your own rating!");			
			}
			
			Offer offer = r.getOffer();
			
			double oldRating = offer.getAvgRating();
			int oldNmbOfRatings = offer.getNmbOfRatings();
			
			double newRating = 0;
			
			// avoiding division with 0
			if (oldNmbOfRatings != 1) {
				newRating = (oldRating * oldNmbOfRatings - r.getRating()) / (oldNmbOfRatings - 1);
			}
			
			offer.setAvgRating(newRating);
			offer.setNmbOfRatings(oldNmbOfRatings - 1);
			offerRepo.save(offer);
			
		    repo.deleteById(ratingId);
		    return;
		}
		throw new Exception("Rating with given id does not exits.");
		
	}

	public void deleteRatingById(Long id) {
		repo.deleteById(id);
	}


}
