package ui.objects;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ui.utilities.CommonMethods;
import ui.utilities.DriverFactory;

public class HomepageObject {

    private WebDriver driver = DriverFactory.getDriver();
    private CommonMethods commonMethods = new CommonMethods();
    public void checkTheTitle(String title) {
        Assert.assertEquals(driver.getTitle().trim(), title, "Expected title: " + title + " but found: " + driver.getTitle().trim());
    }
}
