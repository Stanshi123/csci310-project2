Feature: Functionality and display on main page.
  Login Page has a username input box, password input box, login button, and sign-up button. 
 
Background:
    Given User has navigated to localhost and on the login page

  Scenario: Requirements on login page
	Then there is a username input box
	And there is a password input box
	And there is a login button
	And there is a sign-up button

  Scenario: User can register
    Given the User successfully registers
    Then User is redirected to mainpage.jsp L1

  Scenario: User fails to register
    Given the User enters an existing username
    Then an error message will show up1

  Scenario: User logs in
    Given the User enters a correct username and password
    Then the User will be redirected to mainpage.jsp L2

  Scenario: User fails to log in
    Given the User enters an incorrect username and password
    Then an error message will show up2