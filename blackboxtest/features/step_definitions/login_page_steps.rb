Given(/^User has navigated to localhost and on the login page$/) do
  visit "http://localhost:8080/Egalloc/loginspage.jsp"
end

Then(/^there is a username input box$/) do
  page.should have_selector("input[type=text id=username-input]")
end

Then(/^there is a password input box$/) do
  page.should have_selector("input[type=text id=password-input]")
end

Then(/^there is a login button$/) do
  page.should have_selector("button[id=login-button]")
end

Then(/^there is a sign\-up button$/) do
  page.should have_selector("button[id=signup-button]")
end

