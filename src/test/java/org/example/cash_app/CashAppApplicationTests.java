package org.example.cash_app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashAppApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Container
    private final GenericContainer<?> cash_app_Container = new GenericContainer<>("cash-app:1.0")
            .withExposedPorts(5500);


    @Test
    void contextLoads() {
        Integer port = cash_app_Container.getMappedPort(5500);
        //
        String jsonBody = "{\n" +
                "  \"cardFromNumber\": \"1234567890125622\",\n" +
                "  \"cardFromValidTill\": \"12/25\",\n" +
                "  \"cardFromCVV\": \"123\",\n" +
                "  \"cardToNumber\": \"6543210987654321\",\n" +
                "  \"amount\": {\n" +
                "    \"value\": 100.00,\n" +
                "    \"currency\": \"USD\"\n" +
                "  }\n" +
                "}";
        //
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        //
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        //
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/transfer",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        //
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
