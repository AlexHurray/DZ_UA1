import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Supplier;

public class BasePageObject {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePageObject(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 60);
    }

    protected void clickOnElement(WebElement element) {

        String script = "var object = arguments[0];"
                + "var theEvent = document.createEvent(\"MouseEvent\");"
                + "theEvent.initMouseEvent(\"click\", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
                + "object.dispatchEvent(theEvent);"
                ;

        ((JavascriptExecutor)driver).executeScript(script, element);
    }

    // Не нашел иного способа очистить поле телефона и ввести его заново, чтобы не нарушился порядок цифр
    protected void sendString2Element(WebElement element, String string){
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
        element.sendKeys(Keys.ENTER);
        element.click();

        element.sendKeys(string);
    }

    protected void waitPageLoad(){
        wait.until(d -> ((JavascriptExecutor)d).executeScript("return document.readyState").equals("complete"));
    }

    protected void refreshPage(){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0, 0);");
        driver.navigate().refresh();
    }

    protected void checkVisibility(WebElement element, boolean visibility){
        if (visibility){
            wait.until(d -> ExpectedConditions.visibilityOf(element));
        }
        else{
            wait.until(d -> ExpectedConditions.stalenessOf(element));
        }
    }

    protected void checkIntEquals(Supplier<Integer> supplier, int origin){
        wait.until(d ->supplier.get() ==  origin);
    }

    protected void checkIntLessThan(Supplier<Integer> supplier, int origin){
        wait.until(d -> supplier.get() < origin);
    }

    protected void checkIsEnable(WebElement element, boolean enable){
        wait.until(d -> element.isEnabled() == enable);
    }
}
