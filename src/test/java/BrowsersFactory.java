import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.util.HashMap;
import java.util.Map;

public enum  BrowsersFactory {
    chrome {
        public WebDriver create() {
            updateProperty("chrome");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");

            return new ChromeDriver(options);
        }
    },
    firefox {
        public WebDriver create() {
            updateProperty("firefox");

            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("dom.webnotifications.enabled", false);

            return new FirefoxDriver(options);
        }
    };

    public WebDriver create() {
        return null;
    }

    void updateProperty(String browserName) {
        System.out.println(String.format("\nstarting %s-browser......", browserName));
        if (System.getProperty("browser") == null) System.setProperty("browser", browserName);
    }
}