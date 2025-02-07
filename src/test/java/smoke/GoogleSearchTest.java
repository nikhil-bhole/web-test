package smoke;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import webpages.GoogleHome;

public class GoogleSearchTest extends BaseTest {

    @Test(description = "Search test 1")
    public void searchTest1() {
        GoogleHome home = new GoogleHome();
        home.type("iPhone 11");
    }

    @Test(description = "Search test 2")
    public void searchTest2() {
        GoogleHome home = new GoogleHome();
        home.type("iPhone 11");
        Assert.assertTrue(false, "Not able to search the 'Samsung TV'!");
    }

    @Test(description = "Search test 3")
    public void searchTest3() {
        GoogleHome home = new GoogleHome();
        home.type("iPhone 11");
        Assert.assertTrue(false, "Not able to search the desired result!");
    }

    @Test(description = "Search test 4")
    public void searchTest4() {
        GoogleHome home = new GoogleHome();
        home.type("iPhone 11");
    }
}
