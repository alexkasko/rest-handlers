package com.alexkasko.rest.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;

/**
 * Default implementation of {@link NotFoundHandler}, returns plain-text 404 with
 * registered handlers list.
 *
 * @author alexkasko
 * Date: 11/14/12
 */
public class DefaultNotFoundHandler implements NotFoundHandler {
    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(HandlersDispatcher dispatcher, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain");
        response.setStatus(SC_NOT_FOUND);
        response.getWriter().write("Cannot find handler for request, path: '" + request.getPathInfo() + "'\n");
        response.getWriter().write("Registered handlers:\n");
        for(HandlersMappingEntry en : dispatcher.getRegistryGet()) {
            response.getWriter().write("GET: ");
            response.getWriter().write(en.getPattern());
        }
        for(HandlersMappingEntry en : dispatcher.getRegistryPost()) {
            response.getWriter().write("POST: ");
            response.getWriter().write(en.getPattern());
        }
        for(HandlersMappingEntry en : dispatcher.getRegistryPut()) {
            response.getWriter().write("PUT: ");
            response.getWriter().write(en.getPattern());
        }
    }
}
