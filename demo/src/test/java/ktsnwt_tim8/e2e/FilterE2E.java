package ktsnwt_tim8.e2e;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ktsnwt_tim8.pages.FilterPage;
import ktsnwt_tim8.pages.LoginPage;
import ktsnwt_tim8.pages.MainOffersPage;

public class FilterE2E {

	private WebDriver driver;

    private MainOffersPage mainOffersPage;
	private FilterPage filterPage;
    
	@Before
    public void setUp() throws InterruptedException {
    	System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:4200/home");
		mainOffersPage = PageFactory.initElements(driver, MainOffersPage.class);
		filterPage = PageFactory.initElements(driver, FilterPage.class);
		justWait(3000);
		
    }
	
	@Test
	public void filter() throws InterruptedException {
		filterPage.getFilterMore().click();
		justWait(2000);
		//filterPage.getCategories().get(0).click();
		justWait(2000);
		System.out.println(filterPage.getCategories().get(0).getText());
		String searchName = "pozoriste";
		filterPage.getNameInput().sendKeys(searchName);
		filterPage.getSearch().click();
		justWait(2000);
		boolean correct = true;
		for (String name: filterPage.getOffersTitles()) {
			if (!name.toLowerCase().contains(searchName.toLowerCase())) {
				correct = false;
			}
		};
		assertTrue(correct);
	}
	
	private void justWait(int milliseconds) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(milliseconds);
        }
    }
	
	 @After
	    public void tearDown() {
	        driver.quit();
	    }
	
}
