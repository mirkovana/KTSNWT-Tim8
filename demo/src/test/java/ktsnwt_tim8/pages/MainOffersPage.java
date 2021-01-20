package ktsnwt_tim8.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainOffersPage {
	
	private WebDriver driver;
	
	
	@FindBy(linkText = "Exit festival")
    private WebElement anchor1;
	
    public WebElement getAnchor1() {
        return anchor1;
    }
    
    public void ensureIsDisplayedA1() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.linkText("Exit festival")));
    }    


	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	
	public void setAnchor1(WebElement anchor1) {
		this.anchor1 = anchor1;
	}
    
    
}
