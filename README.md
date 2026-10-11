# PromptPilot Selenium Tests

A beginner QA portfolio project using **Java, Selenium WebDriver, TestNG and Maven** to test the real [PromptPilot](https://github.com/Guddu46/PromptPilot) React frontend. The UI currently uses the name **ProPrompt** in some places.

The suite contains 14 automated tests for public navigation, guest routing and client-side login/signup validation. It does not claim full end-to-end authentication or AI coverage. See [test cases](docs/TEST_CASES.md).

 structure

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
```


Use `mvn test` to run and watch the tests.
