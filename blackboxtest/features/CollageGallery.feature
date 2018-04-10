Feature: User can view/delete previously saved collages

Background:
	Given User has navigated to localhost and login successfully
	And User is on main Page
	And User has saved a collage to history

	Scenario: User clicks on a previously saved collage
		When the User clicks on a collage in the gallery
		Then the clicked collage should appear on the main space

	Scenario: User deletes a previously saved collage
		When the User clicks on Delete Collage button
		Then the related collage should disappear from the main page

	Scenario: Deleted collage should not appear in future sessions
		When the User clicks on Delete Collage button
		And the User has reloaded the page
		Or creates a new session
		Then the previously deleted collage should not appear on the main page

	Scenario: Requirements for when collage is not saved to gallery
		Given the User did not save the previously built collage
		When the User has reloaded the page
		Or creates a new session
		Then the previously built collage should not appear on the main page

	Scenario: User builds a collage without saving previous collage
		Given the User has built a collage
		And the User did not save the previously built collage
		And the User has built a collage
		Then the previously built collage should not appear on the main page

	Scenario: User clicks on collage in gallery without saving previous collage
		Given the User has built a collage
		And the User did not save the previously built collage
		When the User clicks on a collage in the gallery
		Then the clicked collage should appear on the main space
		And the previously built collage should not be saved
		And the previously built collage should not appear on the main page