package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import java.io.File;

public class ListenerClass implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        try {
            // Take screenshot
            ScreenshotUtil screenshotUtil = new ScreenshotUtil();
            screenshotUtil.takeScreenshot(result.getName());
            
            // Log failure in Extent Report
            if (ExtentReportManager.getTest() != null) {
                ExtentReportManager.getTest().log(Status.FAIL, "Test Failed: " + result.getThrowable().getMessage());
                
                // Try to use the screenshot from the persistent location first
                String timestamp = String.valueOf(System.currentTimeMillis());
                String fileName = result.getName() + "_" + timestamp + ".png";
                
                // Check both locations - persistent directory first, then test-output
                String baseDir = System.getProperty("user.dir");
                File persistentFile = new File(baseDir, "screenshots/" + fileName);
                File reportFile = new File(baseDir, "test-output/screenshots/" + fileName);
                
                if (persistentFile.exists()) {
                    ExtentReportManager.getTest().addScreenCaptureFromPath(persistentFile.getAbsolutePath());
                } else if (reportFile.exists()) {
                    ExtentReportManager.getTest().addScreenCaptureFromPath(reportFile.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
