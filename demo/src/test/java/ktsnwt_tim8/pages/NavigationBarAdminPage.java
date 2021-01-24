package ktsnwt_tim8.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavigationBarAdminPage {

	private WebDriver driver;
 

	@FindBy(name = "addCategory")
	WebElement addCategory;

	@FindBy(name = "addSubcategory")
	WebElement addSubcategory;
	
	@FindBy(name = "addOffer")
	WebElement addOffer;

	public NavigationBarAdminPage(WebElement addCategory, WebElement addSubcategory, WebElement addOffer) {
 		this.addCategory = addCategory;
 		this.addSubcategory=addSubcategory;
 		this.addOffer=addOffer;

 		
	}


	public NavigationBarAdminPage() {
 	}


	public WebDriver getDriver() {
		return driver;
	}


	public WebElement getAddCategory() {
		return addCategory;
	}


	public WebElement getAddSubcategory() {
		return addSubcategory;
	}


	public WebElement getAddOffer() {
		return addOffer;
	}	
}
