package apiAuto.common.config;

import apiAuto.common.helpers.PropertiesHelper;


public final class Props {
    public static final String BASE_URI = PropertiesHelper.props.getProperty("base.url");

    public static final String APP_SECRET = PropertiesHelper.props.getProperty("app.secret");

    public static final String API_KEY = PropertiesHelper.props.getProperty("api.key");

    public static final int HTTP_CONNECTION_TIMEOUT = Integer.parseInt(
            System.getProperty(
                    "http.connection.timeout", PropertiesHelper.props.getProperty(
                            "http.connection.timeout")));

    public static final int HTTP_SOCKET_TIMEOUT = Integer.parseInt(System.getProperty(
            "http.socket.timeout", PropertiesHelper.props.getProperty(
                    "http.socket.timeout")));
}
