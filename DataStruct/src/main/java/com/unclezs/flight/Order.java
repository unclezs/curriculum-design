package com.unclezs.flight;

import java.io.Serializable;

/*
 *@author unclezs.com
 *@date 2019.06.05 15:43
 */
public class Order implements Serializable {
    private String Onumber;//订单号
    private String Hnumber;//航班串号
    private String CNumber;//客户编号
    private int tiketNum;//购买票数量
    private String date;//购买时间
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Order{" +
                "Onumber='" + Onumber + '\'' +
                ", Hnumber='" + Hnumber + '\'' +
                ", CNumber='" + CNumber + '\'' +
                ", tiketNum=" + tiketNum +
                ", date='" + date + '\'' +
                '}';
    }

    public String getOnumber() {
        return Onumber;
    }

    public void setOnumber(String onumber) {
        Onumber = onumber;
    }

    public String getHnumber() {
        return Hnumber;
    }

    public void setHnumber(String hnumber) {
        Hnumber = hnumber;
    }

    public String getCNumber() {
        return CNumber;
    }

    public void setCNumber(String CNumber) {
        this.CNumber = CNumber;
    }

    public int getTiketNum() {
        return tiketNum;
    }

    public void setTiketNum(int tiketNum) {
        this.tiketNum = tiketNum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
