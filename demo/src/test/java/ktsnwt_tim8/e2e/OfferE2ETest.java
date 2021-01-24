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

import ktsnwt_tim8.pages.LoginPage;
import ktsnwt_tim8.pages.MainOffersPage;
import ktsnwt_tim8.pages.NavigationBarAdminPage;
import ktsnwt_tim8.pages.OfferPage;
import ktsnwt_tim8.pages.PostPage;

public class OfferE2ETest {
	
	private WebDriver driver;
	private OfferPage offerPage;
	private LoginPage loginPage;
	private NavigationBarAdminPage navigationBarAdminPage;
	private MainOffersPage mainOffersPage;
	
	@Before
	public void setUp() {

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		offerPage = PageFactory.initElements(driver, OfferPage.class);
		navigationBarAdminPage = PageFactory.initElements(driver, NavigationBarAdminPage.class);
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
	
	private void chooseOffer() throws InterruptedException{
    	driver.get("http://localhost:4200/home");
        mainOffersPage = PageFactory.initElements(driver, MainOffersPage.class);
        justWait(1000);
        mainOffersPage.getAnchor3().click();
        justWait(2000);
        offerPage = PageFactory.initElements(driver, OfferPage.class);
        justWait(2000);
       
    }
	
	private void justWait(int milliseconds) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(milliseconds);
        }
    }
	
	//-------------------------ADD OFFER-------------------------------------

//	@Test
//	public void AddOfferTestSuccess() throws InterruptedException {
//		login();
//		justWait(1000);
//
//		navigationBarAdminPage.getAddOffer().click();
//
//
//		justWait(1000);
//		offerPage.getOfferTitle().sendKeys("Ponuda1234");
//		offerPage.getOfferDescription().sendKeys("opisssss");
//		offerPage.getOfferPlace().sendKeys("Beograd");
//		offerPage.getOfferSubcategorty().sendKeys("Muzej");
//		offerPage.getAddOfferBtn().click();
//		justWait(5000);
//		assertEquals("http://localhost:4200/home", driver.getCurrentUrl());
//	}
//	
//	@Test
//	public void AddOfferTestTitleEmpty() throws InterruptedException {
//
//		login();
//		justWait(1000);
//
//		navigationBarAdminPage.getAddOffer().click();
//
//
//		justWait(1000);
//		offerPage.ensureIsDisplayedTitle();
//		offerPage.getOfferTitle().sendKeys("");
//		offerPage.getOfferDescription().sendKeys("opisssss");
//		offerPage.getOfferPlace().sendKeys("Beograd");
//		offerPage.getOfferSubcategorty().sendKeys("Muzej");
//		offerPage.getAddOfferBtn().click();
//		justWait(2000);
//		assertTrue(offerPage.messageErrorDisplayed("offerTitleReq") );
//		assertFalse(offerPage.ensureIsDisabledButton());
//		assertEquals("http://localhost:4200/addOffer", driver.getCurrentUrl());
//	}
//	
//	@Test
//	public void AddOfferTestDescriptionEmpty() throws InterruptedException {
//
//		login();
//		justWait(1000);
//
//		navigationBarAdminPage.getAddOffer().click();
//
//
//		justWait(1000);
//		offerPage.ensureIsDisplayedTitle();
//		offerPage.getOfferTitle().sendKeys("Naslov");
//		offerPage.getOfferDescription().sendKeys("");
//		offerPage.getOfferPlace().sendKeys("Beograd");
//		offerPage.getOfferSubcategorty().sendKeys("Muzej");
//		offerPage.getAddOfferBtn().click();
//		justWait(2000);
//		assertTrue(offerPage.messageErrorDisplayed("offerDescriptionReq") );
//		assertFalse(offerPage.ensureIsDisabledButton());
//		assertEquals("http://localhost:4200/addOffer", driver.getCurrentUrl());
//	}
//	
//	@Test
//	public void AddOfferTestPlaceEmpty() throws InterruptedException {
//
//		login();
//		justWait(1000);
//
//		navigationBarAdminPage.getAddOffer().click();
//
//
//		justWait(1000);
//		offerPage.ensureIsDisplayedTitle();
//		offerPage.getOfferTitle().sendKeys("Naslov");
//		offerPage.getOfferDescription().sendKeys("opis");
//		offerPage.getOfferPlace().sendKeys("");
//		offerPage.getOfferSubcategorty().sendKeys("Muzej");
//		offerPage.getAddOfferBtn().click();
//		justWait(2000);
//		assertTrue(offerPage.messageErrorDisplayed("offerPlaceReq") );
//		assertFalse(offerPage.ensureIsDisabledButton());
//		assertEquals("http://localhost:4200/addOffer", driver.getCurrentUrl());
//	}
//	
//	@Test
//	public void AddOfferTestSubcategoryEmpty() throws InterruptedException {
//
//		login();
//		justWait(1000);
//
//		navigationBarAdminPage.getAddOffer().click();
//
//
//		justWait(1000);
//		offerPage.ensureIsDisplayedTitle();
//		offerPage.getOfferTitle().sendKeys("Naslov");
//		offerPage.getOfferDescription().sendKeys("opis");
//		offerPage.getOfferPlace().sendKeys("Beograd");
//		offerPage.getOfferSubcategorty().sendKeys("");
//		offerPage.getAddOfferBtn().click();
//		justWait(2000);
//		assertFalse(offerPage.ensureIsDisabledButton());
//		assertEquals("http://localhost:4200/addOffer", driver.getCurrentUrl());
//	}
	
	//-------------------------EDIT OFFER-------------------------------------
	
//	@Test
//	public void EditOfferTestSuccess() throws InterruptedException {
//		login();
//		chooseOffer();
//		justWait(1000);
// 
//		offerPage.getTitleForEditOffer().clear();
//		offerPage.getTitleForEditOffer().sendKeys("ovo je editovan offer");
//		offerPage.getDescriptionForEditOffer().clear();
//		offerPage.getDescriptionForEditOffer().sendKeys("ovo je editovan offer");
//		offerPage.getEditOfferBtnnn().click();
//		justWait(1000);
//
//		assertEquals("http://localhost:4200/offers/5", driver.getCurrentUrl());
//	}
	
//	@Test
//	public void EditOfferTestTitleEmpty() throws InterruptedException {
//		login();
//		chooseOffer();
//		justWait(1000);
// 
//		offerPage.getTitleForEditOffer().clear();
//		offerPage.getTitleForEditOffer().sendKeys("");
//		offerPage.getEditOfferBtnnn().click();
//
//		assertEquals("http://localhost:4200/offers/5", driver.getCurrentUrl());
//	}
	
//	@Test
//	public void EditOfferTestDescriptionEmpty() throws InterruptedException {
//		login();
//		chooseOffer();
//		justWait(1000);
// 
//		offerPage.getDescriptionForEditOffer().clear();
//		offerPage.getDescriptionForEditOffer().sendKeys("");
//		offerPage.getEditOfferBtnnn().click();
//
//		assertEquals("http://localhost:4200/offers/5", driver.getCurrentUrl());
//	}
	
	//-------------------------DELETE OFFER-------------------------------------
	
	@Test
	public void DeleteOfferTestSuccess() throws InterruptedException {
		login();
		chooseOffer();
		justWait(1000);
 
		offerPage.getDeleteOfferBtnnn().click();
		offerPage.getYesButtonOffer().click();
		justWait(1000);

		assertEquals("http://localhost:4200/home", driver.getCurrentUrl());
	}
}
