package ktsnwt_tim8.demo.e2e;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import ktsnwt_tim8.demo.page.HomePage;
import ktsnwt_tim8.demo.page.LoginPage;

public class HomeE2ETest {

	private WebDriver driver;

	private LoginPage loginPage;

	private HomePage homePage;

	@Before
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		homePage = PageFactory.initElements(driver, HomePage.class);
	}

	@After
	public void tearDown() {
		driver.quit();
	}

	public void login() {
		driver.get("http://localhost:4200/login");

		loginPage.ensureIsDisplayedUsername();

		loginPage.getUsername().sendKeys("kor1@nesto.com");

		loginPage.getPassword().sendKeys("1");

		loginPage.getLoginBtn().click();

		loginPage.ensureIsNotVisibleLoginBtn();

		homePage.ensureIsDisplayedMap();

		assertEquals("http://localhost:4200/home", driver.getCurrentUrl());
	}

	@Test
	public void SubscribeSuccess() throws InterruptedException {
		login();

		homePage.ensureIsDisplayedMap();

		homePage.getZoomIn().click();
		justWait(300);
		homePage.getZoomIn().click();
		justWait(300);
		homePage.getZoomIn().click();
		justWait(300);
		homePage.getZoomIn().click();
		justWait(300);

		homePage.getMarker().click();

		justWait();

		homePage.getSubBtn().click();

		justWait();

		homePage.ensureIsDisplayedGreenToast();

	}

//	 Already subscribed 
	@Test
	public void SubscribeUnuccess() throws InterruptedException {
		login();

		homePage.ensureIsDisplayedMap();

		homePage.getZoomIn().click();
		justWait(300);
		homePage.getZoomIn().click();
		justWait(300);
		homePage.getZoomIn().click();
		justWait(300);
		homePage.getZoomIn().click();
		justWait(300);

		homePage.getBadMarker().click();

		homePage.getSubBtn().click();

		justWait();

		homePage.ensureIsDisplayedRedToast();

	}

	private void justWait() throws InterruptedException {
		synchronized (driver) {
			driver.wait(1000);
		}
	}

	private void justWait(int time) throws InterruptedException {
		synchronized (driver) {
			driver.wait(time);
		}
	}
}
