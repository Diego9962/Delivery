package sv.edu.ues.delivery.boundary;

import java.nio.file.Paths;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class BaseIntegrationTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseIntegrationTest.class);
    static Network red = Network.newNetwork();
    private static final String ROOT_PROJECT = Paths.get("").toAbsolutePath().toString();
    private static final String POSTGRES_DRIVER = Paths.get("postgresql-42.5.3.jar").toAbsolutePath().toString();
    private static final String PAYARA_INIT_COMMANDS = Paths.get("init.sh").toAbsolutePath().toString();
    protected static String payaraHost;
    protected static Integer payaraPort;

    @Container
    protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("delivery")
            .withPassword("root")
            .withUsername("postgres")
            .withNetwork(red)
            .withNetworkAliases("db");

    @Container
    protected static GenericContainer<?> payara = new
    GenericContainer<>(DockerImageName.parse("payara/server-full:6.2023.3-jdk17"))
    .dependsOn(postgres)
    .withNetwork(red)
    .withFileSystemBind(ROOT_PROJECT + "/", "/delivery-app", BindMode.READ_ONLY)
    .withFileSystemBind(POSTGRES_DRIVER, "/opt/payara/appserver/glassfish/domains/domain1/lib/postgresql-42.5.3.jar", BindMode.READ_ONLY)
    .withFileSystemBind(PAYARA_INIT_COMMANDS, "/opt/init.sh")
    .withCommand("bash", "-c", "/opt/init.sh")
    .withExposedPorts(8080,4848)
    .withLogConsumer(new Slf4jLogConsumer(LOGGER))
    .withStartupTimeout(Duration.ofMinutes(3))
    .waitingFor(Wait.forHttp("/delivery/hello").forStatusCode(200));

}
