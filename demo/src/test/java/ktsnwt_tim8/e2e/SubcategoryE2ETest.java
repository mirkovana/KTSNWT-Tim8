package ktsnwt_tim8.e2e;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import ktsnwt_tim8.pages.LoginPage;
import ktsnwt_tim8.pages.NavigationBarAdminPage;
import ktsnwt_tim8.pages.SubcategoryPage;

public class SubcategoryE2ETest {
	private WebDriver driver;

    
    private LoginPage loginPage;
    private NavigationBarAdminPage navigationBarAdminPage;
    private SubcategoryPage subcategoryPage;

    
    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        navigationBarAdminPage = PageFactory.initElements(driver, NavigationBarAdminPage.class);
        subcategoryPage = PageFactory.initElements(driver, SubcategoryPage.class);
       
    }
	
	 private void login() throws InterruptedException {
	        
	       driver.get ("http://localhost:4200/login");
	       loginPage = PageFactory.initElements(driver, LoginPage.class);
	       loginPage.getEmail().sendKeys("admin@nesto.com");
	       loginPage.getPassword().sendKeys("1");
	       loginPage.getLoginBtn().click();
	       justWait();
	       
	       
	    }
	 
	 
	 @Test
		public void NewSubcategoryTestSuccess() throws InterruptedException {

		
			login();
			justWait();


			navigationBarAdminPage.getAddSubcategory().click();
			justWait();
			subcategoryPage.getSubcategoryName().sendKeys("znam za jedan grad zove se beograd");
			subcategoryPage.getCategoryOpt().sendKeys("Institucija");
			justWait();
			subcategoryPage.getSaveSubcategory().click();
			

			 

			assertEquals("http://localhost:4200/home", driver.getCurrentUrl());

		}
		@Test
		public void NewSubcategoryTestNameEmpty() throws InterruptedException {

			
			login();
			justWait();

			 

			navigationBarAdminPage.getAddSubcategory().click();
			justWait();
			subcategoryPage.getSubcategoryName().sendKeys("");
			subcategoryPage.getCategoryOpt().sendKeys("Institucija");
			subcategoryPage.getSaveSubcategory().click();			 
			subcategoryPage.ensureIsDisabledButton();
		    assertTrue(subcategoryPage.messageErrorDisplayed("nameReq") );
			 
			assertEquals("http://localhost:4200/home", driver.getCurrentUrl());

		}
		
		@Test
		public void NewSubcategoryTestCategoryNotSelectedEmpty() throws InterruptedException {

			
			login();
			justWait();

			 

			navigationBarAdminPage.getAddSubcategory().click();
			justWait();
			subcategoryPage.getSubcategoryName().sendKeys("dddddddddd");
			subcategoryPage.getCategoryOpt().sendKeys("");
			subcategoryPage.getSaveSubcategory().click();			 
			subcategoryPage.ensureIsDisabledButton();
		    assertTrue(subcategoryPage.messageErrorDisplayed("catReq") );
			 
			assertEquals("http://localhost:4200/home", driver.getCurrentUrl());

		}
	 @After
	    public void tearDown() {
	        driver.quit();
	    }
	    
	    private void justWait() throws InterruptedException {
			synchronized (driver) {
				driver.wait(3000);
			}
		}
}
