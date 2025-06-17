package comsystem.initializer;

import com.comsystem.ComsystemApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(classes = ComsystemApplication.class)
@Testcontainers
public class IntegrationTestInitializer {

    static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16.2")
                    .withDatabaseName("test_db")
                    .withUsername("testuser")
                    .withPassword("testpass");

    static {
        postgres.start();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
    }
}
