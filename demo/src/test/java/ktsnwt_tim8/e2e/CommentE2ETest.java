package ktsnwt_tim8.e2e;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        justWait(3000);
        
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

    
    //@Test
    public void createComment() throws InterruptedException {
    	
    	int oldComments = commentsPage.getNumOfElements();
    	String newCommentText = "New comment text.";
    	commentsPage.getNewCommentTextInput().sendKeys(newCommentText);
    	commentsPage.getSubmitButton().click();
    	justWait(3000);
    	int newComments = commentsPage.getNumOfElements();
    	// checking if number of all comments changed
    	assertEquals(oldComments + 1, newComments);
    	// checking if the text of the first comment equals to the text of new comment
    	assertEquals(commentsPage.getFirstCommentText(), newCommentText);
    	// check if the first comment username is equals to logged-in username
    	assertEquals("kor1@nesto.com", commentsPage.getFirstCommentUsername());
    	
    }
    
    private String toBase64(File file) {
    	byte[] fileContent;
    	try {
			fileContent = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			return null;
		}
    	return Base64.getEncoder().encodeToString(fileContent);
    }
    
    @Test
    public void createCommentWithPicture() throws InterruptedException {
    	
    	int oldComments = commentsPage.getNumOfElements();
    	String newCommentText = "New comment text.";
    	commentsPage.getNewCommentTextInput().sendKeys(newCommentText);
    	commentsPage.getFileInput().sendKeys("C:\\Users\\Korisnik\\Desktop\\djava.jpg");
    	justWait(2000);
    	File file = new File("src/test/resources/imagesForUpload/petrovaradin.jpg");
    	String base64 = "data:image/jpg;base64," + toBase64(file);
    	URI    uri  = file.toURI();
    	file = new File(uri);
    	// Get the absolute path to the file.
    	String path = file.getAbsolutePath();
    	//commentsPage.getFileInput().sendKeys("src/test/resources/imagesForUpload/petrovaradin.jpg");
    	commentsPage.getFileInput().sendKeys(path);
    	justWait(3000);
    	commentsPage.getSubmitButton().click();
    	justWait(3000);
    	int newComments = commentsPage.getNumOfElements();
    	// checking if number of all comments changed
    	assertEquals(oldComments + 1, newComments);
    	// checking if the text of the first comment equals to the text of new comment
    	assertEquals(commentsPage.getFirstCommentText(), newCommentText);
    	// check if the first comment username is equals to logged-in username
    	assertEquals("kor1@nesto.com", commentsPage.getFirstCommentUsername());
    	
    	//base64 = "data:image/jpg;base64," + base64;
    	assertEquals(base64, commentsPage.getFirstImageSrc());
    	justWait(10000);
    	
    }
    
    //@Test
    public void createCommentEmptyText() throws InterruptedException {
    	
    	String newCommentText = "s";
    	commentsPage.getNewCommentTextInput().sendKeys(newCommentText);
    	// to ensure input field was 'dirtied'
    	commentsPage.getNewCommentTextInput().sendKeys(Keys.BACK_SPACE);
    	justWait(1000);
    	assertEquals(commentsPage.getSubmitButton().isEnabled(), false);
    	assertEquals(commentsPage.messageErrorDisplayed(), true);
    	justWait(1000);	
    	
    }
    
    //@Test
    public void editComment() throws InterruptedException {
    	prepCreateComment("C");
    	justWait(2000);
    	//commentsPage.getEditButtons();
    	//commentsPage.getEditButton().click();
    	//justWait(4000);
    	for (WebElement e: commentsPage.getComments()) {
    		System.out.println(e.getText() + "tekst   a tag name je " + e.getTagName());
    	}
    	for (WebElement w: commentsPage.getComments().get(0).findElements(By.cssSelector("button"))) {
    		//System.out.println(w.getText() + " tekst prvog?");
    		//w.click();
    		//break;
    	}
    	//commentsPage.getEditButton().click();
    	//System.out.println(commentsPage.getEditButton().getText());
    	commentsPage.getDeleteButton().click();
    	justWait(10000);
    }
    
    //@Test
    public void deleteComment() throws InterruptedException {
    	prepCreateComment("Comment for deletion!");
    	justWait(2000);    
    	int oldComments = commentsPage.getNumOfElements();
    	commentsPage.getDeleteButton().click();    	
    	justWait(2000);
    	int newComments = commentsPage.getNumOfElements();
    	// checking if number of all comments changed
    	assertEquals(oldComments - 1, newComments); 
    	// checking if the text of the first comment equals to the text of new comment
    	assertNotEquals(commentsPage.getFirstCommentText(), "Comment for deletion!");
     }
    
    
    private void prepCreateComment(String text) throws InterruptedException {
    	commentsPage.getNewCommentTextInput().sendKeys(text);
    	commentsPage.getSubmitButton().click();
    	justWait(2000);
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
