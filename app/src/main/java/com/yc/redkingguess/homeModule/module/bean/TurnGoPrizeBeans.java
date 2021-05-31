package com.yc.redkingguess.homeModule.module.bean;

public class TurnGoPrizeBeans {

    /**
     * id : 1
     * name : 0.01元
     * money : 0.01
     * percent : 30
     */

    private int id;
    private String name;
    private String money;
    private int percent;
    private int prize_num;
    private int new_level;

    public int getPrize_num() {
        return prize_num;
    }

    public void setPrize_num(int prize_num) {
        this.prize_num = prize_num;
    }

    public int getNew_level() {
        return new_level;
    }

    public void setNew_level(int new_level) {
        this.new_level = new_level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
