
Feature: EmbossingRetail
Scenario Outline: EmbossingRetail
    Given John is on Add Card page
    And He enters <creditType> in the <cardtype> field
    And He enters <embossingName> in the embossing name field
    And The card is created
     Examples:     
          |  creditType  |  cardtype  |  embossingName  |  
