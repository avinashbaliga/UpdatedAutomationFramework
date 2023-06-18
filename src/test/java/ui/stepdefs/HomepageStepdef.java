package ui.stepdefs;

import io.cucumber.java.en.Then;
import ui.objects.HomepageObject;
import ui.utilities.CommonMethods;

public class HomepageStepdef {

    private HomepageObject homepageObject = new HomepageObject();
    private CommonMethods commonMethods = new CommonMethods();

    @Then("The title should be {string}")
    public void checkTheTitle(String title){
        commonMethods.putStepName("The title should be "+title);
        homepageObject.checkTheTitle(title);
    }
}
