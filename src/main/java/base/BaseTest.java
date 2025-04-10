package base;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
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
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (isHeadless) {
                options.addArguments("--headless", "--disable-gpu", "--window-size=1920x1080");
            }
            driver = new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) {
                options.addArguments("--headless");
            }
            driver = new FirefoxDriver(options);

        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            if (isHeadless) {
                options.addArguments("--headless");
            }
            driver = new EdgeDriver(options);

        } else {
            throw new IllegalArgumentException("Invalid Browser Name: " + browser);
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://amazon.in");
    }

    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeSuite
    public void setUpExtendReports() {
        String reportFolderPath = System.getProperty("user.dir") + "/test-output/extent-reports/";
        File reportFolder = new File(reportFolderPath);

        if (!reportFolder.exists()) {
            reportFolder.mkdirs();
        }

        for (File file : reportFolder.listFiles()) {
            if (file.isFile()) {
                file.delete();
            }
        }

        ExtentReportManager.getInstance();
    }

    @AfterSuite
    public void flushExtentReports() {
        ExtentReportManager.flushReport();
    }
}
