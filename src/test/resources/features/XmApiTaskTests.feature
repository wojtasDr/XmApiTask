Feature: API tests - API task

  @UseCase1
  Scenario: Check film with latest release date, its tallest character and the tallest character of all films
    When I get all films
    Then The response status code equals 200
    And Film title with latest release date is "Revenge of the Sith"
    And The tallest character in that film is is "Tarfful"
    And The tallest character in all Star Wars films is "Yarael Poof"

  @UseCase2
  Scenario: Verify /people response schema
    When I send GET /people request
    Then The response status code equals 200
    And People response json schema is correct
