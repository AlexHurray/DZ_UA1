import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;

public class TestPayment extends BaseRunner {
    @Test
    public void testPayment() throws InterruptedException  {
        driver.get(baseUrl);
        driver.findElement(By.xpath("//div[@id='x48761']/div[3]/div/div/div/div/div/div/div/div/div/div[6]/a/span")).click();
        driver.findElement(By.xpath("//input[@id='']")).click();
        driver.findElement(By.xpath("//input[@id='']")).clear();
        driver.findElement(By.xpath("//input[@id='']")).sendKeys("Тинькофф Мобайл");
        driver.findElement(By.xpath("//div[@id='search-and-pay-container']/div[2]/div[2]/div/form/div[3]/div/div/div/div[2]/div/div/div/div/div/div/div/div")).click();
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if ("Оплатить мобильную связь".equals(driver.getTitle())) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }

        driver.findElement(By.xpath("//input[@id='']")).click();
        driver.findElement(By.xpath("//input[@id='']")).clear();
        driver.findElement(By.xpath("//input[@id='']")).sendKeys("5");
        driver.findElement(By.xpath("//h2")).click();
        assertEquals(driver.findElement(By.xpath("//div[2]/div/div/div/div/form/div/div/div[2]")).getText(), "Поле обязательное");
        assertEquals(driver.findElement(By.xpath("//form/div[2]/div/div/div/div/div/div/div/div[2]")).getText(), "Минимум — 10 ₽");
    }
}
