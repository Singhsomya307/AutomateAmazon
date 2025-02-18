Feature: Amazon Shopping

  Scenario: Login and add a product to the cart
    Given the user is on the Amazon login page
    When the user logs in with valid credentials
    Then the home screen should be displayed
    When the user searches for a "pepsi"
    And the user selects the first item from the search results
    And the user is redirected to the product's page
    And the user adds the item to the cart
    Then close the browser

