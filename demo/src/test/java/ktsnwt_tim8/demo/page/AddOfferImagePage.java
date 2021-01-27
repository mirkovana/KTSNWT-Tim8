package ktsnwt_tim8.demo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddOfferImagePage {

	private WebDriver driver;

	@FindBy(xpath = "//*[@id=\"addImage\"]")
	private WebElement addImageBtn;

	@FindBy(xpath = "//*[@id=\"submit\"]")
	private WebElement submitBtn;

	@FindBy(xpath = "//*[@id=\"desc\"]")
	private WebElement description;

	@FindBy(xpath = "//*[@id=\"editOffer\"]")
	private WebElement editOffer;

	public AddOfferImagePage() {

	}

	public AddOfferImagePage(WebDriver driver) {
		this.driver = driver;
	}

	public void ensureFormIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("forma")));
	}

	public void ensureIsDisplayedRedToast() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.className("red-snackbar")));
	}

	public void ensureIsDisplayedGreenToast() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.className("green-snackbar")));
	}

	public WebElement getAddImageBtn() {
		return addImageBtn;
	}

	public WebElement getSubmitBtn() {
		return submitBtn;
	}

	public WebElement getDescription() {
		return description;
	}

	public WebElement getEditOffer() {
		return editOffer;
	}
}
