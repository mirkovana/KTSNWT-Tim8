package ktsnwt_tim8.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommentsPage {
	private WebDriver driver;
	
	@FindBy(css = "app-comment-edit")
	List<WebElement> edits;
	
	
	WebElement newCommentTextInput;
	
	//mat-paginator-range-label
	@FindBy(css = ".mat-paginator-range-label")
	WebElement paginatorPages;
	// slika
	
    WebElement submitButton;
	
    @FindBy(css = "app-comment")
	List<WebElement> comments;
    
    WebElement firstComment;
    
	@FindBy(linkText = "Exit festival")
    private WebElement anchor1;
	
    public WebElement getAnchor1() {
        return anchor1;
    }
    
    public void ensureIsDisplayedA1() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.linkText("Exit festival")));
    }
    
    public WebElement getNewCommentForm() {
    	return edits.get(0);
    }
    
    public void setupFormElements() {
    	this.newCommentTextInput = edits.get(0).findElement(By.cssSelector("textarea"));
    	
    	// slika
    	
        this.submitButton = edits.get(0).findElement(By.cssSelector("button[type='submit']"));
    	
    }
    
    public int getNumOfElements() {
    	String[] els = this.getPaginatorPages().getText().split(" ");
    	return Integer.parseInt(els[4]);
    	
    }

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public List<WebElement> getEdits() {
		return edits;
	}

	public void setEdits(List<WebElement> edits) {
		this.edits = edits;
	}

	public WebElement getNewCommentTextInput() {
		return newCommentTextInput;
	}

	public void setNewCommentTextInput(WebElement newCommentTextInput) {
		this.newCommentTextInput = newCommentTextInput;
	}

	public WebElement getSubmitButton() {
		return submitButton;
	}

	public void setSubmitButton(WebElement newCommentPost) {
		this.submitButton = newCommentPost;
	}

	public void setAnchor1(WebElement anchor1) {
		this.anchor1 = anchor1;
	}

	public WebElement getPaginatorPages() {
		return paginatorPages;
	}

	public void setPaginatorPages(WebElement paginatorPages) {
		this.paginatorPages = paginatorPages;
	}

	public List<WebElement> getComments() {
		return comments;
	}

	public void setComments(List<WebElement> comments) {
		this.comments = comments;
	}

	public WebElement getFirstComment() {
		return this.getComments().get(0);
	}
	
	public String getFirstCommentText() {
		return this.getComments().get(0).findElement(By.cssSelector("p")).getText();
	}

	public void setFirstComment(WebElement firstComment) {
		this.firstComment = firstComment;
	}
    
	public String getFirstCommentUsername() {
		return this.getComments().get(0).findElement(By.cssSelector(".mat-card-title")).getText();
	}
	
	public boolean submitButtonEnabled() {
		return true;
	}

	public boolean messageErrorDisplayed() {
		
		try {
			this.getNewCommentForm().findElement(By.cssSelector("span.text-danger"));
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public List<WebElement> getEditButtons() {
		List<WebElement> els = this.driver.findElements(By.cssSelector("button.mat-icon-button"));
		for (WebElement el: els) {
			System.out.println(el.getTagName());
		}
		return els;
		//return this.firstComment.findElement(By.cssSelector("button[matTooltip='Edit comment']"));
	}
	
	public WebElement getEditButton() {
		return getComments().get(0).findElements(By.cssSelector("button")).get(0);
	}
	
	public WebElement getDeleteButton() {
		//return this.firstComment.findElement(By.cssSelector("button"));
		return getComments().get(0).findElements(By.cssSelector("button")).get(1);
			
	}
}
