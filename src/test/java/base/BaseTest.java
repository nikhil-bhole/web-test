package base;

import core.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void testSetup() {
        driver = DriverFactory.getInstance().getDriver();
        driver.navigate().to("https://www.google.com");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.getInstance().quit();
    }
}
