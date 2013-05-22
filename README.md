Library for creating REST services
==================================

_Note: if you are happy with JAX-RS (JSR311) you probably don't need this library_

Thin library over servlet API, eases request dispatching with regular expressions. Uses extended
regular expression library with named groups support (for JDK6) to create clean URLs.

Library depends on [named-regexp](https://github.com/tony19/named-regexp).

Javadocs for the latest release are available [here](http://alexkasko.github.com/rest-handlers/javadocs/rest-handlers).

Usage
-----

Maven dependency (available in [central repository](http://repo1.maven.org/maven2/com/alexkasko/rest/)):

    <dependency>
        <groupId>com.alexkasko.rest</groupId>
        <artifactId>rest-handlers</artifactId>
        <version>1.0.1</version>
    </dependency>

Request processing is split between transport-level (`TransportHandler`) and application level (`RestHandler`) classes.
Transport handlers may be singletones, one for each transport type. They take all input parameters from servlet, additional
parameters from URL matching (e.g. `id` for `GET` requests) and `RestHandler` class. Rest handler instance
may be instantiated or obtained from some kind of DI.

JSON `TransportHandler` example:

    public class JsonTransportHandler implements TransportHandler<JsonHandler> {
        // may be preconfigured
        private static final Gson gson = new Gson();

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

Application `RestHandler` example (may have any methods definition, only called from its `TransportHandler`):

    public class JsonHandler implements RestHandler {
        // sends text message back untouched (any application logic here)
        public OutputMessage handle(InputMessage input) {
            return new OutputMessage(input.getText());
        }

        // inpup message class to be instantiated by transport handler
        public Class<InputMessage> inputClass() {
            return InputMessage.class;
        }
    }

Setup example (web.xml):

    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>com.alexkasko.rest.handlers.HandlersDispatcherServlet</servlet-class>
        <init-param>
            <param-name>dispatcherKeyInServletContext</param-name>
            <param-value>dispatcher</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>
        <url-pattern>/rest/*</url-pattern>
    </servlet-mapping>
    <listener>
        <listener-class>com.myapp.InitListener</listener-class>
    </listener>

Dispather init from servlet listener:

    // create dispatcher instance with some handlers
    HandlersDispatcher dispatcher = HandlersDispatcher.builder()
        .addGet("^/status/jobid/(?<jobid>\\d+)$", new MyTransportHandler(), JobStatusHandler.class)
        .addPost("^/echo$", new JsonTransportHandler(), EchoHandler.class)
        .build();
    // add dispatcher to servlet context so dispatcherServlet will be able to access it
    sce.getServletContext().setAttribute("dispatcher", dispatcher);

Call with curl:

    curl http://127.0.0.1:8080/rest/status/jobid/42

Url parameter matched with `(?<jobid>\\d+)` will be provided to handler as `Map<String, String>` - `jobid:42`

Working example with JSON may be found [here](https://github.com/alexkasko/rest-handlers/tree/master/rest-handlers-json-example),
[example javadocs](http://alexkasko.github.com/rest-handlers/javadocs/rest-handlers-json-example).

Classes description
-------------------

 * `HandlersDispatcher` - regexp-based dispatcher, `GET`, `POST` and `PUT` requests are registered separately
 * `HandlersDispatcherServlet` - servlet, delegate all work to dispatcher (gets it from `ServletContext` by `dispatcherKeyInServletContext` key)
 * `TransportHandler` - transport part of request handler, implementation should get nessessary parameters from requests
 and delegates processing to application level handler (with clean method definitions, from DI with decalarative transactions etc)
 * `RestHandler` - application level marker-interface for calling from `TransportHandler`
 * `NotFoundHandler` - will be called if no other handlers matched
  * `DefaultNotFoundHandler` - default implementation, returns `404` with available handlers list
 * `ExceptionHandler` - will be called on exception during requst processing
  * `DefaultExceptionHandler` - default implementation, returns `500` with stacktrace

License information
-------------------

This project is released under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0)

Changelog
---------

**1.0.1** (2013-05-22)

 * formatting of list of all available handlers on missed request

**1.0** (2012-11-13)

 * initial version