package ktsnwt_tim8.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import ktsnwt_tim8.demo.controller.CategoryControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.OfferControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.PostControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.SubcategoryControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.UserControllerIntegrationTest;
import ktsnwt_tim8.demo.repository.OfferRepositoryIntegrationTest;
import ktsnwt_tim8.demo.repository.PostRepositoryIntegrationTest;
import ktsnwt_tim8.demo.repository.UserRepositoryIntegrationTest;
import ktsnwt_tim8.demo.service.OfferServiceIntegrationTest;
import ktsnwt_tim8.demo.service.PostServiceIntegrationTest;
import ktsnwt_tim8.demo.service.UserServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({OfferServiceIntegrationTest.class,  OfferServiceIntegrationTest.class, OfferServiceUnitTest.class, PostServiceIntegrationTest.class, UserServiceIntegrationTest.class,
	OfferRepositoryIntegrationTest.class, PostRepositoryIntegrationTest.class, UserRepositoryIntegrationTest.class,
	CategoryControllerIntegrationTest.class, OfferControllerIntegrationTest.class, PostControllerIntegrationTest.class, SubcategoryControllerIntegrationTest.class,
	UserControllerIntegrationTest.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {

}
