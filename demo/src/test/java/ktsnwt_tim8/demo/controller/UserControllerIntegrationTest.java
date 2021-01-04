package ktsnwt_tim8.demo.controller;

import static ktsnwt_tim8.demo.constants.UserConstants.ADMIN_EMAIL;
import static ktsnwt_tim8.demo.constants.UserConstants.USER_EMAIL;
import static ktsnwt_tim8.demo.constants.UserConstants.USER_PASSWORD;
import static ktsnwt_tim8.demo.constants.UserConstants.ADMIN_PASSWORD;
import static ktsnwt_tim8.demo.constants.UserConstants.FIND_ALL_NUMBER_OF_ITEMS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
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

import ktsnwt_tim8.demo.constants.OfferImageConstants;
import ktsnwt_tim8.demo.dto.OfferDTO;
import ktsnwt_tim8.demo.dto.OfferImageDTO;
import ktsnwt_tim8.demo.dto.UserDTO;
import ktsnwt_tim8.demo.dto.UserLoginDTO;
import ktsnwt_tim8.demo.dto.UserTokenStateDTO;
import ktsnwt_tim8.demo.helper.RestResponsePage;
import ktsnwt_tim8.demo.model.User;
import ktsnwt_tim8.demo.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class UserControllerIntegrationTest {

	@Autowired
	private UserService userService;

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

	/* USPESNO DODAVANJE */
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveUser() throws Exception {
		int size = userService.listAll().size(); // broj slogova pre ubacivanja novog

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(
				new UserDTO(10L, "username@nesto.com", "pass", "ime", "prez"), headers);

		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/auth/sign-up",
				HttpMethod.POST, httpEntity, UserDTO.class);

		// provera odgovora servera
		UserDTO user = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(user);
		assertEquals("username@nesto.com", user.getUsername());

		List<User> users = userService.listAll();
		assertEquals(size + 1, users.size()); // mora biti jedan vise slog sada nego pre
		// poslednja kategorija u listi treba da bude nova kategorija koja je ubacena u
		// testu
		assertEquals("username@nesto.com", users.get(users.size() - 1).getUsername());

		// uklanjamo dodatu kategoriju
		userService.delete(user.getID());
	}

	/* NEUSPESNO DODAVANJE */
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveUser_UsernameAndPasswordAreEmpty() throws Exception {
		int size = userService.listAll().size(); // broj slogova pre ubacivanja novog

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new UserDTO(11L, "sdfgh", "", "ime", "prez"), headers);

		ResponseEntity<UserDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/auth/sign-up",
				HttpMethod.POST, httpEntity, UserDTO.class);

		// provera odgovora servera
		UserDTO user = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		List<User> users = userService.listAll();
		assertEquals(size, users.size()); // mora biti jednak broj kao i pre
	}

	@Test
	public void getUserSubscriptions() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(USER_EMAIL, USER_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ParameterizedTypeReference<RestResponsePage<OfferDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<OfferDTO>>() {
		};

		ResponseEntity<RestResponsePage<OfferDTO>> response = restTemplate.exchange("/api/users/getSubscriptions/0/10",
				HttpMethod.GET, httpEntity, responseType);

		List<OfferDTO> images = response.getBody().getContent();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, images.size());
		
	}

}
