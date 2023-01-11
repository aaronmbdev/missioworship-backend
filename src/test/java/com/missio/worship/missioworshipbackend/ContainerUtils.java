package com.missio.worship.missioworshipbackend;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Assertions;
import org.testcontainers.containers.Container;

import java.time.Duration;

import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class ContainerUtils {
    public static void waitFor(@NotNull Container<?> server, Duration maxWait) {
        try {
            await().pollInterval(Duration.ofMillis(100)).atMost(maxWait).until(server::isRunning);
        } catch (Exception e) {
            Assertions.fail("Server " + server + " is taking too long to start.", e);
        }
    }
}
