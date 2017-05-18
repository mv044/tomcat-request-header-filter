package com.lonelyplanet.filters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class TestRequestHeaderFilter {

    private static String HEADER_NAME = "X-Test-Header";
    private static String HEADER_VALUE = "test-header-value";

    private HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
    private HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
    private FilterConfig filterConfig = Mockito.mock(FilterConfig.class);

    private String observedHeaderValue;
    private ArrayList<String> observedHeaderNames;

    @Before
    public void setup() {
        req = Mockito.mock(HttpServletRequest.class);
        resp = Mockito.mock(HttpServletResponse.class);
        filterConfig = Mockito.mock(FilterConfig.class);

        Mockito.when(filterConfig.getInitParameter("header-name")).thenReturn(HEADER_NAME);
        Mockito.when(filterConfig.getInitParameter("header-value")).thenReturn(HEADER_VALUE);

        this.observedHeaderValue = null;
        this.observedHeaderNames = null;
    }

    @Test
    public void testGetHeader() throws ServletException, IOException {
        Filter requestHeaderFilter = new RequestHeaderFilter();

        requestHeaderFilter.init(filterConfig);
        requestHeaderFilter.doFilter(req, resp, observableFilterChain());

        Assert.assertEquals(HEADER_VALUE, observedHeaderValue);

        requestHeaderFilter.destroy();
    }

    @Test
    public void testGetHeaderNames() throws ServletException, IOException {
        Filter requestHeaderFilter = new RequestHeaderFilter();

        requestHeaderFilter.init(filterConfig);
        requestHeaderFilter.doFilter(req, resp, observableFilterChain());

        Assert.assertTrue(observedHeaderNames.contains(HEADER_NAME));

        requestHeaderFilter.destroy();
    }

    private FilterChain observableFilterChain() {
        return new FilterChain() {
            public void doFilter(ServletRequest req, ServletResponse res) throws IOException, ServletException {
                observedHeaderValue = ((HttpServletRequest) req).getHeader(HEADER_NAME);
                observedHeaderNames = Collections.list(((HttpServletRequest) req).getHeaderNames());
            }
        };
    }
}
