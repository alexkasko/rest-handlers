package com.alexkasko.rest.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Used on exception happened during request processing
 *
 * @author alexkasko
 * Date: 11/14/12
 */
public interface ExceptionHandler {
    /**
     * Implementation may report exception to client.
     * Must not throw runtime exception.
     *
     * @param e exception happened during request processing
     * @param request request
     * @param response response
     */
    void handle(Exception e, HttpServletRequest request, HttpServletResponse response);
}
