package ui.objects;

import commonUtilities.ProjectProperties;
import org.openqa.selenium.WebDriver;
import ui.utilities.DriverFactory;

public class CommonObjects {

    private WebDriver driver = DriverFactory.getDriver();
    public void launchHomepage() {
        driver.get(ProjectProperties.getProperty("url"));
        driver.manage().window().maximize();
    }
}
