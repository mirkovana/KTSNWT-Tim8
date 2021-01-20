package ktsnwt_tim8.e2e;


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

public class CommentE2ETest {

	
	private WebDriver driver;

    private CommentsPage commentsPage;
    private LoginPage loginPage;
    private MainOffersPage mainOffersPage;
    
    
    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
         
        driver.get ("http://localhost:4200/login");
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.getEmail().sendKeys("kor1@nesto.com");;
        loginPage.getPassword().sendKeys("1");;
        loginPage.getLoginBtn().click();
        //this.driver.wait(5000);
        justWait(5000);
        
        mainOffersPage = PageFactory.initElements(driver, MainOffersPage.class);
        //mainOffersPage.ensureIsDisplayedA1();
        justWait(1000);
        mainOffersPage.getAnchor1().click();
        justWait(2000);
        //mainOffersPage.getAnchor1().click();
        //commentsPage = PageFactory.initElements(driver, CommentsPage.class);
        //driver.get("http://localhost:4200/offer");
        //justWait(5000);
        commentsPage = PageFactory.initElements(driver, CommentsPage.class);
        commentsPage.setupFormElements();
        justWait(2000);
       
    }

    
    @Test
    public void createComment() throws InterruptedException {
    	
    	int oldComments = commentsPage.getNumOfElements();
    	String newCommentText = "New comment text.";
    	commentsPage.getNewCommentTextInput().sendKeys(newCommentText);
    	commentsPage.getNewCommentPost().click();
    	justWait(6000);
    	int newComments = commentsPage.getNumOfElements();
    	// checking if number of all comments changed
    	assertEquals(oldComments + 1, newComments);
    	// checking if the text of the first comment equals to the text of new comment
    	assertEquals(commentsPage.getFirstCommentText(), newCommentText);
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
