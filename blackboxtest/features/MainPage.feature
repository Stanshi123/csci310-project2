Feature: Functionality and display on main page.
  Main web page has a topic input box, collage shape input box, collage options, save to history button, export collage button, build collage button, and collage gallery. 
 
Background:
    Given User has navigated to localhost and login successfully
    And User is on main Page

  Scenario: Requirements on topic input box
		Then there is a topic input box

  Scenario: Requirements on collage dimension input box
		Then there is a collage width pixel input
		And there is a collage height pixel input

  Scenario: Requirements on collage options
		Then there is a radio button of filter option No filter
		And there is a radio button of filter option Black and White
		And there is a radio button of filter option Grayscale
		And there is a radio button of filter option Sepia

		And there is a radio button of border option On
		And there is a radio button of border option Off

		And there is a radio button of rotation option On
		And there is a radio button of rotation option Off

  Scenario: Requirements on save to history button
		Then there is a save to history button

  Scenario: Requirements on view history button
		Then there is a view history button

  Scenario: Requirements on export collage button. 
		Then there is an export collage button

  Scenario: Requirements on build collage button. 
		Then there is a build collage button

  Scenario: Requirements on collage gallery
		Given the User has saved previously built collages
		Then the collage gallery should show previously built collages corresponding to the User

  Scenario: User is building a collage
  		Given the user is building a collage
  		Then an animation of loading symbol should show up

  Scenario: User failed to build a collage
  		Given the User failed to build a collage
  		Then the main collage space should display an error message "Insufficient number of images found."

  Scenario: Clicking on Export Collage Button allows user to download of collage
  	When Export Collage Button is clicked
  	Then User is able to download PNG of Collage
  	
  Scenario: Size of exported image should be same as currently displayed collage
  	When Export Collage Button is clicked
  	And Image is successfully exported
  	Then Size of Downloaded image is the same as Currently displayed collage