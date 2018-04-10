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
	sleep(10.to_i)
	page.find_by_id("saveButton").click()
	fill_in('search-bar-input', :with => "UCLA")
	fill_in('shape-input', :with => "UCLA")
	page.find_by_id("search-bar-submit").click()
	sleep(10.to_i)
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
	page.should have_no_content("collage-UCLA")
end