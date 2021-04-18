Feature: Update article
  I want to update an article

	Scenario: Update existing article
    Given the following articles exist:
      | ID  | Title            | Content                   |
      | foo | Some Title       | Something interesting     |
      | bar | A Second Article | Another interesting topic |
    When I update an article with ID "foo" to have a title of "A New Title" and content of "Updated content"
    Then I receive an article response status code of 200
    And I receive the following articles in the response body:
    	| ID  | Title            | Content                   |
      | foo | A New Title      | Updated content           |
    And the following articles now exist:
    	| ID  | Title            | Content                   |
      | foo | A New Title      | Updated content           |
      | bar | A Second Article | Another interesting topic |
    
  Scenario: Update non-existent article
    Given the following articles exist:
      | ID  | Title            | Content                   |
      | foo | Some Title       | Something interesting     |
      | bar | A Second Article | Another interesting topic |
    When I update an article with ID "baz" to have a title of "A New Title" and content of "Updated content"
    Then I receive an article response status code of 200
    And I receive the following articles in the response body:
    	| ID  | Title            | Content                   |
      | baz | A New Title      | Updated content           |
    And the following articles now exist:
    	| ID  | Title            | Content                   |
      | foo | Some Title       | Something interesting     |
      | bar | A Second Article | Another interesting topic |
      | baz | A New Title      | Updated content           |