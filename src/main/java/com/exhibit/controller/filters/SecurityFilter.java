package com.exhibit.controller.filters;

import com.exhibit.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.exhibit.util.constants.UtilConstants.INFO_LOGGER;

public class SecurityFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void init(FilterConfig filterConfig)  {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String path = req.getRequestURI();

        if(path.contains("addExhibition")
                || path.contains("adminPanel")) {
            User user = (User) req.getSession().getAttribute("user");
            if (user == null || !user.getRole().equals("admin")) {
                logger.warn("User access without permission");
                resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));

            } else {
                logger.info("Admin access");
                chain.doFilter(servletRequest,servletResponse);
            }
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

