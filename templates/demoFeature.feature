Feature: Login to Saucelabs website

Feature: Verify user actions on a webpage

  Scenario Outline: User interacts with elements on a web page
    Given <User> is on <url> page
    When he views the value in the field <X-Path>
    Then he should be able to view <data> in the field
    When he enters the value <data> in the field with a path <X-path>
    When he enters the value <data> in the field identified by <Element ID>
    When he enters the value <data> in the field named <Element name>
    When he clicks the button identified by <Button ID>
	  When he clicks the button identified by xpath <Button Xpath>
    When he clicks the button named <Button name>
    When he clicks the element identified by <Element ID>

    Examples:
      | User    | url                      | X-Path         | data     | X-path         | Element ID     | Element name | Button ID   | Button name |
      | JohnDoe | https://example.com/home | //*[@id='name'] | TestData | //*[@id='name'] | field-id       | field-name   | button-id   | button-name |

