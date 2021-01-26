package ktsnwt_tim8.demo.e2e;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ktsnwt_tim8.demo.page.AddOfferImagePage;
import ktsnwt_tim8.demo.page.EditOfferPage;
import ktsnwt_tim8.demo.page.HomePage;
import ktsnwt_tim8.demo.page.LoginPage;

public class EditOfferImageE2ETest {

	static String NOTFOUND = "No images found";

	static String NEW_DESCRIPTION = "some new description";

	private WebDriver driver;

	private LoginPage loginPage;

	private HomePage homePage;

	private EditOfferPage editPage;

	private AddOfferImagePage addOfferPage;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		homePage = PageFactory.initElements(driver, HomePage.class);
		editPage = PageFactory.initElements(driver, EditOfferPage.class);
		addOfferPage = PageFactory.initElements(driver, AddOfferImagePage.class);
	}

//	@After
//	public void tearDown() {
//		driver.quit();
//	}

	public void login() {
		driver.get("http://localhost:4200/login");

		loginPage.ensureIsDisplayedUsername();

		loginPage.getUsername().sendKeys("admin@nesto.com");

		loginPage.getPassword().sendKeys("1");

		loginPage.getLoginBtn().click();

		loginPage.ensureIsNotVisibleLoginBtn();

		homePage.ensureIsDisplayedMap();

		assertEquals("http://localhost:4200/home", driver.getCurrentUrl());
	}

	@Test
	public void editOfferWithNoImages() throws InterruptedException {
		login();

		homePage.ensureIsDisplayedMap();

		homePage.getKraljevoMarker().click();

		justWait();

		homePage.getEditBtn().click();

		assertEquals("http://localhost:4200/edit-offer?offerID=6", driver.getCurrentUrl());

		assertEquals(NOTFOUND, editPage.getErrorMsg().getText());
	}

	@Test
	public void editImageDescription() throws InterruptedException {
		login();

		homePage.ensureIsDisplayedMap();

		homePage.getBadMarker().click();

		justWait();

		homePage.getEditBtn().click();

		assertEquals("http://localhost:4200/edit-offer?offerID=1", driver.getCurrentUrl());

		editPage.getFirstInput().sendKeys(NEW_DESCRIPTION);

		editPage.getFirstSubmit().click();

		editPage.ensureIsDisplayedGreenToast();

		String placeHolader = driver.findElement(By.xpath("//input[@placeholder=\"" + NEW_DESCRIPTION + "\"]"))
				.getAttribute("placeholder");

		assertEquals(placeHolader, NEW_DESCRIPTION);

	}

	@Test
	public void deleteOfferImage() throws InterruptedException {

		login();

		homePage.ensureIsDisplayedMap();

		homePage.getBadMarker().click();

		justWait();

		homePage.getEditBtn().click();

		assertEquals("http://localhost:4200/edit-offer?offerID=1", driver.getCurrentUrl());

		List<WebElement> inputs = driver.findElements(By.tagName("input"));

		editPage.getSecondDelete().click();
		justWait();

		List<WebElement> inputs1 = driver.findElements(By.tagName("tr"));

		assertEquals(inputs.size() - 1, inputs1.size());

	}

	
	private void justWait() throws InterruptedException {
		synchronized (driver) {
			driver.wait(1000);
		}
	}

}
