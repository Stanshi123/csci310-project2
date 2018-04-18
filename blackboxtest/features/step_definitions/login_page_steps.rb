Given(/^User has navigated to localhost and on the login page$/) do
  visit "https://localhost:8443/index.jsp"
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
  page.should have_selector("button[id=signUpButton]")
end

#scenario: user can register
Given (/^the User successfully registers$/) do
	fill_in('username', :with => "USC")
	fill_in('password', :with => "USC")
	page.find_by_id("signUpButton").click()
	sleep(5.to_i);
end

Then (/^User is redirected to mainpage.jsp L1$/) do
	#Nothing, since localhost automatically redirects to main page
end
#end scenario

#scenario: user fails to register
Given (/^the User enters an existing username$/) do
	visit "https://localhost:8443/index.jsp"
	fill_in('username', :with => "USC")
	fill_in('password', :with => "USC")
	page.find_by_id("signUpButton").click()
	sleep(1.to_i);
end

Then (/^an error message will show up1$/) do
	expect(page.find_by_id("error_msg").native.text).to eq "Failed to register. Please try another username"
end

#scenario: user logs in
Given (/^the User enters a correct username and password$/) do
	visit "https://localhost:8443/index.jsp"
	fill_in('username', :with => "USC")
	fill_in('password', :with => "USC")
	page.find_by_id("loginButton").click()
	sleep(5.to_i);
end

Then (/^the User will be redirected to mainpage.jsp L2$/) do
	#Nothing, since localhost automatically redirects to main page
end
#end scenario

#scenario: user fails to log in
Given (/^the User enters an incorrect username and password$/) do
	visit "https://localhost:8443/index.jsp"
	fill_in('username', :with => "USC")
	fill_in('password', :with => "ASD")
	page.find_by_id("loginButton").click()
	sleep(1.to_i);
end

Then (/^an error message will show up2$/) do
	expect(page.find_by_id("error_msg").native.text).to eq "Wrong username/password combination."
end
#end scenario