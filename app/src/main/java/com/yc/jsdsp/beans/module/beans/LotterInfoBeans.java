package com.yc.jsdsp.beans.module.beans;

public class LotterInfoBeans {

    /**
     * id : 1
     * type : 1
     * money : 0.30
     * rand_percent : 20
     * sort : 1
     */

    private int id;
    private int type;
    private String money;
    private int rand_percent;
    private int sort;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getRand_percent() {
        return rand_percent;
    }

    public void setRand_percent(int rand_percent) {
        this.rand_percent = rand_percent;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
