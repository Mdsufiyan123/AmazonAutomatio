package utils;

import org.testng.ITestListener;
import base.BaseTest;
import org.openqa.selenium.OutputType;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtil extends BaseTest implements ITestListener {
   
    public void takeScreenshot(String testName){
        try {
            // Get absolute path for screenshots directory - use a persistent location
            String baseDir = System.getProperty("user.dir");
            File screenshotsDir = new File(baseDir, "screenshots");
            
            // Ensure directory exists and is writable
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }
            
            if (!screenshotsDir.canWrite()) {
                return;
            }
            
            WebDriver driver = getDriver();
            
            if (driver == null) {
                return;
            }
            
            if (!(driver instanceof TakesScreenshot)) {
                return;
            }
            
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
            String fileName = testName + "_" + System.currentTimeMillis() + ".png";
            File destFile = new File(screenshotsDir, fileName);
            
            // Ensure parent directory exists
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            
            FileUtils.copyFile(screenshot, destFile);
            
            // Copy to test-output for Extent Reports (this one might get deleted)
            File testOutputDir = new File(baseDir, "test-output/screenshots");
            if (!testOutputDir.exists()) {
                testOutputDir.mkdirs();
            }
            File reportFile = new File(testOutputDir, fileName);
            FileUtils.copyFile(screenshot, reportFile);
            
        } catch (WebDriverException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

