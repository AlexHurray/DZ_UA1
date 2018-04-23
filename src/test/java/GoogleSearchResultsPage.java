import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Set;

public class GoogleSearchResultsPage extends BasePageObject{

    @FindBy(xpath = "//*[@id='res']//h3/a")
    private WebElement firstLink;

    public GoogleSearchResultsPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public TinkoffMainPage openFirstLink(){
        Set<String> oldWindowsSet = driver.getWindowHandles();

        firstLink.click();

        wait.until(d -> d.getWindowHandles().size() != oldWindowsSet.size());

        Set<String> newWindowsSet = driver.getWindowHandles();
        newWindowsSet.removeAll(oldWindowsSet);
        String newWindowHandle = newWindowsSet.iterator().next();
        driver.switchTo().window(newWindowHandle);
        waitPageLoad();

        return new TinkoffMainPage(driver);
    }

}
