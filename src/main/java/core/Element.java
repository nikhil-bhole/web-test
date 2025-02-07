package core;

import core.driver.DriverFactory;
import enums.WaitTime;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.List;

import static enums.WaitTime.DEFAULT;

/**
 * Utility class for interacting with web elements.
 */
public class Element {

    WebDriver driver;
    By by;
    WebElement element;
    WebDriverWait wait;

    public Element(By by, WaitTime waitTime) {
        this.driver = DriverFactory.getInstance().getDriver();
        this.by = by;
        this.wait = new WebDriverWait(driver, waitTime.getDuration());
        findElement();
    }

    public Element(By by) {
        this.driver = DriverFactory.getInstance().getDriver();
        this.by = by;
        this.wait = new WebDriverWait(driver, DEFAULT.getDuration());
        findElement();
    }

    private void findElement() {
        try {
            this.element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Element not found: " + by);
        }
    }

    /**
     * Returns the WebElement.
     */
    public WebElement $() {
        return element;
    }

    /**
     * Returns a nested Element inside the current element.
     */
    public Element $(By nestedBy) {
        return new Element(nestedBy);
    }

    /**
     * Returns a list of nested Elements inside the current element.
     */
    public List<Element> $$(By nestedBy) {
        List<WebElement> elements = element.findElements(nestedBy);
        List<Element> list = new ArrayList<>();
        for (WebElement el : elements) {
            list.add(new Element(nestedBy));
        }
        return list;
    }

    public Element visible(WaitTime waitTime) {
        this.element = new WebDriverWait(driver, waitTime.getDuration()).until(ExpectedConditions.visibilityOfElementLocated(by));
        return this;
    }

    /**
     * Clicks the element.
     */
    public Element click() {
        try {
            element.click();
        } catch (Exception e) {
            clickJS();
        }
        return this;
    }

    /**
     * Sends keys to the element.
     */
    public Element sendKeys(String text) {
        try {
            element.sendKeys(text);
        } catch (Exception e) {
            sendKeysJS(text);
        }
        return this;
    }

    /**
     * Clears text from an input field.
     */
    public Element clear() {
        element.clear();
        return this;
    }

    /**
     * Gets the text of the element.
     */
    public String getText() {
        return element.getText();
    }

    /**
     * Scrolls the element into view.
     */
    public Element scrollIntoView() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return this;
    }

    /**
     * Highlights the element with a red border (useful for debugging).
     */
    public Element highlight() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
        return this;
    }

    /**
     * Clicks the element using JavaScript.
     */
    private void clickJS() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Sends keys using JavaScript.
     */
    private void sendKeysJS(String text) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute('value', '" + text + "')", element);
    }
}
