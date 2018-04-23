import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TinkoffMainPage extends BasePageObject {
    public static final String TINKOFF_URL = "https://www.tinkoff.ru";

    @FindBy(xpath = "//div[@id='x48761']//a[@href='/payments/' and @data-qa-file='Link']/span")
    private WebElement paymentsTab;

    @FindBy(xpath = "//div[@id='x48761']//a[@href='/mobile-operator/' and @data-qa-file='Link']/span")
    private WebElement mobileTab;

    public TinkoffMainPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public TinkoffMainPage(WebDriver driver, boolean open){
        super(driver);

        if (open){
            driver.get(TINKOFF_URL);
        }
        PageFactory.initElements(driver, this);
    }

    public TinkoffPaymentsPage openPayments(){
        paymentsTab.click();
        return new TinkoffPaymentsPage(driver);
    }

    public TinkoffMobilePage openMobile(){
        mobileTab.click();
        return new TinkoffMobilePage(driver);
    }
}
