package ktsnwt_tim8.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RatingPage {

	// ng-reflect-rate

	private WebDriver driver;

	@FindBy(linkText = "Log in")
    private WebElement loginLink;
	
	@FindBy(linkText = "sign up")
    private WebElement signupLink;
	
	
	@FindBy(css = "ngb-rating")
	private WebElement rating;

	public RatingPage() {
	}

	public RatingPage(WebDriver driver) {
		this.driver = driver;
	}

	public int getCurrentRatingValue() {
		return Integer.parseInt(rating.getAttribute("ng-reflect-rate"));
	}

	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void setRating(WebElement currentRating) {
		this.rating = currentRating;
	}

	public WebElement getRating() {
		return rating;
	}

	public WebElement getAStar() {
		return driver.findElement(By.cssSelector(".ng-star-inserted:nth-child(6)"));	
	}
	
	public WebElement getRateButton(){
		try {
			return driver.findElement(By.name("rateButton"));

		} catch(Exception e) {
			return null;
		}
	}

	public WebElement getChangeRatingButton(){
		try {
			return driver.findElement(By.name("changeRatingButton"));

		} catch(Exception e) {
			return null;
		}
	}

	
	public WebElement getDeleteRatingButton(){
		try {
			return driver.findElement(By.name("deleteRatingButton"));

		} catch(Exception e) {
			return null;
		}
	}
	
	public WebElement getSaveUpdateButton(){
		try {
			return driver.findElement(By.name("saveUpdateButton"));

		} catch(Exception e) {
			return null;
		}
	}

	
	
	public boolean snackBarSuccess(String text) {
		try {
		WebElement e = driver.findElement(By.cssSelector("simple-snack-bar > span"));
		System.out.println(e);
		return true;
		//return e.getText().equals(text);
		}
		catch (Exception e) {
			System.out.println("Nije uspeo da ga nadje");
			return false;
		}
	}

	public WebElement getAStar(int rating) {
		return driver.findElement(By.cssSelector(".ng-star-inserted:nth-child(" + rating*2 + ")"));	
	}

	public WebElement getLoginLink() {
		return loginLink;
	}

	public WebElement getSignupLink() {
		return signupLink;
	}
	

}
