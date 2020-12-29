package ktsnwt_tim8.demo.service;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.web.multipart.MultipartFile;

import ktsnwt_tim8.demo.constants.Constants;
import ktsnwt_tim8.demo.DemoApplication;
import ktsnwt_tim8.demo.dto.CommentDTO;
import ktsnwt_tim8.demo.model.Comment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
@TestPropertySource("classpath:test.properties")
public class CommentServiceIntegrationTests {

	@Autowired
	private CommentService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	

	public void login(String username, String password) {
		Authentication authentication = authenticationManager.authenticate
				(new UsernamePasswordAuthenticationToken(username, password)); 
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	
	//------------------------------ G E T  --------------------------------//
	
	@Test(expected = Exception.class)
	public void findCommentsWithNonExistingOfferId() throws Exception {
		try{
			Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
			service.findAllByOfferID(Constants.BAD_ID, pageable);
		}
		catch(Exception re)
		{
			String message = "Offer with the given ID does not exist.";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("Offer with non-existent ID exception did not throw!");
		
	}
	
	@Test
	@Transactional
	public void findCommentsWithExistingOfferId() throws Exception {

		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Page<Comment> page = service.findAllByOfferID(10L, pageable);
		
		assertEquals(page.getContent().size(), 1);
	}
	
	
	
	//------------------------------ C R E A T E --------------------------------//
	
	@Test (expected = Exception.class)
	public void createCommentWithNonExistentOffer() throws Exception {
		try{
			login("kor1@nesto.com", "1");
			service.create(Constants.BAD_ID, new CommentDTO(Constants.TEXT), null);
		}
		catch(Exception re)
		{
			String message = "Offer with passed ID does not exist";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("Offer with passed ID does not exist exception not thrown.");
	}
	

	@Test (expected = Exception.class)
	public void createCommentWithoutText() throws Exception {
		try{
			login("kor1@nesto.com", "1");
			service.create(1L, new CommentDTO(""), null);
		}
		catch(Exception re)
		{
			String message = "Comment cannot be empty";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("Comment cannot be empty exception not thrown.");
	}
		

	@Test
	@Transactional
	public void commentWithoutPictureSuccess() throws Exception {

		login("kor1@nesto.com", "1");

		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Page<Comment> page = service.findAllByOfferID(1L, pageable);
		int oldNumOfComments = page.getContent().size();

		byte[] arr = new byte[0];
		
		MultipartFile multipartFile = new MockMultipartFile("empty", arr);
		
		Comment c = service.create(1L, new CommentDTO(Constants.TEXT), multipartFile);
		
		page = service.findAllByOfferID(1L, pageable);
		int newNumOfComments =  page.getContent().size();	
		
		assertEquals(oldNumOfComments + 1, newNumOfComments);
		assertEquals(c.getText(), Constants.TEXT);
		
		// deleting the added comment
		service.deleteCommentById(c);

	}
	
	@Test
	@Transactional
	public void commentWithPictureSuccess() throws Exception {

		login("kor1@nesto.com", "1");

		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Page<Comment> page = service.findAllByOfferID(1L, pageable);
		int oldNumOfComments = page.getContent().size();

		
		MultipartFile multipartFile = new MockMultipartFile("new", new FileInputStream(new File("src/main/resources/images/beforeHouseWasDisco.jpg")));
		
		Comment c = service.create(1L, new CommentDTO(Constants.TEXT), multipartFile);

		File file = new File(c.getImagePath());
		
		page = service.findAllByOfferID(1L, pageable);
		int newNumOfComments =  page.getContent().size();
		
		assertTrue(file.exists());
		assertEquals(oldNumOfComments + 1, newNumOfComments);
		assertEquals(c.getText(), Constants.TEXT);
		
		//deleting the added comment
		service.deleteComment(c.getID());

	}

	
	//------------------------------ U P D A T E --------------------------------//
	
	@Test (expected = Exception.class)
	public void updateCommentNonExistentId() throws Exception {
		try{
			login("kor1@nesto.com", "1");
			MultipartFile multipartFile = new MockMultipartFile("empty", new byte[0]);
			service.updateComment(Constants.BAD_ID, new CommentDTO(Constants.TEXT), multipartFile);
		}
		catch(Exception re)
		{
			String message = "Comment with given id does not exist.";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("Comment with given id does not exist! exception did not throw!");
	}
	
	@Test (expected = Exception.class)
	public void updateCommentEmpty() throws Exception {
		try{
			login("kor1@nesto.com", "1");
			MultipartFile multipartFile = new MockMultipartFile("empty", new byte[0]);
			service.updateComment(2L, new CommentDTO(""), multipartFile);
		}
		catch(Exception re)
		{
			String message = "Comment cannot be empty.";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("Comment cannot be empty exception did not throw!");
	}
	
	@Test (expected = Exception.class)
	public void updateCommentsSomeoneElses() throws Exception {
		try{
			login("kor3@nesto.com", "1");
			MultipartFile multipartFile = new MockMultipartFile("empty", new byte[0]);
			service.updateComment(2L, new CommentDTO(Constants.TEXT), multipartFile);
		}
		catch(Exception re)
		{
			String message = "You can update only your comments.";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("You can update only your comments exception did not throw!");
	}
	
	@Test
	public void updateOfferSuccess() throws Exception {

		login("kor2@nesto.com", "1");

		MultipartFile multipartFile = new MockMultipartFile("empty", new byte[0]);
		Comment c = service.updateComment(3l, new CommentDTO(Constants.TEXT), multipartFile);

		assertEquals(c.getText(), Constants.TEXT);		

	}
	
	
	//------------------------------ D E L E T E --------------------------------//

	@Test  (expected = Exception.class)
	@Transactional
	public void deleteCommentSomeoneElses() throws Exception {
		try{
			login("kor3@nesto.com", "1");
			service.deleteComment(1l);
		}
		catch(Exception re)
		{
			String message = "You can delete only your comments.";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("You can delete only your comments. exception did not throw!");
	}


	@Test (expected = Exception.class)
	@Transactional
	public void deleteCommentNonExistent() throws Exception {
		try{
			login("kor1@nesto.com", "1");
			service.deleteComment(Constants.BAD_ID);
		}
		catch(Exception re)
		{
			String message = "Comment with given id does not exits.";
			assertEquals(message, re.getMessage());
			throw re;
		}
		fail("Comment with given id does not exits. exception did not throw!");
	}

	//@Test 
	//@Transactional
	public void deleteCommentWithoutPictureSuccess() throws Exception {
		login("kor1@nesto.com", "1");

		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Page<Comment> page = service.findAllByOfferID(1L, pageable);
		
		int oldNumOfComments = page.getContent().size();
		
		service.deleteComment(1L);

		page = service.findAllByOfferID(1L, pageable);
		int newNumOfComments = page.getContent().size();
		
		assertEquals(oldNumOfComments - 1, newNumOfComments);
	
	}
	

	@Test 
	@Transactional
	public void deleteCommentWithPictureSuccess() throws Exception {
		// ili da je ovde kreiram pa obrisem?
		login("kor3@nesto.com", "1");

		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Page<Comment> page;// = service.findAllByOfferID(1L, pageable);
		
		//int oldNumOfComments = page.getContent().size();
		//File images = new File("src/main/resources/images");
		//int oldNumOfImgs = images.listFiles().length;
		
		service.deleteComment(4L);
		
		//images = new File("src/main/resources/images");
		//int newNumOfImgs = images.listFiles().length;

		page = service.findAllByOfferID(3L, pageable);
		int newNumOfComments = page.getContent().size();
		
		
		assertEquals(0, newNumOfComments);
		//assertEquals(oldNumOfImgs - 1, newNumOfImgs);
	}
	
	
}
