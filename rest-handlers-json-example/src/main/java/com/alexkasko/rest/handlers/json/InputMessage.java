package com.alexkasko.rest.handlers.json;

/**
 * Json input message example
 *
 * @author alexkasko
 * Date: 11/14/12
 */
public class InputMessage {
    private String text;

    /**
     * Contructor for Gson
     */
    private InputMessage() {
    }

    /**
     * Constructor for clients
     *
     * @param text some message
     */
    public InputMessage(String text) {
        this.text = text;
    }

    /**
     * Returns message text
     *
     * @return message text
     */
    public String getText() {
        return text;
    }
}
