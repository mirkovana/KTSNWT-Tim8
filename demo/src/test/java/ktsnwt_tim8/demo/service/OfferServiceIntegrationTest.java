package ktsnwt_tim8.demo.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ktsnwt_tim8.demo.constants.OfferConstants;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.Subcategory;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class OfferServiceIntegrationTest {

	@Autowired
	private OfferService offerService;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Test
	public void testFindAllPageable() {
		
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Offer> found = offerService.findAllPageable(pageable);
		assertEquals(FIND_ALL_NUMBER_OF_ITEMS, found.getNumberOfElements());
	}
	
	

	@Test
	@Transactional
	public void subscribe() throws Exception {
		login();
		Offer offer = offerService.subscribe(OfferConstants.NEW_OFFER_ID);

		assertEquals(OfferConstants.NEW_OFFER_ID, offer.getID());
		assertEquals(OfferConstants.TOTAL_SUBSCRIPTIONS, offer.getUsers().size());
	}

	@Test(expected = java.lang.Exception.class)
	@Transactional
	public void subscribeBadOfferID() throws Exception {
		login();
		Offer offer = offerService.subscribe(OfferConstants.BAD_OFFER_ID);
	}

	@Test(expected = java.lang.Exception.class)
	@Transactional
	public void subscribeAllreadySubscribed() throws Exception {
		login();
		offerService.subscribe(OfferConstants.OFFER_ID);
		offerService.subscribe(OfferConstants.OFFER_ID);
	}

	@Test
	@Transactional
	public void deleteSubscriber() throws Exception {
		login();
		Offer offer = offerService.deleteSubscriber(OfferConstants.OFFER_ID);

		assertEquals(OfferConstants.TOTAL_SUBSCRIPTIONS - 1, offer.getUsers().size());
		offerService.subscribe(OfferConstants.OFFER_ID);
	}

	@Test(expected = java.lang.Exception.class)
	@Transactional
	public void deleteSubscriberBadID() throws Exception {
		login();
		Offer offer = offerService.deleteSubscriber(OfferConstants.BAD_OFFER_ID);
	}

	@Test(expected = java.lang.Exception.class)
	@Transactional
	public void deleteSubscriberNotSubscribed() throws Exception {
		login();
		Offer offer = offerService.deleteSubscriber(OfferConstants.NEW_OFFER_ID);
	}
	
	@Test
	public void filter1() {
		List<Long> list = new ArrayList<Long>();
		list.add(3L);
		
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Offer> filtered = this.offerService.filter("", "", list, pageable);
		assertEquals(2, filtered.getContent().size());
	}
	
	@Test
	public void filter2() {
		List<Long> list = new ArrayList<Long>();
		list.add(3L);
		String name = null;
		String location = "NIS";
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Offer> found =this.offerService.filter(name, location, list, pageable);
		assertEquals(1, found.getNumberOfElements());
	}

	@Test
	public void filter3() {
		List<Long> list = new ArrayList<Long>();
		list.add(3L);
		list.add(5L);
		list.add(6L);
		String title = "";
		String location = "beOg";
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Offer> found = this.offerService.filter(title, location, list, pageable);
		assertEquals(2, found.getNumberOfElements());
	}

	@Test
	public void filter4() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Offer> found = this.offerService.filter("", "beograd", null, pageable);
		assertEquals(2, found.getNumberOfElements());
	}
	
	@Test
	public void filter5() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
		Page<Offer> found = this.offerService.filter("kalemegda", null, null, pageable);
		assertEquals(1, found.getNumberOfElements());
	}


	public void login() {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken("kor1@nesto.com", "1"));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
