package com.dpermi.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by val620@126.com on 2018/7/12.
 */
public class CookieUtil {
    public static void createCookie(String name,String value,int maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        //设置路径，这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
        cookie.setPath("/");
        HttpServletResponse response=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        response.addCookie(cookie);
    }

    public static String getCookie(String name) {
        HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Cookie[] cookies = request.getCookies();
        String value = "";
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    value = cookie.getValue();
                }
            }
        }
        return value;
    }
}
