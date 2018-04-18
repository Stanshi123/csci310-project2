Given(/^User has navigated to localhost and login successfully3$/) do
  visit "https://localhost:8443/index.jsp"
  fill_in('username', :with => "USC")
  fill_in('password', :with => "USC")
  page.find_by_id("loginButton").click()
  sleep(5.to_i)
end

Given (/^User is on main Page3$/) do
  #Nothing, since localhost automatically redirects to main page
end

Given (/^User has built a collage$/) do
	fill_in('search-bar-input', :with => "UCLA")
	fill_in('shape-input', :with => "UCLA")
	page.find_by_id("search-bar-submit").click()
	sleep(30.to_i)
end

When("the User clicks on Save to History button") do
    page.find_by_id("search-bar-save-history").click()
end

Then (/^the collage should appear in the gallery section$/) do
	sleep(10.to_i)
	page.find_by_id("collage-2", visible: true)
end

Then(/^there is a delete button$/) do
	sleep(5.to_i);
	page.find_by_id("delete-button-2", visible: true)
end
