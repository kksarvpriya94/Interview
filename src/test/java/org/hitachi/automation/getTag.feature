Feature: To test the endpoint for tag creation service
         GET http://host:8082/api/v2/tag/byname?tagname=""

  Background: Create and initialize Base Url
    Given url 'http://localhost:8082/'

  Scenario Outline: To Get api response for different testdata
    Given path 'api/v2/tag/byname'
    And param tagname = '<tagname>'
    And header Accept = 'application/json'
    When method get
    Then assert responseStatus == '<status>'
    And assert responseTime < 1000
    And match response ==
      """
      {
      	"name": '#string'
       "description": '#string',
       "domainKey": '#string'
      }
      """
    And match response.name == 'TestSensivity'
    And match response.description == 'Intel Data.CRM.MAG Account'
    And match response.domainKey == 'ra6a3959e9bde14993'

    Examples: 
      | tagname       | status |
      | TestSensivity |    200 |

  Scenario Outline: To get tags by name when name is not present or invalid
     Given call read("classpath:\\src\\test\\java\\org\hitachi\\automation\\createTag")
    * def errorSchema =
      """
      {
      "errors": {
      "message": [
        "Invalid"
      ]
      }
      }
      """
    Given path 'api/v2/tag/byname'
    And param tagname = '<tagname>'
    And header Accept = 'application/json'
    When method get
    Then assert responseStatus == '<status>'
    And assert responseTime < 1000
    * def errMessage = karate.jsonPath(response,"$.errors.message[0]")
    And match errMessage == 'Invalid tagname value'

    Examples: 
      | tagname | status |
      |         |    404 |
      | tagname |    404 |
