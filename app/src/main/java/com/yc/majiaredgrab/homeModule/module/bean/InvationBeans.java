package com.yc.majiaredgrab.homeModule.module.bean;

public class InvationBeans {
    /**
     * id : 1
     * level_upgrade : 4
     * level_exchange : 2,3
     * cash_exchange : 0.3
     * exchange_num : 1
     * is_check : 1
     */

    private int id;
    private int level_upgrade;
    private String level_exchange;
    private String cash_exchange;
    private int exchange_num;
    private int is_check;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel_upgrade() {
        return level_upgrade;
    }

    public void setLevel_upgrade(int level_upgrade) {
        this.level_upgrade = level_upgrade;
    }

    public String getLevel_exchange() {
        return level_exchange;
    }

    public void setLevel_exchange(String level_exchange) {
        this.level_exchange = level_exchange;
    }

    public String getCash_exchange() {
        return cash_exchange;
    }

    public void setCash_exchange(String cash_exchange) {
        this.cash_exchange = cash_exchange;
    }

    public int getExchange_num() {
        return exchange_num;
    }

    public void setExchange_num(int exchange_num) {
        this.exchange_num = exchange_num;
    }

    public int getIs_check() {
        return is_check;
    }

    public void setIs_check(int is_check) {
        this.is_check = is_check;
    }
}

