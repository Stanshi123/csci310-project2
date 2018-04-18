Feature: User can save a built collage to the gallery

Background:
    Given User has navigated to localhost and login successfully3
    And User is on main Page3
    And User has built a collage

    Scenario: User clicks on Save to History button
    	When the User clicks on Save to History button
    	Then the collage should appear in the gallery section

    Scenario: Delete button exists
        Given User has saved a collage to history
        Then there is a delete button
