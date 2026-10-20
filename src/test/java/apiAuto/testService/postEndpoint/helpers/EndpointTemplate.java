package apiAuto.testService.postEndpoint.helpers;

import apiAuto.common.config.Props;
import apiAuto.common.helpers.RequestTemplate;
import apiAuto.testService.postEndpoint.config.EndpointSpecs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class EndpointTemplate {
    public static Response endpointTemplate(String endpoint,
                                            String token,
                                            String action,
                                            int httpStatus){
        return RequestTemplate.post(EndpointSpecs.requestSpec(Props.API_KEY),
                endpoint,
                token,
                action,
                httpStatus);
    }

    public static Response endpointTemplate(RequestSpecification spec,
                                            String endpoint,
                                            Map<String, String> formParams,
                                            int httpStatus) {
        return RequestTemplate.post(spec,
                endpoint,
                formParams,
                httpStatus);
    }
}


