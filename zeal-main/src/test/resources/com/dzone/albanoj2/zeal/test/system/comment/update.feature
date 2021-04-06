Feature: Update comment
  I want to update an comment

	Scenario: Update existing comment
    Given the following comments exist:
      | ID  | Article ID | Content         |
      | foo | article1   | Some comment    |
      | bar | article1   | Another comment |
    And the following articles exist:
    	| ID       | Title            | Content                   |
      | article1 | Some Title       | Something interesting     |
      | article2 | A Second Article | Another interesting topic |
    When I update a comment with ID "foo" to have an article ID of "article2" and content of "Updated comment"
    Then I receive a comment response status code of 200
    And I receive the following comments in the response body:
    	| ID  | Article ID | Content         |
      | foo | article2   | Updated comment |
    And the following comments now exist:
    	| ID  | Article ID | Content         |
      | foo | article2   | Updated comment |
      | bar | article1   | Another comment |
    
  Scenario: Update non-existent comment
    Given the following comments exist:
      | ID  | Article ID | Content         |
      | foo | article1   | Some comment    |
      | bar | article1   | Another comment |
    And the following articles exist:
    	| ID       | Title            | Content                   |
      | article1 | Some Title       | Something interesting     |
      | article2 | A Second Article | Another interesting topic |
    When I update a comment with ID "baz" to have an article ID of "article2" and content of "Updated comment"
    Then I receive a comment response status code of 200
    And I receive the following comments in the response body:
    	| ID  | Article ID | Content         |
      | baz | article2   | Updated comment |
    And the following comments now exist:
    	| ID  | Article ID | Content         |
      | foo | article1   | Some comment    |
      | bar | article1   | Another comment |
      | baz | article2   | Updated comment |
    
  Scenario: Update comment for non-existent article
    Given the following comments exist:
      | ID  | Article ID | Content         |
      | foo | article1   | Some comment    |
      | bar | article1   | Another comment |
    And the following articles exist:
    	| ID       | Title            | Content                   |
      | article1 | Some Title       | Something interesting     |
    When I update a comment with ID "foo" to have an article ID of "article2" and content of "Updated comment"
    Then I receive a comment response status code of 404