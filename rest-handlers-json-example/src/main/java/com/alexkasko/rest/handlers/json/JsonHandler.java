package com.alexkasko.rest.handlers.json;

import com.alexkasko.rest.handlers.RestHandler;

/**
 * Example of {@link RestHandler} implementation,
 * request and response are deliberately removed as transport specific.
 *
 * @author alexkasko
 * Date: 11/14/12
 */
public interface JsonHandler<I, O> extends RestHandler {

    /**
     * App specific request processing
     *
     * @param input input object parsed from json
     * @return output object
     */
    O handle(I input);

    /**
     * Returns input object class
     *
     * @return input object class
     */
    Class<I> inputClass();
}
