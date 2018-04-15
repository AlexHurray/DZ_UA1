import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import java.util.concurrent.TimeUnit;

public class BaseRunner {
    public static ThreadLocal<WebDriver> tl = new ThreadLocal<>();
    public WebDriver driver;
    public String browserName = System.getProperty("browser");
    public String baseUrl;
    public String tinkoffUrl = "https://www.tinkoff.ru";
    public WebDriverWait wait;

    @BeforeClass(alwaysRun = true)
    public void setUp(){
        if (tl.get() != null) {
            driver = tl.get();
        } else {
            driver = getDriver();
            tl.set(driver);
        }
        driver.manage().window().maximize();
        baseUrl = "http://www.google.ru";
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 20);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            driver.quit();
            driver = null;
        }));
    }


    @AfterClass(alwaysRun = true)
    public void tearDown() {
    }

    private WebDriver getDriver() {
        try {
            BrowsersFactory.valueOf(System.getProperty("browser"));
        } catch (NullPointerException | IllegalArgumentException e) {
            System.out.println("\nThe driver is not set. Running a chrome browser...");
            browserName = "chrome";
            System.setProperty("browser", browserName);
        }
        return BrowsersFactory.valueOf(browserName).create();
    }
}