package com.exhibit.controller.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.exhibit.dao.constants.UtilConstants.INFO_LOGGER;

@WebFilter("/*")
public class LocaleFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getParameter("lang") != null) {
            req.getSession().setAttribute("lang", req.getParameter("lang"));
            String info = "Language filter " + req.getParameter("lang");
            logger.info(info);
        }
        chain.doFilter(request, response);
    }
}
