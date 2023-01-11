package com.missio.worship.missioworshipbackend.testing.containers;

import com.missio.worship.missioworshipbackend.ContainerUtils;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import java.time.Duration;

public class MySQL {

    public static final OperationsMySQLContainer server = new OperationsMySQLContainer();
    public static class OperationsMySQLContainer extends MySQLContainer<OperationsMySQLContainer> {
        public OperationsMySQLContainer() {
            super("mysql:8.0.31-debian");
            this.withReuse(true);
        }
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            MySQL.configProperties(applicationContext);
        }
    }

    private static void configProperties(ConfigurableApplicationContext context) {
        ContainerUtils.waitFor(server, Duration.ofSeconds(10));
        TestPropertyValues.of(
                "spring.datasource.url=" + server.getJdbcUrl(),
                "spring.datasource.username=" + server.getUsername(),
                "spring.datasource.password=" + server.getPassword()
        ).applyTo(context);
    }


}
