package ktsnwt_tim8.e2e;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ktsnwt_tim8.pages.LoginPage;

public class LoginE2ETest {
	
	private WebDriver driver;
	private LoginPage loginPage;

	@Before
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		loginPage = PageFactory.initElements(driver, LoginPage.class);
	}

	@After
	public void tearDown() {
		driver.quit();
	}
	
	private void justWait(int milliseconds) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(milliseconds);
        }
    }
	
//	@Test
//	public void LoginTestSuccess() throws InterruptedException {
//
//		driver.get("http://localhost:4200/login");
//
//		justWait(1000);
//
//		loginPage.ensureIsDisplayedEmail();
//		loginPage.getEmail().sendKeys("admin@nesto.com");
//		loginPage.getPassword().sendKeys("1");
//		loginPage.getLoginBtn().click();
//		justWait(2000);
//		loginPage.ensureIsNotVisibleLoginBtn();
//		loginPage.ensureIsNotVisibleEmail();
//
//		assertEquals("http://localhost:4200/home", driver.getCurrentUrl());
//	}
	
//	@Test
//	public void LoginTestEmailDNE() throws InterruptedException {
//
//		driver.get("http://localhost:4200/login");
//
//		justWait(1000);
//
//		loginPage.ensureIsDisplayedEmail();
//		loginPage.getEmail().sendKeys("fff@nesto.com");
//		loginPage.getPassword().sendKeys("1");
//		loginPage.getLoginBtn().click();
//		justWait(2000);
//		loginPage.ensureIsDisplayedEmail();
//
//		assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
//	}
//	
//	@Test
//	public void LoginTestPasswordDNE() throws InterruptedException {
//
//		driver.get("http://localhost:4200/login");
//
//		justWait(1000);
//
//		loginPage.ensureIsDisplayedEmail();
//		loginPage.getEmail().sendKeys("admin@nesto.com");
//		loginPage.getPassword().sendKeys("5555551");
//		loginPage.getLoginBtn().click();
//		justWait(2000);
//		loginPage.ensureIsDisplayedEmail();
//
//		assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
//	}
	
//	@Test
//	public void LoginTestEmailEmpty() throws InterruptedException {
//
//		driver.get("http://localhost:4200/login");
//
//		justWait(1000);
//
//		loginPage.ensureIsDisplayedEmail();
//		loginPage.getEmail().sendKeys("");
//		loginPage.getPassword().sendKeys("5555551");
//		loginPage.getLoginBtn().click();
//		justWait(2000);
//		loginPage.ensureIsDisplayedEmail();
//
//		assertTrue(loginPage.messageErrorDisplayed("usernameReqLogin") );
//		assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
//	}
//	
//	@Test
//	public void LoginTestPasswordEmpty() throws InterruptedException {
//
//		driver.get("http://localhost:4200/login");
//
//		justWait(1000);
//
//		loginPage.ensureIsDisplayedEmail();
//		loginPage.getEmail().sendKeys("admin@nesto.com");
//		loginPage.getPassword().sendKeys("");
//		loginPage.getLoginBtn().click();
//		justWait(2000);
//		loginPage.ensureIsDisplayedEmail();
//
//		assertTrue(loginPage.messageErrorDisplayed("passwordReqLogin") );
//		assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
//	}
//	
//	@Test
//	public void LoginTestEmailAndPasswordEmpty() throws InterruptedException {
//
//		driver.get("http://localhost:4200/login");
//
//		justWait(1000);
//
//		loginPage.ensureIsDisplayedEmail();
//		loginPage.getEmail().sendKeys("");
//		loginPage.getPassword().sendKeys("");
//		loginPage.getLoginBtn().click();
//		justWait(2000);
//		loginPage.ensureIsDisplayedEmail();
//
//		assertTrue(loginPage.messageErrorDisplayed("usernameReqLogin") );
//		assertTrue(loginPage.messageErrorDisplayed("passwordReqLogin") );
//		assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
//	}
	
	@Test
	public void LoginTestEmailNotValidForm() throws InterruptedException {

		driver.get("http://localhost:4200/login");

		justWait(1000);

		loginPage.ensureIsDisplayedEmail();
		loginPage.getEmail().sendKeys("xdcfvgbhjkl");
		loginPage.getPassword().sendKeys("1");
		loginPage.getLoginBtn().click();
		justWait(2000);
		loginPage.ensureIsDisplayedEmail();

		assertTrue(loginPage.messageErrorDisplayed("usernameEmailFormatLogin") );
		assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
	}
}
