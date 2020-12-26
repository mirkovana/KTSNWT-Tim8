package ktsnwt_tim8.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import ktsnwt_tim8.demo.DemoApplication;
import ktsnwt_tim8.demo.constants.Constants;
import ktsnwt_tim8.demo.constants.RatingConstants;
import ktsnwt_tim8.demo.dto.RatingDTO;
import ktsnwt_tim8.demo.dto.UserLoginDTO;
import ktsnwt_tim8.demo.dto.UserTokenStateDTO;
//import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Rating;
//import ktsnwt_tim8.demo.service.OfferService;
import ktsnwt_tim8.demo.service.RatingService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@TestPropertySource("classpath:test.properties")
public class RatingControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private RatingService service;
	
	//@Autowired 
	//private OfferService offerService;
	

	@Autowired
	private AuthenticationManager authenticationManager;

	@Value("${local.server.port}")
	private int port;

	public String login(String username, String password) {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
				new UserLoginDTO(username,password), UserTokenStateDTO.class);
		return "Bearer " + responseEntity.getBody().getAccessToken();
	}

	public void loginService(String username, String password) {
		Authentication authentication = authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(username, password)); 
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	//------------------------------ G E T --------------------------------//

	//------------------------------ C R E A T E --------------------------------//
	@Test
	@Transactional
	public void createRatingTwice() throws Exception {

		RestTemplate template = new RestTemplate();
		RatingDTO payload = new RatingDTO(null, 1);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(payload, headers);
		try {
		
			template.exchange("http://localhost:" + port + "/api/ratings/1", HttpMethod.POST, requestEntity, RatingDTO.class);
		}
		catch(HttpClientErrorException e) {
			String message = "One offer cannot be rated twice by the same user";
			
			String responseBody = e.getResponseBodyAsString();
			JSONObject jsonObject = new JSONObject(responseBody);
			
			assertEquals(message,jsonObject.get("message"));
			assertEquals(HttpStatus.BAD_REQUEST.value(), e.getRawStatusCode());
		}
		
	}	
	
	@Test
	@Transactional
	public void createRatingOutOfRange() throws Exception {

		RestTemplate template = new RestTemplate();
		RatingDTO payload = new RatingDTO(null, RatingConstants.RATING_OUT_OF_RANGE);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor3@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(payload, headers);
		try {
		
			template.exchange("http://localhost:" + port + "/api/ratings/2", HttpMethod.POST, requestEntity, RatingDTO.class);
		}
		catch(HttpClientErrorException e) {
			String message = "Rating out of range!";
			
			String responseBody = e.getResponseBodyAsString();
			JSONObject jsonObject = new JSONObject(responseBody);
			
			assertEquals(message,jsonObject.get("message"));
			assertEquals(HttpStatus.BAD_REQUEST.value(), e.getRawStatusCode());
		}
		
	}	
	

	@Test
	@Transactional
	public void createRatingForbidden() throws Exception {

		RestTemplate template = new RestTemplate();
		RatingDTO payload = new RatingDTO(null, RatingConstants.NEW_RATING);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("admin@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(payload, headers);
		try {
		
			template.exchange("http://localhost:" + port + "/api/ratings/2", HttpMethod.POST, requestEntity, RatingDTO.class);
		}
		catch(HttpClientErrorException e) {
			String message = "Forbidden";
			
			String responseBody = e.getResponseBodyAsString();
			JSONObject jsonObject = new JSONObject(responseBody);
			
			assertEquals(message,jsonObject.get("message"));
			assertEquals(HttpStatus.FORBIDDEN.value(), e.getRawStatusCode());
		}
		
	}
	
	@Test
	@Transactional
	public void createRatingUnauthorized() throws Exception {

		RestTemplate template = new RestTemplate();
		RatingDTO payload = new RatingDTO(null, RatingConstants.NEW_RATING);

		HttpHeaders headers = new HttpHeaders();
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(payload, headers);
		try {
		
			template.exchange("http://localhost:" + port + "/api/ratings/2", HttpMethod.POST, requestEntity, RatingDTO.class);
		}
		catch(HttpClientErrorException e) {
			
			assertEquals(HttpStatus.UNAUTHORIZED.value(), e.getRawStatusCode());
		}
		
	}	
	
	
	@Test
	@Transactional
	public void createRatingSuccess() throws Exception {

		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);

		RestTemplate template = new RestTemplate();
		RatingDTO payload = new RatingDTO(null, 1);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor3@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(payload, headers);

		ResponseEntity<RatingDTO> response = template.exchange(
				"http://localhost:" + port + "/api/ratings/2", HttpMethod.POST, requestEntity, RatingDTO.class);
		

		Page<Rating> page = service.findAllByOfferID(2L, pageable);		
		int newNumOfRatings = page.getContent().size();
		double newAvg = page.getContent().get(0).getOffer().getAvgRating();
		//Offer of = offerService.get(2L);
		assertEquals(response.getBody().getRating(), 1);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(newNumOfRatings, 2);
		assertNotEquals(4, newAvg);

		// restoring previous state
		Long newId = response.getBody().getID();

		loginService("kor3@nesto.com", "1");
		service.deleteRating(newId);
		//of = offerService.get(2L);
		//System.out.println("\n\n\n" + of.getAvgRating() + " " + of.getNmbOfRatings());
	}


	//------------------------------ U P D A T E --------------------------------//
	
	
	/* 
	 * "Rating with given id does not exist!"
	 * "Rating out of range!"
	 * "You can only update your own rating!"
	 */
	
	
	@Test
	@Transactional
	public void updateRatingOutOfRange() throws Exception {

		RestTemplate template = new RestTemplate();
		RatingDTO payload = new RatingDTO(null, RatingConstants.RATING_OUT_OF_RANGE);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor2@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(payload, headers);
		try {
		
			template.exchange("http://localhost:" + port + "/api/ratings/2", HttpMethod.PUT, requestEntity, RatingDTO.class);
		}
		catch(HttpClientErrorException e) {
			String message = "Rating out of range!";
			
			String responseBody = e.getResponseBodyAsString();
			JSONObject jsonObject = new JSONObject(responseBody);
			
			assertEquals(message,jsonObject.get("message"));
			assertEquals(HttpStatus.BAD_REQUEST.value(), e.getRawStatusCode());
		}
		
	}	

	
	@Test
	@Transactional
	public void updateRatingSomeoneElses() throws Exception {

		RestTemplate template = new RestTemplate();
		RatingDTO payload = new RatingDTO(null, RatingConstants.NEW_RATING);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor2@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(payload, headers);
		try {
		
			template.exchange("http://localhost:" + port + "/api/ratings/1", HttpMethod.PUT, requestEntity, RatingDTO.class);
		}
		catch(HttpClientErrorException e) {
			String message = "You can only update your own rating!";
			
			String responseBody = e.getResponseBodyAsString();
			JSONObject jsonObject = new JSONObject(responseBody);
			
			assertEquals(message,jsonObject.get("message"));
			assertEquals(HttpStatus.BAD_REQUEST.value(), e.getRawStatusCode());
		}
		
	}	
	
	@Test
	@Transactional
	public void updateRatingNonExistent() throws Exception {

		RestTemplate template = new RestTemplate();
		RatingDTO payload = new RatingDTO(null, RatingConstants.NEW_RATING);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor2@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(payload, headers);
		try {
		
			template.exchange("http://localhost:" + port + "/api/ratings/" + Constants.BAD_ID, HttpMethod.PUT, requestEntity, RatingDTO.class);
		}
		catch(HttpClientErrorException e) {
			String message = "Rating with given id does not exist!";
			
			String responseBody = e.getResponseBodyAsString();
			JSONObject jsonObject = new JSONObject(responseBody);
			
			assertEquals(message,jsonObject.get("message"));
			assertEquals(HttpStatus.BAD_REQUEST.value(), e.getRawStatusCode());
		}
		
	}

	
	
	@Test
	@Transactional
	public void updateRatingForbidden() throws Exception {

		RestTemplate template = new RestTemplate();
		RatingDTO payload = new RatingDTO(null, RatingConstants.NEW_RATING);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("admin@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(payload, headers);
		try {
		
			template.exchange("http://localhost:" + port + "/api/ratings/2", HttpMethod.PUT, requestEntity, RatingDTO.class);
		}
		catch(HttpClientErrorException e) {
			String message = "Forbidden";
			
			String responseBody = e.getResponseBodyAsString();
			JSONObject jsonObject = new JSONObject(responseBody);
			
			assertEquals(message,jsonObject.get("message"));
			assertEquals(HttpStatus.FORBIDDEN.value(), e.getRawStatusCode());
		}
		
	}
	
	@Test
	@Transactional
	public void updateRatingUnauthorized() throws Exception {

		RestTemplate template = new RestTemplate();
		RatingDTO payload = new RatingDTO(null, RatingConstants.NEW_RATING);

		HttpHeaders headers = new HttpHeaders();
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(payload, headers);
		try {
		
			template.exchange("http://localhost:" + port + "/api/ratings/2", HttpMethod.PUT, requestEntity, RatingDTO.class);
		}
		catch(HttpClientErrorException e) {
			
			assertEquals(HttpStatus.UNAUTHORIZED.value(), e.getRawStatusCode());
		}
		
	}
	
	
	@Test
	@Transactional
	public void updateRatingSuccess() throws Exception {

		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);

		RestTemplate template = new RestTemplate();
		RatingDTO payload = new RatingDTO(null, 2);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(payload, headers);

		ResponseEntity<RatingDTO> response = template.exchange(
				"http://localhost:" + port + "/api/ratings/1", HttpMethod.PUT, requestEntity, RatingDTO.class);
		

		Page<Rating> page = service.findAllByOfferID(1L, pageable);		
		double newAvg = page.getContent().get(0).getOffer().getAvgRating();
		//Offer of = offerService.get(2L);
		assertEquals(response.getBody().getRating(), 2);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotEquals(4, newAvg);

		// restoring previous state
		loginService("kor1@nesto.com", "1");
		service.updateRating(1L, new RatingDTO(null, 5));
		//of = offerService.get(1L);
		//System.out.println("\n\n\n" + of.getAvgRating() + " " + of.getNmbOfRatings());
	}

	

	//------------------------------ D E L E T E --------------------------------//

	
	@Test
	@Transactional
	public void deleteRatingNonExistent() throws Exception {

		RestTemplate template = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(null, headers);
		try {
		
			template.exchange("http://localhost:" + port + "/api/ratings/" + Constants.BAD_ID, HttpMethod.DELETE, requestEntity, RatingDTO.class);
		}
		catch(HttpClientErrorException e) {
			String message = "Rating with given id does not exits.";
			
			String responseBody = e.getResponseBodyAsString();
			JSONObject jsonObject = new JSONObject(responseBody);
			
			assertEquals(message,jsonObject.get("message"));
			assertEquals(HttpStatus.BAD_REQUEST.value(), e.getRawStatusCode());
		}
		
	}	
	
	@Test
	@Transactional
	public void deleteRatingSomeoneElses() throws Exception {

		RestTemplate template = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(null, headers);
		try {
		
			template.exchange("http://localhost:" + port + "/api/ratings/3", HttpMethod.DELETE, requestEntity, RatingDTO.class);
		}
		catch(HttpClientErrorException e) {
			String message = "You can only delete your own rating!";
			
			String responseBody = e.getResponseBodyAsString();
			JSONObject jsonObject = new JSONObject(responseBody);
			
			assertEquals(message,jsonObject.get("message"));
			assertEquals(HttpStatus.BAD_REQUEST.value(), e.getRawStatusCode());
		}
		
	}	

	
	@Test
	@Transactional
	public void deleteRatingForbidden() throws Exception {

		RestTemplate template = new RestTemplate();
		RatingDTO payload = new RatingDTO(null, RatingConstants.NEW_RATING);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("admin@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(payload, headers);
		try {
		
			template.exchange("http://localhost:" + port + "/api/ratings/3", HttpMethod.DELETE, requestEntity, RatingDTO.class);
				}
		catch(HttpClientErrorException e) {
			String message = "Forbidden";
			
			String responseBody = e.getResponseBodyAsString();
			JSONObject jsonObject = new JSONObject(responseBody);
			
			assertEquals(message,jsonObject.get("message"));
			assertEquals(HttpStatus.FORBIDDEN.value(), e.getRawStatusCode());
		}
		
	}	
	
	
	@Test
	@Transactional
	public void deleteRatingUnauthorized() throws Exception {

		RestTemplate template = new RestTemplate();
		RatingDTO payload = new RatingDTO(null, RatingConstants.NEW_RATING);

		HttpHeaders headers = new HttpHeaders();
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(payload, headers);
		try {
		
			template.exchange("http://localhost:" + port + "/api/ratings/3", HttpMethod.DELETE, requestEntity, RatingDTO.class);
				}
		catch(HttpClientErrorException e) {
			
			assertEquals(HttpStatus.UNAUTHORIZED.value(), e.getRawStatusCode());
		}
		
	}	
	
	
	@Test
	@Transactional
	public void deleteRatingSuccess() throws Exception {

		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);

		RestTemplate template = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor3@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(null, headers);

		ResponseEntity<RatingDTO> response = template.exchange(
				"http://localhost:" + port + "/api/ratings/3", HttpMethod.DELETE, requestEntity, RatingDTO.class);
		

		Page<Rating> page = service.findAllByOfferID(1L, pageable);		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		// a bilo je 3
		assertEquals(page.getContent().size(), 2);
		assertNotEquals(page.getContent().get(0).getOffer().getAvgRating(), 4);

		
		// restoring previous state
		loginService("kor3@nesto.com", "1");
		service.create(1L, new RatingDTO(3L, 2));
		//of = offerService.get(1L);
		//System.out.println("\n\n\n" + of.getAvgRating() + " " + of.getNmbOfRatings());
	}

	
}
