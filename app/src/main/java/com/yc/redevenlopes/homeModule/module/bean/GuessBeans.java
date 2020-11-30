package com.yc.redevenlopes.homeModule.module.bean;

import java.util.List;

public class GuessBeans {

    /**
     * total : 14992
     * range : ["1918","3442","4957","2706","1969"]
     * info_id : 3
     * guessno : 2020112001
     * money : 10.00
     * guess_time : 12:00:00
     * add_num : 4232,4231
     * user_other : {"id":12,"user_id":9995,"cash":"0.00","level":1,"group_id":1001,"prize_num":10,"guess_num":10,"treasure_num":0,"login_day":1}
     * excerpt :
     * content :
     */

    private String total;
    private int info_id;
    private int guessno;
    private String money;
    private String guess_time;
    private String user_num;
    private UserOtherBean user_other;
    private String excerpt;
    private String content;
    private List<String> range;

    public String getUser_num() {
        return user_num;
    }

    public void setUser_num(String user_num) {
        this.user_num = user_num;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }

    public int getGuessno() {
        return guessno;
    }

    public void setGuessno(int guessno) {
        this.guessno = guessno;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getGuess_time() {
        return guess_time;
    }

    public void setGuess_time(String guess_time) {
        this.guess_time = guess_time;
    }



    public UserOtherBean getUser_other() {
        return user_other;
    }

    public void setUser_other(UserOtherBean user_other) {
        this.user_other = user_other;
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

    public List<String> getRange() {
        return range;
    }

    public void setRange(List<String> range) {
        this.range = range;
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
