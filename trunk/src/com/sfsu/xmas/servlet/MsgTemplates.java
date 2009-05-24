package com.sfsu.xmas.servlet;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MsgTemplates {

    public static String getSuccess(String message) {
        StringBuffer sb = new StringBuffer();
        sb.append("<div class=\"success\">");
        sb.append("Success: ");
        sb.append(message);
        sb.append(".");
        sb.append("</div>");
        return sb.toString();
    }

    public static String getError(String message) {
        StringBuffer sb = new StringBuffer();

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        DateFormat tf = DateFormat.getTimeInstance(DateFormat.MEDIUM);
        tf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));

        sb.append("<div class=\"error\"><span style=\"float: right; color: gray; font-size: 8px;\">");
        sb.append(df.format(new Date()));
        sb.append(",<br />");
        sb.append(tf.format(new Date()));
        sb.append("</span>");
        sb.append("Error: ");
        sb.append(message);
        sb.append(".");
        sb.append("</div>");
        return sb.toString();
    }
}
