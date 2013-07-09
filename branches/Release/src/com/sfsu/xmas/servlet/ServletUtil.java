package com.sfsu.xmas.servlet;

import com.sfsu.xmas.session.SessionAttributes;
import java.util.Date;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public abstract class ServletUtil {
    
    public static Cookie getCookieFromKey(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (key.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static void emptyCookie(Cookie cookie) {
        cookie.setMaxAge(0);
        cookie.setValue("");
    }

    public static String getNewSessionID(HttpServletRequest request) {
        StringBuffer sb = new StringBuffer();
        Date now = new Date();
        sb.append("DATE_");
        sb.append(now.getTime());
        Cookie jSessionID = ServletUtil.getCookieFromKey(request, SessionAttributes.JSESSIONID);
        if (jSessionID != null && jSessionID.getValue() != null && !jSessionID.getValue().equals("")) {
            // Use JSESSIONID
            sb.append("_SESS_");
            sb.append(jSessionID.getValue());
        } else {
            // Random number
            sb.append("_RAN_");
            sb.append((int) Math.round(Math.random() * 100));
        }
        return sb.toString();
    }
}
