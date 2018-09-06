package com.dpermi.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Created by val620@126.com on 2018/7/12.
 */
@Target({ElementType.TYPE, ElementType.METHOD})//同时用在类和方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    String name();
//    String description();
    String parentId();
//    String url();
}
