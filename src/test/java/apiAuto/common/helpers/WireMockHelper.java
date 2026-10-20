package apiAuto.common.helpers;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WireMockHelper {

    private static WireMockServer mockServer;
    private static final int WIRE_MOCK_PORT = 8888;

    private WireMockHelper() {
    }

    public static void start() {
        mockServer = new WireMockServer(options().port(WIRE_MOCK_PORT));
        mockServer.start();
    }

    public static void stop(){
        if (mockServer != null) {
            mockServer.stop();
            mockServer = null;
        }
    }

    public static void reset() {
        mockServer.resetAll();
    }

    public static void verifyPostTo(String endpoint) {
        mockServer.verify(postRequestedFor(urlEqualTo(endpoint)));
    }

    public static void stub(int statusCode, String stubEndpoint) {
        mockServer.stubFor(post(urlEqualTo(stubEndpoint))
                .willReturn(aResponse().withStatus(statusCode)));
    }
}
