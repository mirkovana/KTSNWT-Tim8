package ktsnwt_tim8.e2e;

//import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ktsnwt_tim8.pages.LoginPage;
import ktsnwt_tim8.pages.MainOffersPage;
import ktsnwt_tim8.pages.RatingPage;

public class RatingE2E {

	private WebDriver driver;

    private RatingPage ratingPage;
    private LoginPage loginPage;
    private MainOffersPage mainOffersPage;
	
    @Before
    public void setUp() {
    	System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
    }
    
	private void logIn() throws InterruptedException {

		driver.get("http://localhost:4200/login");
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.getEmail().sendKeys("kor1@nesto.com");
		loginPage.getPassword().sendKeys("1");
		loginPage.getLoginBtn().click();
		justWait(3000);

	}
	
	private void selectOffer() throws InterruptedException {
		driver.get("http://localhost:4200/home");
		mainOffersPage = PageFactory.initElements(driver, MainOffersPage.class);
		justWait(3000);
		//mainOffersPage.getAnchor1().click();
		//mainOffersPage.getAnchorFirst().click();
		mainOffersPage.linkx.click();
		//mainOffersPage.getLink1j().click();
		//mainOffersPage.getLink1().click();
		justWait(2000);
		ratingPage = PageFactory.initElements(driver, RatingPage.class);
		justWait(2000);
	}
	
	@Test
	public void rateOffer() throws InterruptedException {
		logIn();
		selectOffer();
		justWait(2000);
		// checking if rate button is disabled since initial rating iz 0 stars
		assertTrue(!ratingPage.getRateButton().isEnabled()); 
		ratingPage.getAStar().click(); //css=.ng-star-inserted:nth-child(8)
		ratingPage.getRateButton().click();
		justWait(500);
		assertTrue(ratingPage.snackBarSuccess(("Rating created.")));
		justWait(2000);
		// checking if current value is the one we chose
		assertEquals(3, ratingPage.getCurrentRatingValue());
		// checking if change rating button is displayed
		assertTrue(ratingPage.getChangeRatingButton() != null);
		// checking if delete rating button is displayed
		assertTrue(ratingPage.getDeleteRatingButton() != null);
		// checking if rate button is not displayed
		assertTrue(ratingPage.getRateButton() == null);
	}
	
	
	private void rate() throws InterruptedException {
		if (ratingPage.getRateButton() != null ) {
			ratingPage.getAStar().click(); //css=.ng-star-inserted:nth-child(8)
			ratingPage.getRateButton().click();
			justWait(500);
		}
		
		
	}
	
	@Test
	public void updateOffer() throws InterruptedException {
		logIn();
		selectOffer();
		rate();
		justWait(2000);
		// checking if rate button is not displayed
		assertTrue(ratingPage.getRateButton() == null); 
		
		// checking if change rating button is displayed
		assertTrue(ratingPage.getChangeRatingButton().isDisplayed());
		
		// click on change rating
		ratingPage.getChangeRatingButton().click();
		
		// change rating
		ratingPage.getAStar(2).click(); //css=.ng-star-inserted:nth-child(8)
		// save rating
		ratingPage.getSaveUpdateButton().click();
		justWait(500);
		assertTrue(ratingPage.snackBarSuccess(("Rating updated.")));
		justWait(2000);
		// checking if current value is the one we chose
		assertEquals(2, ratingPage.getCurrentRatingValue());
		// checking if change rating button is displayed
		assertTrue(ratingPage.getChangeRatingButton() != null);
		// checking if delete rating button is displayed
		assertTrue(ratingPage.getDeleteRatingButton() != null);
		// checking if rate button is not displayed
		assertTrue(ratingPage.getRateButton() == null);
	}
	
	@Test
	public void deleteRating() throws InterruptedException {
		logIn();
		selectOffer();
		rate();
		justWait(2000);
		// checking if rate button is not displayed
		assertTrue(ratingPage.getRateButton() == null); 
		
		// checking if change rating button is displayed
		assertTrue(ratingPage.getDeleteRatingButton().isDisplayed());
		
		ratingPage.getDeleteRatingButton().click();
		justWait(500);
		assertTrue(ratingPage.snackBarSuccess(("Rating deleted.")));
		justWait(2000);
		assertEquals(0, ratingPage.getCurrentRatingValue());
		// checking if change rating button is not displayed
		assertTrue(ratingPage.getChangeRatingButton() == null);
		// checking if delete rating button is not displayed
		assertTrue(ratingPage.getDeleteRatingButton() == null);
		// checking if rate button is displayed
		assertTrue(ratingPage.getRateButton() != null);
		assertTrue(!ratingPage.getRateButton().isEnabled());
		
	}
	
	@Test
	public void rateNotLoggedIn1() throws InterruptedException {
		selectOffer();
		// ensure none of the buttons are displayed
		assertTrue(ratingPage.getChangeRatingButton() == null);
		assertTrue(ratingPage.getDeleteRatingButton() == null);
		assertTrue(ratingPage.getRateButton() == null);
		assertTrue(ratingPage.getSaveUpdateButton() == null);
		
		assertTrue(ratingPage.getLoginLink().isDisplayed());
		assertTrue(ratingPage.getSignupLink().isDisplayed());
		
		ratingPage.getLoginLink().click();
		justWait(300);
		assertEquals("http://localhost:4200/login", driver.getCurrentUrl());
		
	}
	@Test
	public void rateNotLoggedIn2() throws InterruptedException {
		selectOffer();
		// ensure none of the buttons are displayed
		assertTrue(ratingPage.getChangeRatingButton() == null);
		assertTrue(ratingPage.getDeleteRatingButton() == null);
		assertTrue(ratingPage.getRateButton() == null);
		assertTrue(ratingPage.getSaveUpdateButton() == null);
		
		assertTrue(ratingPage.getLoginLink().isDisplayed());
		assertTrue(ratingPage.getSignupLink().isDisplayed());
		
		ratingPage.getSignupLink().click();
		justWait(300);
		 
		assertEquals("http://localhost:4200/registration", driver.getCurrentUrl());
		
	}
	
	//Test
	public void doS() throws InterruptedException {
		selectOffer();
	}
	
	private void justWait(int milliseconds) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(milliseconds);
        }
    }
	
    @After
    public void tearDown() {
        driver.quit();
    }
}
