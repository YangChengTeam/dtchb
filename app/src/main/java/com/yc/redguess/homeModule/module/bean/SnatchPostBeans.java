package com.yc.redguess.homeModule.module.bean;

public class SnatchPostBeans {

    /**
     * new_level : 0
     * treasure_num : 996
     * user_num : 1,2,3,4
     */

    private int new_level;
    private int treasure_num;
    private String user_num;
    private int continuity_num;
    private int single_num;

    public int getContinuity_num() {
        return continuity_num;
    }

    public void setContinuity_num(int continuity_num) {
        this.continuity_num = continuity_num;
    }

    public int getSingle_num() {
        return single_num;
    }

    public void setSingle_num(int single_num) {
        this.single_num = single_num;
    }

    public int getNew_level() {
        return new_level;
    }

    public void setNew_level(int new_level) {
        this.new_level = new_level;
    }

    public int getTreasure_num() {
        return treasure_num;
    }

    public void setTreasure_num(int treasure_num) {
        this.treasure_num = treasure_num;
    }

    public String getUser_num() {
        return user_num;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }
}
