package com.yc.jsdps.beans.module.beans;

public class LotterBeans {

    /**
     * lottery_id : 1
     * money : 0.30
     * out_money : 100.30
     */

    private int lottery_id;
    private String money;
    private String out_money;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLottery_id() {
        return lottery_id;
    }

    public void setLottery_id(int lottery_id) {
        this.lottery_id = lottery_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOut_money() {
        return out_money;
    }

    public void setOut_money(String out_money) {
        this.out_money = out_money;
    }
}
