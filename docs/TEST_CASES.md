# Automated test cases

 No successful form submission or backend response is part of these cases.

| ID | Java test method | Steps and data | Expected outcome |
|---|---|---|---|
| PP-01 | LoginTest.emptyLoginShowsBothErrors | Open `/login`; leave both fields blank; submit | Identity: `Email or username must be at least 3 characters`; password: `Password must be at least 6 characters` |
| PP-02 | LoginTest.twoCharacterIdentityIsRejected | Login with identity `ab`, password `dummy123`; submit | Identity error above; no password error |
| PP-03 | LoginTest.threeCharacterUsernamePassesIdentityValidation | Login with identity `abc`, password `12345`; submit | Password error; no identity error; this does not authenticate |
| PP-04 | LoginTest.sixCharacterPasswordPassesPasswordValidation | Login with identity `ab`, password `123456`; submit | Identity error; no password error |
| PP-05 | LoginTest.signupLinkOpensSignup | Open `/login`; click `Sign up` | URL `/signup`; heading `Create Your Account` |
| PP-06 | SignupTest.emptySignupShowsThreeErrors | Open `/signup`; leave fields blank; submit | `Username must be at least 3 characters`, `Invalid email`, `Password must be at least 6 characters` |
| PP-07 | SignupTest.emailWithoutAtSignIsRejected | Signup username `qauser`, email `invalid-email`, password `dummy123`; submit | `Invalid email`; no username or password error |
| PP-08 | SignupTest.usernameIsTrimmedBeforeLengthCheck | Signup username ` ab `, email `learner@example.com`, password `dummy123`; submit | Username error; email has no error |
| PP-09 | SignupTest.threeCharacterUsernamePassesValidation | Signup username `abc`, email `learner@example.com`, password `12345`; submit | Password error; no username/email error; account is not created |
| PP-10 | SignupTest.loginLinkOpensLogin | Open `/signup`; click `Log in` | URL `/login`; heading `Welcome Back` |
| PP-11 | NavigationTest.dashboardRedirectsGuestToLogin | Directly open `/dashboard` without auth token | Redirect to `/login`; heading `Welcome Back` |
| PP-12 | NavigationTest.generateButtonSendsGuestToLogin | Open `/pricing`; click header `Generate` | URL `/login`; heading `Welcome Back` |
| PP-13 | NavigationTest.pricingLinkShowsComingSoonPage | Open `/login`; click `Pricing` | URL `/pricing`; heading `Pricing`; main content includes `Pricing page coming soon...` |
| PP-14 | NavigationTest.getStartedOpensLogin | Open `/pricing`; click `Get Started` | URL `/login`; heading `Welcome Back` |


