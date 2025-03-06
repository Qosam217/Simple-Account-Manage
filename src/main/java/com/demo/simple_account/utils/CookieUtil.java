package com.demo.simple_account.utils;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

    private CookieUtil(){
        throw new IllegalStateException("Cookie Utility Class");
    }
    
    public static Cookie creatCookie(String name, String value, int maxAge, String path){
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);        // ensure cookie is only sent over HTTPS
        cookie.setPath(path);           // specify the endpoint for cookie to sent
        cookie.setMaxAge(maxAge);
        return cookie;
    }
}
