package ktsnwt_tim8.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import ktsnwt_tim8.demo.controller.CategoryControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.CommentControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.OfferControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.OfferImageControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.PostControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.RatingControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.SubcategoryControllerIntegrationTest;
import ktsnwt_tim8.demo.controller.UserControllerIntegrationTest;
import ktsnwt_tim8.demo.repository.CommentRepositoryIntegrationTest;
import ktsnwt_tim8.demo.repository.OfferImageRepositoryIntegrationTest;
import ktsnwt_tim8.demo.repository.OfferRepositoryIntegrationTest;
import ktsnwt_tim8.demo.repository.PostRepositoryIntegrationTest;
import ktsnwt_tim8.demo.repository.RatingRepositoryIntegrationTest;
import ktsnwt_tim8.demo.repository.UserRepositoryIntegrationTest;
import ktsnwt_tim8.demo.service.CommentServiceIntegrationTests;
import ktsnwt_tim8.demo.service.EmailServiceIntegrationTest;
import ktsnwt_tim8.demo.service.OfferImageServiceIntegrationTest;
import ktsnwt_tim8.demo.service.OfferServiceIntegrationTest;
import ktsnwt_tim8.demo.service.OfferServiceUnitTest;
import ktsnwt_tim8.demo.service.PostServiceIntegrationTest;
import ktsnwt_tim8.demo.service.RatingServiceIntegrationTests;
import ktsnwt_tim8.demo.service.UserServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({ OfferServiceIntegrationTest.class, OfferImageServiceIntegrationTest.class,
	PostServiceIntegrationTest.class, OfferServiceUnitTest.class, UserServiceIntegrationTest.class,
	OfferImageControllerIntegrationTest.class, OfferRepositoryIntegrationTest.class,
	PostRepositoryIntegrationTest.class, UserRepositoryIntegrationTest.class,
	CategoryControllerIntegrationTest.class, OfferControllerIntegrationTest.class,
	PostControllerIntegrationTest.class, SubcategoryControllerIntegrationTest.class,
	UserControllerIntegrationTest.class, OfferImageRepositoryIntegrationTest.class,
	RatingRepositoryIntegrationTest.class, CommentRepositoryIntegrationTest.class,
	RatingServiceIntegrationTests.class, RatingControllerIntegrationTest.class,
	CommentServiceIntegrationTests.class,
	CommentControllerIntegrationTest.class, EmailServiceIntegrationTest.class})
	//CommentServiceIntegrationTests.class})
@TestPropertySource("classpath:test.properties")
public class SuiteAll {

}
