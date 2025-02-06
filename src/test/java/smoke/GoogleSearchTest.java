package smoke;

import base.BaseTest;
import org.testng.annotations.Test;
import webpages.GoogleHome;

public class GoogleSearchTest extends BaseTest {

    @Test
    public void searchTest() {
        GoogleHome home = new GoogleHome();
        home.type("iPhone 11");
    }
}
