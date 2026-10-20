package apiAuto.common.helpers;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Шаблоны HTTP-запросов
 */
public class RequestTemplate {
public static Response post(RequestSpecification spec,
                                    String endpoint,
                                    String token,
                                    String action,
                                    int httpStatus) {
        Response response = given(spec)
                .formParam("token", token)
                .formParam("action", action)
                .when().post(endpoint)
                .then().extract().response();

        HttpAssert.httpStatusAssert(response, httpStatus);

        return response;
    }

    public static Response post(RequestSpecification spec,
                                String endpoint,
                                Map<String, String> formParams,
                                int httpStatus) {
        Response response = given(spec)
                .formParams(formParams)
                .when().post(endpoint)
                .then().extract().response();
        HttpAssert.httpStatusAssert(response, httpStatus);

        return response;
    }
}