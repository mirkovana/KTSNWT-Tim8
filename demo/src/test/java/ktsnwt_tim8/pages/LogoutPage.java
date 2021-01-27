package ktsnwt_tim8.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class LogoutPage {
	
	private WebDriver driver;
	
	@FindBy(name = "logoutUser")
	WebElement logoutUser;
	
	public LogoutPage() {
	}

	public LogoutPage(WebDriver driver, WebElement logoutUser) {
		this.driver = driver;
		this.logoutUser = logoutUser;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getLogoutUser() {
		return logoutUser;
	}
}
