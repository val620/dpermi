package com.dpermi.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by val620@126.com on 2017/10/24.
 * 日期格式转化
 */
public class TypeConvert {

    public static Date StringToDate(String dateStr) {
        Date date = StringToDate(dateStr, "yyyy-MM-dd");
        return date;
    }

    public static Date StringToDate(String dateStr, String patten) {
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(patten);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String DateToString(Date date){
        String dateStr = DateToString(date, "yyyy-MM-dd HH:mm:ss");
        return dateStr;
    }

    public static String DateToString(Date date, String patten){
        String dateStr=null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(patten);
            dateStr = dateFormat.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }

        return dateStr;
    }

    /**
     * 四舍五入保留n位小数
     *
     * @param d
     * @param digit
     * @return
     */
    public static double round(double d, int digit) {
        BigDecimal b = new BigDecimal(new Double(d).toString());
        d = b.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d;
    }

}
