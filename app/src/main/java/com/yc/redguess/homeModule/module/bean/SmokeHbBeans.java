package com.yc.redguess.homeModule.module.bean;

import java.util.List;

public class SmokeHbBeans {

    /**
     * sys_time : 0
     * next_time : 0
     * double_num : 2
     * lucky_num : 96
     * lucky_auto : 1
     * list : [{"id":1,"user_id":1,"money":"0.02","status":1}]
     */

    private long sys_time;
    private long next_time;
    private int double_num;
    private int lucky_num;
    private int lucky_auto;
    private List<ListBean> list;

    public long getSys_time() {
        return sys_time;
    }

    public void setSys_time(long sys_time) {
        this.sys_time = sys_time;
    }

    public long getNext_time() {
        return next_time;
    }

    public void setNext_time(long next_time) {
        this.next_time = next_time;
    }

    public int getDouble_num() {
        return double_num;
    }

    public void setDouble_num(int double_num) {
        this.double_num = double_num;
    }

    public int getLucky_num() {
        return lucky_num;
    }

    public void setLucky_num(int lucky_num) {
        this.lucky_num = lucky_num;
    }

    public int getLucky_auto() {
        return lucky_auto;
    }

    public void setLucky_auto(int lucky_auto) {
        this.lucky_auto = lucky_auto;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * user_id : 1
         * money : 0.02
         * status : 1
         */

        private int id;
        private int user_id;
        private String money;
        private int status;

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

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
