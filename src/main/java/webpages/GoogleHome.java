package webpages;

import core.BasePage;
import org.openqa.selenium.By;

import static enums.WaitTime.BRIEF;

public class GoogleHome extends BasePage {

    By searchField = By.name("q");

    public void type(String query) {
       $(searchField, BRIEF).visible(BRIEF).sendKeys(query);
    }
}
