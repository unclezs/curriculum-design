package com.unclezs.queryTel;

/*
 *@author unclezs.com
 *@date 2019.06.09 17:56
 */
public class User {
    private String name;//姓名
    private String address;//地址
    private String tel;//电话号码
    private String time;//查找时间
    private String num;//比较次数

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", time='" + time + '\'' +
                ", num='" + num + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}