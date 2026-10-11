# PromptPilot Selenium Tests

A beginner QA portfolio project using **Java, Selenium WebDriver, TestNG and Maven** to test the real [PromptPilot](https://github.com/Guddu46/PromptPilot) React frontend. The UI currently uses the name **ProPrompt** in some places.

The suite contains 14 automated tests for public navigation, guest routing and client-side login/signup validation. It does not claim full end-to-end authentication or AI coverage. See [test cases](docs/TEST_CASES.md) and [verification results](docs/RESULTS.md).

## Why this structure?

This follows the familiar `testBase`, `pageObjects` and `testCases` packages from [my OpenCart project](https://github.com/Guddu46/opencart). `BaseClass` opens/closes Chrome; `AuthPage` owns repeated form actions and locators; test classes contain steps and assertions. Explicit waits wait for an actual UI condition instead of `Thread.sleep`. There is no Excel framework, Grid, reporting library or API fixture to learn before writing a first test.

```text
src/test/java/
  testBase/BaseClass.java       browser setup, configuration, failure screenshots
  pageObjects/AuthPage.java     shared login/signup form actions
  testCases/LoginTest.java      five login UI tests
  testCases/SignupTest.java     five signup UI tests
  testCases/NavigationTest.java four route/navigation tests
src/test/resources/config.properties
testng.xml                     list of test classes to run
pom.xml                        dependencies and Maven plugins
docs/TEST_CASES.md              steps, data and expected results
docs/RESULTS.md                 what was actually executed
```

## Prerequisites

- JDK 17 or later (a JDK, not only a JRE), with `JAVA_HOME` configured.
- Maven 3.9+, available as `mvn` in your terminal.
- Google Chrome. Selenium Manager downloads/resolves its matching driver automatically; initial execution needs internet access.
- Node.js 22.12+ and npm for the application frontend.
- Git, plus access to the source application repository.

Check `java -version`, `mvn -version`, `node --version` and `npm --version` before starting.

## 1. Start the application in a separate folder

```sh
git clone https://github.com/Guddu46/PromptPilot.git
cd PromptPilot
git checkout 9624e5b0c5089aa3e1ec285d88f188d6d80f5ca4
cd promptpilot-frontend
npm install --legacy-peer-deps
npm run dev -- --host 127.0.0.1 --port 5173 --strictPort
```

Keep this terminal running. Open `http://127.0.0.1:5173/login` to confirm the page loads. The commit is pinned here so the documented locators match the inspected application. `--legacy-peer-deps` handles the application's existing dependency compatibility issue. The application has no tracked npm lockfile at this revision, so exact transitive frontend dependency versions can vary.

No backend, Supabase credentials or Gemini key is required for this suite. Every submitted form deliberately includes an invalid field, so client validation stops submission. No account is created and no credits are consumed. Use a local checkout; the test runner rejects non-local base URLs.

## 2. Run the tests

In another terminal, outside the application folder:

```sh
git clone https://github.com/Guddu46/promptpilot-selenium-tests.git
cd promptpilot-selenium-tests
mvn clean test
```

Chrome is headless by default. To watch it:

```sh
mvn test -Dheadless=false
```

PowerShell users can quote a whole property argument if needed:

```powershell
mvn test "-DbaseUrl=http://127.0.0.1:5173" "-Dheadless=false"
mvn test "-Dtest=LoginTest"
mvn test "-Dtest=LoginTest#emptyLoginShowsBothErrors"
```

You can also edit `src/test/resources/config.properties`. `baseUrl` and `headless` support command-line overrides. `waitSeconds` is read from the file. Do not run tests in parallel: the suite is intentionally sequential and simple. Each method uses a fresh Chrome profile to isolate browser state.

## Eclipse instructions

1. Install Eclipse IDE for Java Developers, configure your JDK under **Window > Preferences > Java > Installed JREs**, and select it.
2. Choose **File > Import > Maven > Existing Maven Projects**.
3. Browse to this test repository (the folder containing `pom.xml`), select it and finish.
4. If dependencies show errors, right-click the project and select **Maven > Update Project**.
5. Start the frontend in a separate terminal using the instructions above.
6. Right-click the project, choose **Run As > Maven build...**, enter `clean test` in Goals, then Run. This does not require the Eclipse TestNG plugin.
7. Optionally install the TestNG Eclipse plugin; then right-click `testng.xml` and choose **Run As > TestNG Suite**. Both paths read `config.properties`.

## Reading the results

Maven prints the total tests, failures and skips. Open `target/surefire-reports/index.html` for TestNG's report, or inspect its XML/text reports. Failed browser tests also save a PNG under `target/screenshots/`. Generated reports and screenshots are ignored by Git.

If all tests fail before any page opens, check Chrome, driver download connectivity and `JAVA_HOME`. If pages cannot load, check the Vite terminal and port. If a locator times out, compare the current application markup with the pinned revision before changing the test. A timeout or setup failure is a failure to investigate, not a passing test. SLF4J's missing-provider warning does not prevent this suite from running.

## Learning walkthrough

Start with `emptyLoginShowsBothErrors`: it opens `/login`, creates an `AuthPage`, clicks submit and asserts the two exact validation messages. In `AuthPage`, `By.cssSelector` locates inputs by placeholders inspected in `AuthForm.tsx`. Mantine generates input IDs, so `errorFor` follows the input's `aria-describedby` to its error rather than hardcoding those IDs. `WebDriverWait` waits for visibility/clickability or validation state. The assertion remains in the test so its expected behavior is obvious.

Boundary tests intentionally keep a second field invalid. For example, a three-character username with a five-character password checks that the username passes **client validation**, while the password prevents a login request. It does not prove the username belongs to a real account.

To add a test: inspect the app behavior and markup, add a clearly named `@Test` method to a listed class, reuse `AuthPage` actions where useful, assert an observable outcome, document it in the case table, then run it. Add a new class to `testng.xml` if needed.

## Coverage limits and future practice

Not automated here: successful signup/login, invalid credentials returned by a real backend, email confirmation, logout, prompt generation, library save/delete, payments, extension behavior, security, accessibility or cross-browser/mobile testing. Guest redirection only checks the frontend route; it is not evidence of API authorization security. Pricing currently displays a coming-soon message, not purchasable plans.

Future backend journeys need a dedicated test backend/Supabase project and disposable accounts. Generation also needs an explicit decision about provider access and credit consumption. These prerequisites were not supplied and those flows were not run. Never put passwords, tokens or API keys in this repository.

## Source references

- PromptPilot revision: `9624e5b0c5089aa3e1ec285d88f188d6d80f5ca4`; inspected `AuthForm.tsx`, `LoginPage.tsx`, `SignupPage.tsx`, `ProtectedRoute.tsx`, `Navbar.tsx`, `PricingPage.tsx` and `App.tsx` under `promptpilot-frontend/src`.
- OpenCart reference: `master` at `e4d365a8345363b09326f0645404e9d78780413a`; inspected its Maven configuration, BaseClass, Loginpage and login test. Selenium 4.50.0 and TestNG 7.11.0 match that reference.
- This repository contains only the test project; the PromptPilot application remains separate.
