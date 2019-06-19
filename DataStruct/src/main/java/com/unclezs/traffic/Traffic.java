package com.unclezs.traffic;

/*
 *@author unclezs.com
 *@date 2019.06.18 09:20
 */
public class Traffic {
    private String fromAddr;//出发地
    private String toAddr;//到达地
    private String departureTime;//出发时间
    private String arrivalTime;//到达时间
    private long timePoor;//消耗时间
    private int price;//票价格
    private int id;
    public Traffic() {
    }

    public Traffic(String fromAddr, String toAddr, String departureTime, String arrivalTime, int price) {
        this.fromAddr = fromAddr;
        this.toAddr = toAddr;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromAddr() {
        return fromAddr;
    }

    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr;
    }

    public String getToAddr() {
        return toAddr;
    }

    public void setToAddr(String toAddr) {
        this.toAddr = toAddr;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public long getTimePoor() {
        return timePoor;
    }

    public void setTimePoor(long timePoor) {
        this.timePoor = timePoor;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Traffic{" +
                "fromAddr='" + fromAddr + '\'' +
                ", toAddr='" + toAddr + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", timePoor=" + timePoor +
                ", price=" + price +
                '}';
    }
}
