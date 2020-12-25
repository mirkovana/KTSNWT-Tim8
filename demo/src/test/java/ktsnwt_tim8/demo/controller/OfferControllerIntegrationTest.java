package ktsnwt_tim8.demo.controller;

import static ktsnwt_tim8.demo.constants.OfferConstants.*;
import static ktsnwt_tim8.demo.constants.UserConstants.ADMIN_EMAIL;
import static ktsnwt_tim8.demo.constants.UserConstants.ADMIN_PASSWORD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import ktsnwt_tim8.demo.dto.OfferDTO;
import ktsnwt_tim8.demo.dto.UserLoginDTO;
import ktsnwt_tim8.demo.dto.UserTokenStateDTO;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.service.OfferService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class OfferControllerIntegrationTest {

	
	
	@Autowired
	private OfferService offerService;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private AuthenticationManager authenticationManager;
	/* ISPISIVANJE SVIH */
	// JWT token za pristup REST servisima. Bice dobijen pri logovanju
	private String accessToken;

	@Value("${local.server.port}")
	private int port;

	public String login(String username, String password) {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
				new UserLoginDTO(username, password), UserTokenStateDTO.class);
		return "Bearer " + responseEntity.getBody().getAccessToken();
	}
	
	/*ISPISIVANJE PONUDA*/
	 @Test
	 public void testGetAllOffers() {

	HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<OfferDTO[]> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/offers?page=0&size=5", HttpMethod.GET, httpEntity, OfferDTO[].class);

		OfferDTO[] offers = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, offers.length);
	    }
	 
	 
	 /*DODAVANJE*/
	 @Test
		@Transactional
		@Rollback(true)
		public void testSaveOffer() throws Exception {
			int size = offerService.listAll().size(); // broj slogova pre ubacivanja novog

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(new OfferDTO(20l, "naslov", "opis" , 0.0, 50, 50.0 , 50.0, "novi sad" ), headers);

			ResponseEntity<OfferDTO> responseEntity = restTemplate.exchange(
					"http://localhost:" + port + "/api/offers/1", HttpMethod.POST, httpEntity, OfferDTO.class);

			// provera odgovora servera
			OfferDTO offer = responseEntity.getBody();
			assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
			assertNotNull(offer);
			assertEquals("naslov", offer.getTitle());

			List<Offer> offers= offerService.listAll();
			assertEquals(size + 1, offers.size()); // mora biti jedan vise slog sada nego pre
			// poslednja kategorija u listi treba da bude nova kategorija koja je ubacena u
			// testu
			assertEquals("naslov", offers.get(offers.size() - 1).getTitle());

			// uklanjamo dodatu kategoriju
			offerService.delete(offer.getID());
		}

	    /* BRISANJE */
		@Test
		@Transactional
		@Rollback(true)
		public void testDeleteOffer() throws Exception {

			Long id = 1L;
			login(ADMIN_EMAIL, ADMIN_PASSWORD);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));

			int size = offerService.listAll().size();
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
			// poziv REST servisa za brisanje
			ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:" + port +"/api/offers/2",
					HttpMethod.DELETE, httpEntity, Void.class);

			// provera odgovora servera
			assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

			// mora biti jedan manje slog sada nego pre
			assertEquals(size - 1, offerService.listAll().size());
		}

     /*IZMENA*/
		@Test
	    @Transactional
	    @Rollback(true)
	    public void testUpdateOffer() throws Exception {
			
			login(ADMIN_EMAIL, ADMIN_PASSWORD);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(new OfferDTO(ID_OFFER, "naslov", "opis" , 0.0, 0, 40.0 , 40.0, "novi sad" ),headers);
			
			
			
	        ResponseEntity<OfferDTO> responseEntity = restTemplate.exchange("http://localhost:" + port +"/api/offers/1", HttpMethod.PUT,httpEntity, OfferDTO.class);

	        OfferDTO offer = responseEntity.getBody();

	        // provera odgovora servera
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertNotNull(offer);
	        assertEquals("naslov", offer.getTitle());
	        

	        // provera da li je izmenjen slog u bazi
	        Offer offerdb = offerService.get(1l);
	        assertEquals(ID_OFFER, offer.getID());


	        // vracanje podatka na staru vrednost
	        offerdb.setTitle(OFFER_TITLE_DB);
	        offerdb.setDescription(OFFER_DESCRIPTION_DB);
            offerService.save(offerdb);
	    }
}