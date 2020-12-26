package ktsnwt_tim8.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
import org.springframework.web.client.RestTemplate;

import ktsnwt_tim8.demo.DemoApplication;
import ktsnwt_tim8.demo.constants.Constants;
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

	//------------------------------ D E L E T E --------------------------------//


	
}
