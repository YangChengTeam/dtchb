package com.yc.wxchb.beans.module.beans;

public class LimitedBeans {

    /**
     * id : 2
     * ad_code : 1
     * num : 2
     * finish_num : 2
     * total : 4
     * money : 2.00
     * status : 0
     */

    private int id;
    private int ad_code;
    private int num;
    private int finish_num;
    private int total;
    private String money;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAd_code() {
        return ad_code;
    }

    public void setAd_code(int ad_code) {
        this.ad_code = ad_code;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getFinish_num() {
        return finish_num;
    }

    public void setFinish_num(int finish_num) {
        this.finish_num = finish_num;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
