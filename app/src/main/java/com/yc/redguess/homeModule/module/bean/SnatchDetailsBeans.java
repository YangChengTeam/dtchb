package com.yc.redguess.homeModule.module.bean;

public class SnatchDetailsBeans {

    /**
     * id : 1
     * num : 0
     * money : 10.00
     * start_time : 1606097662
     * end_time : 1606097718
     * add_date : 20201123
     * add_num : 1
     * start : 10:14:22
     * user_num :
     * user_other : {"id":12,"user_id":9995,"cash":"0.00","level":1,"group_id":1001,"prize_num":10,"guess_num":10,"treasure_num":0,"login_day":1}
     */
    private long sys_time;
    private String excerpt;
    private String content;

    public long getSys_time() {
        return sys_time;
    }

    public void setSys_time(long sys_time) {
        this.sys_time = sys_time;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private int id;
    private int num;
    private String money;
    private long start_time;
    private long end_time;
    private int add_date;
    private int add_num;
    private String start;
    private String user_num;
    private UserOtherBean user_other;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getUser_num() {
        return user_num;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }

    public UserOtherBean getUser_other() {
        return user_other;
    }

    public void setUser_other(UserOtherBean user_other) {
        this.user_other = user_other;
    }

    public static class UserOtherBean {
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

        private int id;
        private int user_id;
        private String cash;
        private int level;
        private int group_id;
        private int prize_num;
        private int guess_num;
        private int treasure_num;
        private int login_day;

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
    }
}
