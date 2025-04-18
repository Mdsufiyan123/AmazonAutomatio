package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import base.BaseTest;
import utils.ExtentReportManager;
import pages.HomePage;

public class SearchAndFilter extends BaseTest {
	private HomePage homePage;

	@BeforeMethod
	public void initPages() {
		setUp("chrome");
		homePage = new HomePage(getDriver());
	}

	@Test(priority = 1, description = "SearchAndFilter - Validate Dropdown Alphabetical Order")
	public void searchDropDownAlphabeticalOrder() {
		ExtentReportManager.createTest("SearchAndFilter - Validate Dropdown Alphabetical Order");
		try {
			ExtentReportManager.getTest().log(Status.INFO, "Starting search dropdown alphabetical order test");
			homePage.disrciptionAlphabetical();
			ExtentReportManager.getTest().log(Status.PASS, "Search dropdown alphabetical order test completed successfully");
		} catch (Exception e) {
			ExtentReportManager.getTest().log(Status.FAIL, "Search dropdown alphabetical order test failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(priority = 2, description = "SearchAndFilter - Validate Product Search")
	public void searchProductAndValidate() {
		ExtentReportManager.createTest("SearchAndFilter - Validate Product Search");
		try {
			ExtentReportManager.getTest().log(Status.INFO, "Starting product search and validation test");
			homePage.searchItemsBasedOnCategory("Electronics", "MacBook");
			homePage.validateItemsSearched("MacBook", "Apple");
			ExtentReportManager.getTest().log(Status.PASS, "Product search and validation test completed successfully");
		} catch (Exception e) {
			ExtentReportManager.getTest().log(Status.FAIL, "Product search and validation test failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(priority = 3, description = "SearchAndFilter - Validate Price Range Filter")
	public void validatePriceRangeFilter() {
		ExtentReportManager.createTest("SearchAndFilter - Validate Price Range Filter");
		try {
			ExtentReportManager.getTest().log(Status.INFO, "Starting price range filter validation test");
			homePage.searchItemsBasedOnCategory("Electronics", "Samsung Mobiles");
			homePage.validatePriceRangeFilter();
			ExtentReportManager.getTest().log(Status.PASS, "Price range filter validation test completed successfully");
		} catch (Exception e) {
			ExtentReportManager.getTest().log(Status.FAIL, "Price range filter validation test failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(priority = 4, description = "SearchAndFilter - Validate Price Sorting")
	public void validatePriceSorting() {
		ExtentReportManager.createTest("SearchAndFilter - Validate Price Sorting");
		try {
			ExtentReportManager.getTest().log(Status.INFO, "Starting price sorting validation test");
			homePage.searchItemsBasedOnCategory("Electronics", "Samsung Mobiles");
			homePage.validatePriceSorting();
			ExtentReportManager.getTest().log(Status.PASS, "Price sorting validation test completed successfully");
		} catch (Exception e) {
			ExtentReportManager.getTest().log(Status.FAIL, "Price sorting validation test failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(priority = 5, description = "SearchAndFilter - Validate Search Suggestions")
	public void validateSearchSuggestions() {
		ExtentReportManager.createTest("SearchAndFilter - Validate Search Suggestions");
		try {
			ExtentReportManager.getTest().log(Status.INFO, "Starting search suggestions validation test");
			homePage.validateSearchSuggestions("samsung");
			ExtentReportManager.getTest().log(Status.PASS, "Search suggestions validation test completed successfully");
		} catch (Exception e) {
			ExtentReportManager.getTest().log(Status.FAIL, "Search suggestions validation test failed: " + e.getMessage());
			throw e;
		}
	}

	@Test(priority = 6, description = "SearchAndFilter - Validate Filters")
	public void validateFilters() {
		ExtentReportManager.createTest("SearchAndFilter - Validate Filters");
		try {
			ExtentReportManager.getTest().log(Status.INFO, "Starting filters validation test");
			homePage.addFiltersAndValidateResults();
			homePage.removeFiltersAndValidateResults();
			ExtentReportManager.getTest().log(Status.PASS, "Filters validation test completed successfully");
		} catch (Exception e) {
			ExtentReportManager.getTest().log(Status.FAIL, "Filters validation test failed: " + e.getMessage());
			throw e;
		}
	}

	@AfterMethod
	public void tearDownTest() {
		tearDown();
	}
}
        

