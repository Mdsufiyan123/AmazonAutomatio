package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import base.BaseTest;
import base.ExtentReportManager;
import pages.HomePage;

public class Pagination extends BaseTest {
	
	private HomePage homePage;
	
	@BeforeClass
	public void initPages() {
		setUp("chrome");
		homePage = new HomePage(driver);

	}
	
	
	@Test(priority=1)
	public void validatePaginationContainer() {
		ExtentReportManager.startTest("Validate Pagination Container");
		ExtentReportManager.getTest().log(Status.INFO, "Starting Validation of Pagination Container Test Case");
		homePage.addFilters();
		homePage.validatePaginationContainer();
		ExtentReportManager.getTest().log(Status.PASS, "Validated of Pagination Container Test Case");
	}
	
	@Test(dependsOnMethods="validatePaginationContainer")
	public void validatePagination() {
		ExtentReportManager.startTest("Validate Pagination");
		ExtentReportManager.getTest().log(Status.INFO, "Validation Pagination");
		homePage.validatePagination();
		ExtentReportManager.getTest().log(Status.PASS, "Validated Pagination");
	}
	
	
	
	
	@AfterClass
    public void tearDownTest() {
		tearDown();
        
	}
}
