package com.bank;

import com.bank.dto.ClientDTO;
import com.google.gson.Gson;
import org.glassfish.jersey.test.JerseyTest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseTest extends JerseyTest {

    // Default base URI used by jetty-test
    private final static String BASE_URI = "http://localhost:9998/";

    protected HttpResponse postHttpRequest(String path, String body) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(UriBuilder.fromUri(BASE_URI + path).build())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    protected String validMockJson() {
        return new Gson().toJson(mockClientDTO());
    }

    protected ConstraintViolationException mockViolationException(String message) {
        ConstraintViolation violation = mock(ConstraintViolation.class);
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        violations.add(violation);
        when(violation.getMessage()).thenReturn(message);
        return new ConstraintViolationException(violations);
    }

    protected ClientDTO mockClientDTO() {
        ClientDTO mockClient = new ClientDTO();
        mockClient.setId(1L);
        mockClient.setAddress("test");
        mockClient.setCity("test");
        mockClient.setCountry("test");
        mockClient.setDocumentNumber("test");
        mockClient.setName("test");
        mockClient.setPhone("test");
        mockClient.setProfession("test");
        mockClient.setState("test");
        return mockClient;
    }
}
