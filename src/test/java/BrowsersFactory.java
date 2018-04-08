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

            // Disable notifications
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", prefs);

            return new ChromeDriver(options);
        }
    },
    firefox {
        public WebDriver create() {
            updateProperty("firefox");

            // Disable notifications
            FirefoxProfile ffprofile = new FirefoxProfile();
            ffprofile.setPreference("dom.webnotifications.enabled", false);
            FirefoxOptions options = new FirefoxOptions();
            options.setProfile(ffprofile);

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