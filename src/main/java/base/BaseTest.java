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

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected WebDriverWait wait;

    public WebDriver getDriver() {
        return driver.get();
    }

    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        WebDriver webDriver;
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (isHeadless) {
                options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
            }
            webDriver = new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) {
                options.addArguments("--headless");
            }
            webDriver = new FirefoxDriver(options);

        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            if (isHeadless) {
                options.addArguments("--headless");
            }
            webDriver = new EdgeDriver(options);

        } else {
            throw new IllegalArgumentException("Invalid Browser Name: " + browser);
        }

        driver.set(webDriver);
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().get("https://amazon.in");
    }

    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }

    @BeforeSuite
    public void setUpExtentReports() {
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
