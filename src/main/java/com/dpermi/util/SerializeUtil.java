package com.dpermi.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by val620@126.com on 2018/7/12.
 */
public class SerializeUtil {
    /**
     * 序列化
     */
    public static byte[] serialize(Object obj) {
        ObjectOutputStream output = null;
        ByteArrayOutputStream byteOut = null;
        try {
            byteOut = new ByteArrayOutputStream();
            output = new ObjectOutputStream(byteOut);
            output.writeObject(obj);
            byte[] bytes = byteOut.toByteArray();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化
     */
    public static Object deserialize(byte[] bytes) {
        ByteArrayInputStream input = null;
        try {
            input = new ByteArrayInputStream(bytes);
            ObjectInputStream obj = new ObjectInputStream(input);
            return obj.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
