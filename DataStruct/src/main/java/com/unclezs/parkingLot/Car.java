package com.unclezs.parkingLot;

import java.util.Date;

/*
 *@author unclezs.com
 *@date 2019.06.06 15:31
 */
public class Car {
    private String number;//车牌号码
    private int state;//当前状态 1在停车位，0在便利道
    private Date sdate;//开始停车时间

    @Override
    public String toString() {
        return "Car{" +
                "number='" + number + '\'' +
                ", state=" + state +
                ", sdate=" + sdate +
                '}';
    }

    public Car() {
    }

    public Car(String number, int state, Date sdate) {
        this.number = number;
        this.state = state;
        this.sdate = sdate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }
}
