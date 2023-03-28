package com.example.cloudStorage;

import com.example.cloudStorage.security.SecurityConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@SpringBootTest(classes = TestConfig.class)
class IntegrationTests {

	// because of usage mokDb connection from testConfig during running tests (real connection has docker container)
	// I can not use autowiring of TestRestTemplate restTemplate with classical solution
	// webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, so I will use full version of RestTemplate

	// one has to use .env config (or ci/cd configs) or set run configuration with envs
	static String DB_URL = System.getenv("DB_URL");
	static String DB_USERNAME = System.getenv("DB_USERNAME");
	static String DB_PASSWORD = System.getenv("DB_PASSWORD");
	static String SECRET = System.getenv("SECRET_KEY");
    static String userTest = "USER_TEST";
    static String passwordTest = "PASSWORD_TEST";

	static Map<String, String> envMap = Map.of(
			"DB_URL", DB_URL,
			"DB_USERNAME", DB_USERNAME,
			"DB_PASSWORD", DB_PASSWORD,
			"SECRET_KEY", SECRET);

	// one has to have previously built docker image locally from the project and run pg db to run that test
	private static final GenericContainer<?> container = new GenericContainer<>("cloud-storage:latest")
			.withExposedPorts(8081).withEnv(envMap);
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeAll
	public static void setUp() {
		container.start();
        // create test user
		// given:
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(
				Map.of("username", userTest, "password", passwordTest), headers);

		// when:
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + container.getMappedPort(8081) + SecurityConstants.SIGN_UP_URL,
				entity, String.class);

		// then:
		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
	}

    @Test
	void testLogin() throws JsonProcessingException {
		// given:
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(
				Map.of("login", userTest, "password", passwordTest), headers);

		// when:
		ResponseEntity<String> response = restTemplate.postForEntity(
				"http://localhost:" + container.getMappedPort(8081) + "/login",
				entity, String.class);
		JsonNode root = objectMapper.readTree(response.getBody());
		String token = root.path("auth-token").toString();

		// then:
		Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
		// check if token is formed as expected:
		Assertions.assertTrue(token.startsWith("\"Bearer"));
    }

}
