Feature: Post comment
  I want to post a comment

	Scenario: Post a comment
		Given I have not posted any comments
		And the following articles exist:
    	| ID       | Title      | Content               |
      | article1 | Some Title | Something interesting |
    When I post a comment with an article ID of "article1" and content of "Some comment"
    Then I receive a comment response status code of 201
    And I receive the following comments in the response body:
    	| ID  | Article ID | Content      |
      | ?   | article1   | Some comment |
    And the following comments now exist:
    	| ID  | Article ID | Content      |
      | ?   | article1   | Some comment |

	Scenario: Post a comment to non-existent article
		Given I have not posted any comments
		And I have not posted any articles
    When I post a comment with an article ID of "article1" and content of "Some comment"
    Then I receive a comment response status code of 404