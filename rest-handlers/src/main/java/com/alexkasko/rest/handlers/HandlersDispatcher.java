package com.alexkasko.rest.handlers;

import com.google.code.regexp.NamedMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * Dispatches request using handlers registry matching with regular expression.
 * Contains separate registries for {@code GET}, {@code POST} and {@code PUT} requests.
 * Registries are ordered in handlers registration order. First matched handler will be used.
 * Dispatcher must be created by application and placed into {@link javax.servlet.ServletContext}
 * using {@code dispatcherKeyInServletContext} parameter of {@link HandlersDispatcherServlet}
 * Handler patterns are registered using <a href="https://github.com/tony19/named-regexp">named-regexes</a>.
 *
 * @author alexey
 *         Date: 4/4/12
 * @see HandlersDispatcherServlet
 */
public class HandlersDispatcher {
    private final List<HandlersMappingEntry<?>> registryGet;
    private final List<HandlersMappingEntry<?>> registryPost;
    private final List<HandlersMappingEntry<?>> registryPut;
    private final List<HandlersMappingEntry<?>> registryDelete;
    private final NotFoundHandler notFoundHandler;
    private final ExceptionHandler exceptionHandler;

    private HandlersDispatcher(List<HandlersMappingEntry<?>> registryGet, List<HandlersMappingEntry<?>> registryPost,
                               List<HandlersMappingEntry<?>> registryPut, List<HandlersMappingEntry<?>> registryDelete,
                               NotFoundHandler notFoundHandler, ExceptionHandler exceptionHandler) {
        this.registryGet = registryGet;
        this.registryPost = registryPost;
        this.registryPut = registryPut;
        this.registryDelete = registryDelete;
        this.notFoundHandler = notFoundHandler;
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * Returns builder for {@code HandlersDispatcher}
     *
     * @return builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Main dispatch method. Chooses registry using request method, than match {@code request.getPathInfo()}
     * against patterns in registry. First matched handler will be used.
     *
     * @param req  request
     * @param resp response
     */
    @SuppressWarnings("unchecked") // generic entries
    public void dispatch(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final List<HandlersMappingEntry<?>> mapping;
            if("GET".equals(req.getMethod())) mapping = registryGet;
            else if("POST".equals(req.getMethod())) mapping = registryPost;
            else if("PUT".equals(req.getMethod())) mapping = registryPut;
            else if("DELETE".equals(req.getMethod())) mapping = registryDelete;
                // cannot happen, methods are filtered in servlet
            else throw new IllegalArgumentException("Unsupported HTTP method: '" + req.getMethod() + "'");
            for(HandlersMappingEntry en : mapping) {
                NamedMatcher matcher = en.matcher(req.getPathInfo());
                if(matcher.matches()) {
                    en.getTransportHandler().handle(en.getRestHandlerClass(), req, resp, matcher.namedGroups());
                    return;
                }
            }
            notFoundHandler.handle(this, req, resp);
        } catch(Exception e) {
            exceptionHandler.handle(e, req, resp);
        }
    }

    /**
     * Returns registry for {@code GET} method
     *
     * @return registry for {@code GET} method
     */
    public List<HandlersMappingEntry<?>> getRegistryGet() {
        return unmodifiableList(registryGet);
    }

    /**
     * Returns registry for {@code POST} method
     *
     * @return registry for {@code POST} method
     */
    public List<HandlersMappingEntry<?>> getRegistryPost() {
        return unmodifiableList(registryPost);
    }

    /**
     * Returns registry for {@code PUT} method
     *
     * @return registry for {@code PUT} method
     */
    public List<HandlersMappingEntry<?>> getRegistryPut() {
        return unmodifiableList(registryPut);
    }

