package com.yc.redkingguess.homeModule.module.bean;

public class SnatchTreasureDetailssBeans {

    /**
     * id : 1
     * user_id : 7117
     * num : 1000
     * money : 10.00
     * start_time : 1606097662
     * end_time : 1606101412
     * prize_num : 969
     * add_date : 20201123
     * add_num : 1
     * total : 45
     * start : 101422
     * end : 111652
     * result_num : 11323969144
     * user_num : 26
     * prize_user : {"nickname":"","face":""}
     */

    private int id;
    private int user_id;
    private int num;
    private String money;
    private int start_time;
    private int end_time;
    private String prize_num;
    private int add_date;
    private int add_num;
    private int total;
    private String start;
    private String end;
    private long result_num;
    private String user_num;
    private PrizeUserBean prize_user;

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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public String getPrize_num() {
        return prize_num;
    }

    public void setPrize_num(String prize_num) {
        this.prize_num = prize_num;
    }

    public int getAdd_date() {
        return add_date;
    }

    public void setAdd_date(int add_date) {
        this.add_date = add_date;
    }

    public int getAdd_num() {
        return add_num;
    }

    public void setAdd_num(int add_num) {
        this.add_num = add_num;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public long getResult_num() {
        return result_num;
    }

    public void setResult_num(long result_num) {
        this.result_num = result_num;
    }

    public String getUser_num() {
        return user_num;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }

    public PrizeUserBean getPrize_user() {
        return prize_user;
    }

    public void setPrize_user(PrizeUserBean prize_user) {
        this.prize_user = prize_user;
    }

    public static class PrizeUserBean {
        /**
         * nickname :
         * face :
         */

        private String nickname;
        private String face;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }
}
