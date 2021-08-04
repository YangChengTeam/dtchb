package com.yc.qqzz.homeModule.module.bean;

public class DayUpgradeDayCashFinshBeans {

    /**
     * id : 1
     * type : 1
     * money : 0.30
     * is_must : 1
     * rand_percent : 0
     */

    private int id;
    private int type;
    private String money;
    private int is_must;
    private int rand_percent;
    private int level_num;


    public int getLevel_num() {
        return level_num;
    }

    public void setLevel_num(int level_num) {
        this.level_num = level_num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getIs_must() {
        return is_must;
    }

    public void setIs_must(int is_must) {
        this.is_must = is_must;
    }

    public int getRand_percent() {
        return rand_percent;
    }

    public void setRand_percent(int rand_percent) {
        this.rand_percent = rand_percent;
    }
}
