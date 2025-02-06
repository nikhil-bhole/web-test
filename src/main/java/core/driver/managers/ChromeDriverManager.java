package core.driver.managers;

import core.driver.DriverManager;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverManager extends DriverManager {

    public void createDriver() {
        driver = new ChromeDriver();
    }

}
