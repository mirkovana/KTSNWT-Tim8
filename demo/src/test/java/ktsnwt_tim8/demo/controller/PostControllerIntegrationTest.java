package ktsnwt_tim8.demo.controller;

import static ktsnwt_tim8.demo.constants.PostConstants.FIND_ALL_NUMBER_OF_ITEMS;
import static ktsnwt_tim8.demo.constants.PostConstants.ID_POST;
import static ktsnwt_tim8.demo.constants.PostConstants.POST_CONTENT_DB;
import static ktsnwt_tim8.demo.constants.PostConstants.POST_TITLE_DB;
import static ktsnwt_tim8.demo.constants.UserConstants.ADMIN_EMAIL;
import static ktsnwt_tim8.demo.constants.UserConstants.ADMIN_PASSWORD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import ktsnwt_tim8.demo.constants.Constants;
import ktsnwt_tim8.demo.dto.CommentDTO;
import ktsnwt_tim8.demo.dto.PostDTO;
import ktsnwt_tim8.demo.dto.UserLoginDTO;
import ktsnwt_tim8.demo.dto.UserTokenStateDTO;
import ktsnwt_tim8.demo.helper.RestResponsePage;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Post;
import ktsnwt_tim8.demo.service.OfferService;
import ktsnwt_tim8.demo.service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PostControllerIntegrationTest {

	@Autowired
	private PostService postService;

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

	/* ISPISIVANJE PONUDA */
	@Test
	public void testGetAllPosts() {
		Pageable pageable = PageRequest.of(0, 2);
		Offer offer = offerService.get(1L);
		Page<Post> page = postService.findAllByOffer1(offer, pageable);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		
		ParameterizedTypeReference<RestResponsePage<PostDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<PostDTO>>() {};

		ResponseEntity<RestResponsePage<PostDTO>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/posts/1?page=0&size=2", HttpMethod.GET, httpEntity, responseType);

		List<PostDTO> searchResult = responseEntity.getBody().getContent();

		assertEquals(page.getContent().size(), searchResult.size());
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, posts.length);
	}

	/* DODAVANJE */
	@Test
	@Transactional
	@Rollback(true)
	public void testSavePost() throws Exception {
		int size = postService.listAll().size(); // broj slogova pre ubacivanja novog
		Date datum = new Date();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(20l, "naslov", "opis", datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.POST, httpEntity, PostDTO.class);

		// provera odgovora servera
		PostDTO post = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(post);
		assertEquals("naslov", post.getTitle());

		List<Post> posts = postService.listAll();
		assertEquals(size + 1, posts.size()); // mora biti jedan vise slog sada nego pre
		// poslednja kategorija u listi treba da bude nova kategorija koja je ubacena u
		// testu
		assertEquals("naslov", posts.get(posts.size() - 1).getTitle());

		// uklanjamo dodatu kategoriju
		postService.delete(post.getID());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSavePostForbidden() throws Exception {
		int size = postService.listAll().size(); // broj slogova pre ubacivanja novog
		Date datum = new Date();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(20l, "naslovv", "opiss", datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.POST, httpEntity, PostDTO.class);

		// provera odgovora servera
		PostDTO post = responseEntity.getBody();
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	
		List<Post> posts = postService.listAll();
		assertEquals(size , posts.size()); 
	}

	/* BRISANJE */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeletePost() throws Exception {

		Long id = 1L;
		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));

		int size = postService.listAll().size();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		// poziv REST servisa za brisanje
		ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/2",
				HttpMethod.DELETE, httpEntity, Void.class);

		// provera odgovora servera
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// mora biti jedan manje slog sada nego pre
		assertEquals(size - 1, postService.listAll().size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeletePostForbidden() throws Exception {

		Long id = 1L;
		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", ADMIN_PASSWORD));

		int size = postService.listAll().size();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		// poziv REST servisa za brisanje
		ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/2",
				HttpMethod.DELETE, httpEntity, Void.class);

		// provera odgovora servera
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

		// mora biti jedan manje slog sada nego pre
		assertEquals(size, postService.listAll().size());
	}
	
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeletePostBadRequest() throws Exception {

		Long id = 1L;
		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));

		int size = postService.listAll().size();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		// poziv REST servisa za brisanje
		ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/987987",
				HttpMethod.DELETE, httpEntity, Void.class);

		// provera odgovora servera
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		// mora biti jedan manje slog sada nego pre
		assertEquals(size , postService.listAll().size());
	}

	/* IZMENA */
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePost() throws Exception {
		Date datum = new Date();

		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(ID_POST, "naslov", "opis", datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.PUT, httpEntity, PostDTO.class);

		PostDTO post = responseEntity.getBody();

		// provera odgovora servera
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(post);
		assertEquals("naslov", post.getTitle());

		// provera da li je izmenjen slog u bazi
		Post postdb = postService.get(1l);
		assertEquals(ID_POST, post.getID());

		// vracanje podatka na staru vrednost
		postdb.setTitle(POST_TITLE_DB);
		postdb.setContent(POST_CONTENT_DB);
		postService.save(postdb);
	}

	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePostForbidden() throws Exception {
		Date datum = new Date();

		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(ID_POST, "naslov", "opis", datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.PUT, httpEntity, PostDTO.class);

		PostDTO post = responseEntity.getBody();

		// provera odgovora servera
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	/* NEUSPESNO DODAVANJE */
	@Test
	@Transactional
	@Rollback(true)
	public void testSavePost_TitleIsEmpty() throws Exception {
		int size = postService.listAll().size(); // broj slogova pre ubacivanja novog
		Date datum = new Date();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(21l, "", "opis", datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.POST, httpEntity, PostDTO.class);

		// provera odgovora servera
		PostDTO post = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		List<Post> posts = postService.listAll();
		assertEquals(size, posts.size()); // mora biti jednak broj kao i pre
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSavePost_ContentEmpty() throws Exception {
		int size = postService.listAll().size(); // broj slogova pre ubacivanja novog
		Date datum = new Date();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(21l, "ovo je nesto novo dodato", "", datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.POST, httpEntity, PostDTO.class);

		// provera odgovora servera
		PostDTO post = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		List<Post> posts = postService.listAll();
		assertEquals(size, posts.size()); // mora biti jednak broj kao i pre
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSavePost_TitleAndContentAreEmpty() throws Exception {
		int size = postService.listAll().size(); // broj slogova pre ubacivanja novog
		Date datum = new Date();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(21l, "", "", datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.POST, httpEntity, PostDTO.class);

		// provera odgovora servera
		PostDTO post = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		List<Post> posts = postService.listAll();
		assertEquals(size, posts.size()); // mora biti jednak broj kao i pre
	}

	/* NEUSPESNA IZMENA */
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePost_TitleIsEmpty() throws Exception {
		Date datum = new Date();

		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(ID_POST, "", "ovo je neki kontent", datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.PUT, httpEntity, PostDTO.class);

		PostDTO post = responseEntity.getBody();

		// provera odgovora servera
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePost_ContentIsEmpty() throws Exception {
		Date datum = new Date();

		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(ID_POST, "naslov", "", datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.PUT, httpEntity, PostDTO.class);

		PostDTO post = responseEntity.getBody();

		// provera odgovora servera
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePost_ContentAndTitleAreEmpty() throws Exception {
		Date datum = new Date();

		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(ID_POST, "", "", datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.PUT, httpEntity, PostDTO.class);

		PostDTO post = responseEntity.getBody();

		// provera odgovora servera
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	
	
	/*DODAVANJE I IZMENA POSTA KADA SU UNETE UNETE VREDNOSTI NULL*/
	/* NEUSPESNO DODAVANJE */
	@Test
	@Transactional
	@Rollback(true)
	public void testSavePost_TitleIsnull() throws Exception {
		int size = postService.listAll().size(); // broj slogova pre ubacivanja novog
		Date datum = new Date();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(21l, null, "opis", datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.POST, httpEntity, PostDTO.class);

		// provera odgovora servera
		PostDTO post = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		List<Post> posts = postService.listAll();
		assertEquals(size, posts.size()); // mora biti jednak broj kao i pre
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSavePost_Contentnull() throws Exception {
		int size = postService.listAll().size(); // broj slogova pre ubacivanja novog
		Date datum = new Date();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(21l, "ovo je nesto novo dodato", null, datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.POST, httpEntity, PostDTO.class);

		// provera odgovora servera
		PostDTO post = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		List<Post> posts = postService.listAll();
		assertEquals(size, posts.size()); // mora biti jednak broj kao i pre
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSavePost_TitleAndContentArenull() throws Exception {
		int size = postService.listAll().size(); // broj slogova pre ubacivanja novog
		Date datum = new Date();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(21l, null, null, datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.POST, httpEntity, PostDTO.class);

		// provera odgovora servera
		PostDTO post = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		List<Post> posts = postService.listAll();
		assertEquals(size, posts.size()); // mora biti jednak broj kao i pre
	}

	/* NEUSPESNA IZMENA */
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePost_TitleIsnull() throws Exception {
		Date datum = new Date();

		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(ID_POST,null, "ovo je neki kontent", datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.PUT, httpEntity, PostDTO.class);

		PostDTO post = responseEntity.getBody();

		// provera odgovora servera
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePost_ContentIsnull() throws Exception {
		Date datum = new Date();

		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(ID_POST, "naslov",null, datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.PUT, httpEntity, PostDTO.class);

		PostDTO post = responseEntity.getBody();

		// provera odgovora servera
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdatePost_ContentAndTitleArenull() throws Exception {
		Date datum = new Date();

		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(ID_POST, null, null, datum), headers);

		ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/posts/1",
				HttpMethod.PUT, httpEntity, PostDTO.class);

		PostDTO post = responseEntity.getBody();

		// provera odgovora servera
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}
}
