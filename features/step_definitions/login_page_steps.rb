Given(/^User has navigated to localhost and on the login page$/) do
  visit "http://localhost:8080/Sprint1/index.jsp"
end

Then(/^there is a username input box$/) do
  page.should have_selector("input[type=text][id=username]")
end

Then(/^there is a password input box$/) do
  page.should have_selector("input[type=password][id=password]")
end

Then(/^there is a login button$/) do
  page.should have_selector("button[id=loginButton]")
end

Then(/^there is a sign\-up button$/) do
  page.should have_selector("button[id=signup-button]")
end

