package com.bank.servlet;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class BankApi extends ResourceConfig {

    public static final String PATH = "/*";

    public BankApi() {
        packages("com.bank.controller");
        register(new ApplicationBinder());
    }
}
