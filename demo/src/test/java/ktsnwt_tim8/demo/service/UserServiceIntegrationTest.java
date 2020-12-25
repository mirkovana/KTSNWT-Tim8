package ktsnwt_tim8.demo.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static ktsnwt_tim8.demo.constants.UserConstants.*;
import ktsnwt_tim8.demo.model.User;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class UserServiceIntegrationTest {

	@Autowired
	private UserService service;

	@Test
	public void testFindByUsername() {
		User found = service.findByUsername(DB_USER_EMAIL);
		assertEquals(DB_USER_EMAIL, found.getUsername());
	}

}
