package ktsnwt_tim8.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver driver;

	@FindBy(css = "input[formControlName='username']")
	private WebElement email;

	@FindBy(css = "input[formControlName='password']")
	private WebElement password;

	@FindBy(css = "button")
	private WebElement loginBtn;

	public LoginPage() {
	}

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void ensureIsDisplayedEmail() {
		(new WebDriverWait(driver, 30))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[formControlName='username']")));
	}

	public void ensureIsNotVisibleLoginBtn() {
		(new WebDriverWait(driver, 10))
				.until(ExpectedConditions.invisibilityOfElementLocated(By.name("buttonForLogin")));
	}

	public void ensureIsNotVisibleEmail() {
		(new WebDriverWait(driver, 10)).until(
				ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("input[formControlName='username']")));
	}

	public boolean messageErrorDisplayed(String naziv) {

		try {
			this.driver.findElement(By.name(naziv));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getLoginBtn() {
		return loginBtn;
	}
}
