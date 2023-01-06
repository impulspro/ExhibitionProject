package com.exhibit.controller.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Session attribute listener.
 */
@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(final HttpSessionBindingEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("Session attribute added: ").append(event.getClass().getSimpleName()).append(" : ").append(event.getName()).append(" : ").append(event.getValue());
    }


    @Override
    public void attributeRemoved(final HttpSessionBindingEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("Session attribute removed: ").append(event.getClass().getSimpleName()).append(" : ").append(event.getName()).append(" : ").append(event.getValue());
    }

}
