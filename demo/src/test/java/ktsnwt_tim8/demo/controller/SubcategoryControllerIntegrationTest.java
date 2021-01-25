package ktsnwt_tim8.demo.controller;

import static ktsnwt_tim8.demo.constants.CategoryConstants.*;
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

import ktsnwt_tim8.demo.dto.SubcategoryDTO;
import ktsnwt_tim8.demo.dto.UserLoginDTO;
import ktsnwt_tim8.demo.dto.UserTokenStateDTO;
import ktsnwt_tim8.demo.model.Subcategory;
import ktsnwt_tim8.demo.service.SubcategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class SubcategoryControllerIntegrationTest {

	@Autowired
	private SubcategoryService subcategoryService;

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

	/* BRISANJE */
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteSubcategory() throws Exception {

		Long id = 1L;
		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));

		int size = subcategoryService.listAll().size();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		// poziv REST servisa za brisanje
		ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/subcategories/7",
				HttpMethod.DELETE, httpEntity, Void.class);

		// provera odgovora servera
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// mora biti jedan manje slog sada nego pre
		assertEquals(size - 1, subcategoryService.listAll().size());
	}
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteSubcategoryForbidden() throws Exception {

		Long id = 1L;
		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", ADMIN_PASSWORD));

		int size = subcategoryService.listAll().size();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		// poziv REST servisa za brisanje
		ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/subcategories/7",
				HttpMethod.DELETE, httpEntity, Void.class);

		// provera odgovora servera
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

		// mora biti jedan manje slog sada nego pre
		assertEquals(size , subcategoryService.listAll().size());
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteSubcategoryBadRequest() throws Exception {

		Long id = 1L;
		login(ADMIN_EMAIL, ADMIN_PASSWORD);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));

		int size = subcategoryService.listAll().size();
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		// poziv REST servisa za brisanje
		ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/subcategories/9999",
				HttpMethod.DELETE, httpEntity, Void.class);

		// provera odgovora servera
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

		// mora biti jedan manje slog sada nego pre
		assertEquals(size, subcategoryService.listAll().size());
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testSaveSubcategory() throws Exception {
		int size = subcategoryService.listAll().size(); // broj slogova pre ubacivanja novog

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new SubcategoryDTO("naziv", 10l), headers);

		ResponseEntity<SubcategoryDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/subcategories/1", HttpMethod.POST, httpEntity, SubcategoryDTO.class);

		// provera odgovora servera
		SubcategoryDTO category = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(category);
		assertEquals("naziv", category.getName());

		List<Subcategory> categories = subcategoryService.listAll();
		assertEquals(size + 1, categories.size()); // mora biti jedan vise slog sada nego pre
		// poslednja kategorija u listi treba da bude nova kategorija koja je ubacena u
		// testu
		assertEquals("naziv", categories.get(categories.size() - 1).getName());

		// uklanjamo dodatu kategoriju
		subcategoryService.delete(category.getID());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveSubcategoryForbidden() throws Exception {
		int size = subcategoryService.listAll().size(); // broj slogova pre ubacivanja novog

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new SubcategoryDTO("naziv", 10l), headers);

		ResponseEntity<SubcategoryDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/subcategories/1", HttpMethod.POST, httpEntity, SubcategoryDTO.class);

		// provera odgovora servera
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
		

		List<Subcategory> categories = subcategoryService.listAll();
		assertEquals(size , categories.size()); // mora biti jedan vise slog sada nego pre
		
	}

	@Test
	public void testGetAllSubcategories() {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<Subcategory[]> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/subcategories", HttpMethod.GET, httpEntity, Subcategory[].class);

		Subcategory[] categories = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS_SUB, categories.length);
	}
	
	/*NEUSPESNO DODAVANJE*/
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveSubcategory_NameIsEmpty() throws Exception {
		int size = subcategoryService.listAll().size(); // broj slogova pre ubacivanja novog

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new SubcategoryDTO("", 11l), headers);

		ResponseEntity<SubcategoryDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/subcategories/1", HttpMethod.POST, httpEntity, SubcategoryDTO.class);

		// provera odgovora servera
		SubcategoryDTO category = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		
		List<Subcategory> categories = subcategoryService.listAll();
		assertEquals(size, categories.size()); // mora isti broj kao i pre
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSaveSubcategory_NameIsnull() throws Exception {
		int size = subcategoryService.listAll().size(); // broj slogova pre ubacivanja novog

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(new SubcategoryDTO(null, 11l), headers);

		ResponseEntity<SubcategoryDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/subcategories/1", HttpMethod.POST, httpEntity, SubcategoryDTO.class);

		// provera odgovora servera
		SubcategoryDTO category = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		
		List<Subcategory> categories = subcategoryService.listAll();
		assertEquals(size, categories.size()); // mora isti broj kao i pre
	}
}
