package utils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static void initReports() {
        try {
            String reportPath = System.getProperty("user.dir") + "/test-output/extent-reports/ExtentReport_" + ".html";
            
            File reportDir = new File(System.getProperty("user.dir") + "/test-output/extent-reports");
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Automation Test Report");
            spark.config().setReportName("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createTest(String testName) {
        try {
            test.set(extent.createTest(testName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void logTestStatus(com.aventstack.extentreports.Status status, String message) {
        try {
            getTest().log(status, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addScreenshot(String screenshotPath) {
        try {
            getTest().addScreenCaptureFromPath(screenshotPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void flushReports() {
        try {
            extent.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 