package com.alexkasko.rest.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Used when no handler can be found for provided request
 *
 * @author alexkasko
 * Date: 11/14/12
 */
public interface NotFoundHandler {
    /**
     * Implementation may send 404 to client
     *
     * @param dispatcher handlers dispatcher
     * @param request request
     * @param response response
     * @throws Exception on any exception
     */
    void handle(HandlersDispatcher dispatcher, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
