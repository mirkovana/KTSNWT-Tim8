package ktsnwt_tim8.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SubcategoryPage {

	private WebDriver driver;

	@FindBy(name = "subcategoryName")
	WebElement subcategoryName;

	@FindBy(name = "saveSubcategory")
	WebElement saveSubcategory;

	@FindBy(name = "categoryOpt")
	WebElement categoryOpt;
	
	
 

	public void ensureIsNotVisibleAddNewButton() {
		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions.invisibilityOfElementLocated(By.name("saveSubcategory")));
	}

	public boolean ensureIsDisabledButton() {
		return driver.findElement(By.name("saveSubcategory")).isEnabled();
	}

	public boolean messageErrorDisplayed(String naziv) {

		try {
			this.driver.findElement(By.name(naziv));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public SubcategoryPage(WebDriver driver) {
		this.driver = driver;
	}

	public SubcategoryPage() {

	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getSubcategoryName() {
		return subcategoryName;
	}

	public WebElement getSaveSubcategory() {
		return saveSubcategory;
	}

	public WebElement getCategoryOpt() {
		return categoryOpt;
	}

}
