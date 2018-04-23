import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TinkoffPaymentsMobilePage extends BasePageObject{
    @FindBy(xpath = "//input[@id='']")
    private WebElement inputFieldSum;

    @FindBy(xpath = "//input[@name='provider-phone']")
    private WebElement inputFieldPhone;

    @FindBy(xpath = "//h2")
    private WebElement buttonOK;

    @FindBy(xpath = "//input[@id='']/../../../..//div[@data-qa-file='UIFormRowError']")
    private WebElement errorSum;

    @FindBy(xpath = "//input[@name='provider-phone']/../../../..//div[@data-qa-file='UIFormRowError']")
    private WebElement errorPhone;


    public TinkoffPaymentsMobilePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void validPhoneField(){
        // когда поле заполнено неверно
        checkErrorMessageSum(inputFieldPhone, errorPhone,"12345", "Поле неправильно заполнено");

        // 	когда поле не заполнено
        checkErrorMessageSum(inputFieldPhone, errorPhone," ", "Поле обязательное");

        //  проверка на автоподстановку символов маски
        checkInputSumText(inputFieldPhone, "9151234567", "+7 (915) 123-45-67");

        // максимально допустимое количество символов ввода
        checkInputSumText(inputFieldPhone, "91512345670000", "+7 (915) 123-45-67");

        // запрет ввода любых символов кроме цифр
        checkInputSumText(inputFieldPhone, "Dasf;!@#$%$%^&", "+7 (");
    }

    public void validSumField(){
        // когда сумма платежа меньше 10 р
        checkErrorMessageSum(inputFieldSum, errorSum,"5", "Минимум — 10 ₽");

        // когда поле не заполнено
        checkErrorMessageSum(inputFieldSum, errorSum," ", "Поле обязательное");

        // когда сумма платежа меньше 15 000 р
        checkErrorMessageSum(inputFieldSum, errorSum,"15001", "Максимум — 15 000 ₽");

        // когда поле заполнено неверно
        checkErrorMessageSum(inputFieldSum, errorSum,"0-)", "Поле заполнено неверно");

        // максимально допустимое количество символов ввода
        checkInputSumText(inputFieldSum, "10000000000000000000", "0,00");

        // запрет ввода любых символов кроме цифр
        checkInputSumText(inputFieldSum, "Dasf;!@#$%$%^&", "");

        checkInputSumText(inputFieldSum, "(100-20)*2", "160");
    }

    private void checkErrorMessageSum(WebElement inputField, WebElement errorField, String input, String errorMessage){
        sendString2Element(inputField, input);
        buttonOK.click();

        wait.until(d -> errorField.getText().equals(errorMessage));
    }

    private void checkInputSumText(WebElement inputField, String input, String result){
        sendString2Element(inputField, input);
        buttonOK.click();

        wait.until(d -> inputField.getAttribute("value").equals(result));
    }
}
