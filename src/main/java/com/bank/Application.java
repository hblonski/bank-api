package com.bank;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.net.InetSocketAddress;

public class Application {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        final Server server = new Server(new InetSocketAddress(HOST, PORT));
        server.setHandler(createContextHandler());

        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

    private static ServletContextHandler createContextHandler() {
        ServletContextHandler handler = new ServletContextHandler();

        // TODO add servlets

        return handler;
    }
}
