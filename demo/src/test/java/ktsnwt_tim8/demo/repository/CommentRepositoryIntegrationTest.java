package ktsnwt_tim8.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ktsnwt_tim8.demo.constants.Constants;
import ktsnwt_tim8.demo.model.Comment;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class CommentRepositoryIntegrationTest {

	@Autowired
	private CommentRepository commentRepository;
	
	//Page<Comment> findAllByOfferID(Long id, Pageable page);

	@Test
	public void testFindAllByOfferID() {
		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Page<Comment> page = commentRepository.findAllByOfferIDOrderByDateDesc(1L, pageable);
		
		assertEquals(page.getContent().size(), 2);
	}
	
	@Test
	public void testFindAllByOfferIDBadID() {
		Pageable pageable = PageRequest.of(Constants.PAGEABLE_PAGE, Constants.PAGEABLE_SIZE);
		Page<Comment> page = commentRepository.findAllByOfferIDOrderByDateDesc(Constants.BAD_ID, pageable);
		
		assertEquals(page.getContent().size(), 0);
	}
	
}
