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
	

	@FindBy(linkText = "Srpsko narodno pozoriste")
    private WebElement anchor2;
	@FindBy(xpath = "//a[contains(@class, 'offer-item-link')]")
	public WebElement linkx;
	
	 @FindBy(css = ".offer-item-link")
	List<WebElement> links;
	    
 	
    public WebElement getAnchor1() {
        return anchor1;
    }
    
    public WebElement getLink1j() {
    	//return links.get(0);
    	return driver.findElements(By.cssSelector(".offer-item-link")).get(0);
    }
    
    public WebElement getAnchorFirst() {
        return driver.findElement(By.cssSelector("ng-star-inserted:nth-child(2) > .page > .item .btn"));
    }
    
    public void ensureIsDisplayedA1() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.presenceOfElementLocated(By.linkText("Exit festival")));
    }    


	public WebElement getAnchor2() {
		return anchor2;
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
