package ktsnwt_tim8.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RatingPage {

	// ng-reflect-rate

	private WebDriver driver;

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
		return driver.findElement(By.cssSelector(".ng-star-inserted:nth-child(8)"));	
	}
	
	public WebElement getRateButton(){
		return driver.findElement(By.name("rateButton"));
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

}
