package com.yc.redguess.homeModule.module.bean;

public class HomeAllBeans {

    /**
     * user_other : {"id":12,"user_id":9995,"cash":"0.00","level":1,"group_id":1001,"prize_num":10,"guess_num":10,"treasure_num":0,"login_day":1}
     * group_num : 400
     * all_money :
     * member_money :
     * hongbao_num : 0
     */

    private UserOtherBean user_other;
    private SiginInfoBean sign_info;

    public SiginInfoBean getSign_info() {
        return sign_info;
    }

    public void setSign_info(SiginInfoBean sign_info) {
        this.sign_info = sign_info;
    }

    public void setOnline_red(long online_red) {
        this.online_red = online_red;
    }

    private int group_num;
    private String all_money;
    private String member_money;
    private int hongbao_num;
    private long online_red;
    private String on_money;
    private long sys_time;
    private String new_hongbao;
    private int treasure_state;
    private String new_treasure;

    public String getNew_hongbao() {
        return new_hongbao;
    }

    public void setNew_hongbao(String new_hongbao) {
        this.new_hongbao = new_hongbao;
    }

    public int getTreasure_state() {
        return treasure_state;
    }

    public void setTreasure_state(int treasure_state) {
        this.treasure_state = treasure_state;
    }

    public String getNew_treasure() {
        return new_treasure;
    }

    public void setNew_treasure(String new_treasure) {
        this.new_treasure = new_treasure;
    }

    public long getSys_time() {
        return sys_time;
    }

    public void setSys_time(long sys_time) {
        this.sys_time = sys_time;
    }

    public long getOnline_red() {
        return online_red;
    }

    public void setOnline_red(int online_red) {
        this.online_red = online_red;
    }

    public String getOn_money() {
        return on_money;
    }

    public void setOn_money(String on_money) {
        this.on_money = on_money;
    }

    public UserOtherBean getUser_other() {
        return user_other;
    }

    public void setUser_other(UserOtherBean user_other) {
        this.user_other = user_other;
    }

    public int getGroup_num() {
        return group_num;
    }

    public void setGroup_num(int group_num) {
        this.group_num = group_num;
    }

    public String getAll_money() {
        return all_money;
    }

    public void setAll_money(String all_money) {
        this.all_money = all_money;
    }

    public String getMember_money() {
        return member_money;
    }

    public void setMember_money(String member_money) {
        this.member_money = member_money;
    }

    public int getHongbao_num() {
        return hongbao_num;
    }

    public void setHongbao_num(int hongbao_num) {
        this.hongbao_num = hongbao_num;
    }
    public static class SiginInfoBean {

        /**
         * money : 0.2
         * user_id : 1
         * add_time : 1608194791
         * add_date : 20201217
         * sign_id : 1
         */

        private String money;
        private int user_id;
        private int add_time;
        private String add_date;
        private String sign_id;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public String getAdd_date() {
            return add_date;
        }

        public void setAdd_date(String add_date) {
            this.add_date = add_date;
        }

        public String getSign_id() {
            return sign_id;
        }

        public void setSign_id(String sign_id) {
            this.sign_id = sign_id;
        }
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
