package com.alexkasko.rest.handlers.json;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: alexkasko
 * Date: 11/14/12
 */

public class JsonHandlersIntegrationTest {
    @Test
    public void test() {
        OutputMessage response = new JsonClient().access(
                "http://127.0.0.1:8080/rest-handlers-json-example/echo", new InputMessage("hello"), OutputMessage.class);
        assertEquals("Response fail", "hello", response.getText());
    }
}
