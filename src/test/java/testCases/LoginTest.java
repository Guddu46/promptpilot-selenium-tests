package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AuthPage;
import testBase.BaseClass;

public class LoginTest extends BaseClass {
    private AuthPage openLogin() {
        driver.get(baseUrl + "/login");
        AuthPage page = new AuthPage(driver, wait);
        Assert.assertEquals(page.heading(), "Welcome Back");
        return page;
    }

    @Test
    public void emptyLoginShowsBothErrors() {
        AuthPage page = openLogin();
        page.submit();
        Assert.assertEquals(page.errorFor("your@email.com"), "Email or username must be at least 3 characters");
        Assert.assertEquals(page.errorFor("Your password"), "Password must be at least 6 characters");
    }

    @Test
    public void twoCharacterIdentityIsRejected() {
        AuthPage page = openLogin();
        page.enterIdentity("ab");
        page.enterPassword("dummy123");
        page.submit();
        Assert.assertEquals(page.errorFor("your@email.com"), "Email or username must be at least 3 characters");
        Assert.assertFalse(page.hasError("Your password"), "Six or more password characters should pass client validation");
    }

    @Test
    public void threeCharacterUsernamePassesIdentityValidation() {
        AuthPage page = openLogin();
        page.enterIdentity("abc");
        page.enterPassword("12345"); // Invalid password prevents an API request.
        page.submit();
        Assert.assertEquals(page.errorFor("Your password"), "Password must be at least 6 characters");
        Assert.assertFalse(page.hasError("your@email.com"), "Login accepts a username, not only an email");
    }

    @Test
    public void sixCharacterPasswordPassesPasswordValidation() {
        AuthPage page = openLogin();
        page.enterIdentity("ab"); // Invalid identity prevents an API request.
        page.enterPassword("123456");
        page.submit();
        Assert.assertEquals(page.errorFor("your@email.com"), "Email or username must be at least 3 characters");
        Assert.assertFalse(page.hasError("Your password"));
    }

    @Test
    public void signupLinkOpensSignup() {
        AuthPage page = openLogin();
        page.followLink("Sign up");
        wait.until(d -> d.getCurrentUrl().equals(baseUrl + "/signup"));
        page.waitForHeading("Create Your Account");
        Assert.assertEquals(page.heading(), "Create Your Account");
    }
}
