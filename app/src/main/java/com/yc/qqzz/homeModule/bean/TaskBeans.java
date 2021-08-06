package com.yc.qqzz.homeModule.bean;

import java.util.List;

public class TaskBeans {

    /**
     * all_money : 365798.00
     * member_money : 182.90
     * uplevel_time : 0
     * user_other : {"id":12,"user_id":9995,"cash":"0.00","level":1,"group_id":1001,"prize_num":10,"guess_num":10,"treasure_num":0,"login_day":1,"is_vip":0}
     * task_info : [{"task_id":"1","num":"8","title":"手气红包","content":"","finish_num":0,"status":2}]
     * bonuses_info : {"id":1,"other_info":[{"title":"任务名称","excerpt":"任务简介","num":5,"other_id":1,"status":0,"finish_num":0}],"cash":"0.20"}
     */

    private String all_money;
    private String member_money;
    private long uplevel_time;
    private String red_money;
    private UserAccountInfoBeans user_other;
    private BonusesInfoBean bonuses_info;
    private List<TaskUnlockBeans> task_info;

    public String getRed_money() {
        return red_money;
    }

    public void setRed_money(String red_money) {
        this.red_money = red_money;
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

    public long getUplevel_time() {
        return uplevel_time;
    }

    public void setUplevel_time(long uplevel_time) {
        this.uplevel_time = uplevel_time;
    }

    public UserAccountInfoBeans getUser_other() {
        return user_other;
    }

    public void setUser_other(UserAccountInfoBeans user_other) {
        this.user_other = user_other;
    }

    public BonusesInfoBean getBonuses_info() {
        return bonuses_info;
    }

    public void setBonuses_info(BonusesInfoBean bonuses_info) {
        this.bonuses_info = bonuses_info;
    }

    public List<TaskUnlockBeans> getTask_info() {
        return task_info;
    }

    public void setTask_info(List<TaskUnlockBeans> task_info) {
        this.task_info = task_info;
    }



    public static class BonusesInfoBean {
        /**
         * id : 1
         * other_info : [{"title":"任务名称","excerpt":"任务简介","num":5,"other_id":1,"status":0,"finish_num":0}]
         * cash : 0.20
         */

        private int id;
        private String cash;
        private List<OtherInfoBean> other_info;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

        public List<OtherInfoBean> getOther_info() {
            return other_info;
        }

        public void setOther_info(List<OtherInfoBean> other_info) {
            this.other_info = other_info;
        }

        public static class OtherInfoBean {
            /**
             * title : 任务名称
             * excerpt : 任务简介
             * num : 5
             * other_id : 1
             * status : 0
             * finish_num : 0
             */

            private String title;
            private String excerpt;
            private int num;
            private int other_id;
            private int status;
            private int finish_num;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getExcerpt() {
                return excerpt;
            }

            public void setExcerpt(String excerpt) {
                this.excerpt = excerpt;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public int getOther_id() {
                return other_id;
            }

            public void setOther_id(int other_id) {
                this.other_id = other_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getFinish_num() {
                return finish_num;
            }

            public void setFinish_num(int finish_num) {
                this.finish_num = finish_num;
            }
        }
    }

}
