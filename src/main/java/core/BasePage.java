package core;

import core.driver.DriverFactory;
import enums.WaitTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {

    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getInstance().getDriver();
    }

    /**
     * Opens a specified URL.
     */
    public void open(String url) {
        driver.get(url);
    }

    /**
     * Returns the title of the page.
     */
    public String getTitle() {
        return driver.getTitle();
    }

    /**
     * Finds an element using By locator.
     */
    protected Element $(By by, WaitTime waitTime) {
        return new Element(by, waitTime);
    }
}
