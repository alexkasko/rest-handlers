package com.alexkasko.rest.handlers.json;

import com.alexkasko.rest.handlers.HandlersDispatcher;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * User: alexkasko
 * Date: 11/14/12
 */
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        HandlersDispatcher dispatcher = HandlersDispatcher.builder()
                .addPost("^/echo$", new JsonTransportHandler(), EchoHandler.class)
                .build();
        sce.getServletContext().setAttribute("dispatcher", dispatcher);
        System.out.println("HandlersDispatcher initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
