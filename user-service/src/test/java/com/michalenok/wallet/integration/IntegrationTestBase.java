package com.michalenok.wallet.integration;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@SqlGroup({
        @Sql(scripts = "classpath:sql/data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:sql/clear_data.sql", executionPhase = AFTER_TEST_METHOD)})
public abstract class IntegrationTestBase {
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres"))
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("root");

    @BeforeAll
    static void runContainer(){
        postgres.start();
    }
}