package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.HomePage;

public class SearchAndFilter extends BaseTest {
	
	
	private HomePage homePage ;
	
	@BeforeMethod
	public void initPages() {
		setUp("chrome");
		homePage = new HomePage(driver);
		
	}
//	validating the search drop down discription are in alphabetical order
	@Test
	public void searchDropDownAlphabeticalOrder() {
		homePage.disrciptionAlphabetical();
	}
	
	
	@Test
	public void searchProductAndValidate() {
		homePage.searchItemsBasedOnCategory("Electronics","MacBook");
		homePage.validateItemsSearched();
	}
	
	@Test
	public void validatePriceRangeFilter() {
		homePage.searchItemsBasedOnCategory("Electronics", "Samsung Mobiles");
		homePage.validatePriceRangeFilter();
			
	}
	
	@Test
	public void validatePriceSorting() {
		homePage.searchItemsBasedOnCategory("Electronics", "Samsung Mobiles");
		homePage.validatePriceSorting();
	}

	@AfterMethod
    public void tearDownTest() {
		tearDown();
    }
}
