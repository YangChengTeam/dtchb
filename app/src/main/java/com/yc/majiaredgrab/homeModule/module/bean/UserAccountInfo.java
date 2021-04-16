package com.yc.majiaredgrab.homeModule.module.bean;

/**
 * Created by suns  on 2020/11/21 11:01.
 */
public class UserAccountInfo {

    public int id;//":12,
    public int user_id;//":9995, 用户ID
    public double cash;//":"0.00", 账户余额
    public int level;//":1, 用户等级
    public int group_id;//":1001, 用户群号
    public int prize_num;//":10, 抽奖剩余次数
    public int guess_num;//":10, 竞猜剩余次数
    public int treasure_num;//":0, 夺宝券数量
    public int login_day;//":1 登录天数

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

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getPrize_num() {
        return prize_num;
    }

    public void setPrize_num(int prize_num) {
        this.prize_num = prize_num;
    }

    public int getGuess_num() {
        return guess_num;
    }

    public void setGuess_num(int guess_num) {
        this.guess_num = guess_num;
    }

    public int getTreasure_num() {
        return treasure_num;
    }

    public void setTreasure_num(int treasure_num) {
        this.treasure_num = treasure_num;
    }

    public int getLogin_day() {
        return login_day;
    }

    public void setLogin_day(int login_day) {
        this.login_day = login_day;
    }
}
