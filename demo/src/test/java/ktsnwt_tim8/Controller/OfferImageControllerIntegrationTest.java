package ktsnwt_tim8.Controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import io.jsonwebtoken.lang.Collections;
import ktsnwt_tim8.demo.DemoApplication;
import ktsnwt_tim8.demo.dto.OfferImageDTO;
import ktsnwt_tim8.demo.dto.UserLoginDTO;
import ktsnwt_tim8.demo.dto.UserTokenStateDTO;
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

//	@Test
//	public void testAddPhoto() {
//
//		HttpHeaders headers = new HttpHeaders();
////		System.out.println("Authorization"+ login("admin@nesto.com", "1"));
//		headers.add("Authorization", login("admin@nesto.com", "1"));
////		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//		OfferImageDTO imgDTO = new OfferImageDTO(65L, "Opis", "slika");
//		HttpEntity<OfferImageDTO> request = new HttpEntity<>(imgDTO, headers);
//
////		ResponseEntity<OfferImageDTO> responseEntity = restTemplate.exchange("/api/Offer-images", HttpMethod.POST,
////				request, OfferImageDTO.class);
//		
//		ResponseEntity<OfferImageDTO> responseEntity =
//                restTemplate.postForEntity("/api/Offer-images",
//                        request,
//                        OfferImageDTO.class);
//		
//		request.getHeaders();
//		request.getBody();
//		OfferImageDTO dto = responseEntity.getBody();
//		System.out.println(dto.getDescription());
//		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//
//	}

	@Test
	public void testGetAllOfferImages() {
		List<OfferImageDTO> list = new ArrayList<OfferImageDTO>();
		Page<OfferImageDTO> slike = new PageImpl<OfferImageDTO>(list);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<OfferImageDTO> request = new HttpEntity<>(headers);
		
		
//		ResponseEntity<OfferImageDTO[]> responseEntity = restTemplate
//				.exchange("/api/Offer-images?id=1&page=0&size=5",HttpMethod.GET,request, OfferImageDTO[].class);
	    
		ResponseEntity<PagedResources<OfferImageDTO>> response = restTemplate.exchange("/api/Offer-images?id=1&page=0&size=5",
		        HttpMethod.GET, null, new ParameterizedTypeReference<PagedResources<OfferImageDTO>>() {});
		
	}

}
