package com.example.cloudStorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(classes = TestConfig.class)
class IntegrationTests {
//	@Autowired
//	TestRestTemplate restTemplate;

	// one has to use .env config (or ci/cd configs) or set run configuration with envs
	static String DB_URL = System.getenv("DB_URL");
	static String DB_USERNAME = System.getenv("DB_USERNAME");
	static String DB_PASSWORD = System.getenv("DB_PASSWORD");
	static String SECRET = System.getenv("SECRET_KEY");

	static Map<String, String> envMap = Map.of(
			"DB_URL", DB_URL,
			"DB_USERNAME", DB_USERNAME,
			"DB_PASSWORD", DB_PASSWORD,
			"SECRET_KEY", SECRET);

	// one has to have previously built docker image locally from the project and run pg db to run that test
	private static final GenericContainer<?> container = new GenericContainer<>("cloud-storage:latest")
			.withExposedPorts(8081).withEnv(envMap);
	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeAll
	public static void setUp() {
		container.start();
	}

//	@Test
//	void testTransfer() throws JsonProcessingException {
//		//given
//		String requestJson = "{\"cardFromNumber\": \"not_valid\", \"cardFromValidTill\": \"12/25\"," +
//				" \"cardFromCVV\": \"330\", \"cardToNumber\": \"not_valid\"," +
//				"\"amount\": {\"value\": 1000, \"currency\": \"RUR\"}}";
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
//
//		//expected
//		String expectedMessage = "\"Acceptor card is not acceptable for transaction\"";
//
//		//when
//		ResponseEntity<String> response =
//				restTemplate.postForEntity("http://localhost:" + container.getMappedPort(5500) + "/transfer", entity, String.class);
//		JsonNode root = objectMapper.readTree(response.getBody());
//		String realMessage = root.path("message").toString();
//
//		//then
//		Assertions.assertEquals(expectedMessage, realMessage);
//	}
//
//	@Test
//	void testConfirm() throws JsonProcessingException {
//		//given
//		String requestJson = "{\"operationId\": \"no_existed_id\", \"code\": \"wrong_code\"}";
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
//
//		//expected
//		String expectedMessage = "\"there is no such transaction or code is wrong\"";
//
//		//when
//		ResponseEntity<String> response =
//				restTemplate.postForEntity("http://localhost:" + container.getMappedPort(5500) + "/confirmOperation", entity, String.class);
//		JsonNode root = objectMapper.readTree(response.getBody());
//		String realMessage = root.path("message").toString();
//
//		//then
//		Assertions.assertEquals(expectedMessage, realMessage);
//	}

}
