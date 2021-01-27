package ktsnwt_tim8.e2e;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
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
       
    }
    
    private void chooseOffer() throws InterruptedException{
    	driver.get("http://localhost:4200/home");
        mainOffersPage = PageFactory.initElements(driver, MainOffersPage.class);
        justWait(1000);
        mainOffersPage.getAnchor1().click();
        justWait(2000);
        commentsPage = PageFactory.initElements(driver, CommentsPage.class);
        justWait(2000);
        commentsPage.setupFormElements();
    }
    
    private void logIn() throws InterruptedException {
        
       driver.get ("http://localhost:4200/login");
       loginPage = PageFactory.initElements(driver, LoginPage.class);
       loginPage.getEmail().sendKeys("kor1@nesto.com");
       loginPage.getPassword().sendKeys("1");
       loginPage.getLoginBtn().click();
       justWait(2000);
       
       
    }

    
    //good
    @Test
    public void createCommentWithoutPicture() throws InterruptedException {
    	logIn();
    	chooseOffer();
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
    	
    	//cleaning up
    	deleteCommentCleanUp();
    	
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
    
    //good
    @Test
    public void createCommentWithPicture() throws InterruptedException {
    	logIn();
    	chooseOffer();
    	int oldComments = commentsPage.getNumOfElements();
    	
    	String newCommentText = "New comment text.";
    	commentsPage.getNewCommentTextInput().sendKeys(newCommentText);
    	
    	String path1 = "src/test/resources/imagesForUpload/petrovaradin2.jpg";
    	File file1 = new File(path1);
    	URI    uri1  = file1.toURI();
    	file1 = new File(uri1);
    	String pathSending = file1.getAbsolutePath();
    	commentsPage.getFileInput().sendKeys(pathSending);
    	justWait(2000);
    	
    	// now changing picture
    	File file = new File("src/test/resources/imagesForUpload/petrovaradin.jpg");
    	URI    uri  = file.toURI();
    	file = new File(uri);
    	String path = file.getAbsolutePath();
    	
    	String base64 = "data:image/jpg;base64," + toBase64(file);
    	String base641 = "data:image/jpg;base64," + toBase64(file1);
    	// checking if the image really changed
    	assertNotEquals(base641, base64);
    	
    	commentsPage.getFileInput().sendKeys(path);
    	
    	justWait(3000);
    	commentsPage.getSubmitButton().click();
    	
    	justWait(3000);
    	int newComments = commentsPage.getNumOfElements();
    	
    	justWait(1000);
    	// checking if number of all comments changed
    	assertEquals(oldComments + 1, newComments);
    	// checking if the text of the first comment equals to the text of new comment
    	assertEquals(commentsPage.getFirstCommentText(), newCommentText);
    	// check if the first comment username is equals to logged-in username
    	assertEquals("kor1@nesto.com", commentsPage.getFirstCommentUsername());
    	
    	// making sure the image we sent is displayed
    	assertEquals(base64, commentsPage.getFirstImageSrc());
    	
    	justWait(2000);
    
    	// cleaning up
    	deleteCommentCleanUp();
    	justWait(5000);
    }
    
    //good
    @Test
    public void createCommentEmptyText() throws InterruptedException {
    	logIn();
    	chooseOffer();
    	String newCommentText = "s";
    	commentsPage.getNewCommentTextInput().sendKeys(newCommentText);
    	// to ensure input field was 'dirtied'
    	commentsPage.getNewCommentTextInput().sendKeys(Keys.BACK_SPACE);
    	justWait(1000);
    	assertEquals(commentsPage.getSubmitButton().isEnabled(), false);
    	assertEquals(commentsPage.messageErrorDisplayed(), true);
    	justWait(1000);
    }
    
    //good
    @Test
    public void editComment() throws InterruptedException {
    	logIn();
    	chooseOffer();
    	prepCreateComment("Edi", null);
    	justWait(2000);
    	commentsPage.getEditButton().click();
    	commentsPage.getEditCommentTextInput().sendKeys("ted comment!!!");
    	commentsPage.getEditSubmitButton().click();
    	justWait(2000);
    	assertEquals(commentsPage.getFirstCommentText(), "Edited comment!!!");
    	// check if the first comment username is equals to logged-in username
    	assertEquals("kor1@nesto.com", commentsPage.getFirstCommentUsername());
    	justWait(2000);
    	
    	// cleaning up
    	deleteCommentCleanUp();

    }
    
    //good
    @Test
    public void editCommentDeletePicture() throws InterruptedException{
    	logIn();
    	chooseOffer();
    	prepCreateComment("cr", "src/test/resources/imagesForUpload/petrovaradin.jpg");
    	commentsPage.getEditButton().click();
    	justWait(1000);
    	commentsPage.getRemoveImageButton().click();
    	commentsPage.getEditSubmitButton().click();
    	justWait(1000);
    	assertEquals(commentsPage.getFirstImageSrc(), null);
    	// making sure nothing else changed
    	assertEquals(commentsPage.getFirstCommentText(), "cr");
    	assertEquals("kor1@nesto.com", commentsPage.getFirstCommentUsername());

    	// cleaning up
    	deleteCommentCleanUp();

    }
    
    //good
    @Test
    public void editCommentAddPicture() throws InterruptedException {
    	logIn();
    	chooseOffer();
    	prepCreateComment("c", null);
    	commentsPage.getEditButton().click();
    	justWait(1000);
    	File file = new File("src/test/resources/imagesForUpload/petrovaradin.jpg");
    	URI    uri  = file.toURI();
    	file = new File(uri);
    	commentsPage.getUploadImageButton().sendKeys(file.getAbsolutePath());
    	justWait(1000);
    	commentsPage.getEditSubmitButton().click();
    	justWait(1000);
    	assertEquals(commentsPage.getFirstImageSrc(), "data:image/jpg;base64," + toBase64(file));
    	// making sure nothing else changed
    	assertEquals(commentsPage.getFirstCommentText(), "c");
    	assertEquals("kor1@nesto.com", commentsPage.getFirstCommentUsername());
    
    	// cleaning up
    	deleteCommentCleanUp();

    }

    //good
    @Test
    public void editCommentCancel() throws InterruptedException {
    	logIn();
    	chooseOffer();
    	prepCreateComment("C", null);
    	commentsPage.getEditButton().click();
    	// to ensure input field was 'dirtied'
    	commentsPage.getEditCommentTextInput().sendKeys(Keys.BACK_SPACE);
    	justWait(1000);
    	// checking if submit button is enabled
    	assertEquals(commentsPage.getEditSubmitButton().isEnabled(), false);
    	// checking if error message is displayed
    	assertEquals(commentsPage.messageErrorDisplayedEdit(), true);
    	justWait(2000);
    	
    	// cleaning up
//    	deleteCommentCleanUp();

    }
    
    // good
    @Test
    public void deleteComment() throws InterruptedException {
    	logIn();
    	chooseOffer();
    	prepCreateComment("I shall be deleted", null);
    	justWait(2000);    
    	int oldComments = commentsPage.getNumOfElements();
    	commentsPage.getDeleteButton().click();    	
    	commentsPage.getYesButton().click();
    	justWait(1000);
    	int newComments = commentsPage.getNumOfElements();
    	// checking if number of all comments changed
    	assertEquals(oldComments - 1, newComments); 
    	// checking if the text of the first comment equals to the text of new comment
    	assertNotEquals(commentsPage.getFirstCommentText(), "I shall be deleted");
    	
     }
    
    //good
    @Test
    public void deleteCommentCancel() throws InterruptedException {
    	logIn();
    	chooseOffer();
    	prepCreateComment("I shan't be deleted", null);
    	justWait(2000);    
    	int oldComments = commentsPage.getNumOfElements();
    	commentsPage.getDeleteButton().click();    	
    	commentsPage.getNoButton().click();
    	justWait(1000);
    	int newComments = commentsPage.getNumOfElements();
    	// checking if number of all comments changed
    	assertEquals(oldComments, newComments); 
    	// checking if the text of the first comment equals to the text of new comment
    	assertEquals(commentsPage.getFirstCommentText(), "I shan't be deleted");
    	
     }
    
    //good
    @Test
    public void commentUnlogged() throws InterruptedException {
    	chooseOffer();
    	justWait(2000);    
    	// make sure submit button is not displayed
    	assertEquals(commentsPage.getSubmitButton(), null);
    	commentsPage.getLogBtn().click();
    	assertEquals(driver.getCurrentUrl(), "http://localhost:4200/login");
     }
    
    @Test
    public void commentLoggedAsAdmin() throws InterruptedException {
    	logInAsAdmin();
    	chooseOffer2();
    	justWait(1000);
    	// ensuring admin can't create comments
    	assertEquals(commentsPage.getNewCommentForm(), null);
    	assertEquals(commentsPage.getEditCommentForm(), null);
    }
    
    private void chooseOffer2() throws InterruptedException {
    driver.get("http://localhost:4200/home");
    mainOffersPage = PageFactory.initElements(driver, MainOffersPage.class);
    justWait(1000);
    mainOffersPage.getAnchor1().click();
    justWait(2000);
    commentsPage = PageFactory.initElements(driver, CommentsPage.class);
    justWait(2000);
    
    }
    private void logInAsAdmin() throws InterruptedException{
        driver.get ("http://localhost:4200/login");
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.getEmail().sendKeys("admin@nesto.com");
        loginPage.getPassword().sendKeys("1");
        loginPage.getLoginBtn().click();
        justWait(1000);
    }
    
    private void prepCreateComment(String text, String path) throws InterruptedException {
    	commentsPage.getNewCommentTextInput().sendKeys(text);
    	if (path != null) {
        	commentsPage.getFileInput().sendKeys(pathForUpload(path));
    	}
    	justWait(1000);
    	commentsPage.getSubmitButton().click();
    	justWait(2000);
    }
    
    private String pathForUpload(String path) {
    	File file = new File(path);
    	URI    uri  = file.toURI();
    	file = new File(uri);
    	return file.getAbsolutePath();
    }
    
    private void deleteCommentCleanUp() throws InterruptedException {
    	justWait(1000);
    	commentsPage.getDeleteButton().click();
    	justWait(1000);
    	commentsPage.getYesButton().click();	
    	justWait(1000);
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
