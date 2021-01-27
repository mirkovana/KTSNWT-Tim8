package ktsnwt_tim8.demo.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SubscriptionsPage {

	private WebDriver driver;

	@FindBy(xpath = "//*[@id=\"Pecka\"]")
	private WebElement peckaBtn;

	@FindBy(xpath = "//*[@id=\"Srpsko\"]")
	private WebElement srpskoBtn;

	@FindBy(xpath = "//*[@id=\"tabela\"]")
	private WebElement tabela;

	@FindBy(xpath = "//*[@id=\"home\"]")
	private WebElement home;

	@FindBy(xpath = "//*[@id=\"1\"]")
	private WebElement unsubOne;

	@FindBy(xpath = "//*[@id=\"5\"]")
	private WebElement unsubFive;

	@FindBy(xpath = "//*[@id=\"10\"]")
	private WebElement unsubTen;

	@FindBy(xpath = "//*[@id=\"Kraljevo\"]")
	private WebElement kraveljnoBtn;

	public SubscriptionsPage() {

	}

	public SubscriptionsPage(WebDriver driver) {
		this.driver = driver;
	}

	public void ensureTabelaIsDisplayed() {
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("tabela")));
	}

	public WebElement getPeckaBtn() {
		return peckaBtn;
	}

	public WebElement getSrpskoBtn() {
		return srpskoBtn;
	}

	public WebElement getTabela() {
		return tabela;
	}

	public WebElement getHome() {
		return home;
	}

	public WebElement getUnsubOne() {
		return unsubOne;
	}

	public WebElement getUnsubFive() {
		return unsubFive;
	}

	public WebElement getKraveljnoBtn() {
		return kraveljnoBtn;
	}

	public WebElement getUnsubTen() {
		return unsubTen;
	}

}
