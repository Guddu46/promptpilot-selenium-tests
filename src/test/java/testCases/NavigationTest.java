package testCases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AuthPage;
import testBase.BaseClass;

public class NavigationTest extends BaseClass {
    @Test
    public void dashboardRedirectsGuestToLogin() {
        driver.get(baseUrl + "/dashboard");
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/login"));
        Assert.assertEquals(new AuthPage(driver, wait).heading(), "Welcome Back");
    }

    @Test
    public void generateButtonSendsGuestToLogin() {
        driver.get(baseUrl + "/pricing");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//header//button[normalize-space()='Generate']"))).click();
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/login"));
        Assert.assertEquals(new AuthPage(driver, wait).heading(), "Welcome Back");
    }

    @Test
    public void pricingLinkShowsComingSoonPage() {
        driver.get(baseUrl + "/login");
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Pricing"))).click();
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/pricing"));
        Assert.assertEquals(wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))).getText(), "Pricing");
        Assert.assertTrue(driver.findElement(By.tagName("main")).getText().contains("Pricing page coming soon..."));
    }

    @Test
    public void getStartedOpensLogin() {
        driver.get(baseUrl + "/pricing");
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Get Started"))).click();
        wait.until(ExpectedConditions.urlToBe(baseUrl + "/login"));
        Assert.assertEquals(new AuthPage(driver, wait).heading(), "Welcome Back");
    }
}
