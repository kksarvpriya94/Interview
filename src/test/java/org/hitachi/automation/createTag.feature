Feature: To test the endpoint for tag creation service
         POST create: http://host1:8082/api/v2/tag/

  Background: Create and initialize Base Url
    Given url 'http://host:8082/'

  @Smoke
  Scenario: To Validate success response for tag creation request
    Given path 'api/v2/tag/'
    * def body = read("data/TagCreation.json")
    And request body
    And header Accept = 'application/json'
    When method post
    Then status 201
    And match response ==
      """
      {
      	"name": '#string'
       "description": '#string',
       "domainKey": '#string'
      }
      """
    And match response.name == '#notnull'
    And match response.description == '#notnull'
    And match response.domainKey == '#notnull'
    And match response.name == 'TestSensivity'
    And match response.description == 'Intel Data.CRM.MAG Account'
    And match response.domainKey == 'ra6a3959e9bde14993'

  @Regression
  Scenario Outline: To Validate negative case when body parameters are missing
    Given path 'api/v2/tag/'
    And request {"name": '<name>',"description":'<description>', "domainKey": '<domainKey>'}
    And header Accept = 'application/json'
    When method post
    Then status '<status>'
    And match response ==
      """
      {
      "errors": '#string'
      }
      """
    And match response.message == '<message>'

    Examples: 
      | name          | description                                                 | domainKey          | status | message                                  |
      |               | Intel Data.CRM.MAG Account                                  | key                |    400 | name field can't be empty                |
      | TestSensivity |                                                             | ra6a3959e9bde14993 |    400 | description field can't be empty         |
      | TestSensivity | Intel Data.CRM.MAG Account                                  |                    |    400 | domainKey can't be empty                 |
      |               | shadhsvadhvasdhvashdvasdhavsdhvasdashdvhsavdasvdashsadddddd |                    |    422 | description should be between 250 chars  |
      | name2         | Intel Data.CRM.MAG Account                                  | ra6a3959e9bde14993 |    422 | Only alphabets are allowed in name field |
