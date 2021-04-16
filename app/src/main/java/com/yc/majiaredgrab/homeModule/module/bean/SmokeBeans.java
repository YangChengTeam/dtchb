package com.yc.majiaredgrab.homeModule.module.bean;

public class SmokeBeans {

    /**
     * id : 4
     * user_id : 1
     * status : 1
     * money : 0.03
     * new_lucky : 1
     * double_money : 0.06
     * lucky_num : 96
     */

    private int id;
    private int user_id;
    private int status;
    private String money;
    private int new_lucky;
    private String double_money;
    private int lucky_num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getNew_lucky() {
        return new_lucky;
    }

    public void setNew_lucky(int new_lucky) {
        this.new_lucky = new_lucky;
    }

    public String getDouble_money() {
        return double_money;
    }

    public void setDouble_money(String double_money) {
        this.double_money = double_money;
    }

    public int getLucky_num() {
        return lucky_num;
    }

    public void setLucky_num(int lucky_num) {
        this.lucky_num = lucky_num;
    }
}
