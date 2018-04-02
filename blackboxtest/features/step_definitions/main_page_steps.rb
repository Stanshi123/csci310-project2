Given(/^User has navigated to localhost and login successfully$/) do
  visit "http://localhost:8080/Sprint1/mainpage.jsp"
end

Given(/^User is on main Page$/) do
  #Nothing, since localhost automatically redirects to main page
end

Then(/^there is a topic input box$/) do
  page.should have_selector("input[type=text][id=search-bar-input]")
end

Then(/^there is a collage width pixel input$/) do
  page.should have_selector("input[type=text][id=collage-width-input]")
end

Then(/^there is a collage height pixel input$/) do
  page.should have_selector("input[type=text][id=collage-height-input]")
end

Then(/^there is a radio button of filter option No filter$/) do
  page.should have_selector("input[type=radio][name=filter][id=no-filter]")
end

Then(/^there is a radio button of filter option Black and White$/) do
  page.should have_selector("input[type=radio][name=filter][id=black-and-white-filter]")
end

Then(/^there is a radio button of filter option Grayscale$/) do
  page.should have_selector("input[type=radio][name=filter][id=grayscale-filter]")
end

Then(/^there is a radio button of filter option Sepia$/) do
  page.should have_selector("input[type=radio][name=filter][id=sepia-filter]")
end

Then(/^there is a radio button of border option On$/) do
  page.should have_selector("input[type=radio][name=border][value=On]")
end

Then(/^there is a radio button of border option Off$/) do
  page.should have_selector("input[type=radio][name=border][value=Off]")
end

Then(/^there is a radio button of rotation option On$/) do
  page.should have_selector("input[type=radio][name=rotation][value=On]")
end

Then(/^there is a radio button of rotation option Off$/) do
  page.should have_selector("input[type=radio][name=rotation][value=Off]")
end

Then(/^there is a save to history button$/) do
  expect(page.find_by_id("search-bar-save-history").native.text).to eq "Save to History"
end

Then(/^there is a view history button$/) do
  expect(page.find_by_id("search-bar-view-history").native.text).to eq "View History"
end

Then(/^there is an export collage button$/) do
  expect(page.find_by_id("search-bar-export").native.text).to eq "Export Collage"
end

Then(/^there is a build collage button$/) do
  expect(page.find_by_id("search-bar-submit").native.text).to eq "Build Collage"
end



