package com.yc.redguess.homeModule.module.bean;

public class SignInfoBeans {

    /**
     * money : 50.00
     * signday : 7
     * days : 2
     * is_signed : 1
     */

    private String money;
    private int signday;
    private int days;
    private int is_signed;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getSignday() {
        return signday;
    }

    public void setSignday(int signday) {
        this.signday = signday;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getIs_signed() {
        return is_signed;
    }

    public void setIs_signed(int is_signed) {
        this.is_signed = is_signed;
    }
}
