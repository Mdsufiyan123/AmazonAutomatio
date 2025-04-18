package utils;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionUtils {
    private static Actions actions;
    private static WebDriverWait wait;

    public static void initActions(WebDriver driver) {
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static void hoverOverElement(WebElement element) {
        try {
            actions.moveToElement(element).perform();
        } catch (Exception e) {
        }
    }

    public static void pressEnter() {
        try {
            actions.sendKeys(org.openqa.selenium.Keys.ENTER).perform();
        } catch (Exception e) {
        }
    }

    public static void waitUntilVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
        }
    }

    public static int extractNumericValue(String text) {
        try {
            return Integer.parseInt(text.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isElementPresent(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static void clickElement(WebElement element) {
        try {
            actions.click(element).perform();
        } catch (Exception e) {
        }
    }

    public static void doubleClickElement(WebElement element) {
        try {
            actions.doubleClick(element).perform();
        } catch (Exception e) {
        }
    }

    public static void rightClickElement(WebElement element) {
        try {
            actions.contextClick(element).perform();
        } catch (Exception e) {
        }
    }

    public static void waitForElement(WebDriver driver, By locator, int timeoutInSeconds) {
        // ... existing code ...
    }
} 