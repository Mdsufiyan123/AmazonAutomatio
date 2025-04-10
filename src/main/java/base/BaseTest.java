package base;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Invalid Browser Name: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialize WebDriverWait

        driver.get("https://amazon.in");
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeSuite
    public void setUpExtendReports() {
        // Dynamically get the user directory for report folder path
        String reportFolderPath = System.getProperty("user.dir") + "/test-output/extent-reports/";
        File reportFolder = new File(reportFolderPath);
        
        // Create the report folder if it doesn't exist
        if (!reportFolder.exists()) {
            reportFolder.mkdirs();
        }
        
        // Clear old report files in the folder (optional)
        for (File file : reportFolder.listFiles()) {
            if (file.isFile()) {
                file.delete(); // Delete each file in the folder
            }
        }
        
        // Initialize ExtentReports
        ExtentReportManager.getInstance();
    }

    @AfterSuite
    public void flushExtentReports() {
        // Ensure the report is flushed and saved after all tests
        ExtentReportManager.flushReport();
    }
}
