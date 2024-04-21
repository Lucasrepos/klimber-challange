@smoke
Feature: As a potential client i need to equests an insurance policy quote

  Background:
    Given I am on Klimber insurance policy quote page

  @QA-01
  Scenario Outline: Verify form age boundary values
    When The user fills out the form with age '<age>'
    Then the site shows the allowed age message '<message>'
    Examples:
      | age | message                                                         |
      | 17  | Por el momento, la edad mínima de contratación es 18 años       |
      | 18  | No-Message. Allows redirection to continue filling out the form |
      | 64  | No-Message Allows redirection to continue filling out the form  |
      | 65  | Por el momento, la edad máxima de contratación es 64 años       |

  @QA-02
  Scenario Outline: Verify Insurance Amount slider entering diferent ages
    When The user fills out the form with age '<age>'
    When The user sets the amount to '<amount>'
    Then the site displays the monthly quote as '<monthly-quote>' and setted total insurance ammount

    Examples:
      | age | amount      | monthly-quote |
      | 18  | $ 6.280.000 | $ 980         |
      | 40  | $ 6.280.000 | $ 1.974       |
      | 50  | $ 5.920.000 | $ 3.975       |

  @QA-03
  Scenario Outline: Verify information displayed correctly when adding additional insurance coverage
    When The user click on checkbox of an aditional coverage '<option>'
    Then the site displays aditional coverage '<exlusion-info>'

    Examples:
      | option    | exlusion-info                  |
      | Diability | Pago doble por accidente -Info |
      | Acident   | Adelanto por invalidez-Info    |
      | Illness   | Adelanto por enfermedad-Info   |

  @QA-04
  Scenario: Verify the entire form happy path
    When The user fills out the form with age '18'
    Then The user tap Yes to all questions, enter height and weight
    When The user enter his contact date
    Then The form is submited and the user notified


