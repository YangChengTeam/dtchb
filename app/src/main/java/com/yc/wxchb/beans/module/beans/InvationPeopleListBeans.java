package com.yc.wxchb.beans.module.beans;

public class InvationPeopleListBeans {

    /**
     * id : 17
     * user_id : 4615
     * be_invite_user : {"id":4632,"nickname":""}
     * is_meet : 1
     * add_time : 1648724824
     */

    private int id;
    private int user_id;
    private BeInviteUserBean be_invite_user;
    private int is_meet;
    private int add_time;

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

    public BeInviteUserBean getBe_invite_user() {
        return be_invite_user;
    }

    public void setBe_invite_user(BeInviteUserBean be_invite_user) {
        this.be_invite_user = be_invite_user;
    }

    public int getIs_meet() {
        return is_meet;
    }

    public void setIs_meet(int is_meet) {
        this.is_meet = is_meet;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public static class BeInviteUserBean {
        /**
         * id : 4632
         * nickname :
         */

        private int id;
        private String nickname;

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
    }
}
