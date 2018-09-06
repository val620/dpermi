package com.dpermi.util;

/**
 * Created by val620@126.com on 2018-06-01.
 */
public class ConstantUtil {


    /**
     * 用户性别
     */
    public static final int USER_MAN = 1;//用户性别男

    public static final int USER_WOMAN = 0;//用户性别女

    //==============================================

    /**
     * 用户状态
     */
    public static final int USER_STATE_UNVALIDATE = 0;//用户未验证

    public static final int USER_STATE_VALIDATE = 1;//用户已验证

    public static final int USER_STATE_REJECT = 2;//用户未通过

    public static final int USER_STATE_BLACKLIST = 3;//用户黑名单

    public static final int USER_STATE_AUDIT = 4;//用户审核中


    //==============================================
    /**
     * 日期格式
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";//日期格式转换

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";//日期时间格式转换

    //==============================================
    public static final String CURRENT_USER = "CURRENT_USER";//当前用户
    public static final String CURRENT_USERNAME = "CURRENT_USERNAME";//当前用户名
    public static final String CURRENT_USER_PERMISSION = "CURRENT_USER_PERMISSION";//当前用户权限
    public static final String ROLES = "ROLES";//角色
    //==============================================

}




















