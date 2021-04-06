Feature: Find all comments
  I want to find all comments

  Scenario: Retrieve empty comments
    Given I have not posted any comments
    And the following articles exist:
      | ID       | Title      | Content               |
      | article1 | Some Title | Something interesting |
    When I retrieve all comments for article ID "article1"
    Then I find no comments
    And I receive a comment response status code of 200
    
  Scenario: Retrieve existing comments
    Given the following comments exist:
    	| ID  | Article ID | Content       |
      | foo | article1   | Some comment  |
      | bar | article1   | Other comment |
    And the following articles exist:
      | ID       | Title      | Content               |
      | article1 | Some Title | Something interesting |
    When I retrieve all comments for article ID "article1"
    Then I receive a comment response status code of 200
    And I receive the following comments in the response body:
    	| ID  | Article ID | Content       |
      | foo | article1   | Some comment  |
      | bar | article1   | Other comment |
      
  Scenario: Retrieve comments when article does not exist
  	Given I have not posted any comments
    And I have not posted any articles
    When I retrieve all comments for article ID "article1"
    Then I receive a comment response status code of 404