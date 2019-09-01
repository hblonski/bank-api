package com.bank.controller;

import com.bank.dto.AccountDTO;
import com.bank.service.AccountService;
import com.bank.util.ResponseUtil;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@QueryParam("clientId") Long clientId, AccountDTO accountDTO) {
        try {
            AccountDTO saved = accountService.save(clientId, accountDTO);
            return ResponseUtil.handleSaved(saved);
        } catch (ConstraintViolationException e) {
            return ResponseUtil.handleConstraintViolationException(e);
        } catch (EntityNotFoundException e) {
            return ResponseUtil.handleGenericException(e, Response.Status.NOT_FOUND);
        } catch (EntityExistsException e) {
            return ResponseUtil.handleGenericException(e, Response.Status.BAD_REQUEST);
        }
    }
}
