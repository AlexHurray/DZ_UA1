import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TinkoffMobilePage extends BasePageObject {
    @FindBy(xpath = "//div[@id='xca7ce']//a[@href='/mobile-operator/tariffs/' and @data-qa-file='Link']/span")
    private WebElement tariffsTab;

    @FindBy(xpath = "//button[.//h3[text()='Заказать SIM-карту']]")
    private WebElement orderSIMBtn;

    @FindBy(xpath = "//button[./span[text()='Далее']]")
    private WebElement nextBtn;

    public TinkoffMobilePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public TinkoffMobilePageTariffsTab openTafiffs(){
        tariffsTab.click();
        return new TinkoffMobilePageTariffsTab(driver);
    }

    public void checkScroll(){
        orderSIMBtn.click();
        checkVisibility(nextBtn, true);
    }
}
