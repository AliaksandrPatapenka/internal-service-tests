package apiAuto.testService.postEndpoint.config;


import io.restassured.specification.RequestSpecification;

import static apiAuto.common.config.BaseSpecs.baseRequestSpec;


public class EndpointSpecs {

    public static RequestSpecification requestSpec(String apiKey){
        return baseRequestSpec()
                .setContentType("application/x-www-form-urlencoded")
                .setAccept("application/json")
                .addHeader("X-Api-Key", apiKey)
                .build();
    }

    public static RequestSpecification requestSpecNotApiKey(){
        return baseRequestSpec()
                .setContentType("application/x-www-form-urlencoded")
                .setAccept("application/json")
                .build();
    }
}
