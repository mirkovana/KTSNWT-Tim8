package ktsnwt_tim8.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {

	private WebDriver driver;

	@FindBy(name = "name")
	WebElement name;

	@FindBy(name = "surname")
	WebElement surname;

	@FindBy(name = "username")
	WebElement username;

	@FindBy(name = "password")
	WebElement password;

	@FindBy(name = "registrationButton")
	WebElement registrationButton;

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
	}

	public RegistrationPage() {
	}
	
	public void ensureIsDisplayedName() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.name("name")));
    }
	
    public void ensureIsNotVisibleRegistrationButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.name("registrationButton")));
    }


    public void ensureIsNotVisibleName() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.name("name")));
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

	public WebElement getName() {
		return name;
	}

	public WebElement getSurname() {
		return surname;
	}

	public WebElement getUsername() {
		return username;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getRegistrationButton() {
		return registrationButton;
	}

}
