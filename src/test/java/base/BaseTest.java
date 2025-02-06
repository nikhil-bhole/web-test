package base;

import core.driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {

    protected WebDriver driver;

    @BeforeTest
    public void testSetup() {
        driver = DriverFactory.getInstance().getDriver();
        driver.navigate().to("https://www.google.com");
    }

    @AfterTest
    public void tearDown() {
        DriverFactory.getInstance().quit();
    }
}
