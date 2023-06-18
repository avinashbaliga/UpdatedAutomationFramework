package api.objects;

import api.commonMethods.GetRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

public class ReqresFeaturesObject {

    private GetRequest getRequest = new GetRequest();
    private RequestSpecification request;
    private Response response;
    public void getRequest(String endpoint) {
         request = getRequest.getRequest(null, null, null, null);
         response = request.get(endpoint);
    }

    public void validateUserList() {
        JsonObject body = (JsonObject) JsonParser.parseString(response.getBody().asString());
        Assert.assertTrue(!body.isEmpty(), "No users found in the system");
        Assert.assertTrue(body.has("data"), "Data element not found in the response");
        JsonArray data = (JsonArray) body.get("data");
        JsonObject element;

        for(JsonElement dataElement: data){
            element = (JsonObject) dataElement;
            Assert.assertTrue(element.has("id"), "id not found in data");
            Assert.assertTrue(element.has("email"), "email not found in data");
            Assert.assertTrue(element.has("first_name"), "first_name not found in data");
            Assert.assertTrue(element.has("last_name"), "last_name not found in data");
            Assert.assertTrue(element.has("avatar"), "avatar not found in data");
        }
    }
}
