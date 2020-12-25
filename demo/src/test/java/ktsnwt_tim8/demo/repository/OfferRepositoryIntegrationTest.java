package ktsnwt_tim8.demo.repository;

import static ktsnwt_tim8.demo.constants.OfferConstants.FIND_ALL_NUMBER_OF_ITEMS;
import static ktsnwt_tim8.demo.constants.OfferConstants.PAGEABLE_PAGE;
import static ktsnwt_tim8.demo.constants.OfferConstants.PAGEABLE_SIZE;
import static org.junit.Assert.assertEquals;

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

import ktsnwt_tim8.demo.model.Offer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class OfferRepositoryIntegrationTest {

	
	@Autowired
	private OfferRepository offerRepository;
	
	@Test
	public void testFindAllPageable() {
		
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Offer> found = offerRepository.findAll(pageable);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
	}
}
