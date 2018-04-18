Given(/^User has navigated to localhost and login successfully1$/) do
  visit "https://localhost:8443/index.jsp"
  fill_in('username', :with => "USC")
  fill_in('password', :with => "USC")
  page.find_by_id("loginButton").click()
  sleep(5.to_i);
end

Given(/^User is on main Page1$/) do
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

Then(/^there is an export collage button-PNG$/) do
  expect(page.find_by_id("search-bar-export-png").native.text).to eq "Export Collage (PNG)"
end

Then(/^there is an export collage button-PDF$/) do
  expect(page.find_by_id("search-bar-export-pdf").native.text).to eq "Export Collage (PDF)"
end

Then(/^there is a build collage button$/) do
  expect(page.find_by_id("search-bar-submit").native.text).to eq "Build Collage"
end

Given (/^the User has saved previously built collages$/) do
  fill_in('search-bar-input', :with => "USC")
  fill_in('shape-input', :with => "USC")
  page.find_by_id("search-bar-submit").click()
  sleep(30.to_i)
  page.find_by_id("search-bar-save-history").click()
  sleep(5.to_i)
end

Then (/^the collage gallery should show previously built collages corresponding to the User$/) do
  page.find_by_id("collage-1", visible: true)
end

Given (/^the User is building a collage$/) do
  fill_in('search-bar-input', :with => "Stanford")
  fill_in('shape-input', :with => "Stanford")
  page.find_by_id("search-bar-submit").click()
end

Then (/^an animation of loading symbol should show up$/) do
  page.find_by_id("animation", visible: false)
  sleep(30.to_i)
end

Given (/^the User failed to build a collage$/) do
  fill_in('search-bar-input', :with => "aiofjalksdjf;lwaejf;sdifja;weakfldsjvioc")
  fill_in('shape-input', :with => "USC")
  page.find_by_id("search-bar-submit").click()
  sleep(30.to_i)
end

Then (/^the main collage space should display an error message "Insufficient number of images found."$/) do
  expect(page.find_by_id("error-message").native.text).to eq "Insufficient number of images found."
end

When(/^C$/) do
  page.find_by_id("search-bar-export-png").click()
end

Then(/^User is able to download PNG of Collage$/) do
  # user should find the collage downloaded to their machine
end

=begin
When (/^Image is successfully exported$/) do
end
Then(/^Size of Downloaded image is the same as Currently displayed collage$/) do
end
=end

Given("the user is building a collage") do
   # Write code here that turns the phrase above into concrete actions
end

When("Export Collage-PNG Button is clicked") do
   # Write code here that turns the phrase above into concrete actions
end

When("Export Collage Button is clicked") do
   # Write code here that turns the phrase above into concrete actions
end

When("Image is successfully exported") do
   # Write code here that turns the phrase above into concrete actions
end

Then("Size of Downloaded image is the same as Currently displayed collage") do
   # Write code here that turns the phrase above into concrete actions
end
