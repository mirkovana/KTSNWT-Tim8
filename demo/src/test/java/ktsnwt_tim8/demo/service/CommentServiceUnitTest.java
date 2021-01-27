package ktsnwt_tim8.demo.service;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import ktsnwt_tim8.demo.constants.Constants;
import ktsnwt_tim8.demo.dto.CommentDTO;
import ktsnwt_tim8.demo.model.Comment;
import ktsnwt_tim8.demo.model.Offer;
import ktsnwt_tim8.demo.model.RegisteredUser;
import ktsnwt_tim8.demo.repository.CommentRepository;
import ktsnwt_tim8.demo.repository.OfferRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CommentServiceUnitTest {

	@Autowired
	private CommentService service;

	@MockBean
	private CommentRepository repo;

	@MockBean
	private OfferRepository offerRepo;

	@Autowired
	private AuthenticationManager authenticationManager;

	private static MultipartFile emptyFile = new MockMultipartFile("empty", new byte[0]);
	
	@Before
	public void setup() {
		Comment c = new Comment(1L, "New comment", new Date());
		RegisteredUser user = new RegisteredUser();
		user.setID(2L);
		c.setUser(user);
		Offer off1 = new Offer(1L, "naslov1", "Opis1", 11.2, 23.0);
		List<Comment> comments = new ArrayList<Comment>();
		comments.add(new Comment());
		comments.add(new Comment());
		comments.add(new Comment());		
		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
        Page<Comment> commentsPage = new PageImpl<Comment>(comments, pageable, 3);
        given(offerRepo.findById(1L)).willReturn(Optional.of(off1));
        given(repo.findAllByOfferID(1L, pageable)).willReturn(commentsPage);
        given(repo.findAllByOfferID(5L, pageable)).willReturn(null);
        given(offerRepo.findOneByID(1L)).willReturn(off1);
        given(repo.save(c)).willReturn(c);
        given(repo.findById(1L)).willReturn(Optional.of(c));
        doNothing().when(repo).deleteById(1L);

	}

	@Test
	public void testFindAllPageable() throws Exception {
		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Page<Comment> found = service.findAllByOfferID(1L, pageable);
		verify(offerRepo, times(1)).findById(1L);
		verify(repo, times(1)).findAllByOfferID(1L, pageable);
		assertEquals(3, found.getNumberOfElements());
	}
	
	@Test
	public void testFindAllPageableBadId() throws Exception {
		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Exception exception = assertThrows(Exception.class, () -> {
			service.findAllByOfferID(5L, pageable);
		});
		assertEquals(exception.getMessage(), "Offer with the given ID does not exist.");

	}
	
	@Test
	public void createCommentEmptyText() {
		login();
		Exception exception = assertThrows(Exception.class, () -> {
	        service.create(1L, new CommentDTO(""), emptyFile);
	    });
		
	    String expectedMessage = "Comment cannot be empty";
	    String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void updateCommentSomeoneElses() throws Exception {
		login2();
		Exception exception = assertThrows(Exception.class, () -> {
			service.updateComment(1L, new CommentDTO("Text"), emptyFile);
		});
		assertEquals(exception.getMessage(), "You can update only your comments.");
	
	}
	
	@Test
	public void deleteCommenNonExistent() throws Exception {
		login2();
		Exception exception = assertThrows(Exception.class, () -> {
		service.deleteComment(5L);
		});
		assertEquals(exception.getMessage(), "Comment with given id does not exits.");
	}
	
	@Test
	public void deleteCommentSuccess() throws Exception {
		login();
		service.deleteComment(1L);
		verify(repo, times(1)).findById(1L);
		verify(repo, times(1)).deleteById(1L);
		
	}
	
	@Test
	public void deleteCommentSomeoneElses() throws Exception {
		login2();
		Exception exception = assertThrows(Exception.class, () -> {
		service.deleteComment(1L);
		});
		assertEquals(exception.getMessage(), "You can delete only your comments.");
	}
	
	public void login() {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken("kor1@nesto.com", "1"));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	public void login2() {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken("kor2@nesto.com", "1"));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
