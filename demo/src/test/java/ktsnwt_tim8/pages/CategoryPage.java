package ktsnwt_tim8.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CategoryPage {
	private WebDriver driver;

	@FindBy(name = "categoryName")
	WebElement categoryName;

	@FindBy(name = "saveCategory")
	WebElement saveCategory;
	

	public CategoryPage(WebDriver driver) {
 		this.driver = driver;
	}
	public CategoryPage() {
		 
	}
	public void ensureIsNotVisibleAddNewButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.name("saveCategory")));
    }

	 public boolean ensureIsDisabledButton() {
		 return driver.findElement(By.name("saveCategory")).isEnabled();
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

	public WebElement getCategoryName() {
		return categoryName;
	}

	public WebElement getSaveCategory() {
		return saveCategory;
	}
	
	
}
