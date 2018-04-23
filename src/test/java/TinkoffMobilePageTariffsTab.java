import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TinkoffMobilePageTariffsTab extends BasePageObject {
    @FindBy(xpath = "//h2")
    private WebElement titleText;

    @FindBy(xpath = "//div[@id='x9c051']")
    private WebElement contactInfoFields;

    @FindBy(xpath = "//div[@data-qa-file='TMobileStickButton']/span/span")
    private WebElement priceField;

    @FindBy(xpath = "//button[./span[text()='Получить SIM-карту']]")
    private WebElement getSIMBtn;

    @FindBy(xpath = "//div[@id='x1f5c2']//*[contains(text(),'Мессенджеры')]/../..//span[./input]")
    private WebElement messengersBtn;

    @FindBy(xpath = "//div[@id='x1f5c2']//*[contains(text(),'Социальные сети')]/../..//span[./input]")
    private WebElement socialNetworksBtn;

    @FindBy(xpath = "//div[@id='x1f5c2']//*[contains(text(),'Музыка')]/../..//span[./input]")
    private WebElement musicBtn;

    @FindBy(xpath = "//div[@id='x1f5c2']//*[contains(text(),'Видео')]/../..//span[./input]")
    private WebElement videoBtn;

    @FindBy(xpath = "//div[@id='xca7ce']//a[@href='/mobile-operator/' and @data-qa-file='Link']/span")
    private WebElement reviewTab;

    @FindBy(xpath = "//div[@id='x1f5c2']//*[contains(text(),'Другие безлимитные сервисы')]")
    @CacheLookup
    private WebElement otherServices;

    @FindBy(xpath = "//div[@id='x1f5c2']//*[contains(text(),'Звонки')]/../..//li[1]")
    private WebElement callsOption1;

    @FindBy(xpath = "//div[@id='x1f5c2']//*[contains(text(),'Интернет')]/../..//li[1]")
    private WebElement internetOption1;

    @FindBy(xpath = "//div[@id='x1f5c2']//*[contains(text(),'Интернет')]/../..//li[5]")
    private WebElement internetOption5;

    @FindBy(xpath = "//div[@id='x1f5c2']//*[contains(text(),'Интернет')]/../..//li[6]")
    private WebElement internetOption6;

    public TinkoffMobilePageTariffsTab(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void showStickButton(){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView(false);", contactInfoFields);
        js.executeScript("arguments[0].scrollIntoView(true);", titleText);
    }

    public TinkoffMobilePage returnToMainTab(){
        reviewTab.click();
        return new TinkoffMobilePage(driver);
    }

    public void switchOffAll(){
        callsOption1.click();
        internetOption1.click();
        clickOnElement(messengersBtn);
        clickOnElement(socialNetworksBtn);
    }

    public void checkIsEnableGetSIM(){
        checkIsEnable(getSIMBtn, false);
    }

    public void checkHidingOtherServices(){
        checkVisibility(otherServices, true);
        internetOption6.click();
        checkVisibility(otherServices, false);
    }

    public void checkUnlim(){
        int priceUnlim = getPrice();
        internetOption5.click();
        musicBtn.click();
        videoBtn.click();
        checkIntLessThan(() -> getPrice(), priceUnlim);
    }

    public int getPrice(){
        wait.until(d -> (priceField.getText().length() > 0));
        return getPriceFromString(priceField.getText());
    }

    private int getPriceFromString(String string){
        StringBuilder sb = new StringBuilder();
        for (char ch : string.toCharArray()){
            if (Character.isDigit(ch))
                sb.append(ch);
        }

        return Integer.valueOf(sb.toString());
    }
}
