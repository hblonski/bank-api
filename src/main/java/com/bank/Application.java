package com.bank;

import com.bank.servlet.BankApi;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import java.net.InetSocketAddress;

public class Application {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        Server server = createServer();
        try {
            server.start();
            server.join();
        } finally {
            server.destroy();
        }
    }

    public static Server createServer() {
        final Server server = new Server(new InetSocketAddress(HOST, PORT));
        ServletContextHandler handler = new ServletContextHandler();
        ServletHolder servletHolder = new ServletHolder(new ServletContainer(new BankApi()));
        handler.addServlet(servletHolder, BankApi.PATH);
        server.setHandler(handler);
        return server;
    }
}
