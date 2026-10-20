package apiAuto.common.config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.filter.log.LogDetail;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.baseURI;

public class BaseSpecs {
    static {
        baseURI = Props.BASE_URI;
    }

    public static final AllureRestAssured allureFilter = new AllureRestAssured();

    public static RequestSpecBuilder baseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setBasePath(basePath)
                .log(LogDetail.ALL)
                .addFilter(allureFilter)
                .setConfig(RestAssured.config()
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", Props.HTTP_CONNECTION_TIMEOUT)
                                .setParam("http.socket.timeout", Props.HTTP_SOCKET_TIMEOUT)));
    }
}
