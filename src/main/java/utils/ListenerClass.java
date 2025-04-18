package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ListenerClass implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        try {
            // Take screenshot and get the path
            ScreenshotUtil screenshotUtil = new ScreenshotUtil();
            String screenshotPath = screenshotUtil.takeScreenshot(result.getName());
            
            // Log failure in Extent Report
            if (ExtentReportManager.getTest() != null) {
                // Log the error message
                ExtentReportManager.getTest().log(Status.FAIL, "Test Failed: " + result.getThrowable().getMessage());
                
                // Add screenshot if available
                if (screenshotPath != null && new File(screenshotPath).exists()) {
                    try {
                        // Use the helper method that combines logging and screenshot
                        ExtentReportManager.addScreenshotWithLog(
                            Status.FAIL, 
                            "Screenshot at failure point", 
                            screenshotPath
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        if (ExtentReportManager.getTest() != null) {
            ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped");
        }
    }
}
