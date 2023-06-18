package api.commonMethods;

import commonUtilities.ProjectProperties;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class GetRequest {

    public RequestSpecification getRequest(Map<String, Object> header, Object payload, Map<String, Object> pathParams, Map<String, Object> queryParams){
        RequestSpecification request = RestAssured.given();
        request.baseUri(ProjectProperties.getProperty("baseUri"));
        if(!(header == null))
            request.headers(header);
        if(!(pathParams == null))
            request.pathParams(pathParams);
        if(!(payload == null))
            request.body(payload);
        if(!(queryParams == null))
            request.queryParams(queryParams);

        return request;
    }
}
