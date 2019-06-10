package com.unclezs.utils;

import java.io.*;

public class ObjUtil {
    /**
     * 保存配置文件
     * @param wobj 要保持的对象
     * @param name 保存成什么名字
     */
    public static void saveConfig(Object wobj,String name) {
        try {
            ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(name));
            out.writeObject(wobj);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取配置文件对象
     * @param name 对象的名字
     * @return 读取到的对象
     */
    public static Object loadConfig(String name){
        File file =new File(name);
        FileInputStream in;
        if (!checkFileExist(name)) {
            return null;
        }
        try {
            in = new FileInputStream(file);
            ObjectInputStream oin=new ObjectInputStream(in);
            Object object=oin.readObject();
            oin.close();
            return object;
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 判断文件是否存在
     *
     * @param path 文件路径
     * @return 存在返回true
     */
    public static boolean checkFileExist(String path) {
        if (new File(path).exists())
            return true;
        return false;
    }
}
