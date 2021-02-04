package com.yc.redguess.homeModule.module.bean;

import java.util.List;

public class TithDrawBeans {

    /**
     * cash_out : {"id":1,"wxstatus":1,"alistatus":0,"txstatus":1,"content":"说明","outamount":[{"money":"0.3","num":9,"level":"2,4,6,8,10,12,14,16,18","is_check":0,"total":0,"out_level":"2","other_num":9}],"addtime":1606295482}
     * user_other : {"id":12,"user_id":9995,"cash":"0.00","level":1,"group_id":1001,"prize_num":10,"guess_num":10,"treasure_num":0,"login_day":1,"out_status":1,"tx_wxid":""}
     * user_rand : [{"nickname":"","money":"200"}]
     */

    private CashOutBean cash_out;
    private UserOtherBean user_other;
    private List<UserRandBean> user_rand;
    private int out_today;//当天是否提现0:否1:是
    private int signed_day;//是否领取过连续签到7天奖励，0:否1:是

    public int getOut_today() {
        return out_today;
    }

    public void setOut_today(int out_today) {
        this.out_today = out_today;
    }

    public int getSigned_day() {
        return signed_day;
    }

    public void setSigned_day(int signed_day) {
        this.signed_day = signed_day;
    }

    public CashOutBean getCash_out() {
        return cash_out;
    }

    public void setCash_out(CashOutBean cash_out) {
        this.cash_out = cash_out;
    }

    public UserOtherBean getUser_other() {
        return user_other;
    }

    public void setUser_other(UserOtherBean user_other) {
        this.user_other = user_other;
    }

    public List<UserRandBean> getUser_rand() {
        return user_rand;
    }

    public void setUser_rand(List<UserRandBean> user_rand) {
        this.user_rand = user_rand;
    }

    public static class CashOutBean {
        /**
         * id : 1
         * wxstatus : 1
         * alistatus : 0
         * txstatus : 1
         * content : 说明
         * outamount : [{"money":"0.3","num":9,"level":"2,4,6,8,10,12,14,16,18","is_check":0,"total":0,"out_level":"2","other_num":9}]
         * addtime : 1606295482
         */

        private int id;
        private int wxstatus;
        private int alistatus;
        private int txstatus;
        private String content;
        private int addtime;
        private List<OutamountBean> outamount;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getWxstatus() {
            return wxstatus;
        }

        public void setWxstatus(int wxstatus) {
            this.wxstatus = wxstatus;
        }

        public int getAlistatus() {
            return alistatus;
        }

        public void setAlistatus(int alistatus) {
            this.alistatus = alistatus;
        }

        public int getTxstatus() {
            return txstatus;
        }

        public void setTxstatus(int txstatus) {
            this.txstatus = txstatus;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getAddtime() {
            return addtime;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public List<OutamountBean> getOutamount() {
            return outamount;
        }

        public void setOutamount(List<OutamountBean> outamount) {
            this.outamount = outamount;
        }

        public static class OutamountBean {
            /**
             * money : 0.3
             * num : 9
             * level : 2,4,6,8,10,12,14,16,18
             * is_check : 0
             * total : 0
             * out_level : 2
             * other_num : 9
             */
            private boolean isSelect;
            private String money;
            private int num;
            private String level;
            private int is_check;
            private int total;
            private String out_level;
            private int other_num;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public int getIs_check() {
                return is_check;
            }

            public void setIs_check(int is_check) {
                this.is_check = is_check;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public String getOut_level() {
                return out_level;
            }

            public void setOut_level(String out_level) {
                this.out_level = out_level;
            }

            public int getOther_num() {
                return other_num;
            }

            public void setOther_num(int other_num) {
                this.other_num = other_num;
            }
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
         * out_status : 1
         * tx_wxid :
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
        private int out_status;
        private String tx_wxid;

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

        public int getOut_status() {
            return out_status;
        }

        public void setOut_status(int out_status) {
            this.out_status = out_status;
        }

        public String getTx_wxid() {
            return tx_wxid;
        }

        public void setTx_wxid(String tx_wxid) {
            this.tx_wxid = tx_wxid;
        }
    }

    public static class UserRandBean {
        /**
         * nickname :
         * money : 200
         */

        private String nickname;
        private String money;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
