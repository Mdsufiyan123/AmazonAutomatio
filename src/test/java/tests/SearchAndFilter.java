package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.BaseTest;
import base.ExtentReportManager;
import pages.HomePage;

public class SearchAndFilter extends BaseTest {

	private HomePage homePage;

	@BeforeMethod
	public void initPages() {
		setUp("chrome");
		homePage = new HomePage(driver);

	}

//	validating the search drop down discription are in alphabetical order
	@Test
	public void searchDropDownAlphabeticalOrder() {
		ExtentReportManager.startTest("Search DropDown Alphabetical Order");
		homePage.disrciptionAlphabetical();
	}

	@Test
	public void searchProductAndValidate() {
		ExtentReportManager.startTest("Search Product And Validate");
		homePage.searchItemsBasedOnCategory("Electronics", "MacBook");
		homePage.validateItemsSearched("MacBook","Apple");
	}

	@Test
	public void validatePriceRangeFilter() {
		ExtentReportManager.startTest("Validate Price Range Filter");
		homePage.searchItemsBasedOnCategory("Electronics", "Samsung Mobiles");
		homePage.validatePriceRangeFilter();

	}

	@Test
	public void validatePriceSorting() {
		ExtentReportManager.startTest("Validate Price Sorting");
		homePage.searchItemsBasedOnCategory("Electronics", "Samsung Mobiles");
		homePage.validatePriceSorting();
	}
	
//	Search for a product and verify relevant suggestions in the dropdown
	
	@Test
	public void validateSearchSuggestions() {
		ExtentReportManager.startTest("Validate Search Suggestions");
		homePage.validateSearchSuggestions("samsung");
	}
	
	@Test
	public void validateFilters() {
		ExtentReportManager.startTest("Validate Filters");
		homePage.addFiltersAndValidateResults();
		homePage.removeFiltersAndValidateResults();
	}
	
	@AfterMethod
    public void tearDownTest() {
		tearDown();
	}
}
        

