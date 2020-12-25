package ktsnwt_tim8.demo.repository;

import static ktsnwt_tim8.demo.constants.UserConstants.DB_USER_EMAIL;
import static ktsnwt_tim8.demo.constants.UserConstants.USER_EMAIL;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import ktsnwt_tim8.demo.model.User;
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class UserRepositoryIntegrationTest {

	
	   @Autowired
	    private UserRepository userRepository;

	    @Test
	    public void testFindByUsername() {
	        User found = userRepository.findByUsername(USER_EMAIL);
	        assertEquals(USER_EMAIL, found.getEmail());
	    }
}
