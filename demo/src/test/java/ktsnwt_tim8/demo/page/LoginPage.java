package ktsnwt_tim8.demo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"email\"]")
	private WebElement username;

	@FindBy(xpath = "//*[@id=\"pass\"]")
	private WebElement password;

	@FindBy(xpath = "//*[@id=\"loginBtn\"]")
	private WebElement loginBtn;

	public LoginPage() {
	}

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void ensureIsDisplayedUsername() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("email")));
	}

	public void ensureIsNotVisibleLoginBtn() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("loginBtn")));
	}

	public WebElement getUsername() {
		return username;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getLoginBtn() {
		return loginBtn;
	}

}
