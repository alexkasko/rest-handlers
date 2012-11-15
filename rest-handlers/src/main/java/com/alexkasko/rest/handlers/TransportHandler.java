package com.alexkasko.rest.handlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Transport level request handling.
 * Should process request transport (application level transport, not TCP) and
 * delegate application processing to {@link RestHandler}
 *
 * @author alexey
 * Date: 8/9/12
 * @see RestHandler
 */

public interface TransportHandler<T extends RestHandler> {
    /**
     * Processes request transport (application level transport, not TCP) and
     * delegate application processing to {@link RestHandler}
     *
     * @param handlerClass application handler class
     * @param request request
     * @param response response
     * @param urlParams named parameters from request path
     * @throws Exception on any application exception
     */
    void handle(Class<? extends T> handlerClass, HttpServletRequest request, HttpServletResponse response,
                Map<String, String> urlParams) throws Exception;
}
