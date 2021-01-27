package ktsnwt_tim8.demo.e2e;

import static org.junit.Assert.assertEquals;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URI;
import java.util.List;

import org.junit.After;
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

public class AddOfferImageE2ETest {

	private WebDriver driver;

	private LoginPage loginPage;

	private HomePage homePage;

	private AddOfferImagePage addOfferImagePage;
	
	private EditOfferPage editPage;
	
	private AddOfferImagePage addOfferPage;

	@Before
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		homePage = PageFactory.initElements(driver, HomePage.class);
		addOfferImagePage = PageFactory.initElements(driver, AddOfferImagePage.class);
		editPage = PageFactory.initElements(driver, EditOfferPage.class);
		addOfferPage = PageFactory.initElements(driver, AddOfferImagePage.class);

	}

	@After
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void addImage() throws InterruptedException, AWTException {
		login();

		homePage.ensureIsDisplayedMap();

		homePage.getBadMarker().click();

		justWait();

		homePage.getEditBtn().click();

		assertEquals("http://localhost:4200/edit-offer?offerID=1", driver.getCurrentUrl());

		List<WebElement> inputs = driver.findElements(By.tagName("input"));

		editPage.getAddImage().click();

		assertEquals("http://localhost:4200/add-offer-image?offerID=1", driver.getCurrentUrl());

		addOfferPage.ensureFormIsDisplayed();

		addOfferPage.getAddImageBtn().click();

		justWait();

		Robot rb = new Robot();

//		
		File file = new File("src/main/resources/images/beforeHouseWasDisco.jpg");
    	URI    uri  = file.toURI();
    	file = new File(uri);
    	String path = file.getAbsolutePath();
    	StringSelection str = new StringSelection(path);
		
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);

		rb.keyRelease(KeyEvent.VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_V);

		justWait();

		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);

		addOfferPage.getDescription().sendKeys("SOME NEW DESC");

		addOfferPage.getSubmitBtn().click();

		justWait();

		addOfferPage.ensureIsDisplayedGreenToast();
		
		justWait();

		addOfferPage.getEditOffer().click();
		
		justWait();

		List<WebElement> inputs1 = driver.findElements(By.tagName("tr"));

		assertEquals(inputs.size() + 1, inputs1.size());

	}


	private void justWait() throws InterruptedException {
		synchronized (driver) {
			driver.wait(1000);
		}
	}

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

}
