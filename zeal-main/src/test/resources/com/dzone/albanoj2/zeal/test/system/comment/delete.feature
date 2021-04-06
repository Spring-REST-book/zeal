Feature: Delete comment by ID
  I want to delete a comment by its ID
  
  Background:
  	Given the following articles exist:
    	| ID       | Title      | Content               |
      | article1 | Some Title | Something interesting |

  Scenario: Delete non-existent comment
    Given I have not posted any comments
    When I delete a comment with ID "foo"
    Then I receive a comment response status code of 204
    
  Scenario: Delete existing comment
    Given the following comments exist:
    	| ID  | Article ID | Content       |
      | foo | article1   | Some comment  |
      | bar | article1   | Other comment |
    When I delete a comment with ID "foo"
    Then I receive a comment response status code of 204
    And the following comments now exist:
    	| ID  | Article ID | Content       |
      | bar | article1   | Other comment |