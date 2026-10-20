package apiAuto.testService.test;

import apiAuto.common.config.Props;
import apiAuto.common.helpers.AppRunner;
import apiAuto.testService.postEndpoint.config.EndpointSpecs;
import apiAuto.common.constants.HttpStatus;
import apiAuto.common.constants.Schemas;
import apiAuto.testService.postEndpoint.constants.*;
import apiAuto.common.helpers.DataGenerator;
import apiAuto.testService.postEndpoint.helpers.EndpointTemplate;
import apiAuto.common.helpers.WireMockHelper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("Внутренний сервис")
@Feature("POST /endpoint")
public class PostEndpointTest {
    @BeforeAll
    static void stubStart() {
        WireMockHelper.start();
        AppRunner.start();
    }

    @AfterAll
    static void stubStop() {
        WireMockHelper.stop();
        AppRunner.stop();
    }

    /**
     * ==================== ПОЗИТИВНЫЕ ТЕСТЫ ====================
     */
    @Nested
    @DisplayName("POST /endpoint. PositiveTests")
    class PositiveTests {
        @BeforeEach
        void resetMock() {
            WireMockHelper.reset();
            WireMockHelper.stub(HttpStatus.OK, StubEndpoint.ENDPOINT_AUTH);
        }

        private String getToken() {
            String token = DataGenerator.generateToken();

            EndpointTemplate.endpointTemplate(Endpoints.ENDPOINT,
                    token,
                    Action.ACTION_LOGIN,
                    HttpStatus.OK);

            return token;
        }

        private static final String SUCCESS_SCHEMA = Schemas.SUCCESS_SCHEMA;

        @Test
        @DisplayName("Case 1.1: Аутентификация пользователя")
        void actionLogin() {
            String token = DataGenerator.generateToken();

            EndpointTemplate.endpointTemplate(Endpoints.ENDPOINT,
                            token,
                            Action.ACTION_LOGIN,
                            HttpStatus.OK)
                    .then()
                    .body(matchesJsonSchemaInClasspath(SUCCESS_SCHEMA));
        }

        @Test
        @DisplayName("Case 1.2: Отправка запроса на действие")
        void actionAction() {
            WireMockHelper.stub(HttpStatus.OK, StubEndpoint.ENDPOINT_DO_ACTION);
            String token = getToken();

            EndpointTemplate.endpointTemplate(Endpoints.ENDPOINT,
                            token,
                            Action.ACTION_ACTION,
                            HttpStatus.OK)
                    .then()
                    .body(matchesJsonSchemaInClasspath(SUCCESS_SCHEMA));
        }

        @Test
        @DisplayName("Case 1.3: Завершение сессии пользователя")
        void actionLogout() {
            String token = getToken();

            EndpointTemplate.endpointTemplate(Endpoints.ENDPOINT,
                            token,
                            Action.ACTION_LOGOUT,
                            HttpStatus.OK)
                    .then()
                    .body(matchesJsonSchemaInClasspath(SUCCESS_SCHEMA));
        }
    }

    /**
     * ==================== НЕГАТИВНЫЕ ТЕСТЫ ====================
     */
    @Nested
    @DisplayName("POST /endpoint. NegativeTests")
    class NegativeTests {
        private static final String ERROR_SCHEMA = Schemas.ERROR_SCHEMA;

        @Test
        @DisplayName("Case 2.1: Действие LOGIN. В параметрах запроса отсутствует api-key (401) ")
        void notApiKey() {
            EndpointTemplate.endpointTemplate(EndpointSpecs.requestSpecNotApiKey(),
                            Endpoints.ENDPOINT,
                            Map.of("token", DataGenerator.generateToken(), "action", Action.ACTION_LOGIN),
                            HttpStatus.UNAUTHORIZED)
                    .then()
                    .body(matchesJsonSchemaInClasspath(ERROR_SCHEMA));
        }

        @Test
        @DisplayName("Case 2.2: Действие LOGIN. В параметрах запроса невалидный формат token (400)")
        void invalidFormatToken() {
            EndpointTemplate.endpointTemplate(EndpointSpecs.requestSpec(Props.API_KEY),
                            Endpoints.ENDPOINT,
                            Map.of("token", DataGenerator.generateToken("ABCDEF0123456789", 31), "action", Action.ACTION_LOGIN),
                            HttpStatus.BAD_REQUEST)
                    .then()
                    .body(matchesJsonSchemaInClasspath(ERROR_SCHEMA));
        }

        @Test
        @DisplayName("Case 2.3: Действие LOGIN. В параметрах запроса отсутствует token (400)")
        void notToken() {
            EndpointTemplate.endpointTemplate(EndpointSpecs.requestSpec(Props.API_KEY),
                            Endpoints.ENDPOINT,
                            Map.of("action", Action.ACTION_LOGIN),
                            HttpStatus.BAD_REQUEST)
                    .then()
                    .body(matchesJsonSchemaInClasspath(ERROR_SCHEMA));
        }

        @Test
        @DisplayName("Case 2.4: В запросе отсутствует параметр action (400)")
        void notAction() {
            EndpointTemplate.endpointTemplate(EndpointSpecs.requestSpec(Props.API_KEY),
                            Endpoints.ENDPOINT,
                            Map.of("token", DataGenerator.generateToken()),
                            HttpStatus.BAD_REQUEST)
                    .then()
                    .body(matchesJsonSchemaInClasspath(ERROR_SCHEMA));
        }

        @Test
        @DisplayName("Case 2.5: Действие ACTION. В параметрах запроса токен, который не прошел  LOGIN (403)")
        void ActionTokenNotSystem() {
            EndpointTemplate.endpointTemplate(EndpointSpecs.requestSpec(Props.API_KEY),
                            Endpoints.ENDPOINT,
                            Map.of("token", DataGenerator.generateToken(), "action", Action.ACTION_ACTION),
                            HttpStatus.FORBIDDEN)
                    .then()
                    .body(matchesJsonSchemaInClasspath(ERROR_SCHEMA));
        }

        @Test
        @DisplayName("Case 2.6: Действие LOGOUT. В параметрах запроса токен, который не прошел  LOGIN (403)")
        void LogoutTokenNotSystem() {
            EndpointTemplate.endpointTemplate(EndpointSpecs.requestSpec(Props.API_KEY),
                            Endpoints.ENDPOINT,
                            Map.of("token", DataGenerator.generateToken(), "action", Action.ACTION_LOGOUT),
                            HttpStatus.FORBIDDEN)
                    .then()
                    .body(matchesJsonSchemaInClasspath(ERROR_SCHEMA));
        }
    }
}
