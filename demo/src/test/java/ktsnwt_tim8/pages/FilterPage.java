package ktsnwt_tim8.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FilterPage {

	@FindBy(css = "mat-checkbox.category")
	List<WebElement> categories;
	
	@FindBy(css = "mat-checkbox.subcategory")
	List<WebElement> subcategories;
	
	@FindBy(name = "moreOptions")
	WebElement filterMore;
	
	@FindBy(name = "lessOptions")
	WebElement filterLess;
	
	@FindBy(name = "text")
	WebElement nameInput;
	
	@FindBy(name = "search")
	WebElement search;
	
	@FindBy(tagName = "app-offer-item")
	List<WebElement> offers;
	

	
	public List<WebElement> getOffers() {
		return offers;
	}
	
	public List<String> getOffersTitles(){
		List<String> list = new ArrayList<String>();
		for (WebElement el: offers) {
			list.add(el.findElement(By.cssSelector("p.title")).getText());
		}
		return list;
		
	}

	public WebElement getNameInput() {
		return nameInput;
	}
	
	public WebElement getSearch() {
		return search;
	}

	public WebElement getFilterMore() {
		return filterMore;
	}

	public WebElement getFilterLess() {
		return filterLess;
	}

	public List<WebElement> getCategories() {
		return categories;
	}

	public List<WebElement> getSubcategories() {
		return subcategories;
	}
}