package ktsnwt_tim8.e2e;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

 
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ktsnwt_tim8.pages.ValidationEmailPage;

public class ValidateEmailE2ETest {

	private WebDriver driver;
	private ValidationEmailPage validationEmailPage;

	@Before
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		validationEmailPage = PageFactory.initElements(driver, ValidationEmailPage.class);
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	 
	  @Test 
	  public void RegistrationTestNameEmpty() throws InterruptedException {
	  
	  driver.get("http://localhost:4200/validateEmail");
	  
	  justWait();
	  
	  validationEmailPage.getToken().sendKeys("");
	  
	   
	  validationEmailPage.getValidateToken().click();
	  
	  validationEmailPage.ensureIsDisplayedToken();
	  
	  //validationEmailPage.ensureIsDisplayedToken();
	  assertEquals("http://localhost:4200/validateEmail", driver.getCurrentUrl());
	  assertTrue(validationEmailPage.messageErrorDisplayed("tokenReq") );
	  
	  
	  }
	  
	
	private void justWait() throws InterruptedException {
		synchronized (driver) {
			driver.wait(1000);
		}
	}
}
