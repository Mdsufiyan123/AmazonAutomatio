package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {
	
	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest>	test = new ThreadLocal<>();
	
	public static ExtentReports getInstance() {
		
		if(extent ==null) {
			ExtentSparkReporter	spark = new ExtentSparkReporter("test-output/extent-reports/ExtentReport.html");
			extent = new ExtentReports();
			extent.attachReporter(spark);
		}
		return extent;
	}
	
	public static void startTest(String testName) {
		ExtentTest newTest = getInstance().createTest(testName);
		test.set(newTest);
		
	}
	
	public static ExtentTest getTest() {
		return test.get();
	}
	
	
	public static void flushReport() {
        if (extent != null) {
            extent.flush(); // Ensure report is saved
          
    }
	
	}
}
