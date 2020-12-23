package ktsnwt_tim8.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.TestPropertySource;

import ktsnwt_tim8.service.OfferImageServiceIntegrationTest;

@RunWith(Suite.class)
@SuiteClasses({OfferImageServiceIntegrationTest.class})
@TestPropertySource("classpath:src/test/resources/test.properties")
public class SuiteAll {


}
