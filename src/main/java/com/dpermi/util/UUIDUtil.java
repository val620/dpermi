package com.dpermi.util;

import java.util.UUID;

/**
 * Created by val620@126.com on 2018/7/12.
 */
public class UUIDUtil {

    public static String GetUUID() {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString().replaceAll("-", "");
        return id;
    }
}
