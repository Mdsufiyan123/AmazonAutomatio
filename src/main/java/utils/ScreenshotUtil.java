package utils;

import org.testng.ITestListener;
import base.BaseTest;
import org.openqa.selenium.OutputType;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtil extends BaseTest implements ITestListener {
   
    /**
     * Takes a screenshot and saves it to both a persistent location and the test-output directory
     * @param testName The name of the test
     * @return The absolute path to the screenshot file
     */
    public String takeScreenshot(String testName){
        try {
            WebDriver driver = getDriver();
            
            if (driver == null) {
                return null;
            }
            
            if (!(driver instanceof TakesScreenshot)) {
                return null;
            }
            
            // Create timestamp for unique filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            
            // Get base directory
            String baseDir = System.getProperty("user.dir");
            
            // Set up directories
            File screenshotsDir = new File(baseDir, "screenshots");
            File testOutputDir = new File(baseDir, "test-output/screenshots");
            
            // Ensure directories exist
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }
            
            if (!testOutputDir.exists()) {
                testOutputDir.mkdirs();
            }
            
            // Take screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
            // Save to persistent location
            File persistentFile = new File(screenshotsDir, fileName);
            FileUtils.copyFile(screenshot, persistentFile);
            
            // Save to test-output for Extent Reports
            File reportFile = new File(testOutputDir, fileName);
            FileUtils.copyFile(screenshot, reportFile);
            
            // Return the absolute path to the screenshot
            return persistentFile.getAbsolutePath();
            
        } catch (WebDriverException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

