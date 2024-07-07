Feature: The landing page can be viewed by anyone
  Scenario: client makes call to GET /landing
    When the client calls /landing
    Then the client receives status code of 200
    And the client receives body containing "Landing Page!"