package ktsnwt_tim8.demo.controller;

import static ktsnwt_tim8.demo.constants.UserConstants.ADMIN_EMAIL;
import static ktsnwt_tim8.demo.constants.UserConstants.ADMIN_PASSWORD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import ktsnwt_tim8.demo.DemoApplication;
import ktsnwt_tim8.demo.constants.OfferImageConstants;
import ktsnwt_tim8.demo.dto.CommentDTO;
import ktsnwt_tim8.demo.dto.OfferImageDTO;
import ktsnwt_tim8.demo.dto.UserLoginDTO;
import ktsnwt_tim8.demo.dto.UserTokenStateDTO;
import ktsnwt_tim8.demo.helper.RestResponsePage;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.OfferImage;
import ktsnwt_tim8.demo.service.OfferImageService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@TestPropertySource("classpath:test.properties")

public class OfferImageControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private OfferImageService service;

	@Value("${local.server.port}")
	private int port;

	public String login(String username, String password) {
		ResponseEntity<UserTokenStateDTO> responseEntity = restTemplate.postForEntity("/auth/log-in",
				new UserLoginDTO(username, password), UserTokenStateDTO.class);
		return "Bearer " + responseEntity.getBody().getAccessToken();
	}

	@Test
	public void testAddPhoto() throws ClassNotFoundException, SQLException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		FileSystemResource fsr = new FileSystemResource(OfferImageConstants.IMAGE_PATH);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		body.add("image", fsr);
		body.add("description", OfferImageConstants.IMAGE_DESCRIPTION);
		body.add("offerID", OfferImageConstants.OFFER_ID);

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<OfferImageDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/Offer-images", HttpMethod.POST, request, OfferImageDTO.class);

		OfferImageDTO ret = responseEntity.getBody();
		Pageable paging = PageRequest.of(0, 20);
		Page<OfferImage> images = service.findAllByOfferID(OfferImageConstants.OFFER_ID, paging);
		List<OfferImage> list = images.toList();

		assertNotNull(ret);
		assertEquals(OfferImageConstants.IMAGE_DESCRIPTION, ret.getDescription());

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(OfferImageConstants.FIND_ALL_NUMBER_OF_ITEMS + 1, list.size());
		assertEquals(OfferImageConstants.IMAGE_DESCRIPTION, list.get(list.size() - 1).getDescription());

		service.delete(ret.getID());

//		Class.forName("org.h2.Driver");
//		Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "h2db", "root");
//		java.sql.Statement statement = connection.createStatement();
//		statement.execute("DELETE FROM OFFER_IMAGE WHERE id = '6'");
	}

	@Test
	public void testAddPhotoBadParams() throws ClassNotFoundException, SQLException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		FileSystemResource fsr = new FileSystemResource(OfferImageConstants.IMAGE_PATH);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		body.add("asdas", fsr);
		body.add("descasdasdription", OfferImageConstants.IMAGE_DESCRIPTION);
		body.add("ofasdasdferID", OfferImageConstants.OFFER_ID);

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<OfferImageDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/Offer-images", HttpMethod.POST, request, OfferImageDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void testGetAllOfferImages() {
		
		ParameterizedTypeReference<RestResponsePage<OfferImageDTO>> responseType = new ParameterizedTypeReference<RestResponsePage<OfferImageDTO>>() {};
		
		ResponseEntity<RestResponsePage<OfferImageDTO>> response = restTemplate.exchange("/api/Offer-images/1/0/10", HttpMethod.GET,
				null, responseType);

		List<OfferImageDTO> images = response.getBody().getContent();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(OfferImageConstants.FIND_ALL_NUMBER_OF_ITEMS, images.size());

	}

	@Test(expected = java.lang.Exception.class)
	public void testGetAllOfferImagesBadID() {
		ResponseEntity<OfferImageDTO[]> response = restTemplate.exchange(
				"/api/Offer-images/" + OfferImageConstants.BAD_OFFER_ID + "/0/10", HttpMethod.GET, null,
				OfferImageDTO[].class);

		OfferImageDTO[] images = response.getBody();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteImage() throws SQLException, ClassNotFoundException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/Offer-images/1",
				HttpMethod.DELETE, httpEntity, Void.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// mora biti jedan manje slog sada nego pre
		Pageable paging = PageRequest.of(0, 20);
		assertEquals(OfferImageConstants.FIND_ALL_NUMBER_OF_ITEMS - 1,
				service.findAllByOfferID(OfferImageConstants.OFFER_ID, paging).getTotalElements());

		Class.forName("org.h2.Driver");
		Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "h2db", "root");
		java.sql.Statement statement = connection.createStatement();
		statement.execute(
				"INSERT INTO OFFER_IMAGE(description, path, offer_id) values ('slicica', 'src/main/resources/images/beforeHouseWasDisco.jpg', '1')");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testDeleteImageBadID() throws SQLException, ClassNotFoundException {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		ResponseEntity<Void> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/Offer-images/" + OfferImageConstants.OFFER_IMAGE_BAD_ID,
				HttpMethod.DELETE, httpEntity, Void.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}



	@Test
	public void testUpdateDescNotAllParams() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("offerID1231", OfferImageConstants.NEW_OFFER_IMAGE_ID);
		body.add("descsription12312", OfferImageConstants.NEW_IMAGE_DESCRIPTION);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<OfferImageDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/Offer-images", HttpMethod.PUT, request, OfferImageDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdateDescNotBadParams() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("offerID1231", OfferImageConstants.NEW_OFFER_IMAGE_ID);
		body.add("descsription12312", OfferImageConstants.NEW_IMAGE_DESCRIPTION);
		body.add("somethingsadas", OfferImageConstants.IMAGE_ID);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<OfferImageDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/Offer-images", HttpMethod.PUT, request, OfferImageDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}


	@Test
	public void testUpdateDescBadDescription() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("offerID", OfferImageConstants.OFFER_ID);
		body.add("descsription", OfferImageConstants.BAD_IMAGE_DESCRIPTION);
		body.add("imageID", OfferImageConstants.IMAGE_ID);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<OfferImageDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/Offer-images", HttpMethod.PUT, request, OfferImageDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
	}

	
	
	
	@Test
	@Transactional
	public void testUpdateDesc() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login(ADMIN_EMAIL, ADMIN_PASSWORD));
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("offerID", OfferImageConstants.OFFER_ID);
		body.add("description", OfferImageConstants.NEW_IMAGE_DESCRIPTION);
		body.add("imageID", OfferImageConstants.IMAGE_ID);
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<OfferImageDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/Offer-images", HttpMethod.PUT, request, OfferImageDTO.class);

		OfferImageDTO ret = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(ret);

		assertEquals(OfferImageConstants.IMAGE_ID, ret.getID());

		assertEquals(OfferImageConstants.NEW_IMAGE_DESCRIPTION, ret.getDescription());

		Pageable paging = PageRequest.of(0, 20);
		Page<OfferImage> page = service.findAllByOfferID(OfferImageConstants.OFFER_ID, paging);
		List<OfferImage> list = page.toList();

		// -1 zbog indeksa niza, -1 zbog brisanja prvog reda tabele
		assertEquals(OfferImageConstants.IMAGE_ID, list.get(OfferImageConstants.IMAGE_ID.intValue()-2).getID());
		assertEquals(OfferImageConstants.NEW_IMAGE_DESCRIPTION, list.get(OfferImageConstants.IMAGE_ID.intValue()-2).getDescription());

		ret.setDescription(OfferImageConstants.IMAGE_DESCRIPTION);
		service.updateImageDesc(OfferImageConstants.OFFER_ID, ret);
	}
	
}
