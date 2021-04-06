Feature: Find article by ID
  I want to find an article by its ID

  Scenario: Retrieve empty article by ID
    Given I have not posted any articles
    When I retrieve an article with ID "foo"
    Then I receive an article response status code of 404
    
  Scenario: Retrieve non-matching article by ID
    Given the following articles exist:
      | ID  | Title            | Content               |
      | foo | Some Title       | Something interesting |
    When I retrieve an article with ID "bar"
    Then I receive an article response status code of 404
    
  Scenario: Retrieve matching article by ID
    Given the following articles exist:
      | ID  | Title            | Content               |
      | foo | Some Title       | Something interesting |
    When I retrieve an article with ID "foo"
    Then I receive an article response status code of 200
    And I receive the following articles in the response body:
      | ID  | Title            | Content               |
      | foo | Some Title       | Something interesting |