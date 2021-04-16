package com.yc.majiaredgrab.homeModule.module.bean;

import java.util.List;

public class GuessHistoryBeans {

    /**
     * total : 2
     * money : 20
     * list : [{"id":2,"guessno":2020111902,"money":"10.00","num":4553,"user_id":81,"add_time":1605786420,"nickname":"","face":""}]
     */

    private int total;
    private String money;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 2
         * guessno : 2020111902
         * money : 10.00
         * num : 4553
         * user_id : 81
         * add_time : 1605786420
         * nickname :
         * face :
         */

        private int id;
        private int guessno;
        private String money;
        private int num;
        private int user_id;
        private int add_time;
        private String nickname;
        private String face;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
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
