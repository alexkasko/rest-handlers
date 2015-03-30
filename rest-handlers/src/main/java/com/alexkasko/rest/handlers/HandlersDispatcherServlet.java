package com.alexkasko.rest.handlers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Dispatcher servlet for handlers, delegates {@code GET}, {@code POST} and {@code PUT}
 * dispatching to {@link HandlersDispatcher}. Accesses dispatcher through {@link javax.servlet.ServletContext}
 * using mandatory init parameter {@code dispatcherKeyInServletContext}.
 * Uses {@code UTF-8} as default encoding for request and response.
 *
 * @author  alexkasko
 * Date: 3/18/12
 * @see HandlersDispatcher
 */
public class HandlersDispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 7041995029366447857L;
    private String dispatcherKey;

    /**
     * Checks availability of {@code dispatcherKeyInServletContext} init parameter
     *
     * @throws ServletException on missed init parameter
     */
    @Override
    public void init() throws ServletException {
        String key = this.getServletConfig().getInitParameter("dispatcherKeyInServletContext");
        if(null == key) throw new ServletException("Servlet init parameter 'dispatcherKeyInServletContext' " +
                "must be provided. It will be using for accessing 'HandlersDispatcher' in 'ServletContext'");
        this.dispatcherKey = key;
    }

    /**
     * Delegates request dispatching to {@link HandlersDispatcher}
     *
     * @param req request
     * @param resp response
     * @throws ServletException on {@link HandlersDispatcher} not found in ServletContext
     * @throws IOException cannot happen
     */
    @Override
    public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp);
    }

    /**
     * Delegates request dispatching to {@link HandlersDispatcher}
     *
     * @param req request
     * @param resp response
     * @throws ServletException on {@link HandlersDispatcher} not found in ServletContext
     * @throws IOException cannot happen
     */
    @Override
    public void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp);
    }

    /**
     * Delegates request dispatching to {@link HandlersDispatcher}
     *
     * @param req request
     * @param resp response
     * @throws ServletException on {@link HandlersDispatcher} not found in ServletContext
     * @throws IOException cannot happen
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp);
    }

    /**
     * Delegates request dispatching to {@link HandlersDispatcher}
     *
     * @param req request
     * @param resp response
     * @throws ServletException on {@link HandlersDispatcher} not found in ServletContext
     * @throws IOException cannot happen
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp);
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException, ServletException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        dispatcher().dispatch(req, resp);
    }

    private HandlersDispatcher dispatcher() throws ServletException {
        HandlersDispatcher disp = (HandlersDispatcher) getServletContext().getAttribute(dispatcherKey);
        if(null == disp) throw new ServletException("Cannot find dispatcher in ServletContext using key: '"
                + dispatcherKey + "' check 'dispatcherKeyInServletContext' init parameter for 'HandlersDispatcherServlet'");
        return disp;
    }
}
