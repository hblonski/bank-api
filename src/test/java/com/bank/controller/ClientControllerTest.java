package com.bank.controller;

import com.bank.BaseTest;
import com.bank.dto.ClientDTO;
import com.bank.service.ClientService;
import com.google.gson.Gson;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientControllerTest extends BaseTest {

    private static final String CONTROLLER_PATH = "client";

    @Mock
    private ClientService clientService;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig()
                .register(ClientController.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(clientService).to(ClientService.class);
                    }
                });
    }

    @Test
    public void should_returnCreatedClientInfo_when_clientCreated() throws IOException, InterruptedException {
        ClientDTO created = mockClientDTO();
        when(clientService.save(any())).thenReturn(created);
        HttpResponse response = postHttpRequest(CONTROLLER_PATH + "/create", new Gson().toJson(mockClientDTO()));
        assertEquals(Response.Status.CREATED.getStatusCode(), response.statusCode());
        assertEquals(created.getId(), new Gson().fromJson(response.body().toString(), ClientDTO.class).getId());
    }

    @Test
    public void should_returnErrorMessage_when_validationErrorsInCreation() throws IOException, InterruptedException {
        String violationMessage = "violation";
        ConstraintViolationException exception = mockViolationException(violationMessage);
        when(clientService.save(any())).thenThrow(exception);
        HttpResponse response = postHttpRequest(CONTROLLER_PATH + "/create", new Gson().toJson(mockClientDTO()));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.statusCode());
        assertEquals(violationMessage, response.body().toString());
    }
}
