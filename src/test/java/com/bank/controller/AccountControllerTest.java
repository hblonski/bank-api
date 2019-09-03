package com.bank.controller;

import com.bank.BaseTest;
import com.bank.dto.AccountDTO;
import com.bank.service.AccountService;
import com.google.gson.Gson;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.http.HttpResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest extends BaseTest {

    private static final String CONTROLLER_PATH = "account";
    private static final String SAVE_ACC_PATH = CONTROLLER_PATH + "/create";

    @Mock
    private AccountService accountService;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig()
                .register(AccountController.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(accountService).to(AccountService.class);
                    }
                });
    }

    @Test
    public void should_returnAccountInfo_when_AccountCreated() throws IOException, InterruptedException {
        Long clientId = 1L;
        AccountDTO saved = mockAccountDTO();
        saved.setId(clientId);
        String idParam = "?clientId=" + clientId;
        when(accountService.create(any())).thenReturn(saved);
        HttpResponse response = postHttpRequest(SAVE_ACC_PATH + idParam, new Gson().toJson(saved));
        assertEquals(Response.Status.CREATED.getStatusCode(), response.statusCode());
        assertEquals(saved.getId(), new Gson().fromJson(response.body().toString(), AccountDTO.class).getId());
    }

    @Test
    public void should_returnErrorMessage_when_clientNotFound() throws Exception {
        String errorMessage = "not found";
        Long clientId = 1L;
        String idParam = "?clientId=" + clientId;
        when(accountService.create(any())).thenThrow(new EntityNotFoundException(errorMessage));
        sendPostRequestAndVerifyError(errorMessage, Response.Status.NOT_FOUND, SAVE_ACC_PATH + idParam, mockAccountDTO());
    }

    @Test
    public void should_returnErrorMessage_when_clientAlreadyHasAccount() throws Exception {
        String errorMessage = "already has account";
        Long clientId = 1L;
        String idParam = "?clientId=" + clientId;
        when(accountService.create(any())).thenThrow(new EntityExistsException(errorMessage));
        sendPostRequestAndVerifyError(errorMessage, Response.Status.BAD_REQUEST, SAVE_ACC_PATH + idParam, mockAccountDTO());
    }
}
