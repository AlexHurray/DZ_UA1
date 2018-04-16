import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;

import java.util.List;
import java.util.Set;

public class TestPayment extends BaseRunner {
    @Test
    public void testPaymentByXpath() {
        driver.get(baseUrl);

        WebElement input = driver.findElement(By.xpath("//*[@id='lst-ib']"));
        input.click();
        input.clear();
        input.sendKeys("tinkoff");
        input.sendKeys(Keys.ENTER);

        Set<String> oldWindowsSet = driver.getWindowHandles();
        driver.findElement(By.xpath("//*[@id='res']//h3/a")).click();

        wait.until(d -> d.getWindowHandles().size() != oldWindowsSet.size());

        Set<String> newWindowsSet = driver.getWindowHandles();
        newWindowsSet.removeAll(oldWindowsSet);
        String newWindowHandle = newWindowsSet.iterator().next();
        driver.switchTo().window(newWindowHandle);
        waitPageLoad();

        driver.findElement(By.xpath("//div[@id='x48761']//a[@href='/payments/' and @data-qa-file='Link']/span")).click();
        driver.findElement(By.xpath("//input[@id='']")).click();
        driver.findElement(By.xpath("//input[@id='']")).clear();
        driver.findElement(By.xpath("//input[@id='']")).sendKeys("Тинькофф Мобайл");
        driver.findElement(By.xpath("//div[@id='search-and-pay-container']//div[@data-qa-node='Tag']")).click();

        wait.until(d -> d.getTitle().equals("Оплатить мобильную связь"));

        driver.findElement(By.xpath("//input[@id='']")).click();
        driver.findElement(By.xpath("//input[@id='']")).clear();
        driver.findElement(By.xpath("//input[@id='']")).sendKeys("5");
        driver.findElement(By.xpath("//h2")).click();

        wait.until(d -> d.findElements(By.xpath("//div[@data-qa-file='UIFormRowError']")).size() == 2);
        List<WebElement> elms = driver.findElements(By.xpath("//div[@data-qa-file='UIFormRowError']"));
        assertEquals(elms.get(0).getText(), "Поле обязательное");
        assertEquals(elms.get(1).getText(), "Минимум — 10 ₽");
    }

    @Test
    public void testPaymentByCSS() {
        driver.get(baseUrl);

        WebElement input = driver.findElement(By.cssSelector("#lst-ib"));
        input.click();
        input.clear();
        input.sendKeys("tinkoff");
        input.sendKeys(Keys.ENTER);

        Set<String> oldWindowsSet = driver.getWindowHandles();
        driver.findElement(By.cssSelector("#res h3>a")).click();

        wait.until(d -> d.getWindowHandles().size() != oldWindowsSet.size());

        Set<String> newWindowsSet = driver.getWindowHandles();
        newWindowsSet.removeAll(oldWindowsSet);
        String newWindowHandle = newWindowsSet.iterator().next();
        driver.switchTo().window(newWindowHandle);
        waitPageLoad();

        driver.findElement(By.cssSelector("div#x48761 a[href='/payments/'][data-qa-file='Link'] > span")).click();
        driver.findElement(By.cssSelector("div#search-and-pay-container input[id]")).click();
        driver.findElement(By.cssSelector("div#search-and-pay-container input[id]")).clear();
        driver.findElement(By.cssSelector("div#search-and-pay-container input[id]")).
                sendKeys("Тинькофф Мобайл");
        driver.findElement(By.cssSelector("div#search-and-pay-container div[data-qa-node='Tag']")).click();

        wait.until(d -> d.getTitle().equals("Оплатить мобильную связь"));

        driver.findElement(By.cssSelector("input[id][data-qa-node='ValueComponent']")).click();
        driver.findElement(By.cssSelector("input[id][data-qa-node='ValueComponent']")).clear();
        driver.findElement(By.cssSelector("input[id][data-qa-node='ValueComponent']")).sendKeys("5");
        driver.findElement(By.cssSelector("h2")).click();

        wait.until(d -> d.findElements(By.cssSelector("div[data-qa-file='UIFormRowError']")).size() == 2);
        List<WebElement> elms = driver.findElements(By.cssSelector("div[data-qa-file='UIFormRowError']"));
        assertEquals(elms.get(0).getText(), "Поле обязательное");
        assertEquals(elms.get(1).getText(), "Минимум — 10 ₽");
    }

