package ktsnwt_tim8.e2e;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ktsnwt_tim8.pages.CategoryPage;
import ktsnwt_tim8.pages.LoginPage;
import ktsnwt_tim8.pages.NavigationBarAdminPage;

public class CategoryE2ETest {

	private WebDriver driver;

	private LoginPage loginPage;
	private NavigationBarAdminPage navigationBarAdminPage;
	private CategoryPage categoryPage;

	@Before
	public void setUp() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		navigationBarAdminPage = PageFactory.initElements(driver, NavigationBarAdminPage.class);
		categoryPage = PageFactory.initElements(driver, CategoryPage.class);

	}

	private void login() throws InterruptedException {

		driver.get("http://localhost:4200/login");
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.getEmail().sendKeys("admin@nesto.com");
		loginPage.getPassword().sendKeys("1");
		loginPage.getLoginBtn().click();
		justWait();

	}

	@Test
	public void NewCategoryTestSuccess() throws InterruptedException {

		login();
		justWait();

		navigationBarAdminPage.getAddCategory().click();
		justWait();
		categoryPage.getCategoryName().sendKeys("ovo je neka nova kategorija");
		categoryPage.getSaveCategory().click();

		categoryPage.ensureIsNotVisibleAddNewButton();

		assertEquals("http://localhost:4200/home", driver.getCurrentUrl());

	}

	@Test
	public void NewCategoryTestNameEmpty() throws InterruptedException {

		login();
		justWait();

		navigationBarAdminPage.getAddCategory().click();
		justWait();
		categoryPage.getCategoryName().sendKeys("");
		categoryPage.getSaveCategory().click();

		categoryPage.ensureIsDisabledButton();

		assertTrue(categoryPage.messageErrorDisplayed("nameReq"));
		assertFalse(categoryPage.ensureIsDisabledButton());
		assertEquals("http://localhost:4200/home", driver.getCurrentUrl());

	}

	@After
	public void tearDown() {
		driver.quit();
	}

	private void justWait() throws InterruptedException {
		synchronized (driver) {
			driver.wait(3000);
		}
	}
}
