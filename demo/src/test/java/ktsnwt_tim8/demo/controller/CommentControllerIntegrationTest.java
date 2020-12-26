package ktsnwt_tim8.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import ktsnwt_tim8.demo.DemoApplication;
import ktsnwt_tim8.demo.constants.Constants;
import ktsnwt_tim8.demo.dto.CommentDTO;
import ktsnwt_tim8.demo.dto.RatingDTO;
import ktsnwt_tim8.demo.dto.UserLoginDTO;
import ktsnwt_tim8.demo.dto.UserTokenStateDTO;
import ktsnwt_tim8.demo.model.Comment;
import ktsnwt_tim8.demo.model.Rating;
import ktsnwt_tim8.demo.service.CommentService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@TestPropertySource("classpath:test.properties")
public class CommentControllerIntegrationTest {


	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CommentService service;

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
	public void createCommentBadRequest() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", "1"));
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		FileSystemResource fsr = new FileSystemResource("src/main/resources/images/beforeHouseWasDisco.jpg");
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		body.add("imageFAIL", fsr); //-------------------!-------------------------//
		body.add("text", "Text");
		body.add("offerId", 1L);		

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/comments", HttpMethod.POST, request, CommentDTO.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}


	@Test
	@Transactional
	public void createCommentBadOfferId() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", "1"));
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		FileSystemResource fsr = new FileSystemResource("src/main/resources/images/beforeHouseWasDisco.jpg");
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		body.add("image", fsr);
		body.add("text", "Text");
		body.add("offerId", Constants.BAD_ID);		//-------------------!-------------------------//

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/comments", HttpMethod.POST, request, CommentDTO.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	@Transactional
	public void createCommentNoText() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", "1"));
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		FileSystemResource fsr = new FileSystemResource("src/main/resources/images/beforeHouseWasDisco.jpg");
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		body.add("image", fsr); 
		body.add("text", "");	//-------------------!-------------------------//
		body.add("offerId", 1L);

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/comments", HttpMethod.POST, request, CommentDTO.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}



	@Test
	@Transactional
	public void createCommentForbidden() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("admin@nesto.com", "1"));  //-------------------!-------------------------//
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		FileSystemResource fsr = new FileSystemResource("src/main/resources/images/beforeHouseWasDisco.jpg");
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		body.add("image", fsr); 
		body.add("text", "Text");	
		body.add("offerId", 1L);

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/comments", HttpMethod.POST, request, CommentDTO.class);

		System.out.println(responseEntity.getStatusCode());
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	

	
	
	
	@Test
	@Transactional
	public void createCommentSuccess() throws Exception {

		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", "1"));
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		FileSystemResource fsr = new FileSystemResource("src/main/resources/images/beforeHouseWasDisco.jpg");
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		body.add("image", fsr);
		body.add("text", "New comment");
		body.add("offerId", 1L);

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/comments", HttpMethod.POST, request, CommentDTO.class);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("New comment", responseEntity.getBody().getText());

		Page<Comment> page = service.findAllByOfferID(1L, pageable);		
		int newComms = page.getContent().get(0).getOffer().getComments().size();

		assertEquals(newComms, 3);

		// restoring previous state
		Long newId = responseEntity.getBody().getID();

		loginService("kor1@nesto.com", "1");
		service.deleteComment(newId);
	}

	//------------------------------ U P D A T E --------------------------------//


	@Test
	@Transactional
	public void updateCommentSuccess() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", "1"));
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		FileSystemResource fsr = new FileSystemResource("src/main/resources/images/beforeHouseWasDisco.jpg");
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		body.add("image", fsr);
		body.add("text", "Updated");
		body.add("commentId", 1L);

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/comments", HttpMethod.PUT, request, CommentDTO.class);


		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("Updated", responseEntity.getBody().getText());

	}

	@Test
	@Transactional
	public void updateCommentSomeoneElses() throws JSONException{

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor2@nesto.com", "1"));
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		FileSystemResource fsr = new FileSystemResource("src/main/resources/images/beforeHouseWasDisco.jpg");
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		body.add("image", fsr);
		body.add("text", "Updated");
		body.add("commentId", 1L);

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/comments", HttpMethod.PUT, request, CommentDTO.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

	@Test
	@Transactional
	public void updateCommentEmptyText() throws JSONException{

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor2@nesto.com", "1"));
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		FileSystemResource fsr = new FileSystemResource("src/main/resources/images/beforeHouseWasDisco.jpg");
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		body.add("image", fsr);
		body.add("text", ""); 		//!
		body.add("commentId", 1L);

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/comments", HttpMethod.PUT, request, CommentDTO.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);

	}

	@Test
	@Transactional
	public void updateCommentBadID() throws JSONException{

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor2@nesto.com", "1"));
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		FileSystemResource fsr = new FileSystemResource("src/main/resources/images/beforeHouseWasDisco.jpg");
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		body.add("image", fsr);
		body.add("text", "Text"); 		
		body.add("commentId", Constants.BAD_ID);

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange("http://localhost:" + port + "/api/comments", HttpMethod.PUT, request, CommentDTO.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);

	}
	
	@Test
	@Transactional
	public void updateCommentBadRequest() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor1@nesto.com", "1"));
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		FileSystemResource fsr = new FileSystemResource("src/main/resources/images/beforeHouseWasDisco.jpg");
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

		body.add("imageFAIL", fsr); //-------------------!-------------------------//
		body.add("text", "Text");
		body.add("commentId", 1L);		

		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/comments", HttpMethod.POST, request, CommentDTO.class);

		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}



	//------------------------------ D E L E T E --------------------------------//
	
	
	public void deleteCommentSuccess() throws Exception {

		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor4@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(null, headers);
		
		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/comments/3", HttpMethod.DELETE, requestEntity, CommentDTO.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
		Page<Comment> page = service.findAllByOfferID(1L, pageable);		
		int newComms = page.getContent().get(0).getOffer().getComments().size();

		assertEquals(newComms, 1);
	}
	
	public void deleteCommentSomeoneElses() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor4@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(null, headers);
		
		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/comments/3", HttpMethod.DELETE, requestEntity, CommentDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		
	}
	
	public void deleteCommentBadId() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor4@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(null, headers);
		
		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/comments/" + Constants.BAD_ID, HttpMethod.DELETE, requestEntity, CommentDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		
	}
	
	public void deleteCommentBadRequest() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", login("kor4@nesto.com", "1"));
		
		HttpEntity<RatingDTO> requestEntity = new HttpEntity<>(null, headers);
		
		ResponseEntity<CommentDTO> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/api/comments/a" + Constants.BAD_ID, HttpMethod.DELETE, requestEntity, CommentDTO.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		
	}

}
