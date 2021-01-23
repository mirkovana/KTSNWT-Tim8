package ktsnwt_tim8.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PostPage {

	private WebDriver driver;

	@FindBy(name = "addPostDialog")
	WebElement addPostDialog;
	
	@FindBy(name = "postTitle")
	WebElement postTitle;
	
	@FindBy(name = "postContent")
	WebElement postContent;
	
	@FindBy(name = "addPost")
	WebElement addPost;
	
	@FindBy(name = "dugme2")
	WebElement dugme2;
	
	@FindBy(name = "yesButtonP")
	WebElement yesButtonP;


	@FindBy(name = "savePostEdit1")
	WebElement savePostEdit1;

	@FindBy(id = "name1")
	WebElement name1;

	@FindBy(id = "content1")
	WebElement content1;
	
	
	public PostPage(WebDriver driver) {
 		this.driver = driver;
	}
	public PostPage() {
	}
	
	public void ensureIsNotVisibleAddNewButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.name("addPost")));
    }

	 public boolean ensureIsDisabledButton() {
		 return driver.findElement(By.name("addPost")).isEnabled();
	    } 
	 
	 public boolean messageErrorDisplayed(String naziv) {

			try {
				this.driver.findElement(By.name(naziv));
				return true;
			} catch (Exception e) {
				return false;
			}

		}
	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getAddPostDialog() {
		return addPostDialog;
	}

	public WebElement getPostTitle() {
		return postTitle;
	}

	public WebElement getPostContent() {
		return postContent;
	}
	public WebElement getYesButtonP() {
		return yesButtonP;
	}
	public WebElement getAddPost() {
		return addPost;
	}
	public WebElement getDugme2() {
		return dugme2;
	}
	public WebElement getSavePostEdit1() {
		return savePostEdit1;
	}
	public WebElement getName1() {
		return name1;
	}
	public WebElement getContent1() {
		return content1;
	}
	
}
