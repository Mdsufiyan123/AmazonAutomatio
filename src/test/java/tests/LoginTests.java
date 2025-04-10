package tests;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import base.ExtentReportManager;
import base.JsonReader;
import pages.HomePage;
import pages.LoginPage;

public class LoginTests extends BaseTest {

	private HomePage homePage;
    private LoginPage loginPage;
    private String path = "/Users/smd/eclipse-workspace/com.sufiyan.automation/data/loginData.json";
    

@BeforeMethod
private void initPages() {
	setUp("chrome");
    homePage = new HomePage(driver); // Ensure driver is initialized
    loginPage = new LoginPage(driver);
    ExtentReportManager.startTest("Login Test");
    ExtentReportManager.getTest().log(Status.INFO,"Launching Browser and Navigation to Login Page");
    homePage.performHoverAccountsAndList();
	homePage.clickSignInIcon();
}


	
	@DataProvider(name="loginTestData")
	public Object[][] getLogindData(){
		return new Object[][] {
			{JsonReader.getValue(path, "incorrectMail"),JsonReader.getValue(path, "password")},
			{JsonReader.getValue(path, "email"),JsonReader.getValue(path, "incorrectPassword")},
			{JsonReader.getValue(path, "email"),JsonReader.getValue(path, "password")}
			};
		}
	
	@Test(dataProvider="loginTestData")
	public void performLogin(String email,String password) {
		ExtentReportManager.getTest().log(Status.INFO,"Attempting Login With Email: " + email);
		loginPage.performLogin(email, password);
		ExtentReportManager.getTest().log(Status.PASS, "Successfully Logged In");
	}
	
	@AfterMethod
    public void tearDownTest() {
		tearDown();
    }
	
	
}
