package com.exhibit.controller.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import static com.exhibit.util.UtilConstants.INFO_LOGGER;

/**
 * Session attribute listener.
 */
@WebListener
public class SessionListener implements HttpSessionAttributeListener {

    private static final Logger logger = LogManager.getLogger(INFO_LOGGER);

    @Override
    public void attributeAdded(final HttpSessionBindingEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("Session attribute added: ").append(event.getClass().getSimpleName()).append(" : ").append(event.getName())
                .append(" : ").append(event.getValue());
        logger.info(sb);
    }


    @Override
    public void attributeRemoved(final HttpSessionBindingEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("Session attribute removed: ").append(event.getClass().getSimpleName()).append(" : ").append(event.getName()).
                append(" : ").append(event.getValue());
        logger.info(sb);
    }

}
