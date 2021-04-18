Feature: Find all articles
  I want to find all articles

  Scenario: Retrieve empty articles
    Given I have not posted any articles
    When I retrieve all articles
    Then I find no articles
    And I receive an article response status code of 200
    
  Scenario: Retrieve existing articles
    Given the following articles exist:
      | ID  | Title            | Content                   |
      | foo | Some Title       | Something interesting     |
      | bar | A Second Article | Another interesting topic |
    When I retrieve all articles
    Then I receive an article response status code of 200
    And I receive the following articles in the response body:
      | ID  | Title            | Content                   |
      | foo | Some Title       | Something interesting     |
      | bar | A Second Article | Another interesting topic |