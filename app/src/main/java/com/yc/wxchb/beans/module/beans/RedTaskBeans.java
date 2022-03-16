package com.yc.wxchb.beans.module.beans;

import java.util.List;

public class RedTaskBeans {

    /**
     * hb_online_task : [{"id":1,"num":10,"money_range":"1,2","type":0,"time_second":0,"other_num":10,"finish_num":10,"status":2}]
     * hb_num : 0
     * sys_time : 1646730266
     * last_hb_time : 1646729726
     */
    private int time_out;
    private int hb_num;
    private int sys_time;
    private int last_hb_time;
    private List<HbOnlineTaskBean> hb_online_task;

    public int getTime_out() {
        return time_out;
    }

    public void setTime_out(int time_out) {
        this.time_out = time_out;
    }

    public int getHb_num() {
        return hb_num;
    }

    public void setHb_num(int hb_num) {
        this.hb_num = hb_num;
    }

    public int getSys_time() {
        return sys_time;
    }

    public void setSys_time(int sys_time) {
        this.sys_time = sys_time;
    }

    public int getLast_hb_time() {
        return last_hb_time;
    }

    public void setLast_hb_time(int last_hb_time) {
        this.last_hb_time = last_hb_time;
    }

    public List<HbOnlineTaskBean> getHb_online_task() {
        return hb_online_task;
    }

    public void setHb_online_task(List<HbOnlineTaskBean> hb_online_task) {
        this.hb_online_task = hb_online_task;
    }

    public static class HbOnlineTaskBean {
        /**
         * id : 1
         * num : 10
         * money_range : 1,2
         * type : 0
         * time_second : 0
         * other_num : 10
         * finish_num : 10
         * status : 2
         */

        private int id;
        private int num;
        private String money_range;
        private int type;
        private int time_second;
        private int other_num;
        private int finish_num;
        private int status;

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

        public String getMoney_range() {
            return money_range;
        }

        public void setMoney_range(String money_range) {
            this.money_range = money_range;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTime_second() {
            return time_second;
        }

        public void setTime_second(int time_second) {
            this.time_second = time_second;
        }

        public int getOther_num() {
            return other_num;
        }

        public void setOther_num(int other_num) {
            this.other_num = other_num;
        }

        public int getFinish_num() {
            return finish_num;
        }

        public void setFinish_num(int finish_num) {
            this.finish_num = finish_num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
