package com.unclezs.flight;

import java.io.Serializable;
import java.util.Date;

/*
 *航班信息
 *@author unclezs.com
 *@date 2019.06.03 16:08
 */
public class FlightInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;//航班所属公司名称  华夏航空
    private String number;//航班编号   G52726
    private String setOutTime;//出发时间
    private String endTime;//到达时间
    private String setOutAddress;//出发站
    private String endAddress;//终点站
    private float price;//价格
    private float discount;//折扣
    private String status;//订购窜号
    private int tiketsNum;//当前剩余票量

    @Override
    public String toString() {
        return "FlightInfo{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", setOutTime='" + setOutTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", setOutAddress='" + setOutAddress + '\'' +
                ", endAddress='" + endAddress + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", status='" + status + '\'' +
                ", tiketsNum=" + tiketsNum +
                '}';
    }

    public int getTiketsNum() {
        return tiketsNum;
    }

    public void setTiketsNum(int tiketsNum) {
        this.tiketsNum = tiketsNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSetOutTime() {
        return setOutTime;
    }

    public void setSetOutTime(String setOutTime) {
        this.setOutTime = setOutTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSetOutAddress() {
        return setOutAddress;
    }

    public void setSetOutAddress(String setOutAddress) {
        this.setOutAddress = setOutAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
