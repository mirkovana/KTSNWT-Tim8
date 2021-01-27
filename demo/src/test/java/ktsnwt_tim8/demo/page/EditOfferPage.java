package ktsnwt_tim8.demo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditOfferPage {

	private WebDriver driver;

	@FindBy(xpath = "//*[@id=\"image1\"]")
	private WebElement firstImage;

	@FindBy(xpath = "//*[@id=\"input1\"]")
	private WebElement firstInput;

	@FindBy(xpath = "//*[@id=\"submit1\"]")
	private WebElement firstSubmit;

	@FindBy(xpath = "//*[@id=\"delete1\"]")
	private WebElement deleteFirstImage;

	@FindBy(xpath = "//*[@id=\"delete2\"]")
	private WebElement SecondDelete;

	@FindBy(xpath = "//*[@id=\"errorMsg\"]")
	private WebElement errorMsg;

	@FindBy(xpath = "//*[@id=\"toast\"]")
	private WebElement toast;

	@FindBy(xpath = "//*[@id=\"toast1\"]")
	private WebElement toast1;

	@FindBy(xpath = "//*[@id=\"addImage\"]")
	private WebElement addImage;

	@FindBy(xpath = "//*[@class=\"red-snackbar\"]")
	private WebElement redToast;

	@FindBy(xpath = "//*[@class=\"green-snackbar\"]")
	private WebElement greenToast;

	public EditOfferPage() {

	}

	public EditOfferPage(WebDriver driver) {
		this.driver = driver;
	}

	public void ensureIsDisplayedRedToast() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.className("red-snackbar")));
	}

	public void ensureIsDisplayedGreenToast() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.className("green-snackbar")));
	}

	public WebElement getFirstImage() {
		return firstImage;
	}

	public WebElement getFirstInput() {
		return firstInput;
	}

	public WebElement getFirstSubmit() {
		return firstSubmit;
	}

	public WebElement getDeleteFirstImage() {
		return deleteFirstImage;
	}

	public WebElement getErrorMsg() {
		return errorMsg;
	}

	public WebElement getToast() {
		return toast;
	}

	public WebElement getToast1() {
		return toast1;
	}

	public WebElement getRedToast() {
		return redToast;
	}

	public WebElement getGreenToast() {
		return greenToast;
	}

	public WebElement getSecondDelete() {
		return SecondDelete;
	}

	public WebElement getAddImage() {
		return addImage;
	}

}
