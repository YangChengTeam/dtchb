package com.yc.qqzz.homeModule.module.bean;

public class InvitationShareBeans {

    /**
     * add_date :
     * user_info : {"id":11,"nickname":"","face":""}
     */

    private String add_date;
    private UserInfoBean user_info;

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
         * id : 11
         * nickname :
         * face :
         */

        private int id;
        private String nickname;
        private String face;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
