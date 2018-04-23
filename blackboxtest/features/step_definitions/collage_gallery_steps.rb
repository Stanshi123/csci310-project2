Given(/^User has navigated to localhost and login successfully2$/) do
  visit "https://localhost:8443/index.jsp"
  fill_in('username', :with => "USC")
  fill_in('password', :with => "USC")
  page.find_by_id("loginButton").click()
  sleep(5.to_i);
end

Given (/^User is on main Page2$/) do
  #Nothing, since localhost automatically redirects to main page
end

Given (/^User has saved a collage to history$/) do
	fill_in('search-bar-input', :with => "USC")
	fill_in('shape-input', :with => "USC")
	page.find_by_id("search-bar-submit").click()
	sleep(30.to_i)
	page.find_by_id("search-bar-save-history").click()
	fill_in('search-bar-input', :with => "UCLA")
	fill_in('shape-input', :with => "UCLA")
	page.find_by_id("search-bar-submit").click()
	sleep(30.to_i)
	page.find_by_id("search-bar-save-history").click()
end

When (/^the User clicks on a collage in the gallery$/) do
	page.find_by_id("collage-3").click()
end

Then (/^the clicked collage should appear on the main space$/) do
	page.should have_content("Collage for topic USC, shape USC")
end

When (/^the User clicks on Delete Collage button$/) do
	page.find_by_id("delete-button-3").click()
end

Then (/^the related collage should disappear from the main page$/) do
	page.should have_no_content("collage-3")
end

When (/^the User has reloaded the page$/) do
    visit "https://localhost:8443/index.jsp"
end

Then (/^the previously deleted collage should not appear on the main page$/) do
	page.should have_no_content("collage-3")
end

Given (/^the User has built a collage$/) do
	fill_in('search-bar-input', :with => "UCSD")
	fill_in('shape-input', :with => "UCSD")
	page.find_by_id("search-bar-submit").click()
	sleep(30.to_i)
end

Given (/^the User did not save the previously built collage$/) do

end

Given (/^the User has built another collage$/) do
	fill_in('search-bar-input', :with => "UCI")
	fill_in('shape-input', :with => "UCI")
	page.find_by_id("search-bar-submit").click()
	sleep(30.to_i)
end

Then (/^the previously built collage should not appear on the main page$/) do
	page.should have_no_content("collage-5")
end

# final scenario
# Given (/^the User has built a collage$/) do
# defined above
# end

# Given (/^the User clicks on Save to History button$/) do
# defined in SaveToHistory.feature
# end

# Given (/^the User has built another collage$/) do
# defined above
# end

# Given (/^the User did not save the previously built collage$/) do
# defined above
# end

When (/^the User clicks on a collage in the gallery2$/) do
	page.find_by_id("collage-4").click()
end

Then (/^the clicked collage should appear on the main space2$/) do
	page.should have_content("Collage for topic UCLA, shape UCLA")
end
Then (/^the previously built collage should not be saved$/) do
	page.should have_no_content("collage-5")
end

Given (/^the user has saved two collages$/) do
	#Nothing, since the user has already saved several collages
end

Then (/^a horizontal scroll bar should appear on the main page$/) do
	page.find("#gallery").should have_css('overflow-x: scroll')
end




