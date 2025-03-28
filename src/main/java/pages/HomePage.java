package pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.ActionUtils;

public class HomePage {
	
	private WebDriver driver;
	private WebDriverWait wait;
	private ActionUtils actionUtils;
	
	// Constructor
	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.actionUtils = new ActionUtils(driver); // ✅ Initialize actionUtils
		PageFactory.initElements(driver, this); // ✅ Initialize web elements
	}
	
	// Web Elements
	@FindBy(xpath = "//*[@id='nav-link-accountList']") // ✅ Shortened locator
	private WebElement accountAndList;
	
	@FindBy(xpath = "//a[@class='nav-action-signin-button']//span[text()='Sign in']")
	private WebElement signInIcon;
	
	// Hover over "Accounts & Lists"
	public void performHoverAccountsAndList() {
		actionUtils.hoverOverElement(accountAndList);
	}
	
	// Click on "Sign In" button
	public void clickSignInIcon() {
		signInIcon.click();
	}
}
