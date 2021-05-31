package com.yc.redkingguess.homeModule.module.bean;

public class HomeMsgBeans {

    /**
     * stype : 1
     * info1 : {"id":8,"add_time":1606124400,"hb_id":1,"member_money":"0.00","stype":1,"add_date":"17:40","type":1,"typename":"手气红包","money":"100.00","num":200,"status":0}
     */

    private int stype;
    private Info1Bean info1;
    private Info0Bean info0;

    public Info0Bean getInfo0() {
        return info0;
    }

    public void setInfo0(Info0Bean info0) {
        this.info0 = info0;
    }

    public int getStype() {
        return stype;
    }

    public void setStype(int stype) {
        this.stype = stype;
    }

    public Info1Bean getInfo1() {
        return info1;
    }

    public void setInfo1(Info1Bean info1) {
        this.info1 = info1;
    }




}
