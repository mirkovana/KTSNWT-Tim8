package ktsnwt_tim8.demo.controller;

import static ktsnwt_tim8.demo.constants.PostConstants.*;
import static ktsnwt_tim8.demo.constants.PostConstants.FIND_ALL_NUMBER_OF_ITEMS;
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
import ktsnwt_tim8.demo.dto.PostDTO;
import ktsnwt_tim8.demo.dto.UserLoginDTO;
import ktsnwt_tim8.demo.dto.UserTokenStateDTO;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Post;
import ktsnwt_tim8.demo.service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PostControllerIntegrationTest {
	
	@Autowired
	private PostService postService;

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
	 public void testGetAllPosts() {

	HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<PostDTO[]> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/posts/1?page=0&size=2", HttpMethod.GET, httpEntity, PostDTO[].class);

		PostDTO[] posts = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(FIND_ALL_NUMBER_OF_ITEMS, posts.length);
	    }
	 
	 
	 /*DODAVANJE*/
	 @Test
		@Transactional
		@Rollback(true)
		public void testSavePost() throws Exception {
			int size = postService.listAll().size(); // broj slogova pre ubacivanja novog
			Date datum = new Date();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(20l, "naslov", "opis" , datum), headers);

			ResponseEntity<PostDTO> responseEntity = restTemplate.exchange(
					"http://localhost:" + port + "/api/posts/1", HttpMethod.POST, httpEntity, PostDTO.class);

			// provera odgovora servera
			PostDTO post = responseEntity.getBody();
			assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
			assertNotNull(post);
			assertEquals("naslov", post.getTitle());

			List<Post> posts= postService.listAll();
			assertEquals(size + 1, posts.size()); // mora biti jedan vise slog sada nego pre
			// poslednja kategorija u listi treba da bude nova kategorija koja je ubacena u
			// testu
			assertEquals("naslov", posts.get(posts.size() - 1).getTitle());

			// uklanjamo dodatu kategoriju
			postService.delete(post.getID());
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
			ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:" + port +"/api/posts/2",
					HttpMethod.DELETE, httpEntity, Void.class);

			// provera odgovora servera
			assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

			// mora biti jedan manje slog sada nego pre
			assertEquals(size - 1, postService.listAll().size());
		}
		
		
		
		 /*IZMENA*/
		@Test
	    @Transactional
	    @Rollback(true)
	    public void testUpdatePost() throws Exception {
			Date datum = new Date();

			login(ADMIN_EMAIL, ADMIN_PASSWORD);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
			HttpEntity<Object> httpEntity = new HttpEntity<Object>(new PostDTO(ID_POST, "naslov", "opis" , datum),headers);
			
			
			
	        ResponseEntity<PostDTO> responseEntity = restTemplate.exchange("http://localhost:" + port +"/api/posts/1", HttpMethod.PUT,httpEntity, PostDTO.class);

	        PostDTO post = responseEntity.getBody();

	        // provera odgovora servera
	        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	        assertNotNull(post);
	        assertEquals("naslov", post.getTitle());
	        

	        // provera da li je izmenjen slog u bazi
	        Post postdb= postService.get(1l);
	        assertEquals(ID_POST, post.getID());


	        // vracanje podatka na staru vrednost
	        postdb.setTitle(POST_TITLE_DB);
	        postdb.setContent(POST_CONTENT_DB);
            postService.save(postdb);
	    }
}
