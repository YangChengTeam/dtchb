package com.yc.redguess.homeModule.module.bean;

import java.util.List;

public class RedDetailsBeans {

    /**
     * sum_money : 10.27
     * total : 200
     * get_info : {"id":1,"info_id":18,"user_id":1,"money":"4.35","add_time":1605253682}
     * list : [{"id":1,"info_id":18,"user_id":1,"money":"4.35","add_time":1605253682,"add_date":"15:48","user_info":{"nickname":"","face":""}}]
     */

    private String sum_money;
    private int total;
    private GetInfoBean get_info;
    private List<ListBean> list;

    public String getSum_money() {
        return sum_money;
    }

    public void setSum_money(String sum_money) {
        this.sum_money = sum_money;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public GetInfoBean getGet_info() {
        return get_info;
    }

    public void setGet_info(GetInfoBean get_info) {
        this.get_info = get_info;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class GetInfoBean {
        /**
         * id : 1
         * info_id : 18
         * user_id : 1
         * money : 4.35
         * add_time : 1605253682
         */

        private int id;
        private int info_id;
        private int user_id;
        private String money;
        private long add_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getInfo_id() {
            return info_id;
        }

        public void setInfo_id(int info_id) {
            this.info_id = info_id;
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

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }
    }

    public static class ListBean {
        /**
         * id : 1
         * info_id : 18
         * user_id : 1
         * money : 4.35
         * add_time : 1605253682
         * add_date : 15:48
         * user_info : {"nickname":"","face":""}
         */

        private int id;
        private int info_id;
        private int user_id;
        private String money;
        private long add_time;
        private String add_date;
        private UserInfoBean user_info;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getInfo_id() {
            return info_id;
        }

        public void setInfo_id(int info_id) {
            this.info_id = info_id;
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

        public long getAdd_time() {
            return add_time;
        }

        public void setAdd_time(long add_time) {
            this.add_time = add_time;
        }

        public String getAdd_date() {
            return add_date;
        }

        public void setAdd_date(String add_date) {
            this.add_date = add_date;
        }

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public static class UserInfoBean {
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
}
