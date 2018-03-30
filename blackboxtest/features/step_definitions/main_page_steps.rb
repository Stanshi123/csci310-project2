Given(/^User has navigated to localhost and login successfully$/) do
  visit "http://localhost:8080/Egalloc/mainpage.jsp"

end

Given(/^User is on main Page$/) do
  #Nothing, since localhost automatically redirects to main page
end

Then(/^there is a topic input box$/) do
  page.should have_selector("input[type=text id=topic-input]")
end

Then(/^placeholder text of input box is "([^"]*)"$/) do |arg1|
  page.should have_selector("input[type=text placeholder=arg1]")
end


Then(/^there is a collage width pixel input$/) do
  page.should have_selector("input[type=text id=collage-width-input]")
end

Then(/^there is a collage height pixel input$/) do
  page.should have_selector("input[type=text id=collage-height-input]")
end

Then(/^there is a radio button of filter option No filter$/) do
  page.should have_selector("input[type=radio name=filter value=No filter]")
end

Then(/^there is a radio button of filter option Black and White$/) do
  page.should have_selector("input[type=radio name=filter value=Black and White]")
end

Then(/^there is a radio button of filter option Grayscale$/) do
  page.should have_selector("input[type=radio name=filter value=Grayscale]")
end

Then(/^there is a radio button of filter option Sepia$/) do
  page.should have_selector("input[type=radio name=filter value=Sepia]")
end

Then(/^there is a radio button of border option On$/) do
  page.should have_selector("input[type=radio name=border value=On]")
end

Then(/^there is a radio button of border option Off$/) do
  page.should have_selector("input[type=radio name=border value=Off]")
end

Then(/^there is a radio button of rotation option On$/) do
  page.should have_selector("input[type=radio name=rotation value=On]")
end

Then(/^there is a radio button of rotation option Off$/) do
  page.should have_selector("input[type=radio name=rotation value=Off]")
end

Then(/^there is a save to history button$/) do
  pexpect(page.find_by_id("save-collage-button").native.text).to eq "Save Collage"
end

Then(/^there is an export collage button$/) do
  pexpect(page.find_by_id("export-collage-button").native.text).to eq "Export Collage"
end

Then(/^there is a build collage button$/) do
  pexpect(page.find_by_id("build-collage-button").native.text).to eq "Build Collage"
end



