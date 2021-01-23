package ktsnwt_tim8.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ValidationEmailPage {
	private WebDriver driver;

	@FindBy(name = "token")
	WebElement token;

	@FindBy(name = "validateToken")
	WebElement validateToken;

	public ValidationEmailPage() {
	}

	public ValidationEmailPage(WebDriver driver) {
		this.driver = driver;
	}

	public boolean messageErrorDisplayed(String naziv) {

		try {
			this.driver.findElement(By.name(naziv));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public void ensureIsDisplayedToken() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.name("token")));
	}

	public void ensureIsNotVisibleValidateButton() {
		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions.invisibilityOfElementLocated(By.name("validateToken")));
	}

	public void ensureIsNotVisibleToken() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.name("token")));
	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getToken() {
		return token;
	}

	public WebElement getValidateToken() {
		return validateToken;
	}

}
