package com.unclezs.flight;

import java.io.Serializable;

/*
 *@author unclezs.com
 *@date 2019.06.05 14:51
 */
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tel;//电话号码
    private String name;//姓名
    private String idNumber;//身份证号码
    private String cid;//编号

    @Override
    public String toString() {
        return "Customer{" +
                "tel='" + tel + '\'' +
                ", name='" + name + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
