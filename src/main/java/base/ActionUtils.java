package base;

import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import java.time.Duration;

public class ActionUtils {

	private WebDriver driver;
	private Actions actions;
	private WebDriverWait wait;

	public ActionUtils(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(driver);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	/**
	 * Hovers over the given WebElement.
	 * 
	 * @param element WebElement to hover over.
	 */
	public void hoverOverElement(WebElement element) {
		try {
			waitUntilVisible(element); // Ensure element is visible before hovering
			actions.moveToElement(element).perform();
			System.out.println("Successfully hovered over the element: " + element);
		} catch (Exception e) {
			System.err.println("Error while hovering over element: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Waits until the element is visible before performing an action.
	 * 
	 * @param element WebElement to wait for.
	 */
	public void waitUntilVisible(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public boolean isElementPresent(WebElement element) {
		try {
			return element.isDisplayed(); // Returns true if found and visible
		} catch (NoSuchElementException e) {
			return false; // Element not found in the DOM
		}
	}

	public void pressEnter() {
		actions.sendKeys(Keys.RETURN).perform();
	}

	public void moveSlider(int xOffSet, WebElement slider) {
		actions.clickAndHold(slider).moveByOffset(xOffSet, 0).release().perform();
	}

	// Utility function to extract numeric values from a string
	public int extractNumericValue(String text) {
		return Integer.parseInt(text.replaceAll("[^0-9]", ""));
	}
	
	 public void logAssertion(String logMessage, boolean condition, String failureMessage, String successMessage) {
	        // Log the action being performed
	        ExtentReportManager.getTest().log(Status.INFO, logMessage);
	        
	        try {
	            // Assert condition
	            if (!condition) {
	                throw new AssertionError(failureMessage);
	            }
	            // Log as Pass if assertion is successful
	            ExtentReportManager.getTest().log(Status.PASS, successMessage);
	        } catch (AssertionError e) {
	            // Log as Fail if assertion fails
	            ExtentReportManager.getTest().log(Status.FAIL, "Failed: " + failureMessage);
	        }
	    }
}
