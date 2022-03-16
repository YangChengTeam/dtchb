package com.yc.wxchb.beans.module.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class OtherBeans implements Parcelable {

    /**
     * id : 12
     * user_id : 9995
     * cash : 0.00
     * level : 1
     * group_id : 1001
     * prize_num : 10
     * guess_num : 10
     * treasure_num : 0
     * login_day : 1
     */
    private int hb_num;
    private int id;
    private int user_id;
    private String cash;
    private int level;
    private int group_id;
    private int prize_num;
    private int guess_num;
    private int treasure_num;
    private int login_day;

    public int getHb_num() {
        return hb_num;
    }

    public void setHb_num(int hb_num) {
        this.hb_num = hb_num;
    }

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

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.user_id);
        dest.writeString(this.cash);
        dest.writeInt(this.level);
        dest.writeInt(this.group_id);
        dest.writeInt(this.prize_num);
        dest.writeInt(this.guess_num);
        dest.writeInt(this.treasure_num);
        dest.writeInt(this.login_day);
    }

    public OtherBeans() {
    }

    protected OtherBeans(Parcel in) {
        this.id = in.readInt();
        this.user_id = in.readInt();
        this.cash = in.readString();
        this.level = in.readInt();
        this.group_id = in.readInt();
        this.prize_num = in.readInt();
        this.guess_num = in.readInt();
        this.treasure_num = in.readInt();
        this.login_day = in.readInt();
    }

    public static final Creator<OtherBeans> CREATOR = new Creator<OtherBeans>() {
        @Override
        public OtherBeans createFromParcel(Parcel source) {
            return new OtherBeans(source);
        }

        @Override
        public OtherBeans[] newArray(int size) {
            return new OtherBeans[size];
        }
    };
}
