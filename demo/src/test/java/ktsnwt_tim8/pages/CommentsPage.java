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
	
	@FindBy(css = ".mat-paginator-range-label")
	List<WebElement> paginatorPagesList;
	// slika
	


	WebElement submitButton;
    
    @FindBy(name = "yesButton")
    WebElement yesButton;
	
    @FindBy(name = "noButton")
    WebElement noButton;
	
    @FindBy(name = "logBtn")
    WebElement logBtn;
	
    
    public WebElement getLogBtn() {
		return logBtn;
	}

	public WebElement getNoButton() {
		return noButton;
	}

	public WebElement getYesButton() {
		return yesButton;
	}

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
    
    public WebElement getEditCommentForm() {
    	return edits.get(1);
    }
    
   
    
    public void setupFormElements() {
    	this.newCommentTextInput = edits.get(0).findElement(By.cssSelector("textarea"));
    	
    	// slika
    	try {
    		this.submitButton = edits.get(0).findElement(By.cssSelector("button[type='submit']"));
    	}catch(Exception e) {
    		
    	}
    }
    
    public WebElement getFileInput() {
    	return edits.get(0).findElement(By.cssSelector("input"));
    	
    }
    
    public WebElement getEditCommentTextInput() {
    	return edits.get(1).findElement(By.cssSelector("textarea"));
    }
    
    public int getNumOfElements() {
    	String[] els = this.getPaginatorPages().getText().split(" ");
    	if (els.length < 4) {
    		return 0;
    	}
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
		try {
			return edits.get(0).findElement(By.cssSelector("button[type='submit']"));
		}
		catch (Exception e) {
			return null;
		}
	}

	public void setSubmitButton(WebElement newCommentPost) {
		this.submitButton = newCommentPost;
	}

	public void setAnchor1(WebElement anchor1) {
		this.anchor1 = anchor1;
	}

	public WebElement getPaginatorPages() {
		 return this.paginatorPagesList.get(1);
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
	
	public boolean messageErrorDisplayedEdit() {
		
		try {
			this.getEditCommentForm().findElement(By.cssSelector("span.text-danger"));
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public WebElement getDeleteButton() {
		//return this.firstComment.findElement(By.cssSelector("button"));
		return getComments().get(0).findElements(By.cssSelector("button")).get(1);
			
	}

	public String getFirstImageSrc() {
		try {
		return this.getComments().get(0).findElement(By.cssSelector("img")).getAttribute("src");
		}
		catch(Exception e) {
			return null;
		}
		}
    public List<WebElement> getPaginatorPagesList() {
		return paginatorPagesList;
	}
    
    public boolean snackBarSuccess(String text) {
		try {
			driver.findElement(By.cssSelector("simple-snack-bar > span"));
			return true;
		}
		catch (Exception e) {
			System.out.println("nije ga nasao");
			return false;
		}
	}

	public WebElement getEditSubmitButton() {
		return edits.get(1).findElement(By.cssSelector("button[type='submit']"));
	}
	public WebElement getRemoveImageButton() {
		return edits.get(1).findElement(By.cssSelector("button[name='removeImage']"));
	}

	public WebElement getUploadImageButton() {
		return edits.get(1).findElement(By.cssSelector("input"));
	}
}
