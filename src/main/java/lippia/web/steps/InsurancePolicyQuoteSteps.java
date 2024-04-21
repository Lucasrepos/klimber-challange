package lippia.web.steps;

import com.crowdar.core.PageSteps;
import io.cucumber.java.en.*;
import lippia.web.services.InsurancePolicyQuotePage;
import lippia.web.utils.RandomGenerator;
import org.testng.asserts.SoftAssert;
import static lippia.web.constants.InsurancePolicyQuoteConstants.*;
import static lippia.web.services.InsurancePolicyQuotePage.*;

public class InsurancePolicyQuoteSteps extends PageSteps {


    @Given("I am on Klimber insurance policy quote page")
    public void iAmOnKlimberInsurancePolicyQuotePage() {
        InsurancePolicyQuotePage.goToKlimberInsuranceQuotePage();
    }


    @When("The user fills out the form with age {string}")
    public void theUserFillsOutTheFormWithAge(String age) {
        enteredAge = Integer.parseInt(age);
        enterBirthYear(Integer.parseInt(calculateYearToEnter(Integer.parseInt(age))));
        selectProvince();
        enterPhoneNumber();
    }


    @Then("the site shows the allowed age message {string}")
    public void theSiteShowsTheAgeMessage(String message) {
        assertMessageOrRedirect(message);
    }

    @When("The user sets the amount to {string}")
    public void theUserSetsTheAmountToAmount(String ammount) {
        setInsuranceAmmount(ammount);
    }

    @Then("the site displays the monthly quote as {string} and setted total insurance ammount")
    public void theSiteDisplaysTheMonthlyQuoteAsMonthlyQuote(String monthy) {
        assertMonthlyQuote(monthy);
    }

    @When("The user click on checkbox of an aditional coverage {string}")
    public void theUserClickOnCheckboxOfAnAditionalCoverageAditionalCoverage(String optionSelected) {
        tapAditionalCoverageCheckbox(optionSelected);
    }



    @Then("the site displays aditional coverage {string}")
    public void theSiteDisplaysAditionalCoverageInfo(String exlutionOption) {
        assertAditionalCoverageExlusionInfo(exlutionOption);
    }

    @Then("The user tap Yes to all questions, enter height and weight")
    public void theUserTapYesToAllQuestions() {
        AllowsContinueFillingTheForm();
        clickYesAllRadioButtons();
        enterHeight();
        enterWeight();
        submitForm();
    }

    @Then("The form is submited and the user notified")
    public void theFormIsSubmitedAndTheUserNotified() {
        submitContactForm();
        CheckAllowedAgeMessage("El e-mail fue enviado correctamente.");
    }

    @When("The user enter his contact date")
    public void theUserEnterHisContactDate() {
        enterName(RandomGenerator.generateRandomName());
        enterEmail(RandomGenerator.generateRandomEmail());
        enterPhone();
    }

}



