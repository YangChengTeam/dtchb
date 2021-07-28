package com.yc.qqzz.homeModule.bean;

public class HomeLineRedBeans {

    /**
     *  hb_online : {"id":1,"min_money":"0.01","max_money":"0.20","num":10,"time_out":10}
     * has_hb_num : 0
     * sys_time : 1627090371
     * last_hb_time : 0
     */

    private hbOnlineBean hb_online;
    private int has_hb_num;
    private long sys_time;
    private long last_hb_time;

    public hbOnlineBean getHb_online() {
        return hb_online;
    }

    public void setHb_online(hbOnlineBean hb_online) {
        this.hb_online = hb_online;
    }

    public int getHas_hb_num() {
        return has_hb_num;
    }

    public void setHas_hb_num(int has_hb_num) {
        this.has_hb_num = has_hb_num;
    }

    public long getSys_time() {
        return sys_time;
    }

    public void setSys_time(long sys_time) {
        this.sys_time = sys_time;
    }

    public long getLast_hb_time() {
        return last_hb_time;
    }

    public void setLast_hb_time(long last_hb_time) {
        this.last_hb_time = last_hb_time;
    }

    public static class hbOnlineBean {
        /**
         * id : 1
         * min_money : 0.01
         * max_money : 0.20
         * num : 10
         * time_out : 10
         */

        private int id;
        private String min_money;
        private String max_money;
        private int num;
        private int time_out;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMin_money() {
            return min_money;
        }

        public void setMin_money(String min_money) {
            this.min_money = min_money;
        }

        public String getMax_money() {
            return max_money;
        }

        public void setMax_money(String max_money) {
            this.max_money = max_money;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getTime_out() {
            return time_out;
        }

        public void setTime_out(int time_out) {
            this.time_out = time_out;
        }
    }
}
