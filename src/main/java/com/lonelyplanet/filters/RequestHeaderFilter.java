package com.lonelyplanet.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Filter which adds the configured header to every request
 */
public class RequestHeaderFilter implements Filter {
    private String headerName;
    private String headerValue;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.headerName = filterConfig.getInitParameter("header-name");
        this.headerValue = filterConfig.getInitParameter("header-value");
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = new EnsureHeaderPresent((HttpServletRequest) req, headerName, headerValue);
        chain.doFilter(request, res);
    }

    public void destroy() {
        this.headerName = null;
        this.headerValue = null;
    }

    private class EnsureHeaderPresent extends HttpServletRequestWrapper {
        private String headerName;
        private String headerValue;

        public EnsureHeaderPresent(HttpServletRequest request, String name, String value) {
            super(request);
            this.headerName = name;
            this.headerValue = value;
        }

        @Override
        public String getHeader(String name) {
            if (this.headerName != null && this.headerName.equals(name)) {
                return this.headerValue;
            }

            return super.getParameter(name);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            Enumeration<String> headerNames = super.getHeaderNames();
            ArrayList<String> all;
            if (headerNames == null) {
                all = new ArrayList<String>();
            } else {
                all = Collections.list(headerNames);
            }

            all.add(this.headerName);
            return Collections.enumeration(all);
        }

    }
}
