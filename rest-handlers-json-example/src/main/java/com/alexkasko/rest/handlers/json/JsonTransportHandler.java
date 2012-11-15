package com.alexkasko.rest.handlers.json;

import com.alexkasko.rest.handlers.TransportHandler;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * TransportHandler implementation for JSON requests and responses
 *
 * @author alexkasko
 * Date: 11/14/12
 */
public class JsonTransportHandler implements TransportHandler<JsonHandler> {
    // may be preconfigured
    private static final Gson gson = new Gson();

    /**
     * Instantiate handler class, got input class form it, parses input object from request,
     * gives it to handlers, writes results to response as JSON
     *
     * @param handlerClass application handler class
     * @param request request
     * @param response response
     * @param urlParams named parameters from request path
     * @throws Exception on any app exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public void handle(Class<? extends JsonHandler> handlerClass, HttpServletRequest request,
                       HttpServletResponse response, Map<String, String> urlParams) throws Exception {
        // set proper content-type
        response.setContentType("application/json");
        // obtain handler instance, may be singleton, from DI etc
        JsonHandler ha = handlerClass.newInstance();
        // parse input object from request body
        Object in = gson.fromJson(request.getReader(), ha.inputClass());
        // fire handler
        Object out = ha.handle(in);
        // write results to client
        gson.toJson(out, response.getWriter());
    }
}
