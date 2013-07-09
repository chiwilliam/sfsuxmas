package com.sfsu.xmas.session;

import javax.servlet.http.Cookie;

public class EatenCookie extends Cookie {
  
  public EatenCookie(String name, String value) {
    super(name, value);
    setMaxAge(0);
  }
}