    @Test
    public void testAdditional() {
        // 1. Открыть https://www.tinkoff.ru/
        driver.get(tinkoffUrl);

        // 2. Из верхнего меню по нажатию "Мобайл" выполнить переход на страницу Тинькофф Мобайл
        driver.findElement(By.
                xpath("//div[@id='x48761']//a[@href='/mobile-operator/' and @data-qa-file='Link']/span")).click();

        // 3. Выполнить переход на вкладку Тарифы
        driver.findElement(By.xpath(
                "//div[@id='xca7ce']//a[@href='/mobile-operator/tariffs/' and @data-qa-file='Link']/span")).click();

        // 4. Выполнить скролл до появления всплывающего желтого окошка (с кнопкой "Оформить"
        // и суммой ежемесячного платежа). Запомнить значение суммы стартового предложения в локальной переменной.
        showStickButton();
        int price = getPrice();

        // 5. Обнулить все пакеты и отключить все дополнительные услуги
        driver.findElement(By.xpath("//div[@id='x1f5c2']//*[contains(text(),'Звонки')]/../..//li[1]")).click();
        driver.findElement(By.xpath("//div[@id='x1f5c2']//*[contains(text(),'Интернет')]/../..//li[1]")).click();
        clickOnElement(driver.findElement(By.
                xpath("//div[@id='x1f5c2']//*[contains(text(),'Мессенджеры')]/../..//span[./input]")));
        clickOnElement(driver.findElement(By.
                xpath("//div[@id='x1f5c2']//*[contains(text(),'Социальные сети')]/../..//span[./input]")));

        // Убедится в том, что сумма предложения станет 0 рублей
        wait.until(d -> getPrice() == 0);

        // а кнопка "Получить SIM-карту" станет не активна
        wait.until(d ->
                driver.findElement(By.xpath("//button[./span[text()='Получить SIM-карту']]")).isEnabled() == false);

        // 6. Обновить страницу и убедиться, что стартовое предложение вернулось в первоначальное значение (шаг 4)
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0, 0);");
        driver.navigate().refresh();
        showStickButton();
        wait.until(d -> price == getPrice());

        // 7. Выбрать безлимитного пакета интернета и убедиться, что все элементы из блока
        // "Другие безлимитных сервисов" скрылись
        wait.until(d ->
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[@id='x1f5c2']//*[contains(text(),'Другие безлимитные сервисы')]")));
        WebElement otherServices =
                driver.findElement(By.xpath("//div[@id='x1f5c2']//*[contains(text(),'Другие безлимитные сервисы')]"));
        driver.findElement(By.xpath("//div[@id='x1f5c2']//*[contains(text(),'Интернет')]/../..//li[6]")).click();
        wait.until(ExpectedConditions.stalenessOf(otherServices));

        // 8. Убедиться в том, что безлимитный пакет интернета дороже набора из пакета 15 ГБ +
        // сумма всех безлимитных сервисов
        int priceUnlim = getPrice();
        driver.findElement(By.xpath("//div[@id='x1f5c2']//*[contains(text(),'Интернет')]/../..//li[5]")).click();
        driver.findElement(By.xpath("//div[@id='x1f5c2']//*[contains(text(),'Музыка')]/../..//span[./input]")).click();
        driver.findElement(By.xpath("//div[@id='x1f5c2']//*[contains(text(),'Видео')]/../..//span[./input]")).click();
        wait.until(d -> priceUnlim > getPrice());

        // 9. Проверить скролл по нажатию кнопки "Заказать SIM-карту"
        driver.findElement(By.xpath("//div[@id='xca7ce']//a[@href='/mobile-operator/' and @data-qa-file='Link']/span"))
                .click();
        driver.findElement(By.xpath("//button[.//h3[text()='Заказать SIM-карту']]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[./span[text()='Далее']]")));
    }

    private int getPrice(){
        WebElement el = driver.findElement(By.xpath("//div[@data-qa-file='TMobileStickButton']/span/span"));
        wait.until(d -> (el.getText().length() > 0));

        return getPriceFromString(el.getText());

    }

    private void showStickButton(){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView(false);", driver.findElement(By.xpath("//div[@id='x9c051']")));
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h2")));
    }

    private int getPriceFromString(String string){
        StringBuilder sb = new StringBuilder();
        for (char ch : string.toCharArray()){
            if (Character.isDigit(ch))
                sb.append(ch);
        }

        return Integer.valueOf(sb.toString());
    }

    // так как в Firefox'e нельзя ткнуть на объект под другим объектом(всплывающим окном)
    private void clickOnElement(WebElement element) {

        String script = "var object = arguments[0];"
                + "var theEvent = document.createEvent(\"MouseEvent\");"
                + "theEvent.initMouseEvent(\"click\", true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
                + "object.dispatchEvent(theEvent);"
                ;

        ((JavascriptExecutor)driver).executeScript(script, element);
    }

    private void waitPageLoad(){
        wait.until(d -> ((JavascriptExecutor)d).executeScript("return document.readyState").equals("complete"));
    }
}
