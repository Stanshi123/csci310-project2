Given(/^User has navigated to localhost and login successfully$/) do
  visit "http://localhost:8080/Sprint1/mainpage.jsp"
end

And (/^User is on main Page$/) do
  #Nothing, since localhost automatically redirects to main page
end

And (/^User has saved a collage to history$/) do
	fill_in('search-bar-input', :with => "USC")
	fill_in('shape-input', :with => "USC")
	page.find_by_id("search-bar-submit").click()
	sleep(20.to_i)
	page.find_by_id("saveButton").click()
	fill_in('search-bar-input', :with => "UCLA")
	fill_in('shape-input', :with => "UCLA")
	page.find_by_id("search-bar-submit").click()
	sleep(20.to_i)
	page.find_by_id("saveButton").click()
end

When (/^the User clicks on a collage in the gallery$/) do
	page.find_by_id("collage-USC").click()
end

Then (/^the clicked collage should appear on the main space$/) do
	page.find_by_id("main-collage-title").text == "Collage for topic USC"
end

When (/^the User clicks on Delete Collage button$/) do
	page.find_by_id("delete-USC-button").click()
end

Then (/^the related collage should disappear from the main page$/) do
	page.should have_no_content("collage-USC")
end

When (^/the User has reloaded the page$/) do
	driver.navigate.refresh
end

When (^/creates a new session$/) do
	page.driver.browser.close
	visit "http://localhost:8080/Sprint1/mainpage.jsp"
end

Given (/^the User has built a collage$/) do
	fill_in('search-bar-input', :with => "UCSD")
	fill_in('shape-input', :with => "UCSD")
	page.find_by_id("search-bar-submit").click()
	sleep(20.to_i)
end

Given (/^the User did not save the previously built collage$/) do

end

Then (/^the previously built collage should not appear on the main page$/) do
	page.should have_no_content("collage-UCSD")
end

Given (/^the User has built another collage$/) do
	fill_in('search-bar-input', :with => "UCI")
	fill_in('shape-input', :with => "UCI")
	page.find_by_id("search-bar-submit").click()
	sleep(20.to_i)
end

Then (/^the previously built collage should not be saved$/) do
	page.driver.browser.close
	visit "http://localhost:8080/Sprint1/mainpage.jsp"
	page.should have_no_content("collage-UCSD")	
end