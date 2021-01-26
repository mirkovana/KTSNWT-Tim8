package ktsnwt_tim8.demo.e2e;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import ktsnwt_tim8.demo.page.HomePage;
import ktsnwt_tim8.demo.page.LoginPage;
import ktsnwt_tim8.demo.page.SubscriptionsPage;

public class SubscriptionsE2ETest {

	private WebDriver driver;

	private LoginPage loginPage;

	private HomePage homePage;

	private SubscriptionsPage subPage;

	@Before
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.navigate().to("http://localhost:4200/login");

		driver.manage().window().maximize();
		homePage = PageFactory.initElements(driver, HomePage.class);
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		subPage = PageFactory.initElements(driver, SubscriptionsPage.class);

	}

	@After
	public void tearDown() {
		driver.quit();
	}

	public void login() {
		loginPage.ensureIsDisplayedUsername();

		loginPage.getUsername().sendKeys("kor1@nesto.com");

		loginPage.getPassword().sendKeys("1");

		loginPage.getLoginBtn().click();

		loginPage.ensureIsNotVisibleLoginBtn();

		homePage.ensureIsDisplayedMap();

		assertEquals("http://localhost:4200/home", driver.getCurrentUrl());
	}

	public void subscribe(int uslov, boolean uslov1) throws InterruptedException {
		homePage.ensureIsDisplayedMap();

		if (uslov == 0) {
			homePage.getMarkerUzice().click();
		} else if (uslov == 1) {
			homePage.getBadMarker().click();
		} else if (uslov == 2) {
			homePage.getKraljevoMarker().click();
		}
		justWait(1000);

		homePage.getSubBtn().click();

		justWait(1000);
		if (uslov1) {
			homePage.ensureIsDisplayedGreenToast();
		} else {
			homePage.ensureIsDisplayedRedToast();
		}
	}

	@Test
	public void SubscriptionsTestSuccess() throws InterruptedException {
		login();

		justWait(1000);
		homePage.getSubs().click();

		assertEquals("http://localhost:4200/subscriptions", driver.getCurrentUrl());

		justWait(1000);

		subPage.ensureTabelaIsDisplayed();

		List<WebElement> subs = driver.findElements(By.tagName("tr"));

		subPage.getHome().click();

		justWait(1000);

		subscribe(0, true);

		justWait(1000);

		homePage.getSubs().click();

		assertEquals("http://localhost:4200/subscriptions", driver.getCurrentUrl());

		subPage.ensureTabelaIsDisplayed();

		List<WebElement> subs1 = driver.findElements(By.tagName("tr"));

		assertEquals(subs.size() + 1, subs1.size());

	}

	@Test
	public void AlreadySubscribed() throws InterruptedException {
		login();

		justWait(1000);
		homePage.getSubs().click();

		assertEquals("http://localhost:4200/subscriptions", driver.getCurrentUrl());

		justWait(1000);

		subPage.ensureTabelaIsDisplayed();

		List<WebElement> subs = driver.findElements(By.tagName("tr"));

		subPage.getHome().click();

		justWait(1000);

		subscribe(1, false);

		justWait(1000);

		homePage.getSubs().click();

		assertEquals("http://localhost:4200/subscriptions", driver.getCurrentUrl());

		subPage.ensureTabelaIsDisplayed();

		List<WebElement> subs1 = driver.findElements(By.tagName("tr"));

		assertEquals(subs.size(), subs1.size());

	}

	@Test
	public void UnsubscribeOneOffer() throws InterruptedException {
		login();

		justWait(1000);
		homePage.getSubs().click();

		assertEquals("http://localhost:4200/subscriptions", driver.getCurrentUrl());

		justWait(1000);

		subPage.ensureTabelaIsDisplayed();

		List<WebElement> subs = driver.findElements(By.tagName("tr"));

		subPage.getUnsubOne().click();

		List<WebElement> subs1 = driver.findElements(By.tagName("tr"));

		assertEquals(subs.size() - 1, subs1.size());

	}

	@Test
	public void UnsubAndSubAgain() throws InterruptedException {
		login();

		justWait(1000);
		homePage.getSubs().click();

		assertEquals("http://localhost:4200/subscriptions", driver.getCurrentUrl());

		justWait(1000);

		subPage.ensureTabelaIsDisplayed();

		List<WebElement> subs = driver.findElements(By.tagName("tr"));

		subPage.getUnsubOne().click();

		List<WebElement> subs1 = driver.findElements(By.tagName("tr"));

		assertEquals(subs.size() - 1, subs1.size());

		subPage.getHome().click();

		justWait(1000);

		subscribe(1, true);

		justWait(1000);

		homePage.getSubs().click();

		assertEquals("http://localhost:4200/subscriptions", driver.getCurrentUrl());

		subPage.ensureTabelaIsDisplayed();

		List<WebElement> subs2 = driver.findElements(By.tagName("tr"));

		assertEquals(subs1.size() + 1, subs2.size());

	}

	@Test
	public void SubAndUsubSameOffer() throws InterruptedException {
		login();

		justWait(1000);
		homePage.getSubs().click();

		assertEquals("http://localhost:4200/subscriptions", driver.getCurrentUrl());

		justWait(1000);

		subPage.ensureTabelaIsDisplayed();

		List<WebElement> subs = driver.findElements(By.tagName("tr"));

		subPage.getHome().click();

		justWait(1000);

		subscribe(2, true);

		justWait(1000);

		homePage.getSubs().click();

		assertEquals("http://localhost:4200/subscriptions", driver.getCurrentUrl());

		subPage.ensureTabelaIsDisplayed();

		List<WebElement> subs1 = driver.findElements(By.tagName("tr"));

		assertEquals(subs.size() + 1, subs1.size());

		subPage.getUnsubFive().click();

		List<WebElement> subs2 = driver.findElements(By.tagName("tr"));

		assertEquals(subs1.size() - 1, subs2.size());

	}

	private void justWait(int time) throws InterruptedException {
		synchronized (driver) {
			driver.wait(time);
		}
	}

}
