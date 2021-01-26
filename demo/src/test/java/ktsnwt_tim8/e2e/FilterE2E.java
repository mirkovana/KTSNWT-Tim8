package ktsnwt_tim8.e2e;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ktsnwt_tim8.pages.FilterPage;

public class FilterE2E {

	private WebDriver driver;

	private FilterPage filterPage;
    
	@Before
    public void setUp() throws InterruptedException {
    	System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:4200/home");
		filterPage = PageFactory.initElements(driver, FilterPage.class);
		justWait(3000);
		
    }
	
	@Test
	public void filter() throws InterruptedException {
		filterPage.getFilterMore().click();
		justWait(2000);
		//filterPage.getCategories().get(0).click();
		//filterPage.getSubcategories().get(3).click();
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
	
	@Test
	public void filterGibberish() throws InterruptedException {
		String searchName = "lkjwfwpe9ffewljk";
		filterPage.getNameInput().sendKeys(searchName);
		filterPage.getSearch().click();
		justWait(2000);
		assertEquals(filterPage.getNumOfElements(), 0);
	}
	
	@Test
	public void filterGibberishThenClear() throws InterruptedException {
		String searchName = "lkjwfwpe9ffewljk";
		filterPage.getNameInput().sendKeys(searchName);
		filterPage.getSearch().click();
		justWait(2000);
		assertEquals(filterPage.getNumOfElements(), 0);
		filterPage.getClearSearch().click();
		filterPage.getSearch().click();
		justWait(2000);
		assertNotEquals(filterPage.getNumOfElements(), 0);
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
