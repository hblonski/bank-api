package com.bank.controller;

import com.bank.BaseTest;
import com.bank.dto.TransactionDTO;
import com.bank.exception.TransactionException;
import com.bank.service.TransactionService;
import com.google.gson.Gson;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.net.http.HttpResponse;
import java.sql.SQLDataException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionControllerTest extends BaseTest {

    private static final String CONTROLLER_PATH = "transactions";
    private static final String TRANSFER_PATH = CONTROLLER_PATH + "/transfer";
    private static final String DEPOSIT_PATH = CONTROLLER_PATH + "/deposit";

    @Mock
    private TransactionService transactionService;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        return new ResourceConfig()
                .register(TransactionController.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(transactionService).to(TransactionService.class);
                    }
                });
    }

    @Test
    public void should_returnTransactionData_when_transferExecuted() throws Exception {
        TransactionDTO transactionDTO = mockTransactionDTO();
        when(transactionService.transfer(any())).thenReturn(transactionDTO);
        HttpResponse response =
                postHttpRequest(TRANSFER_PATH, new Gson().toJson(mockTransactionDTO()));
        assertEquals(Response.Status.OK.getStatusCode(), response.statusCode());
        assertEquals(transactionDTO.getId(),
                     new Gson().fromJson(response.body().toString(), TransactionDTO.class).getId());
    }

    @Test
    public void should_returnErrorMessage_when_transferDatabaseError() throws Exception {
        when(transactionService.transfer(any())).thenThrow(mock(SQLDataException.class));
        HttpResponse response =
                postHttpRequest(TRANSFER_PATH, new Gson().toJson(mockTransactionDTO()));
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.statusCode());
    }

    @Test
    public void should_returnErrorMessage_when_transferTransactionException() throws Exception {
        String errorMessage = "transaction error";
        when(transactionService.transfer(any())).thenThrow(new TransactionException(errorMessage));
        postRequestAndVerifyError(errorMessage, Response.Status.BAD_REQUEST, TRANSFER_PATH, mockTransactionDTO());
    }

    @Test
    public void should_returnErrorMessage_when_transferAccountsNotFound() throws Exception {
        String errorMessage = "accounts not found error";
        when(transactionService.transfer(any())).thenThrow(new EntityNotFoundException(errorMessage));
        postRequestAndVerifyError(errorMessage, Response.Status.NOT_FOUND, TRANSFER_PATH, mockTransactionDTO());
    }

    @Test
    public void should_returnErrorMessage_when_transferConstraintsViolated() throws Exception {
        String errorMessage = "validation";
        ConstraintViolationException e = mockViolationException(errorMessage);
        when(transactionService.transfer(any())).thenThrow(e);
        postRequestAndVerifyError(errorMessage, Response.Status.BAD_REQUEST, TRANSFER_PATH, mockTransactionDTO());
    }

    @Test
    public void should_returnTransactionInfo_when_depositExecuted() throws Exception {
        TransactionDTO transactionDTO = mockTransactionDTO();
        when(transactionService.deposit(any())).thenReturn(transactionDTO);
        HttpResponse response =
                postHttpRequest(DEPOSIT_PATH, new Gson().toJson(mockTransactionDTO()));
        assertEquals(Response.Status.OK.getStatusCode(), response.statusCode());
        assertEquals(transactionDTO.getId(),
                     new Gson().fromJson(response.body().toString(), TransactionDTO.class).getId());
    }

    @Test
    public void should_returnErrorMessage_when_depositConstraintsViolated() throws Exception {
        String errorMessage = "validation";
        ConstraintViolationException e = mockViolationException(errorMessage);
        when(transactionService.deposit(any())).thenThrow(e);
        postRequestAndVerifyError(errorMessage, Response.Status.BAD_REQUEST, DEPOSIT_PATH, mockTransactionDTO());
    }

    @Test
    public void should_returnErrorMessage_when_depositDatabaseError() throws Exception {
        when(transactionService.deposit(any())).thenThrow(mock(SQLDataException.class));
        HttpResponse response =
                postHttpRequest(DEPOSIT_PATH, new Gson().toJson(mockTransactionDTO()));
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.statusCode());
    }

    @Test
    public void should_returnErrorMessage_when_depositTransactionException() throws Exception {
        String errorMessage = "transaction error";
        when(transactionService.deposit(any())).thenThrow(new TransactionException(errorMessage));
        postRequestAndVerifyError(errorMessage, Response.Status.BAD_REQUEST, DEPOSIT_PATH, mockTransactionDTO());
    }
}
