# Verification results

Executed on **11 October 2026, 18:34 IST** against the actual local PromptPilot React frontend at application commit `9624e5b0c5089aa3e1ec285d88f188d6d80f5ca4`.

| Check | Actual outcome |
|---|---|
| Maven `test-compile` | Passed; five Java source files compiled with release 17 |
| Final Maven `clean test` | **14 tests, 0 failures, 0 errors, 0 skipped; BUILD SUCCESS** |
| Browser test duration | 26.33 seconds (Maven total 29.707 seconds) |
| Browser | Headless Chrome 154.0.8037.98; ChromeDriver 154.0.8037.92 |
| Java / Maven | JDK 24.0.2 / Maven 3.9.11 on Windows 11 |
| Frontend runtime | Node 22.14.0, npm 10.9.2, Vite 7.3.7 |
| Backend / API fixtures | Neither used; client validation and public routes only |
| Paid AI calls / account mutations | None performed |
| Eclipse execution / headed Chrome / other browsers | Instructions provided; not executed |

The first development run had four failures because the test helper checked `aria-invalid` for password fields. A second attempted correction had six failures because text fields and password fields use different attributes. Inspection of Mantine's implementation and failure screenshots confirmed the messages were visible. The final helper handles both `aria-invalid` (text input) and `data-invalid` (password input), then follows `aria-describedby` to the exact field error. The final clean run passed all cases. No application source change was needed.

Final Maven summary:

```text
Tests run: 14, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

The checked-in [execution summary](execution-summary.csv) lists each executed method and its observed result, extracted from the final Surefire XML. Raw local reports remain under `target/surefire-reports/` and are ignored by Git because they include machine-specific paths. Maven/JDK emitted an Unsafe deprecation warning and TestNG emitted an SLF4J no-provider warning; neither affected the successful run.

These results demonstrate frontend behavior only. Successful authentication, backend validation, Supabase persistence, generation, library actions, extension and payment behavior remain unverified. Running those journeys would require a separate test environment and designated accounts/provider configuration. The application's production build and its own unit tests were not part of this verification.
