package ktsnwt_tim8.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Rating;
import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.model.User;
import ktsnwt_tim8.demo.repository.OfferRepository;
import ktsnwt_tim8.demo.repository.RatingRepository;
import ktsnwt_tim8.demo.repository.UserRepository;

@Service
public class RatingService {

	@Autowired
	private RatingRepository repo;
	
	@Autowired
	private OfferRepository offerRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	
	public Page<Rating> findAll(Pageable page){
		return repo.findAll(page);
	}
	
	public Page<Rating> findAllByOfferID(Long id, Pageable page) {
		return repo.findAllByOfferID(id, page);
	}


	public Rating create(Long offerId, Rating rating) throws Exception {
	
		
		
		Offer offer = offerRepo.findOneByID(offerId);
		
		if (offer == null) {
			throw new Exception("Offer with passed if does not exist");
		}
		rating.setOffer(offer);
		
		// this is a dummy user, we will get the user automatically once Spring Security is implemented
		User u = userRepo.findOneByID(2l);
		
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


	public Rating updateRating(Long ratingId, Rating r) throws Exception {
		Rating rating = repo.getOne(ratingId);
		
		if (rating == null) {
			throw new Exception("Rating with given id does not exits.");
		}
		
		Offer offer = rating.getOffer();
		
		int oldRating = rating.getRating();
		int newRating = r.getRating();
		
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
			
			Offer offer = r.getOffer();
			
			double oldRating = offer.getAvgRating();
			int oldNmbOfRatings = offer.getNmbOfRatings();
			
			double newRating = (oldRating * oldNmbOfRatings - r.getRating()) / (oldNmbOfRatings - 1);
			
			offer.setAvgRating(newRating);
			offer.setNmbOfRatings(oldNmbOfRatings - 1);
			offerRepo.save(offer);
			
		    repo.deleteById(ratingId);
		    return;
		}
		throw new Exception("Rating with given id does not exits.");
		
	}



}
