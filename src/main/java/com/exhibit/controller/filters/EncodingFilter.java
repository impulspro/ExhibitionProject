package com.exhibit.controller.filters;

import javax.servlet.*;
import java.io.IOException;

/**
 * Session encoding filter UTF-8
 */
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }
        chain.doFilter(request, response);
    }
}