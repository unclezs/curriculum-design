package com.unclezs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *@author unclezs.com
 *@date 2019.05.29 23:44
 */
public class DateUtil {
    /**
     * 时间转字符串
     * @param date
     * @return
     */
    public static String d2s(Date date,String format){
        SimpleDateFormat sf=new SimpleDateFormat(format);
        return sf.format(date);
    }

    /**
     * 毫秒数转时间字符串
     * @param time
     * @param format
     * @return
     */
    public static String ld2s(Long time,String format){
        return d2s(new Date(time),format);
    }

    /**
     * 计算两个时间相差多久天
     * @param endDate
     * @param nowDate
     * @return
     */
    public static String getDatePoor(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
         long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        return hour + "时" + min + "分"+sec+"秒";
    }
    /**
     * 计算两个时间相差多久天
     * @param endDate
     * @param nowDate
     * @return
     */
    public static String getDatePoorToHour(Date endDate, Date nowDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少小时
        long hour = diff % nd / nh;
        return hour+"";
    }

    /**
     * 字符串转时间
     * @param date 时间
     * @param format 格式化
     * @return
     * @throws ParseException
     */
    public static Date s2d(String date,String format) throws ParseException {
        SimpleDateFormat sf=new SimpleDateFormat(format);
        return sf.parse(date);
    }
    //根据日期取得星期几
    public static String getWeek(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(date);
        return week;
    }
}
