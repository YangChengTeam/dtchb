package com.yc.redevenlopes.homeModule.module.bean;

public class HomeRedMessage {

    /**
     * id : 19
     * hb_id : 3
     * balance_money : 100.00
     * add_time : 13:42
     * group_id : 1000
     * num : 200
     * type : 1
     * typename : 手气红包
     * task_id : 1
     */

    private int id;
    private int hb_id;
    private String balance_money;
    private String add_time;
    private int group_id;
    private int num;
    private String type;
    private String typename;
    private int task_id;

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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }
}
