package com.bank.controller;

import com.bank.dto.AccountDTO;
import com.bank.service.AccountService;
import com.bank.util.ResponseUtil;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
            return ResponseUtil.handleGenericPostSuccess(saved, Response.Status.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.handleGenericException(e, Response.Status.NOT_FOUND);
        } catch (EntityExistsException e) {
            return ResponseUtil.handleGenericException(e, Response.Status.BAD_REQUEST);
        }
    }
}
