package ui.stepdefs;

import io.cucumber.java.en.Given;
import ui.objects.CommonObjects;
import ui.utilities.CommonMethods;

public class CommonStepdefs {

    private CommonObjects commonObjects = new CommonObjects();
    private CommonMethods commonMethods = new CommonMethods();

    @Given("I launch the homepage")
    public void iLaunchTheHomepage(){
        commonMethods.putStepName("I launch the homepage");
        commonObjects.launchHomepage();
    }
}
