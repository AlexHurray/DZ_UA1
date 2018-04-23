import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TinkoffPaymentsPage extends BasePageObject {
    @FindBy(xpath = "//input[@id='']")
    private WebElement inputField;

    @FindBy(xpath = "//div[@id='search-and-pay-container']//div[@data-qa-node='Tag']")
    private WebElement firstSearchResult;

    public TinkoffPaymentsPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public TinkoffPaymentsMobilePage openPaymentsMobile(){
        inputField.click();
        inputField.clear();
        inputField.sendKeys("Тинькофф Мобайл");

        checkVisibility(firstSearchResult, true);
        firstSearchResult.click();

        wait.until(d -> d.getTitle().equals("Оплатить мобильную связь"));

        return new TinkoffPaymentsMobilePage(driver);
    }
}
