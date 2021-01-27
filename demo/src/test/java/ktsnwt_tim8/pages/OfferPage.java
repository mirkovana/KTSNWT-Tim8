package ktsnwt_tim8.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OfferPage {
	
	private WebDriver driver;

	@FindBy(name = "offerTitle")
	WebElement offerTitle;
	
	@FindBy(name = "offerDescription")
	WebElement offerDescription;
	
	@FindBy(name = "offerPlace")
	WebElement offerPlace;
	
	@FindBy(name = "offerSubcategortyOpt")
	WebElement offerSubcategorty;
	
	@FindBy(id = "addOfferBtnnn")
	WebElement addOfferBtn;
	
	@FindBy(name = "titleForEditOffer")
	WebElement titleForEditOffer;
	
	@FindBy(name = "descriptionForEditOffer")
	WebElement descriptionForEditOffer;
	
	@FindBy(name = "editOfferBtnnn")
	WebElement editOfferBtnnn;
	
	@FindBy(name = "deleteOfferBtnnn")
	WebElement deleteOfferBtnnn;

	@FindBy(name = "yesButtonOffer")
	WebElement yesButtonOffer;
	
	@FindBy(xpath = "//*[@class=\"green-snackbar\"]")
	private WebElement greenToast;
	
	public OfferPage() {
	}

	public OfferPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void ensureIsDisplayedGreenToast() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.className("green-snackbar")));
	}
	
	public void ensureIsDisplayedTitle() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.name("offerTitle")));
    }
	
    public void ensureIsNotVisibleAddOfferButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("addOfferBtnnn")));
    }


    public void ensureIsNotVisibleTitle() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.name("offerTitle")));
    }
        
    public boolean messageErrorDisplayed(String naziv) {
		
		try {
			this.driver.findElement(By.name(naziv));
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
    
    public boolean ensureIsDisabledButton() {
		 return driver.findElement(By.id("addOfferBtnnn")).isEnabled();
	}
    
    public boolean ensureIsDisabledButtonForEditOffer() {
		 return driver.findElement(By.name("editOfferBtnnn")).isEnabled();
	}

	public WebDriver getDriver() {
		return driver;
	}

	public WebElement getOfferTitle() {
		return offerTitle;
	}

	public WebElement getOfferDescription() {
		return offerDescription;
	}

	public WebElement getOfferPlace() {
		return offerPlace;
	}

	public WebElement getOfferSubcategorty() {
		return offerSubcategorty;
	}

	public WebElement getAddOfferBtn() {
		return addOfferBtn;
	}

	public WebElement getTitleForEditOffer() {
		return titleForEditOffer;
	}

	public WebElement getDescriptionForEditOffer() {
		return descriptionForEditOffer;
	}

	public WebElement getEditOfferBtnnn() {
		return editOfferBtnnn;
	}

	public WebElement getDeleteOfferBtnnn() {
		return deleteOfferBtnnn;
	}

	public WebElement getYesButtonOffer() {
		return yesButtonOffer;
	}

	public WebElement getGreenToast() {
		return greenToast;
	}
}
