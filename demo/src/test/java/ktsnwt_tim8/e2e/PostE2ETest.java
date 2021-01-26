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

import ktsnwt_tim8.pages.CommentsPage;
import ktsnwt_tim8.pages.LoginPage;
import ktsnwt_tim8.pages.MainOffersPage;
import ktsnwt_tim8.pages.PostPage;

public class PostE2ETest {
	private WebDriver driver;

    private PostPage postPage;
    private LoginPage loginPage;
    private MainOffersPage mainOffersPage;
    
    
    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
       
    }
    
    private void chooseOffer() throws InterruptedException{
    	driver.get("http://localhost:4200/home");
        mainOffersPage = PageFactory.initElements(driver, MainOffersPage.class);
        justWait(1000);
        mainOffersPage.getAnchor2().click();
        justWait(2000);
        postPage = PageFactory.initElements(driver, PostPage.class);
        justWait(2000);
       
    }
    
    private void logIn() throws InterruptedException {
        
       driver.get ("http://localhost:4200/login");
       loginPage = PageFactory.initElements(driver, LoginPage.class);
       loginPage.getEmail().sendKeys("admin@nesto.com");
       loginPage.getPassword().sendKeys("1");
       loginPage.getLoginBtn().click();
       justWait(2000);
       
       
    }

//	 @Test
//	public void NewPostTestSuccess() throws InterruptedException {
//
//	 
//		logIn();
//		chooseOffer();
//		justWait(1000);
// 
//
//		postPage.getAddPostDialog().click();
//		postPage.getPostTitle().sendKeys("ovo je novi naziv za post");
//		postPage.getPostContent().sendKeys("neki kontent!!!!!");
//		postPage.getAddPost().click();
//		justWait(1000);
//		postPage.ensureIsNotVisibleAddNewButton();
//
//
//		assertEquals("http://localhost:4200/offers/1", driver.getCurrentUrl());
//
//	}
//	@Test
//	public void NewPostTitleEmptyTest() throws InterruptedException {
//
//		logIn();
//		chooseOffer();
//		justWait(1000);
// 
//
//		postPage.getAddPostDialog().click();
//		postPage.getPostTitle().sendKeys("");
//		postPage.getPostContent().sendKeys("gore je prazno");
//		postPage.getAddPost().click();
//	   
//	 
//		 
//	 
//	    assertTrue(postPage.messageErrorDisplayed("postTitleReq") );
//		assertFalse(postPage.ensureIsDisabledButton()); 
//		assertEquals("http://localhost:4200/offers/1", driver.getCurrentUrl());
//
//	}
    
//	@Test
//	public void NewPostContentEmptyTest() throws InterruptedException {
//
//		logIn();
//		chooseOffer();
//		justWait(1000);
//
//		 
//
//		postPage.getAddPostDialog().click();
//		postPage.getPostTitle().sendKeys("nestoooooooooooooooooooooo");
//		postPage.getPostContent().sendKeys("");
//		postPage.getAddPost().click();
//	   
//	 
//		 
//	 
//	    assertTrue(postPage.messageErrorDisplayed("postContentReq") );
//		assertFalse(postPage.ensureIsDisabledButton()); 
//		assertEquals("http://localhost:4200/offers/1", driver.getCurrentUrl());
//
//	} 
//	@Test
//	public void EditPostTestSuccess() throws InterruptedException {
//
//		 
//		logIn();
//		chooseOffer();
//		justWait(1000);
//
//		 
//
//		postPage.getName1().clear();
//		postPage.getContent1().clear();
//		postPage.getName1().sendKeys("ovo ce biti novi naslov");
//		postPage.getContent1().sendKeys("neki tekst promenjen");
//		postPage.getSavePostEdit1().click();
//		justWait(1000);
//		
//
//
//		assertEquals("http://localhost:4200/offers/1", driver.getCurrentUrl());
//
//	}
	 
//	@Test
//	public void DeletePostTest() throws InterruptedException {
//
//		logIn();
//		chooseOffer();
//		justWait(1000);
//	
//		postPage.getDugme2().click();
//	    postPage.getYesButtonP().click();
//	    
//		 justWait(2000);
//	  
//		assertEquals("http://localhost:4200/offers/1", driver.getCurrentUrl());
//
//	}
    
    @Test
	public void EditPostTestTitleEmpty() throws InterruptedException {	 
		logIn();
		chooseOffer();
		justWait(1000);

		postPage.getName1().clear();
		postPage.getName1().sendKeys("");

		assertEquals("http://localhost:4200/offers/1", driver.getCurrentUrl());
	}
    
    @Test
	public void EditPostTestContentEmpty() throws InterruptedException {
		logIn();
		chooseOffer();
		justWait(1000);
		
		postPage.getContent1().clear();
		postPage.getContent1().sendKeys("");
		
		assertEquals("http://localhost:4200/offers/1", driver.getCurrentUrl());
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
}
