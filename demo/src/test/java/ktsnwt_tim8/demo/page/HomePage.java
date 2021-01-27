package ktsnwt_tim8.demo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

	private WebDriver driver;

	@FindBy(xpath = "//*[@id=\"mapa\"]")
	private WebElement map;

	@FindBy(xpath = "//*[@id=\"subs\"]")
	private WebElement subs;

	@FindBy(xpath = "//*[@id=\"editOffer\"]")
	private WebElement editOffer;

	@FindBy(xpath = "//*[@id=\"registration\"]")
	private WebElement registration;

	@FindBy(xpath = "//img[contains(@title,'pozoriste jagodina')]")
	private WebElement marker;

	@FindBy(xpath = "//a[contains(@title,'Zoom in')]")
	private WebElement zoomIn;

	@FindBy(xpath = "//img[contains(@title,'Uzice')]")
	private WebElement markerUzice;

	@FindBy(xpath = "//img[contains(@title,'Srpsko narodno pozoriste')]")
	private WebElement badMarker;

	@FindBy(xpath = "//img[contains(@title,'Madjarsko pozoriste')]")
	private WebElement madjarskoMarker;

	@FindBy(xpath = "//img[contains(@title,'Kraljevo')]")
	private WebElement kraljevoMarker;

	@FindBy(xpath = "//*[@id=\"subBtn\"]")
	private WebElement subBtn;

	@FindBy(xpath = "//*[@class=\"red-snackbar\"]")
	private WebElement redToast;

	@FindBy(xpath = "//*[@class=\"green-snackbar\"]")
	private WebElement greenToast;

	@FindBy(xpath = "//*[@id=\"edit\"]")
	private WebElement editBtn;

	public HomePage() {

	}

	public HomePage(WebDriver driver) {
		this.driver = driver;

	}

	public void ensureIsDisplayedMap() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("mapa")));
	}

	public void ensureIsDisplayedRedToast() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.className("red-snackbar")));
	}

	public void ensureIsDisplayedGreenToast() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.className("green-snackbar")));
	}

	public WebElement getMap() {
		return map;
	}

	public WebElement getSubs() {
		return subs;
	}

	public WebElement getEditOffer() {
		return editOffer;
	}

	public WebElement getRegistration() {
		return registration;
	}

	public WebElement getMarker() {
		return marker;
	}

	public WebElement getSubBtn() {
		return subBtn;
	}

	public WebElement getRedToast() {
		return redToast;
	}

	public WebElement getGreenToast() {
		return greenToast;
	}

	public WebElement getBadMarker() {
		return badMarker;
	}

	public WebElement getMarkerUzice() {
		return markerUzice;
	}

	public WebElement getKraljevoMarker() {
		return kraljevoMarker;
	}

	public WebElement getMadjarskoMarker() {
		return madjarskoMarker;
	}

	public WebElement getEditBtn() {
		return editBtn;
	}

	public WebElement getZoomIn() {
		return zoomIn;
	}

}