    /**
     * Returns registry for {@code DELETE} method
     *
     * @return registry for {@code DELETE} method
     */
    public List<HandlersMappingEntry<?>> getRegistryDelete() {
        return unmodifiableList(registryDelete);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("HandlersDispatcher");
        sb.append("{registryGet=").append(registryGet);
        sb.append(", registryPost=").append(registryPost);
        sb.append(", registryPut=").append(registryPut);
        sb.append(", registryDelete=").append(registryDelete);
        sb.append(", notFoundHandler=").append(notFoundHandler);
        sb.append(", exceptionHandler=").append(exceptionHandler);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Builder class for {@link HandlersDispatcher}
     */
    public static class Builder {
        private final List<HandlersMappingEntry<?>> mappingGet = new ArrayList<HandlersMappingEntry<?>>();
        private final List<HandlersMappingEntry<?>> mappingPost = new ArrayList<HandlersMappingEntry<?>>();
        private final List<HandlersMappingEntry<?>> mappingPut = new ArrayList<HandlersMappingEntry<?>>();
        private final List<HandlersMappingEntry<?>> mappingDelete = new ArrayList<HandlersMappingEntry<?>>();
        private NotFoundHandler notFoundHandler;
        private ExceptionHandler exceptionHandler;

        /**
         * Constructor
         */
        public Builder() {
        }

        /**
         * Registers handler for {@code GET} requests
         *
         * @param pattern named regex pattern
         * @param transportHandler transport handler instance
         * @param appHandlerClass app handler class
         * @param <T> app handler type
         * @return builder itself
         */
        @SuppressWarnings("unchecked")
        public <T extends RestHandler> Builder addGet(String pattern, TransportHandler<T> transportHandler,
                                                      Class<? extends T> appHandlerClass) {
            mappingGet.add(new HandlersMappingEntry<T>(pattern, transportHandler, (Class) appHandlerClass));
            return this;
        }

        /**
         * Registers handler for {@code POST} requests
         *
         * @param pattern named regex pattern
         * @param transportHandler transport handler instance
         * @param appHandlerClass app handler class
         * @param <T> app handler type
         * @return builder itself
         */
        @SuppressWarnings("unchecked")
        public <T extends RestHandler> Builder addPost(String pattern, TransportHandler<T> transportHandler,
                                                       Class<? extends T> appHandlerClass) {
            mappingPost.add(new HandlersMappingEntry<T>(pattern, transportHandler, (Class) appHandlerClass));
            return this;
        }

        /**
         * Registers handler for {@code PUT} requests
         *
         * @param pattern named regex pattern
         * @param transportHandler transport handler instance
         * @param appHandlerClass app handler class
         * @param <T> app handler type
         * @return builder itself
         */
        @SuppressWarnings("unchecked")
        public <T extends RestHandler> Builder addPut(String pattern, TransportHandler<T> transportHandler,
                                                      Class<? extends T> appHandlerClass) {
            mappingPut.add(new HandlersMappingEntry<T>(pattern, transportHandler, (Class) appHandlerClass));
            return this;
        }

        /**
         * Registers handler for {@code PUT} requests
         *
         * @param pattern named regex pattern
         * @param transportHandler transport handler instance
         * @param appHandlerClass app handler class
         * @param <T> app handler type
         * @return builder itself
         */
        @SuppressWarnings("unchecked")
        public <T extends RestHandler> Builder addDelete(String pattern, TransportHandler<T> transportHandler,
                                                      Class<? extends T> appHandlerClass) {
            mappingDelete.add(new HandlersMappingEntry<T>(pattern, transportHandler, (Class) appHandlerClass));
            return this;
        }


        /**
         * Adds all GET, PUT and DELETE handlers from specified list to this builder
         * prepending specified prefix to all patterns from the list.
         *
         * @param list list of handlers mapping
         * @return builder itself
         */
        @SuppressWarnings("unchecked")
        public Builder addList(String prefix, HandlersSubmapping list) {
            for(HandlersMappingEntry<?> en : list.getMappingGet()) {
                mappingGet.add(new HandlersMappingEntry(prefix, en));
            }
            for(HandlersMappingEntry<?> en : list.getMappingPost()) {
                mappingPost.add(new HandlersMappingEntry(prefix, en));
            }
            for(HandlersMappingEntry<?> en : list.getMappingPut()) {
                mappingPut.add(new HandlersMappingEntry(prefix, en));
            }
            for(HandlersMappingEntry<?> en : list.getMappingDelete()) {
                mappingDelete.add(new HandlersMappingEntry(prefix, en));
            }
            return this;
        }

        /**
         * Sets handler to use when no handler can be found in registry for provided request
         *
         * @param notFoundHandler not-found handler
         * @return builder itself
         */
        public Builder setNotFound(NotFoundHandler notFoundHandler) {
            this.notFoundHandler = notFoundHandler;
            return this;
        }

        /**
         * Sets handler to use on exception happened during request processing
         *
         * @param exceptionHandler exception handler
         * @return builder itself
         */
        public Builder setException(ExceptionHandler exceptionHandler) {
            this.exceptionHandler = exceptionHandler;
            return this;
        }

        /**
         * Builds dispatcher instance
         *
         * @return dispatcher instance
         */
        public HandlersDispatcher build() {
            NotFoundHandler nfh = null != this.notFoundHandler ? this.notFoundHandler : new DefaultNotFoundHandler();
            ExceptionHandler eh = null != this.exceptionHandler ? this.exceptionHandler : new DefaultExceptionHandler();
            return new HandlersDispatcher(mappingGet, mappingPost, mappingPut, mappingDelete, nfh, eh);
        }
    }
}
