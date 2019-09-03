package com.bank.controller;

import com.bank.dto.AccountDTO;
import com.bank.service.AccountService;
import com.bank.util.ResponseUtil;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
public class AccountController {

    @Inject
    private AccountService accountService;

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@QueryParam("clientId") Long clientId) {
        try {
            AccountDTO saved = accountService.create(clientId);
            return ResponseUtil.handleGenericResponse(saved, Response.Status.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.handleGenericException(e, Response.Status.NOT_FOUND);
        } catch (EntityExistsException e) {
            return ResponseUtil.handleGenericException(e, Response.Status.BAD_REQUEST);
        }
    }

    @GET
    @Path("/{number}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("number") String accountNumber) {
        try {
            AccountDTO account = accountService.get(accountNumber);
            if (account == null) {
                return ResponseUtil.handleGenericResponse("Account not found.", Response.Status.NOT_FOUND);
            }
            return ResponseUtil.handleGenericResponse(account, Response.Status.OK);
        } catch (ConstraintViolationException e) {
            return ResponseUtil.handleConstraintViolationException(e);
        }
    }
}
