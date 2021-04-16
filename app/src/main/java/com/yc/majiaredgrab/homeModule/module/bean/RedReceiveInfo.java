package com.yc.majiaredgrab.homeModule.module.bean;

/**
 * Created by suns  on 2020/11/23 10:44.
 */
public class RedReceiveInfo {
    public int status;
    public int id;//": 5,
    public long upd_time;//": 1606098239,
    public int is_finish;//": 1,     等于1时  代表已领取
    public double money;//": "0.1"

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUpd_time() {
        return upd_time;
    }

    public void setUpd_time(long upd_time) {
        this.upd_time = upd_time;
    }

    public int getIs_finish() {
        return is_finish;
    }

    public void setIs_finish(int is_finish) {
        this.is_finish = is_finish;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
