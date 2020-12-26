package ktsnwt_tim8.demo.service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ktsnwt_tim8.demo.constants.Constants;
import ktsnwt_tim8.demo.constants.OfferConstants;
import ktsnwt_tim8.demo.constants.RatingConstants;
import ktsnwt_tim8.demo.DemoApplication;
import ktsnwt_tim8.demo.dto.RatingDTO;
import ktsnwt_tim8.demo.model.Rating;
import ktsnwt_tim8.demo.service.RatingService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@TestPropertySource("classpath:test.properties")
public class RatingServiceIntegrationTests {

	@Autowired
	private RatingService service;

	@Autowired
	private AuthenticationManager authenticationManager;


	// treba mi login?
	public void login(String username, String password) {
		Authentication authentication = authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(username, password)); 
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}


	//@Test (expected = RuntimeException.class)
	public void first() {
		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class, 
				() -> { throw new IllegalArgumentException("a message"); }
				);

		assertEquals("a message", exception.getMessage());
	}



	//------------------------------ G E T  --------------------------------//

	@Test (expected = Exception.class)
	public void findRatingsWithNonExistentID() throws Exception {
		try{
			Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
			service.findAllByOfferID(Constants.BAD_ID, pageable);
			//assertTrue(page.get().findAny().isPresent()); //?
		}
		catch(Exception re)
		{
			String message = "Offer with the given ID does not exist.";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("Offer with non-existent ID exception did not throw!");
	}

	@Test 
	public void findRatingsWithExistentID() throws Exception {

		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Page<Rating> page = service.findAllByOfferID(1L, pageable);

		assertEquals(page.getContent().size(), RatingConstants.RATINGS_FOR_ID_1);

	}



	//------------------------------ C R E A T E --------------------------------//


	@Test (expected = Exception.class)
	public void rateOfferOutOfRange() throws Exception {
		try{
			login("kor3@nesto.com", "1");
			service.create(RatingConstants.OFFER_ID_FOR_NEW_RATING, new RatingDTO(null, RatingConstants.RATING_OUT_OF_RANGE));

		}
		catch(Exception re)
		{
			String message = "Rating out of range!";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("Rating out of range exception did not throw!");
	}

	@Test (expected = Exception.class)
	public void rateOfferNull() throws Exception {
		try{
			login("kor3@nesto.com", "1");
			service.create(null, new RatingDTO(null, RatingConstants.NEW_RATING));

		}
		catch(Exception re)
		{
			String message = "Offer ID cannot be null!";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("Offer ID cannot be null! exception did not throw!");
	}

	@Test (expected = Exception.class)
	public void rateOfferNonExistent() throws Exception {
		try{
			login("kor3@nesto.com", "1");
			service.create(Constants.BAD_ID, new RatingDTO(null, RatingConstants.NEW_RATING));

		}
		catch(Exception re)
		{
			String message = "Offer with passed if does not exist";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("Offer with passed if does not exist exception did not throw!");
	}

	@Test
	@Transactional
	public void rateOfferSuccess() throws Exception {

		login("kor3@nesto.com", "1");

		Pageable pageable1 = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Page<Rating> pageOld = service.findAllByOfferID(RatingConstants.OFFER_ID_FOR_NEW_RATING, pageable1);
		int oldNumOfRatings = pageOld.getContent().get(0).getOffer().getNmbOfRatings();
		double oldAvg = pageOld.getContent().get(0).getOffer().getAvgRating();
		
		
		Rating r = service.create(RatingConstants.OFFER_ID_FOR_NEW_RATING, new RatingDTO(null, 1));

		Pageable pageable2 = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Page<Rating> pageNew = service.findAllByOfferID(RatingConstants.OFFER_ID_FOR_NEW_RATING, pageable2);
		int newNumOfRatings = pageNew.getContent().get(0).getOffer().getNmbOfRatings();
		double newAvg = pageOld.getContent().get(0).getOffer().getAvgRating();
		
		System.out.println(oldAvg + " " + newAvg);
		
		assertEquals(pageOld.getContent().size() + 1, pageNew.getContent().size());
		assertEquals(oldNumOfRatings + 1, newNumOfRatings);
		assertEquals(r.getRating(), 1);
		assertNotEquals(oldAvg, newAvg);
		//assertTrue(oldAvg != newAvg);

	}

	@Test (expected = Exception.class)
	@Transactional
	public void rateOfferTwice() throws Exception {
		try{
			login("kor1@nesto.com", "1");
			service.create(RatingConstants.OFFER_ID_FOR_NEW_RATING, new RatingDTO(null, RatingConstants.NEW_RATING));
		}
		catch(Exception re)
		{
			String message = "One offer cannot be rated twice by the same user";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("One offer cannot be rated twice by the same user exception did not throw!");
	}



	//------------------------------ U P D A T E --------------------------------//

	@Test (expected = Exception.class)
	public void updateOfferNonExistent() throws Exception {
		try{
			login("kor3@nesto.com", "1");
			service.updateRating(Constants.BAD_ID, new RatingDTO(null, RatingConstants.UPDATED_RATING));
		}
		catch(Exception re)
		{
			String message = "Rating with given id does not exist!";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("Rating with given id does not exist! exception did not throw!");
	}


	@Test (expected = Exception.class)
	public void updateOfferOutOfRange() throws Exception {
		try{
			login("kor3@nesto.com", "1");
			service.updateRating(3l, new RatingDTO(null, RatingConstants.RATING_OUT_OF_RANGE));
		}
		catch(Exception re)
		{
			String message = "Rating out of range!";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("Rating out of range! exception did not throw!");
	}


	@Test (expected = Exception.class)
	public void updateOfferSomeoneElses() throws Exception {
		try{
			login("kor2@nesto.com", "1");
			service.updateRating(3l, new RatingDTO(null, RatingConstants.UPDATED_RATING));
		}
		catch(Exception re)
		{
			String message = "You can only update your own rating!";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("You can only update your own rating! exception did not throw!");
	}

	@Test
	public void updateOfferSuccess() throws Exception {

		login("kor3@nesto.com", "1");

		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Page<Rating> ratings = service.findAllByOfferID(1L, pageable);
		Double oldRating = ratings.getContent().get(0).getOffer().getAvgRating();
		Integer oldNumOfRates = ratings.getContent().get(0).getOffer().getNmbOfRatings();


		Rating r = service.updateRating(3l, new RatingDTO(null, RatingConstants.UPDATED_RATING));
		ratings = service.findAllByOfferID(1L, pageable);
		Double newRating = ratings.getContent().get(0).getOffer().getAvgRating();
		Integer newNumOfRates = ratings.getContent().get(0).getOffer().getNmbOfRatings();


		assertEquals(r.getRating(), RatingConstants.UPDATED_RATING);
		assertEquals(oldNumOfRates, newNumOfRates);
		assertNotEquals(oldRating, newRating);

	}



	//------------------------------ D E L E T E --------------------------------//

	@Test  (expected = Exception.class)
	@Transactional
	public void deleteRatingSomeoneElses() throws Exception {
		try{
			login("kor2@nesto.com", "1");
			service.deleteRating(4l);
		}
		catch(Exception re)
		{
			String message = "You can only delete your own rating!";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("You can only delete your own rating! exception did not throw!");
	}

	@Test (expected = Exception.class)
	@Transactional
	public void deleteRatingNonExistent() throws Exception {
		try{
			login("kor3@nesto.com", "1");
			service.deleteRating(Constants.BAD_ID);
		}
		catch(Exception re)
		{
			String message = "Rating with given id does not exits.";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("Rating with given id does not exits. exception did not throw!");
	}


	@Test 
	@Transactional
	public void deleteRatingSuccess() throws Exception {
		login("kor1@nesto.com", "1");

		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Page<Rating> ratings = service.findAllByOfferID(RatingConstants.OFFER_ID_FOR_NEW_RATING, pageable);
		//Integer oldNumOfRates = ratings.getContent().get(0).getOffer().getNmbOfRatings();
		int oldRatings = ratings.getContent().size();

		service.deleteRating(4L);
		ratings = service.findAllByOfferID(RatingConstants.OFFER_ID_FOR_NEW_RATING, pageable);
		//Integer newNumOfRates = ratings.getContent().get(0).getOffer().getNmbOfRatings();
		int newRatings = ratings.getContent().size();

		assertEquals(oldRatings - 1, newRatings);
	}

}
