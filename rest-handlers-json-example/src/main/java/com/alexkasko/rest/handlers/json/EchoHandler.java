package com.alexkasko.rest.handlers.json;

/**
 * {@link JsonHandler} example implementation, return input message untouched
 *
 * @author alexkasko
 * Date: 11/14/12
 */
public class EchoHandler implements JsonHandler<InputMessage, OutputMessage> {

    /**
     * Says hello back untouched
     *
     * @param input input object parsed from json
     * @return input message untouched
     */
    @Override
    public OutputMessage handle(InputMessage input) {
        return new OutputMessage(input.getText());
    }

    /**
     * Returns {@link InputMessage} class to be instantiated by transport handler
     *
     * @return {@link InputMessage} class
     */
    @Override
    public Class<InputMessage> inputClass() {
        return InputMessage.class;
    }
}
