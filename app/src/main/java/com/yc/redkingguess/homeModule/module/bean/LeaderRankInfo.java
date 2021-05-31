package com.yc.redkingguess.homeModule.module.bean;

/**
 * Created by suns  on 2020/11/18 10:46.
 */
public class LeaderRankInfo {

    /**
     * user_id : 20
     * money : 7524.75
     * nickname :
     * level : 20
     */
    private String face;
    private int user_id;
    private String money;
    private String nickname;
    private int level;

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
