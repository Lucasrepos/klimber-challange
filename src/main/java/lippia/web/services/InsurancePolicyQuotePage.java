package lippia.web.services;

import com.crowdar.core.PropertyManager;
import com.crowdar.core.actions.ActionManager;
import com.crowdar.core.actions.WebActionManager;
import com.crowdar.driver.DriverManager;
import lippia.web.constants.InsurancePolicyQuoteConstants;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.crowdar.core.actions.WebActionManager.navigateTo;
import static lippia.web.constants.InsurancePolicyQuoteConstants.*;
import static lippia.web.constants.InsurancePolicyQuoteConstants.PHONE_CODE_NUMBER;

public class InsurancePolicyQuotePage extends ActionManager {
    public static void goToKlimberInsuranceQuotePage() {
        navigateTo(PropertyManager.getProperty("web.base.url"));
    }

    public static void enterBirthYear(int year) {
        setInput(InsurancePolicyQuoteConstants.FECHA_DE_NACIMIENTO_INPUT, String.valueOf(year));
    }

    public static String calculateYearToEnter(int age) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateToEnter = currentDate.minusYears(age);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String formattedDate = dateToEnter.format(formatter);
        return formattedDate;
    }

    public static void selectProvince() {
        Select province = new Select(waitClickable(PROVINCE_DROPDOWN));
        String CABA_Value = "078cbbfb-b59a-11e7-81ed-7be3fe53a234";
        province.selectByValue(CABA_Value);
    }

    public static void enterPhoneNumber() {
        setInput(PHONE_NUMBER, "62626262");
        setInput(PHONE_CODE_NUMBER, "11");
        getElement(PHONE_CODE_NUMBER).sendKeys(Keys.ENTER);
    }

    public static void assertMessageOrRedirect(String message) {
        if (enteredAge == 17 || enteredAge == 65) {
            CheckAllowedAgeMessage(message);
        } else {
            AllowsContinueFillingTheForm();
        }
    }

    public static void CheckAllowedAgeMessage(String message) {
        String originalMessage = waitVisibility(NOTIFICATION_MESSAGE).getText();
        Assert.assertEquals(originalMessage, message);
    }

    public static void explicitWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void AllowsContinueFillingTheForm() {
        click(CONTRATAR_BTN);
        String currentUrl = DriverManager.getDriverInstance().getCurrentUrl();
        String expectedUrl = EXPECTED_CONTRATAR_URL;
        Assert.assertEquals(currentUrl, expectedUrl);
    }

    public static void clickYesAllRadioButtons() {
        for (int i = 0; i <= 2; i++) {
            String cssSelector = String.format("input[type='radio'][name='UnderwritingCustom[%d].ResponseBool'][value='true']", i);
            click("css:" + cssSelector);
        }
    }

    public static void enterHeight() {
        setInput(HEIGHT_INPUT, "180");
    }

    public static void enterWeight() {
        setInput(WEIGHT_INPUT, "80");
    }

    public static void submitForm() {
        click(SIGUIENTE_BTN);
    }

    public static void assertMonthlyQuote(String monthly){
        explicitWait(3);
        String sumaAseguradaOriginal = getText(TOTAL_INSURANCE_AMMOUNT_TEXT);
        String mensualidadOriginal = getText(MONTHLY_PRICE);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(sumaAseguradaOriginal, TotalInsuranceAmmount);
        softAssert.assertEquals(mensualidadOriginal, monthly);
        softAssert.assertAll();
    }


    public static void setInsuranceAmmount(String ammount) {
        //**el click en TOTAL_INSURANCE_AMMOUNT_TEXT está para que el sitio reaccione*/
        click(TOTAL_INSURANCE_AMMOUNT_TEXT);
        TotalInsuranceAmmount = ammount;
        int xOffset = calculateXOffset(ammount);
        int yOffset = 0;
        WebElement sliderHandle = DriverManager.getDriverInstance().getWrappedDriver().findElement(By.className(INSURANSE_AMMOUNT_SLIDER));
        Actions actions = new Actions(DriverManager.getDriverInstance().getWrappedDriver());
        actions.dragAndDropBy(sliderHandle, xOffset, yOffset).perform();
    }

    private static int calculateXOffset(String amount) {
        int xOffset;
        switch (amount) {
            case "$ 6.280.000":
                xOffset = 10;
                break;
            case "$ 5.920.000":
                xOffset = 2;
                break;
            default:
                xOffset = 10;
        }
        return xOffset;
    }

    public static void tapAditionalCoverageCheckbox(String aditionalCoverage) {
        switch (aditionalCoverage) {
            case "Diability":
                click(PAGO_DOBLE_POR_ACCIDENTE_ADICIONAL_CHECKBOX);
                break;
            case "Acident":
                click(ADELANTO_POR_ACCIDENTE_ADICIONAL_CHECKBOX);
                break;
            case "Illness":
                click(ADELANTO_POR_ENFERMEDAD_ADICIONAL_CHECKBOX);
                break;
        }
    }

    public static void assertAditionalCoverageExlusionInfo(String optionSelected) {
        scrollToExlutionInfo(optionSelected);
        tapOnExlusion(optionSelected);
        compareToExpecedItems(optionSelected);

    }

    public static void enterName(String randomName) {
        setInput(NAME_INPUT, randomName);
    }

    public static void enterEmail(String randomEmail) {
        setInput(EMAIL_INPUT, randomEmail);
    }

    public static void enterPhone() {
        setInput(CELL_PHONE_INPUT, "3624623914");
    }

    public static void submitContactForm() {
        click(SIGUIENTE_SUBMITFORM_BTN);
    }

    public static void compareToExpecedItems(String optionSelected) {
        WebElement element3 = WebActionManager.getElement(elementProviderAditionalCoverageExlution(optionSelected).get("EXLUSION_ITEMS_ELEMENT"));
        List<WebElement> paragraphs = element3.findElements(By.tagName("p"));
        int itemsQuantityInt = Integer.parseInt(elementProviderAditionalCoverageExlution(optionSelected).get("ITEMS_QUANTITY"));
        Assert.assertTrue(itemsQuantityInt == paragraphs.size());
    }

    public static void scrollToExlutionInfo(String optionSelected) {
        WebElement element = DriverManager.getDriverInstance().getWrappedDriver().findElement(By.id(elementProviderAditionalCoverageExlution(optionSelected).get("EXLUSION_TITLE_ELEMENT")));
        DriverManager.getDriverInstance().executeScript("arguments[0].scrollIntoView(true);", element);
        System.out.println(elementProviderAditionalCoverageExlution(optionSelected).get("EXLUSION_TITLE_ELEMENT"));
    }

    public static void tapOnExlusion(String optionSelected) {
        click(elementProviderAditionalCoverageExlution(optionSelected).get("EXLUSION_XPATH"));


    }

    public static Map<String, String> elementProviderAditionalCoverageExlution(String optionSelected) {
        Map<String, String> coverageExlutionElements = new HashMap<>();
        switch (optionSelected) {
            case "Pago doble por accidente -Info":
                coverageExlutionElements.put("EXLUSION_XPATH", PAGO_DOBLE_POR_ACCIDENTE_EXLUSION_XPATH);
                coverageExlutionElements.put("EXLUSION_TITLE_ELEMENT", PAGO_DOBLE_POR_ACCIDENTE_EXLUSION_TXT);
                coverageExlutionElements.put("EXLUSION_ITEMS_ELEMENT", PAGO_DOBLE_POR_ACCIDENTE_ITEMS_TXT);
                coverageExlutionElements.put("ITEMS_QUANTITY", "6");
                return coverageExlutionElements;
            case "Adelanto por invalidez-Info":
                coverageExlutionElements.put("EXLUSION_XPATH", ADELANTO_POR_ACCIDENTE_EXLUSION_XPATH);
                coverageExlutionElements.put("EXLUSION_TITLE_ELEMENT", ADELANTO_POR_ACCIDENTE_EXLUSION_TXT);
                coverageExlutionElements.put("EXLUSION_ITEMS_ELEMENT", ADELANTO_POR_ACCIDENTE_ITEMS_TXT);
                coverageExlutionElements.put("ITEMS_QUANTITY", "5");
                return coverageExlutionElements;
            case "Adelanto por enfermedad-Info":
                coverageExlutionElements.put("EXLUSION_XPATH", ADELANTO_POR_ENFERMEDAD_EXLUSION_XPATH);
                coverageExlutionElements.put("EXLUSION_TITLE_ELEMENT", ADELANTO_POR_ENFERMEDAD_EXLUSION_TXT);
                coverageExlutionElements.put("EXLUSION_ITEMS_ELEMENT", ADELANTO_POR_ENFERMEDAD_ITEMS_TXT);
                coverageExlutionElements.put("ITEMS_QUANTITY", "6");
                return coverageExlutionElements;
        }
        return null;
    }
}









