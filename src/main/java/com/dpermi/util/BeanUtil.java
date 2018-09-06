package com.dpermi.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by val620@126.com on 2018/7/24.
 */
public class BeanUtil {
    private static ApplicationContext context;

    static {
        if (context == null)
            context = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
    }

    public static Object getBean(String name) {
        Object obj = context.getBean(name);
        return obj;
    }

    public static <T> T getBean(String name,Class<T> tClass) {
        T obj = context.getBean(name,tClass);
        return obj;
    }
}
