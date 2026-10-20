package apiAuto.common.helpers;

import apiAuto.common.config.Props;

public class AppRunner {
    private static Process appProcess;

    public static void start() {
        try {
            appProcess = new ProcessBuilder(
                    "java", "-jar",
                    "-Dsecret=" + Props.APP_SECRET,
                    "-Dmock=http://localhost:8888/",
                    "app/internal-0.0.1-SNAPSHOT.jar"
            ).inheritIO().start();

            Thread.sleep(10000);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void stop() {
        if (appProcess != null) {
            appProcess.destroy();
        }
    }
}
