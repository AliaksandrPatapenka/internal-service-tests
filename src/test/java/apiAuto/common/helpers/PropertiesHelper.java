package apiAuto.common.helpers;

import java.io.InputStream;
import java.util.Properties;


/**
 * Загрузчик properties-файлов из classpath по профилю (/config/{profile}/{service}.properties) и хранение их в общем поле props
 */

public class PropertiesHelper {
    public static final Properties props = new Properties();

    static {
        load();
    }

    private static void load() {
        try (InputStream in = PropertiesHelper.class.getResourceAsStream("/config/local/app.properties")) {
            if (in == null) {
                throw new IllegalStateException("Не найден properties: " + "/config/local/app.properties");
            }
            props.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка чтения properties: " + "/config/local/app.properties", e);
        }
    }
}
