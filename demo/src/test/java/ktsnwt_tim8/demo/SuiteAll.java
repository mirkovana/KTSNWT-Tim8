package ktsnwt_tim8.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import ktsnwt_tim8.demo.controller.CategoryControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.PostControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.SubcategoryControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.UserControllerIntegrationTest;
import ktsnwt_tim8.demo.repository.PostRepositoryIntegrationTest;
import ktsnwt_tim8.demo.service.PostServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ PostServiceIntegrationTest.class, 
	 PostRepositoryIntegrationTest.class, 
	CategoryControllerIntegrationTest.class,  PostControllerIntegrationTest.class, SubcategoryControllerIntegrationTest.class,
	UserControllerIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {

}
