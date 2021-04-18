Feature: Post article
  I want to post an article

	Scenario: Post an article
		Given I have not posted any articles
    When I post an article with a title of "Some Title" and content of "Something interesting"
    Then I receive an article response status code of 201
    And I receive the following articles in the response body:
    	| ID | Title      | Content               |
      | ?  | Some Title | Something interesting |
    And the following articles now exist:
    	| ID | Title      | Content               |
      | ?  | Some Title | Something interesting |