Feature: Login Functionality for Application Practice.com
  Author: Santosh Kulkarni

  @Smoke
  Scenario Outline: Validate with Valid Credentials
    When I enter username "<username>"
    And I enter password "<password>"
    And I click on the submit button
    Then I should see the welcome message

    Examples: 
      | username | password   |
      | student  | Password123 |
