package api.stepdef;

import api.commonMethods.GetRequest;
import api.objects.ReqresFeaturesObject;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.specification.RequestSpecification;

public class ReqresFeatures {

    private ReqresFeaturesObject reqresFeaturesObject = new ReqresFeaturesObject();

    @Given("I hit reqres get users API")
    public void iHitReqresGetUsersApi(String endpoint){
        reqresFeaturesObject.getRequest(endpoint);
    }

    @Then("I should get user list")
    public void iShouldGetUserList(){
        reqresFeaturesObject.validateUserList();
    }
}
