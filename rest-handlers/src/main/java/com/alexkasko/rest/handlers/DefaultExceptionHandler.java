package com.alexkasko.rest.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * Default {@link ExceptionHandler} implementation, returns plain-text 500 with stacktrace
 *
 * @author alexkasko
 * Date: 11/14/12
 */
public class DefaultExceptionHandler implements ExceptionHandler {
    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(Exception e, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/plain");
            response.setStatus(SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Exception thrown on processing request, path: '" + request.getPathInfo() + "'\n");
            e.printStackTrace(response.getWriter());
        } catch(Exception e1) {
            System.err.println("Exception thrown from 'ExceptionHandler':");
            e1.printStackTrace();
            System.err.println("Source handlers exception:");
            e.printStackTrace();
        }
    }
}
