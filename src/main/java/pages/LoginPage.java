package pages;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.ActionUtils;


public class LoginPage {
	
	private WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor jsExecutor;
	private ActionUtils actionUtils;
	
	public LoginPage(WebDriver driver) {
		this.driver= driver;
		this.jsExecutor = (JavascriptExecutor) driver;
		this.wait = new WebDriverWait(driver,Duration.ofSeconds(10)); 
		this.actionUtils = new ActionUtils(driver);
		PageFactory.initElements(driver,this);
		
	}
	
	@FindBy(xpath="//input[contains(@id,'ap_email')]")
	private WebElement userNameField;
	
	@FindBy(xpath="//span[@id='continue']")
	private WebElement continueIcon;
	
	@FindBy(xpath="//input[contains(@id,'ap_password')]")
	private WebElement passwordField;
	
	
	@FindBy(id="signInSubmit")
	private WebElement SignInIcon;
	
	@FindBy(id="navbar-main")
	private WebElement headerNav;
	
	@FindBy(xpath="//*[contains(text(),'Your password is incorrect')]")
	private WebElement incorrectPasswordMsg;
	
	@FindBy(xpath="//*[contains(text(),'Invalid email')]")
	private WebElement invalidEmailMsg;
	
	@FindBy(xpath="//span[@id='continue']/ancestor::div/h1[contains(text(),'Sign in')]")
	private WebElement signInOrCreateIcon;
	
	
	public void performLogin(String email , String password) {
		
		userNameField.sendKeys(email);
		continueIcon.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(actionUtils.isElementPresent(signInOrCreateIcon) && signInOrCreateIcon.isDisplayed()) {
			Assert.assertTrue(invalidEmailMsg.isDisplayed(),"Error For Invalid Mail is Not Displayed");
		}else {
		
		passwordField.sendKeys(password);
		SignInIcon.click();
		if(actionUtils.isElementPresent(SignInIcon) && SignInIcon.isDisplayed()) {

			Assert.assertTrue(incorrectPasswordMsg.isDisplayed(),"Proper Error Message is Not Displayed");
			}else {
				Assert.assertEquals(driver.getCurrentUrl(), "https://www.amazon.in/?ref_=nav_signin");
				Assert.assertTrue(headerNav.isDisplayed(), "Correct Page Not Found");
			}
		
	}
	}
}
	
