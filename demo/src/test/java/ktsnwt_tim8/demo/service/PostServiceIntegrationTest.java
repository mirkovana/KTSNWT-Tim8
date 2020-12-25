package ktsnwt_tim8.demo.service;

//import static ktsnwt_tim8.demo.constants.PostConstants.DB_OFFER;
import static ktsnwt_tim8.demo.constants.PostConstants.FIND_ALL_NUMBER_OF_ITEMS;
import static ktsnwt_tim8.demo.constants.PostConstants.PAGEABLE_PAGE;
import static ktsnwt_tim8.demo.constants.PostConstants.PAGEABLE_SIZE;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class PostServiceIntegrationTest {

	@Autowired
	private OfferService service;

	@Autowired
	private PostService postService;

	@Test
	public void testFindAllByOffer1() {
		Long idOffer = 2L;
		Offer offer = service.get(idOffer);
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Post> found = postService.findAllByOffer1(offer, pageable);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
	}

	@Test
	public void testFindAllByOffer() {
		 Long idOffer = 2L;
		 Offer offer = service.get(idOffer);
		 List<Post> found = postService.findAllByOffer(offer);
	     assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.size());
	}
}
