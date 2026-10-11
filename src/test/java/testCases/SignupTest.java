package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AuthPage;
import testBase.BaseClass;

public class SignupTest extends BaseClass {
    private AuthPage openSignup() {
        driver.get(baseUrl + "/signup");
        AuthPage page = new AuthPage(driver, wait);
        Assert.assertEquals(page.heading(), "Create Your Account");
        return page;
    }

    @Test
    public void emptySignupShowsThreeErrors() {
        AuthPage page = openSignup();
        page.submit();
        Assert.assertEquals(page.errorFor("Your username"), "Username must be at least 3 characters");
        Assert.assertEquals(page.errorFor("your@email.com"), "Invalid email");
        Assert.assertEquals(page.errorFor("Your password"), "Password must be at least 6 characters");
    }

    @Test
    public void emailWithoutAtSignIsRejected() {
        AuthPage page = openSignup();
        page.enterUsername("qauser");
        page.enterIdentity("invalid-email");
        page.enterPassword("dummy123");
        page.submit();
        Assert.assertEquals(page.errorFor("your@email.com"), "Invalid email");
        Assert.assertFalse(page.hasError("Your username"));
        Assert.assertFalse(page.hasError("Your password"));
    }

    @Test
    public void usernameIsTrimmedBeforeLengthCheck() {
        AuthPage page = openSignup();
        page.enterUsername(" ab ");
        page.enterIdentity("learner@example.com");
        page.enterPassword("dummy123");
        page.submit();
        Assert.assertEquals(page.errorFor("Your username"), "Username must be at least 3 characters");
        Assert.assertFalse(page.hasError("your@email.com"));
    }

    @Test
    public void threeCharacterUsernamePassesValidation() {
        AuthPage page = openSignup();
        page.enterUsername("abc");
        page.enterIdentity("learner@example.com");
        page.enterPassword("12345"); // Keep one field invalid: no account is created.
        page.submit();
        Assert.assertEquals(page.errorFor("Your password"), "Password must be at least 6 characters");
        Assert.assertFalse(page.hasError("Your username"));
        Assert.assertFalse(page.hasError("your@email.com"));
    }

    @Test
    public void loginLinkOpensLogin() {
        AuthPage page = openSignup();
        page.followLink("Log in");
        wait.until(d -> d.getCurrentUrl().equals(baseUrl + "/login"));
        Assert.assertEquals(page.heading(), "Welcome Back");
    }
}
