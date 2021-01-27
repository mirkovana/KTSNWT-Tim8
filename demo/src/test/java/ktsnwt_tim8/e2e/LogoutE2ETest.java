package ktsnwt_tim8.e2e;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ktsnwt_tim8.pages.LoginPage;
import ktsnwt_tim8.pages.LogoutPage;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogoutE2ETest {
	
	private WebDriver driver;
	private LoginPage loginPage;
	private LogoutPage logoutPage;
	
	@Before
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		logoutPage = PageFactory.initElements(driver, LogoutPage.class);
	}

	@After
	public void tearDown() {
		driver.quit();
	}
	
	private void login() throws InterruptedException {

		driver.get("http://localhost:4200/login");
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.getEmail().sendKeys("admin@nesto.com");
		loginPage.getPassword().sendKeys("1");
		loginPage.getLoginBtn().click();
		justWait(1000);
	}
	
	private void justWait(int milliseconds) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(milliseconds);
        }
    }
	
	@Test
	public void LogoutTestSuccess() throws InterruptedException {
		login();
		justWait(1000);

		logoutPage.getLogoutUser().click();

		justWait(1000);
		assertEquals("http://localhost:4200/home", driver.getCurrentUrl());
	}
}
