package ktsnwt_tim8.demo.repository;

import static ktsnwt_tim8.demo.constants.OfferConstants.FIND_ALL_NUMBER_OF_ITEMS;
import static ktsnwt_tim8.demo.constants.OfferConstants.PAGEABLE_PAGE;
import static ktsnwt_tim8.demo.constants.OfferConstants.PAGEABLE_SIZE;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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

import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Subcategory;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
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
	
	@Test
	public void filterNamePlaceSubcats1() {
		Subcategory s1 = new Subcategory();
		s1.setID(3L);
		List<Subcategory> subcats = new ArrayList<Subcategory>();
		subcats.add(s1);
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Offer> found = offerRepository.filterByTitlePlaceAndSubcategory("", "", subcats, pageable);
		assertEquals(2, found.getNumberOfElements());
	}
	
	@Test
	public void filterNamePlaceSubcats2() {
		Subcategory s1 = new Subcategory();
		s1.setID(3L);
		List<Subcategory> subcats = new ArrayList<Subcategory>();
		subcats.add(s1);
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Offer> found = offerRepository.filterByTitlePlaceAndSubcategory("", "nis", subcats, pageable);
		assertEquals(1, found.getNumberOfElements());
	}
	
	@Test
	public void filterNamePlaceSubcats3() {
		Subcategory s1 = new Subcategory();
		s1.setID(3L);
		Subcategory s2 = new Subcategory();
		s2.setID(5L);
		Subcategory s3 = new Subcategory();
		s3.setID(6L);
		List<Subcategory> subcats = new ArrayList<Subcategory>();
		subcats.add(s1);
		subcats.add(s2);
		subcats.add(s3);
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Offer> found = offerRepository.filterByTitlePlaceAndSubcategory("", "beog", subcats, pageable);
		assertEquals(2, found.getNumberOfElements());
	}
	
	@Test
	public void filterNamePlace3() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Offer> found = offerRepository.filterByTitleAndPlace("", "beograd", pageable);
		assertEquals(2, found.getNumberOfElements());
	}
}
