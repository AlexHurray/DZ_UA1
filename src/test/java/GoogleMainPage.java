import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class GoogleMainPage extends BasePageObject{
    public static final String GOOGLE_URL = "http://www.google.ru";

    @FindBy(xpath = "//*[@id='lst-ib']")
    private WebElement inputSearch;

    public GoogleMainPage(WebDriver driver, boolean open){
        //this.driver = driver;
        super(driver);

        if (open) {
            this.driver.get(GOOGLE_URL);
        }
        PageFactory.initElements(driver, this);
    }

    public GoogleSearchResultsPage search(String input){
        inputSearch.click();
        inputSearch.clear();
        inputSearch.sendKeys(input);
        inputSearch.sendKeys(Keys.ENTER);

        return new GoogleSearchResultsPage(driver);
    }
}
