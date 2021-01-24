package ktsnwt_tim8.e2e;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ktsnwt_tim8.pages.FilterPage;
import ktsnwt_tim8.pages.LoginPage;

public class FilterE2E {

	private WebDriver driver;

	private FilterPage filterPage;

	@Before
	public void setUp() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:4200/home");
		filterPage = PageFactory.initElements(driver, FilterPage.class);
		justWait(3000);

	}

	@Test
	public void filter() throws InterruptedException {
		String searchName = "pozoriste";
		filterPage.getNameInput().sendKeys(searchName);
		filterPage.getSearch().click();
		justWait(2000);
		boolean correct = true;
		for (String name : filterPage.getOffersTitles()) {
			if (!name.toLowerCase().contains(searchName.toLowerCase())) {
				correct = false;
			}
		};
		assertTrue(correct);
	}

	@Test
	public void filter2() throws InterruptedException {
		logIn("kor1@nesto.com", "1");
		filterPage.getFilterMore().click();
		justWait(2000);
		filterPage.getFilterMore();
		String location = "novi";
		filterPage.getLocation().sendKeys(location);
		justWait(2000);
		String searchName = "pozoriste";
		filterPage.getNameInput().sendKeys(searchName);
		filterPage.getSearch().click();
		justWait(2000);
		boolean correct = true;
		for (String name : filterPage.getOffersTitles()) {
			if (!name.toLowerCase().contains(searchName.toLowerCase())) {
				correct = false;
			}
		};
		for (String loc : filterPage.getOffersLocations()) {
			if (!loc.toLowerCase().contains(location.toLowerCase())) {
				correct = false;
			}
		};

		assertTrue(correct);
	}

	@Test
	public void filter3() throws InterruptedException {
		logIn("admin@nesto.com", "1");
		String searchName = "spomenik";
		filterPage.getNameInput().sendKeys(searchName);
		filterPage.getSearch().click();
		justWait(2000);
		boolean correct = true;
		for (String name : filterPage.getOffersTitles()) {
			if (!name.toLowerCase().contains(searchName.toLowerCase())) {
				correct = false;
			}
		};
		assertTrue(correct);
	}

	@Test
	public void filterGibberish() throws InterruptedException {
		String searchName = "lkjwfwpe9ffewljk";
		filterPage.getNameInput().sendKeys(searchName);
		filterPage.getSearch().click();
		justWait(2000);
		assertEquals(filterPage.getNumOfElements(), 0);
	}

	@Test
	public void filterGibberishThenClear() throws InterruptedException {
		String searchName = "lkjwfwpe9ffewljk";
		filterPage.getNameInput().sendKeys(searchName);
		filterPage.getSearch().click();
		justWait(2000);
		assertEquals(filterPage.getNumOfElements(), 0);
		filterPage.getClearSearch().click();
		filterPage.getSearch().click();
		justWait(2000);
		assertNotEquals(filterPage.getNumOfElements(), 0);
	}

	private void logIn(String username, String password) throws InterruptedException {
		LoginPage loginPage;
		driver.get("http://localhost:4200/login");
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.getEmail().sendKeys(username);
		loginPage.getPassword().sendKeys(password);
		loginPage.getLoginBtn().click();
		justWait(2000);
	}

	private void justWait(int milliseconds) throws InterruptedException {
		synchronized (driver) {
			driver.wait(milliseconds);
		}
	}

	@After
	public void tearDown() {
		driver.quit();
	}

}
