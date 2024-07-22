Feature: The landing page can be viewed by anyone
  Scenario: client makes call to the home page
    When the client calls /
    Then the client receives status code of 200
    And the client receives body containing "Welcome!"