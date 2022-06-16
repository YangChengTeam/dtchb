package com.yc.jsdsp.beans.module.beans;

public class MoneyTaskBeans {

    /**
     * id : 1
     * money : 0.30
     * num : 3
     * is_check : 0
     * finish_num : 3
     * status : 1
     * is_tx : 1
     */

    private int id;
    private String money;
    private int num;
    private int is_check;
    private int finish_num;
    private int status;
    private int is_tx;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getIs_check() {
        return is_check;
    }

    public void setIs_check(int is_check) {
        this.is_check = is_check;
    }

    public int getFinish_num() {
        return finish_num;
    }

    public void setFinish_num(int finish_num) {
        this.finish_num = finish_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_tx() {
        return is_tx;
    }

    public void setIs_tx(int is_tx) {
        this.is_tx = is_tx;
    }
}
