package com.dpermi.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by val620@126.com on 2017/9/21.
 */
public class FileConfig {

    public static final Boolean DEBUG = getDebug();
    private static HashMap<String, String> hashMap;

    /**
     * 获得application.properties的配置值，将来需要保存到缓存里
     *
     * @param key 键
     * @return 值
     */
    public static String getConfig(String key) {
        if(hashMap==null){
            hashMap=new HashMap<>();
        }
        if(hashMap.containsKey(key)) {
            return hashMap.get(key);
        }

        Properties p;
        p = new Properties();
        FileInputStream fis = null;
        URL url;
        url = FileConfig.class.getClassLoader().getResource("application.properties");
        try {
            fis = new FileInputStream(url.getPath());
            p.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String value=p.getProperty(key);
        hashMap.put(key,value);
        return value;
    }

    private static Boolean isDebug = null;

    private static Boolean getDebug() {
        if (isDebug == null) {
            String debugStr = getConfig("dpermi.debug");
            isDebug = Boolean.valueOf(debugStr);
        }
        return isDebug;
    }

}
