package core.driver;

import core.driver.managers.ChromeDriverManager;
import org.openqa.selenium.WebDriver;

public class DriverFactory {

    public static DriverFactory instance = null;

    private DriverFactory() {
    }

    public static DriverFactory getInstance() {
        if (instance == null) {
            synchronized (DriverFactory.class) {
                if (instance == null) {
                    instance = new DriverFactory();
                }
            }
        }
        return instance;
    }

    ThreadLocal<DriverManager> tlDriverManager = ThreadLocal.withInitial(this::setDriverManager);

    public DriverManager setDriverManager() {
        tlDriverManager.set(new ChromeDriverManager());
        return tlDriverManager.get();
    }

    public DriverManager getDriverManager() {
        return tlDriverManager.get();
    }

    public WebDriver getDriver() {
        return tlDriverManager.get().getDriver();
    }

    public void quit() {
        tlDriverManager.get().quitDriver();
        tlDriverManager.remove();
    }
}
