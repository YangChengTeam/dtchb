package com.yc.redkingguess.homeModule.module.bean;

public class WalletDetailBeans {

    /**
     * id : 30
     * user_id : 10000
     * task_id : 2
     * money : 0.10
     * describe : 领取答题红包
     * add_time : 11-26 18:22
     * stype : 0
     */

    private int id;
    private int user_id;
    private int task_id;
    private String money;
    private String describe;
    private String add_time;
    private int stype;

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

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getStype() {
        return stype;
    }

    public void setStype(int stype) {
        this.stype = stype;
    }
}
