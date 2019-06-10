package com.unclezs.parkingLot;

/*
 * 停车收费记录
 *@author unclezs.com
 *@date 2019.06.06 21:32
 */
public class Record {
    private String number;//车牌号
    private String stime;//开始时间
    private String etime;//结束时间
    private int unitPrice;//单价
    private String paseTime;//停车时间
    private String totalPrice;//总计费用

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPaseTime() {
        return paseTime;
    }

    public void setPaseTime(String paseTime) {
        this.paseTime = paseTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Record{" +
                "number='" + number + '\'' +
                ", stime='" + stime + '\'' +
                ", etime='" + etime + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", paseTime='" + paseTime + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                '}';
    }
}
