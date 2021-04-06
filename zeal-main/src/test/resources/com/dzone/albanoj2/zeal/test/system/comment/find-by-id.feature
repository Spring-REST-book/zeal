Feature: Find comment by ID
  I want to find a comment by its ID

  Scenario: Retrieve empty comment by ID
    Given I have not posted any comments
    When I retrieve a comment with ID "foo"
    Then I receive a comment response status code of 404
    
  Scenario: Retrieve non-matching comment by ID
    Given the following comments exist:
    	| ID  | Article ID | Content      |
      | foo | article1   | Some comment |
    When I retrieve a comment with ID "bar"
    Then I receive a comment response status code of 404
    
  Scenario: Retrieve matching comment by ID
    Given the following comments exist:
    	| ID  | Article ID | Content       |
      | foo | article1   | Some comment  |
      | bar | article1   | Other comment |
    When I retrieve a comment with ID "foo"
    Then I receive a comment response status code of 200
    And I receive the following comments in the response body:
    	| ID  | Article ID | Content       |
      | foo | article1   | Some comment  |