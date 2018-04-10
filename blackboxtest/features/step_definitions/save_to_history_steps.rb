Given(/^User has navigated to localhost and login successfully$/) do
  visit "http://localhost:8080/Sprint1/mainpage.jsp"
end

And (/^User is on main Page$/) do
  #Nothing, since localhost automatically redirects to main page
end

And (/^User has built a collage$/) do
	fill_in('search-bar-input', :with => "USC")
	fill_in('shape-input', :with => "USC")
	page.find_by_id("search-bar-submit").click()
	sleep(10.to_i)
	page.find_by_id("collage-area").text.should != ''
end

When (/^User clicks on Save to History button$/) do
	page.find_by_id("saveButton").click()
end

Then (/^the collage should appear in the gallery section$/) do
	page.find_by_id("gallery").text.should != ''
end