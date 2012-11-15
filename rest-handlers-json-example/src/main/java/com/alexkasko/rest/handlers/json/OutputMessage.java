package com.alexkasko.rest.handlers.json;

/**
 * Json output message example
 *
 * @author alexkasko
 * Date: 11/14/12
 */
public class OutputMessage {
    private String text;

    /**
     * Contructor for Gson
     */
    private OutputMessage() {
    }

    /**
     * Constructor for clients
     *
     * @param text some message
     */
    public OutputMessage(String text) {
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
