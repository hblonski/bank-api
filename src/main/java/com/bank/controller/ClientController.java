package com.bank.controller;

import com.bank.dto.ClientDTO;
import com.bank.service.ClientService;
import com.bank.util.ResponseUtil;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/client")
public class ClientController {

    @Inject
    private ClientService clientService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(ClientDTO client) {
        try {
            ClientDTO saved = clientService.save(client);
            return ResponseUtil.handleSaved(saved);
        } catch (ConstraintViolationException e) {
            return ResponseUtil.handleConstraintViolationException(e);
        }
    }
}
