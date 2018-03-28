Feature: Functionality and display on main page.
  Main web page has a topic input box, collage shape input box, collage options, save to history button, and build collage button. 
 
Background:
    Given User has navigated to localhost and login successfully
    And User is on main Page

  Scenario: Requirements on topic input box
	Then there is a topic input box
	Then placeholder text of input box is "Enter topics"

  Scenario: Requirements on collage shape input box
	Then there is a collage shape input box

  Scenario: Requirements on collage filter options
	Then there is a collage width pixel input
	And there is a collage height pixel input

	And there is a radio button of filter option "No filter"
	And there is a radio button of filter option "Black and White"
	And there is a radio button of filter option "Grayscale"
	And there is a radio button of filter option "Sepia"

	And there is a radio button of border option "On"
	And there is a radio button of border option "Off"

	And there is a radio button of rotation option "On"
	And there is a radio button of rotation option "Off"

	

  Scenario: Requirements on save to history button
	Then there is a save to history button

  Scenario: Requirements on build collage button. 
	Then there is a build collage button

	When User enters text "usc" in input box
        Then Prompt text disappears
	
	
	When User enters text "usc" in input box
        And User presses enter key
        Then Build Collage process is triggered
 

