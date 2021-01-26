package ktsnwt_tim8.e2e;

 
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

 
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ktsnwt_tim8.pages.RegistrationPage;
import ktsnwt_tim8.pages.ValidationEmailPage;

public class RegistrationE2ETest {

	private WebDriver driver;
	private RegistrationPage registrationPage;
	private ValidationEmailPage validationEmailPage;

	@Before
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		registrationPage = PageFactory.initElements(driver, RegistrationPage.class);
		validationEmailPage = PageFactory.initElements(driver, ValidationEmailPage.class); 
	}

	@After
	public void tearDown() {
		driver.quit();
	}

//	@Test
//	public void RegistrationTestSuccess() throws InterruptedException {
//
//		driver.get("http://localhost:4200/registration");
//
//		justWait();
//
//		registrationPage.getName().sendKeys("jovan");
//
//		registrationPage.getSurname().sendKeys("jovanovic");
//		registrationPage.getUsername().sendKeys("jovan@nesto.com");
//		registrationPage.getPassword().sendKeys("123456");
//		registrationPage.getRegistrationButton().click();
//
//		registrationPage.ensureIsNotVisibleRegistrationButton();
//
//		validationEmailPage.ensureIsDisplayedToken();  
//		assertEquals("http://localhost:4200/validateEmail", driver.getCurrentUrl());
//
//	}
//
//	
//	@Test
//	public void RegistrationTestNameEmpty() throws InterruptedException {
//
//		driver.get("http://localhost:4200/registration");
//
//		justWait();
//
//		registrationPage.getName().sendKeys("");
//
//		registrationPage.getSurname().sendKeys("jovanovic");
//		registrationPage.getUsername().sendKeys("jovanasd@nesto.com");
//		registrationPage.getPassword().sendKeys("123456");
//		registrationPage.getRegistrationButton().click();
//
//		registrationPage.ensureIsDisplayedName();
//
//		//validationEmailPage.ensureIsDisplayedToken();  
//		assertEquals("http://localhost:4200/registration", driver.getCurrentUrl());
//		assertTrue(registrationPage.messageErrorDisplayed("nameReq") );
//	
//     
//
//	}
//	
//	@Test
//	public void RegistrationTestSurnameEmpty() throws InterruptedException {
//
//		driver.get("http://localhost:4200/registration");
//
//		justWait();
//
//		registrationPage.getName().sendKeys("jovan");
//
//		registrationPage.getSurname().sendKeys("");
//		registrationPage.getUsername().sendKeys("jovanasd@nesto.com");
//		registrationPage.getPassword().sendKeys("123456");
//		registrationPage.getRegistrationButton().click();
//
//		registrationPage.ensureIsDisplayedName();
//
//		//validationEmailPage.ensureIsDisplayedToken();  
//		assertEquals("http://localhost:4200/registration", driver.getCurrentUrl());
//		assertTrue(registrationPage.messageErrorDisplayed("surnameReq") );
//     
//
//	}
//	@Test
//	public void RegistrationTestEmailEmpty() throws InterruptedException {
//
//		driver.get("http://localhost:4200/registration");
//
//		justWait();
//
//		registrationPage.getName().sendKeys("jovan");
//
//		registrationPage.getSurname().sendKeys("jovanovic");
//		registrationPage.getUsername().sendKeys("");
//		registrationPage.getPassword().sendKeys("123456");
//		registrationPage.getRegistrationButton().click();
//
//		registrationPage.ensureIsDisplayedName();
//
// 		assertEquals("http://localhost:4200/registration", driver.getCurrentUrl());
//		assertTrue(registrationPage.messageErrorDisplayed("emailReq") );
//     
//
//	}
//	
//	@Test
//	public void RegistrationTestPasswordEmpty() throws InterruptedException {
//
//		driver.get("http://localhost:4200/registration");
//
//		justWait();
//
//		registrationPage.getName().sendKeys("jovan");
//
//		registrationPage.getSurname().sendKeys("jovanovic");
//		registrationPage.getUsername().sendKeys("jovanasd@nesto.com");
//		registrationPage.getPassword().sendKeys("");
//		registrationPage.getRegistrationButton().click();
//
//		registrationPage.ensureIsDisplayedName();
//
// 		assertEquals("http://localhost:4200/registration", driver.getCurrentUrl());
//		assertTrue(registrationPage.messageErrorDisplayed("passwordReq") );
//     
//
//	}
//	
//	@Test
//	public void RegistrationTestPasswordTooShort() throws InterruptedException {
//
//		driver.get("http://localhost:4200/registration");
//
//		justWait();
//
//		registrationPage.getName().sendKeys("jovan");
//
//		registrationPage.getSurname().sendKeys("jovanovic");
//		registrationPage.getUsername().sendKeys("jovanasd@nesto.com");
//		registrationPage.getPassword().sendKeys("12");
//		registrationPage.getRegistrationButton().click();
//
//		registrationPage.ensureIsDisplayedName();
//
// 		assertEquals("http://localhost:4200/registration", driver.getCurrentUrl());
//		assertTrue(registrationPage.messageErrorDisplayed("passwordShort") );
//     
//
//	}
	
	@Test
	public void RegistrationTestEmailNotValidForm() throws InterruptedException {

		driver.get("http://localhost:4200/registration");

		justWait();

		registrationPage.ensureIsDisplayedName();
		
		registrationPage.getName().sendKeys("jovan");
		registrationPage.getSurname().sendKeys("jovanovic");
		registrationPage.getUsername().sendKeys("jovanasdnestoom");
		registrationPage.getPassword().sendKeys("12");
		registrationPage.getRegistrationButton().click();
		justWait();
		registrationPage.ensureIsDisplayedName();

		assertTrue(registrationPage.messageErrorDisplayed("usernameEmailFormatRegistration") );
		assertEquals("http://localhost:4200/registration", driver.getCurrentUrl());
	}
	
	private void justWait() throws InterruptedException {
		synchronized (driver) {
			driver.wait(1000);
		}
	}
}
