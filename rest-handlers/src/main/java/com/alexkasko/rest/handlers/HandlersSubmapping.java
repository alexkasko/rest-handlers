package com.alexkasko.rest.handlers;

import java.util.ArrayList;
import java.util.List;

/**
 * List of handlers mappings for submodules configuration
 *
 * @author alexkasko
 * Date: 7/18/13
 */
public class HandlersSubmapping {
    private final List<HandlersMappingEntry<? extends RestHandler>> mappingGet = new ArrayList<HandlersMappingEntry<? extends RestHandler>>();
    private final List<HandlersMappingEntry<? extends RestHandler>> mappingPost = new ArrayList<HandlersMappingEntry<? extends RestHandler>>();
    private final List<HandlersMappingEntry<? extends RestHandler>> mappingPut = new ArrayList<HandlersMappingEntry<? extends RestHandler>>();

    /**
     * Registers handler for {@code GET} requests
     *
     * @param pattern          named regex pattern
     * @param transportHandler transport handler instance
     * @param appHandlerClass  app handler class
     * @param <T>              app handler type
     * @return builder itself
     */
    @SuppressWarnings("unchecked")
    public <T extends RestHandler> HandlersSubmapping addGet(String pattern, TransportHandler<T> transportHandler,
                                                              Class<? extends T> appHandlerClass) {
        mappingGet.add(new HandlersMappingEntry<T>(pattern, transportHandler, (Class) appHandlerClass));
        return this;
    }

    /**
     * Registers handler for {@code POST} requests
     *
     * @param pattern          named regex pattern
     * @param transportHandler transport handler instance
     * @param appHandlerClass  app handler class
     * @param <T>              app handler type
     * @return builder itself
     */
    @SuppressWarnings("unchecked")
    public <T extends RestHandler> HandlersSubmapping addPost(String pattern, TransportHandler<T> transportHandler,
                                                               Class<? extends T> appHandlerClass) {
        mappingPost.add(new HandlersMappingEntry<T>(pattern, transportHandler, (Class) appHandlerClass));
        return this;
    }

    /**
     * Registers handler for {@code PUT} requests
     *
     * @param pattern          named regex pattern
     * @param transportHandler transport handler instance
     * @param appHandlerClass  app handler class
     * @param <T>              app handler type
     * @return builder itself
     */
    @SuppressWarnings("unchecked")
    public <T extends RestHandler> HandlersSubmapping addPut(String pattern, TransportHandler<T> transportHandler,
                                                              Class<? extends T> appHandlerClass) {
        mappingPut.add(new HandlersMappingEntry<T>(pattern, transportHandler, (Class) appHandlerClass));
        return this;
    }

    List<HandlersMappingEntry<? extends RestHandler>> getMappingGet() {
        return mappingGet;
    }

    List<HandlersMappingEntry<? extends RestHandler>> getMappingPost() {
        return mappingPost;
    }

    List<HandlersMappingEntry<? extends RestHandler>> getMappingPut() {
        return mappingPut;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("HandlersMappingList");
        sb.append("{mappingGet=").append(mappingGet);
        sb.append(", mappingPost=").append(mappingPost);
        sb.append(", mappingPut=").append(mappingPut);
        sb.append('}');
        return sb.toString();
    }
}
