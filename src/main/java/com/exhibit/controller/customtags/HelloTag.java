package com.exhibit.controller.customtags;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;
/**
 * Custom tag printing message
 */
public class HelloTag extends SimpleTagSupport {
    private String message;

    public void setMessage(String msg) {
        this.message = msg;
    }
    StringWriter sw = new StringWriter();
    @Override
    public void doTag()

            throws JspException, IOException {
        if (message != null) {
            /* Use message from attribute */
            JspWriter out = getJspContext().getOut();
            out.println( message );
        } else {
            /* use message from the body */
            getJspBody().invoke(sw);
            getJspContext().getOut().println(sw.toString());
        }
    }
}