package com.sfsu.xmas.session;

import javax.servlet.http.Cookie;

public class YearLongCookie extends Cookie {
  public static final int SECONDS_PER_YEAR = 60*60*24*365;
  
  public YearLongCookie(String name, String value) {
    super(name, value);
    setMaxAge(SECONDS_PER_YEAR);
  }
}
