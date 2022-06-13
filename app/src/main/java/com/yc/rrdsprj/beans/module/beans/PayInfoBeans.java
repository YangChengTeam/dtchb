package com.yc.rrdsprj.beans.module.beans;

import java.util.List;

public class PayInfoBeans {

    /**
     * lottery_num : 0
     * out_arr : [{"id":1,"user_id":1,"lottery_id":3,"money":"0.00","out_money":"145.60","hb_task_id":2,"add_time":1646808616,"add_date":20220309,"time_second":3000,"other_time":1526}]
     * cash_out : {"id":1,"wxstatus":1,"alistatus":0,"txstatus":1,"seevido":0,"content":"说明","outamount":[{"money":"0.3","num":9,"is_check":0,"total":0,"other_num":9}]}
     * user_other : {"id":12,"user_id":9995,"cash":"0.00","level":1,"group_id":1001,"prize_num":10,"guess_num":10,"treasure_num":0,"login_day":1,"out_status":1,"invite_code":"","be_invite_code":"","invite_num":0,"invite_meet_num":0,"invite_exchange_num":0}
     */

    private int lottery_num;
    private CashOutBean cash_out;
    private UserOtherBean user_other;
    private List<OutArrBean> out_arr;

    public int getLottery_num() {
        return lottery_num;
    }

    public void setLottery_num(int lottery_num) {
        this.lottery_num = lottery_num;
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

    public List<OutArrBean> getOut_arr() {
        return out_arr;
    }

    public void setOut_arr(List<OutArrBean> out_arr) {
        this.out_arr = out_arr;
    }

    public static class CashOutBean {
        /**
         * id : 1
         * wxstatus : 1
         * alistatus : 0
         * txstatus : 1
         * seevido : 0
         * content : 说明
         * outamount : [{"money":"0.3","num":9,"is_check":0,"total":0,"other_num":9}]
         */

        private int id;
        private int wxstatus;
        private int alistatus;
        private int txstatus;
        private int seevido;
        private String content;
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

        public int getSeevido() {
            return seevido;
        }

        public void setSeevido(int seevido) {
            this.seevido = seevido;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
             * is_check : 0
             * total : 0
             * other_num : 9
             */
            private String hb_total;
            private boolean isSelect;
            private String money;
            private int num;
            private int is_check;
            private int total;
            private int other_num;

            public String getHb_total() {
                return hb_total;
            }

            public void setHb_total(String hb_total) {
                this.hb_total = hb_total;
            }

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
         * invite_code :
         * be_invite_code :
         * invite_num : 0
         * invite_meet_num : 0
         * invite_exchange_num : 0
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
        private String invite_code;
        private String be_invite_code;
        private int invite_num;
        private int invite_meet_num;
        private int invite_exchange_num;

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

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getBe_invite_code() {
            return be_invite_code;
        }

        public void setBe_invite_code(String be_invite_code) {
            this.be_invite_code = be_invite_code;
        }

        public int getInvite_num() {
            return invite_num;
        }

        public void setInvite_num(int invite_num) {
            this.invite_num = invite_num;
        }

        public int getInvite_meet_num() {
            return invite_meet_num;
        }

        public void setInvite_meet_num(int invite_meet_num) {
            this.invite_meet_num = invite_meet_num;
        }

        public int getInvite_exchange_num() {
            return invite_exchange_num;
        }

        public void setInvite_exchange_num(int invite_exchange_num) {
            this.invite_exchange_num = invite_exchange_num;
        }
    }

    public static class OutArrBean {
        /**
         * id : 1
         * user_id : 1
         * lottery_id : 3
         * money : 0.00
         * out_money : 145.60
         * hb_task_id : 2
         * add_time : 1646808616
         * add_date : 20220309
         * time_second : 3000
         * other_time : 1526
         */

        private int id;
        private int user_id;
        private int lottery_id;
        private String money;
        private String out_money;
        private int hb_task_id;
        private long add_time;
        private long add_date;
        private long time_second;
        private long other_time;

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

        public int getLottery_id() {
            return lottery_id;
        }

        public void setLottery_id(int lottery_id) {
            this.lottery_id = lottery_id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getOut_money() {
            return out_money;
        }

        public void setOut_money(String out_money) {
            this.out_money = out_money;
        }

        public int getHb_task_id() {
            return hb_task_id;
        }

        public void setHb_task_id(int hb_task_id) {
            this.hb_task_id = hb_task_id;
        }

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public long getAdd_date() {
            return add_date;
        }

        public void setAdd_date(long add_date) {
            this.add_date = add_date;
        }

        public long getTime_second() {
            return time_second;
        }

        public void setTime_second(int time_second) {
            this.time_second = time_second;
        }

        public long getOther_time() {
            return other_time;
        }

        public void setOther_time(int other_time) {
            this.other_time = other_time;
        }
    }
}
