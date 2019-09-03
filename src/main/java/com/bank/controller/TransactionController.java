package com.bank.controller;

import com.bank.data.value.BankProperties;
import com.bank.data.value.TransactionType;
import com.bank.dto.TransactionDTO;
import com.bank.exception.TransactionException;
import com.bank.service.TransactionService;
import com.bank.util.ResponseUtil;

import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/transactions")
public class TransactionController {

    @Inject
    private TransactionService transactionService;

    @POST
    @Path("/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response transfer(TransactionDTO transactionDTO) {
        transactionDTO.setType(TransactionType.TRANSFER);
        try {
            TransactionDTO transfer = transactionService.transfer(transactionDTO);
            return ResponseUtil.handleGenericResponse(transfer, Response.Status.OK);
        } catch (TransactionException e) {
            return ResponseUtil.handleGenericException(e, Response.Status.BAD_REQUEST);
        } catch (ConstraintViolationException e) {
            return ResponseUtil.handleConstraintViolationException(e);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.handleGenericException(e, Response.Status.NOT_FOUND);
        }
    }

    @POST
    @Path("/deposit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deposit(TransactionDTO transactionDTO) {
        transactionDTO.setType(TransactionType.DEPOSIT);
        try {
            transactionDTO.setDestinationAccountBank(BankProperties.BANK_CODE);
            TransactionDTO deposit = transactionService.deposit(transactionDTO);
            return ResponseUtil.handleGenericResponse(deposit, Response.Status.OK);
        } catch (TransactionException e) {
            return ResponseUtil.handleGenericException(e, Response.Status.BAD_REQUEST);
        } catch (ConstraintViolationException e) {
            return ResponseUtil.handleConstraintViolationException(e);
        }
    }
}
