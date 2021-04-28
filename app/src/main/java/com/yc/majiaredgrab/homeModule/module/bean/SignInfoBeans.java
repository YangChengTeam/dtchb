package com.yc.majiaredgrab.homeModule.module.bean;

import java.util.List;

public class SignInfoBeans {


    /**
     * money : 50.00
     * signday : 7
     * days : 2
     * is_signed : 1
     * unlock : 0
     * other_info : [{"title":"完成任务1","excerpt":"任务简介","num":2,"other_id":1,"finish_num":0}]
     */

    private String money;
    private int signday;
    private int days;
    private int is_signed;
    private int unlock;
    private List<TaskUnlock> other_info;

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

    public int getUnlock() {
        return unlock;
    }

    public void setUnlock(int unlock) {
        this.unlock = unlock;
    }

    public List<TaskUnlock> getOther_info() {
        return other_info;
    }

    public void setOther_info(List<TaskUnlock> other_info) {
        this.other_info = other_info;
    }


}
