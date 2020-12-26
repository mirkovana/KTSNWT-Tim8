package ktsnwt_tim8.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ktsnwt_tim8.demo.constants.OfferImageConstants;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.OfferImage;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class OfferImageRepositoryIntegrationTest {

	@Autowired
	private OfferImageRepository repo;

	@Test
	public void findAllByOfferByID() {
		Pageable paging = PageRequest.of(OfferImageConstants.PAGEABLE_PAGE, OfferImageConstants.PAGEABLE_SIZE);
		Page<OfferImage> images = repo.findAllByOfferID(OfferImageConstants.OFFER_ID, paging);

		assertEquals(OfferImageConstants.FIND_ALL_NUMBER_OF_ITEMS, images.getTotalElements());
	}
	
	@Test
	public void findAllByOffer() {
		Offer off = new Offer(OfferImageConstants.OFFER_ID, "Title", "Desc", 12.2, 20.0);
		List<OfferImage> images = repo.findAllByOffer(off);
		
		assertEquals(OfferImageConstants.FIND_ALL_NUMBER_OF_ITEMS, images.size());
	}
}
