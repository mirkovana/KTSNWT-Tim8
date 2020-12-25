package ktsnwt_tim8.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ktsnwt_tim8.constants.OfferConstants;
import ktsnwt_tim8.demo.DemoApplication;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.model.User;
import ktsnwt_tim8.demo.repository.OfferRepository;
import ktsnwt_tim8.demo.service.OfferService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@TestPropertySource("classpath:test.properties")
public class OfferServiceUnitTest {

	@Autowired
	private OfferService service;

	@MockBean
	private OfferRepository repo;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Before
	public void setup() {
		Offer off1 = new Offer(1L, "naslov", "Opis", 12.2, 23.0);
		Offer off2 = new Offer(2L, "naslov", "Opis", 12.2, 23.0);
		Set<RegisteredUser> users = new HashSet<RegisteredUser>();
		Set<RegisteredUser> users1 = new HashSet<RegisteredUser>();
//		RegisteredUser u1 = new RegisteredUser("Name", "surname");
//		users.add(u1);
		off2.setUsers(users);
		off1.setUsers(users1);
		given(repo.findById(OfferConstants.OFFER_ID)).willReturn(java.util.Optional.of(off1));
		given(repo.findOneByID(OfferConstants.NEW_OFFER_ID)).willReturn(off2);
		given(repo.findOneByID(OfferConstants.OFFER_ID)).willReturn(off1);
	}

	@Test
	public void testFindById() {
		Offer found = service.get(OfferConstants.OFFER_ID);
		verify(repo, times(1)).findById(OfferConstants.OFFER_ID);
		assertEquals(OfferConstants.OFFER_ID, found.getID());
	}

	@Test
	public void testSubscribe() throws Exception {
		login();
		Offer sub = service.subscribe(OfferConstants.NEW_OFFER_ID);
	
		verify(repo, times(1)).findOneByID(OfferConstants.NEW_OFFER_ID);

		assertEquals(OfferConstants.NEW_OFFER_ID, sub.getID());
	}

	@Test(expected = java.lang.Exception.class)
	public void testSubscribeBadID() throws Exception {
		login();
		Offer sub = service.subscribe(OfferConstants.BAD_OFFER_ID);
	}
	
	@Test(expected = java.lang.Exception.class)
	public void testSubscribeAllreadySubscribed() throws Exception {
		login();
		Offer sub = service.subscribe(OfferConstants.OFFER_ID);
	}
	
	@Test
	public void deleteSubscriber() throws Exception {
		login();
		Offer sub = service.deleteSubscriber(OfferConstants.OFFER_ID);
		verify(repo, times(1)).findOneByID(OfferConstants.OFFER_ID);
		
		assertEquals(OfferConstants.OFFER_ID, sub.getID());
	}
	
	
	@Test(expected = java.lang.Exception.class)
	public void deleteSubscriberBadID() throws Exception {
		login();
		Offer sub = service.deleteSubscriber(OfferConstants.BAD_OFFER_ID);
	}
	
	@Test(expected = java.lang.Exception.class)
	public void deleteSubscriberNotSubscribed() throws Exception {
		login();
		Offer sub = service.deleteSubscriber(OfferConstants.NEW_OFFER_ID);
	}

	public void login() {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken("kor1@nesto.com", "1"));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
