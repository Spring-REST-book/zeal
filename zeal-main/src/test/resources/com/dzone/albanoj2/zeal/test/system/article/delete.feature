Feature: Delete article by ID
  I want to delete an article by its ID

  Scenario: Delete non-existent article
    Given I have not posted any articles
    When I delete an article with ID "foo"
    Then I receive an article response status code of 204
    
  Scenario: Delete existing articles
    Given the following articles exist:
      | ID  | Title            | Content                   |
      | foo | Some Title       | Something interesting     |
      | bar | A Second Article | Another interesting topic |
    When I delete an article with ID "foo"
    Then I receive an article response status code of 204
    And the following articles now exist:
    	| ID  | Title            | Content                   |
      | bar | A Second Article | Another interesting topic |