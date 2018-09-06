package com.dpermi.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by val620@126.com on 2018/7/12.
 */
public class SessionUtil {
    public static HttpSession session(){
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getSession();
        return session;
    }

    public static void setSession(String name,Object value){
        HttpSession session = session();
        session.setAttribute(name, value);
    }

    public static Object getSession(String name){
        HttpSession session = session();
        Object value = session.getAttribute(name);
        return value;
    }

    public static void delSession(String name){
        HttpSession session = session();
        session.removeAttribute(name);
    }
}
