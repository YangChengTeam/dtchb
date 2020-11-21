package com.yc.redevenlopes.homeModule.module.bean;

public class OpenRedEvenlopes {

    /**
     * id : 19
     * hb_id : 3
     * balance_money : 100.00
     * add_time :
     * group_id : 1000
     * status : 2
     */

    private int id;
    private int hb_id;
    private String balance_money;
    private String add_time;
    private int group_id;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHb_id() {
        return hb_id;
    }

    public void setHb_id(int hb_id) {
        this.hb_id = hb_id;
    }

    public String getBalance_money() {
        return balance_money;
    }

    public void setBalance_money(String balance_money) {
        this.balance_money = balance_money;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
