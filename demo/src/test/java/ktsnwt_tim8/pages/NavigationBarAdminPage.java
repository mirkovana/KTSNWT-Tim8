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

	public NavigationBarAdminPage(WebElement addCategory, WebElement addSubcategory) {
 		this.addCategory = addCategory;
 		this.addSubcategory=addSubcategory;
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
	
	
	
}
