package testBase;

import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseClass {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl;

    @BeforeMethod
    public void setup() throws Exception {
        Properties config = new Properties();
        try (InputStream file = getClass().getResourceAsStream("/config.properties")) {
            config.load(file);
        }
        baseUrl = System.getProperty("baseUrl", config.getProperty("baseUrl")).replaceAll("/+$", "");
        URI address = URI.create(baseUrl);
        // This learning suite is intended for a local app, never a production account.
        if (!"http".equals(address.getScheme()) ||
                !("localhost".equals(address.getHost()) || "127.0.0.1".equals(address.getHost()))) {
            throw new IllegalArgumentException("baseUrl must be a local http://localhost or http://127.0.0.1 URL");
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1440,1000");
        driver = new ChromeDriver(options); // Selenium Manager finds the matching driver.
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(config.getProperty("waitSeconds"))));
        // Every test gets a fresh browser, so cookies/storage cannot leak between tests.
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver == null) return;
        try {
            if (!result.isSuccess()) {
                Path folder = Path.of("target", "screenshots");
                Files.createDirectories(folder);
                Files.write(folder.resolve(result.getMethod().getMethodName() + ".png"),
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            }
        } catch (Exception error) {
            System.err.println("Could not save failure screenshot: " + error.getMessage());
        } finally {
            driver.quit();
            driver = null;
        }
    }
}
