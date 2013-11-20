package com.alexkasko.rest.handlers;

import com.google.code.regexp.NamedMatcher;
import com.google.code.regexp.NamedPattern;

/**
 * Handler URL mapping entry, used in {@link com.alexkasko.rest.handlers.HandlersDispatcher#builder()}.
 * Made public for mappings introspection in {@link NotFoundHandler}
 *
 * @author alexkasko
 * Date: 11/14/12
 * @see HandlersDispatcher.Builder
 */
public class HandlersMappingEntry<T extends RestHandler> {
    private final NamedPattern pattern;
    private final TransportHandler<T> ra;
    private final Class<T> clazz;

    /**
     * Package private constructor
     *
     * @param pattern named regex pattern
     * @param th transport handler
     * @param clazz app handler class
     */
    HandlersMappingEntry(String pattern, TransportHandler<T> th, Class<T> clazz) {
        if(null == pattern) throw new IllegalArgumentException("Provided pattern is null");
        if(null == th) throw new IllegalArgumentException("Provided transport handler is null");
        if(null == clazz) throw new IllegalArgumentException("Provided handler class is null");
        this.pattern = NamedPattern.compile(pattern);
        this.ra = th;
        this.clazz = clazz;
    }

    /**
     * Constructor for creating new entries from existing ones with additional pattern prefix
     *
     * @param prefix pattern prefix
     * @param en child mapping entry
     */
    HandlersMappingEntry(String prefix, HandlersMappingEntry<T> en) {
        if (null == prefix) throw new IllegalArgumentException("Provided prefix is null");
        if (null == en) throw new IllegalArgumentException("Provided entry is null");
        this.pattern = NamedPattern.compile(prefix + en.pattern.namedPattern());
        this.ra = en.ra;
        this.clazz = en.clazz;
    }

    /**
     * Matched request path info
     *
     * @param input request path info
     * @return matcher
     */
    NamedMatcher matcher(String input) {
        return pattern.matcher(input);
    }

    /**
     * Returns named regex pattern string
     *
     * @return named regex pattern as it was provided to {@link HandlersDispatcher.Builder}
     */
    public String getPattern() {
        return pattern.namedPattern();
    }

    /**
     * Returns transport handler
     *
     * @return transport handler
     */
    public TransportHandler<T> getTransportHandler() {
        return ra;
    }

    /**
     * Returns app handler class
     *
     * @return handler class
     */
    public Class<T> getRestHandlerClass() {
        return clazz;
    }
}

