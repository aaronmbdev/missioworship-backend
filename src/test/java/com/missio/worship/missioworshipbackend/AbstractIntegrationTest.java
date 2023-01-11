package com.missio.worship.missioworshipbackend;

import com.missio.worship.missioworshipbackend.testing.containers.MySQL;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ContextConfiguration(
        initializers = {
                MySQL.Initializer.class,
        })
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {
        public static final MySQL.OperationsMySQLContainer mysqlServer = MySQL.server;
        @BeforeAll
        public static void setUpAll() {
                mysqlServer.start();
        }

        @AfterAll
        public static void tearDownAll() {
                mysqlServer.stop();
        }
}
