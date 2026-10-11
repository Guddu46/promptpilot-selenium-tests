package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

// Login and signup use the same AuthForm component in PromptPilot.
public class AuthPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By identity = By.cssSelector("input[placeholder='your@email.com']");
    private final By password = By.cssSelector("input[placeholder='Your password']");
    private final By username = By.cssSelector("input[placeholder='Your username']");
    private final By submit = By.cssSelector("form button[type='submit']");

    public AuthPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private void enter(By locator, String value) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(locator));
        field.clear();
        field.sendKeys(value);
    }

    public void enterIdentity(String value) { enter(identity, value); }
    public void enterPassword(String value) { enter(password, value); }
    public void enterUsername(String value) { enter(username, value); }
    public void submit() { wait.until(ExpectedConditions.elementToBeClickable(submit)).click(); }
    public String heading() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2"))).getText();
    }
    public String errorFor(String placeholder) {
        // Mantine connects each input to its error using aria-describedby; generated IDs are not hardcoded.
        WebElement input = driver.findElement(By.cssSelector("input[placeholder='" + placeholder + "']"));
        // TextInput uses aria-invalid; PasswordInput uses data-invalid.
        wait.until(d -> isInvalid(input));
        String errorId = input.getDomAttribute("aria-describedby");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(errorId))).getText();
    }
    public boolean hasError(String placeholder) {
        return isInvalid(driver.findElement(By.cssSelector("input[placeholder='" + placeholder + "']")));
    }
    private boolean isInvalid(WebElement input) {
        return "true".equals(input.getDomAttribute("aria-invalid"))
                || input.getDomAttribute("data-invalid") != null;
    }
    public void followLink(String text) {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(text))).click();
    }
}
