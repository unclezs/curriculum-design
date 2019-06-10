package com.unclezs.utils;


import java.util.Random;

/*
 *@author unclezs.com
 *@date 2019.05.25 20:55
 */
public class RandomUtils {
    /**
     * 获取随机字符串
     * @param length 需要获取的长度
     * @param seed 用户根据需要传入
     * @return 指定长度随机字符串
     */
    public static String getRandomString(int length,String seed){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"+seed;
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    /**
     * 获取指定长度随机数
     * @param len
     * @return
     */
    public static String getRandom(int len){
        Random r = new Random();
        int bitNum=1;
        for(int i=0;i<len;i++){
            bitNum=bitNum*10;
        }
        long num = Math.abs(r.nextLong() % (bitNum));
        String s = String.valueOf(num);
        for (int i = len - s.length(); i >0 ;i--) {
            s = "0" + s;
        }
        if(s.length()>len){
            s=s.substring(0,len);
        }
        return s;
    }
}
