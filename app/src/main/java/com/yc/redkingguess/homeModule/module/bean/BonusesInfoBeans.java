package com.yc.redkingguess.homeModule.module.bean;

import java.util.List;

public class BonusesInfoBeans {

    /**
     * id : 1
     * other_info : [{"title":"任务名称","excerpt":"任务简介","num":5,"other_id":1,"status":0,"finish_num":0}]
     * cash : 0.20
     */

    private int id;
    private String cash;
    private List<TaskUnlock> other_info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public List<TaskUnlock> getOther_info() {
        return other_info;
    }

    public void setOther_info(List<TaskUnlock> other_info) {
        this.other_info = other_info;
    }
}
