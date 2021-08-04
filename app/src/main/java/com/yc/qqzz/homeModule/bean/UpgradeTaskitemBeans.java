package com.yc.qqzz.homeModule.bean;

import java.util.List;

public class UpgradeTaskitemBeans {


    /**
     * day_cash : [{"id":4,"type":1,"money":"10.00","is_must":0}]
     * daylevel : [{"id":4,"type":2,"money":"0.00","level_num":2,"must_level":"","is_must":0,"rand_percent":0}]
     * state : 0
     * num : 0
     * is_before : 1
     * task_arr : [{"title":"看一次视频","excerpt":"观看视频广告","num":1,"is_click":0,"other_id":1,"is_finish":0,"finish_num":0}]
     */

    private int state;
    private int num;
    private int is_before;
    private String out_money;
    private List<DayCashBean> day_cash;
    private List<DaylevelBean> day_level;
    private List<TaskArrBean> task_arr;
    private List<TaskArrBean> after_task;

    public List<DaylevelBean> getDay_level() {
        return day_level;
    }

    public void setDay_level(List<DaylevelBean> day_level) {
        this.day_level = day_level;
    }

    public List<TaskArrBean> getAfter_task() {
        return after_task;
    }

    public void setAfter_task(List<TaskArrBean> after_task) {
        this.after_task = after_task;
    }

    public String getOut_money() {
        return out_money;
    }

    public void setOut_money(String out_money) {
        this.out_money = out_money;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getIs_before() {
        return is_before;
    }

    public void setIs_before(int is_before) {
        this.is_before = is_before;
    }

    public List<DayCashBean> getDay_cash() {
        return day_cash;
    }

    public void setDay_cash(List<DayCashBean> day_cash) {
        this.day_cash = day_cash;
    }

    public List<DaylevelBean> getDaylevel() {
        return day_level;
    }

    public void setDaylevel(List<DaylevelBean> daylevel) {
        this.day_level = daylevel;
    }

    public List<TaskArrBean> getTask_arr() {
        return task_arr;
    }

    public void setTask_arr(List<TaskArrBean> task_arr) {
        this.task_arr = task_arr;
    }

    public static class DayCashBean {
        /**
         * id : 4
         * type : 1
         * money : 10.00
         * is_must : 0
         */

        private int id;
        private int type;
        private String money;
        private int is_must;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getIs_must() {
            return is_must;
        }

        public void setIs_must(int is_must) {
            this.is_must = is_must;
        }
    }

    public static class DaylevelBean {
        /**
         * id : 4
         * type : 2
         * money : 0.00
         * level_num : 2
         * must_level :
         * is_must : 0
         * rand_percent : 0
         */

        private int id;
        private int type;
        private String money;
        private int level_num;
        private String must_level;
        private int is_must;
        private int rand_percent;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getLevel_num() {
            return level_num;
        }

        public void setLevel_num(int level_num) {
            this.level_num = level_num;
        }

        public String getMust_level() {
            return must_level;
        }

        public void setMust_level(String must_level) {
            this.must_level = must_level;
        }

        public int getIs_must() {
            return is_must;
        }

        public void setIs_must(int is_must) {
            this.is_must = is_must;
        }

        public int getRand_percent() {
            return rand_percent;
        }

        public void setRand_percent(int rand_percent) {
            this.rand_percent = rand_percent;
        }
    }

    public static class TaskArrBean {
        /**
         * title : 看一次视频
         * excerpt : 观看视频广告
         * num : 1
         * is_click : 0
         * other_id : 1
         * is_finish : 0
         * finish_num : 0
         */

        private String title;
        private String excerpt;
        private int num;
        private int is_click;
        private int other_id;
        private int is_finish;
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

        public int getIs_click() {
            return is_click;
        }

        public void setIs_click(int is_click) {
            this.is_click = is_click;
        }

        public int getOther_id() {
            return other_id;
        }

        public void setOther_id(int other_id) {
            this.other_id = other_id;
        }

        public int getIs_finish() {
            return is_finish;
        }

        public void setIs_finish(int is_finish) {
            this.is_finish = is_finish;
        }

        public int getFinish_num() {
            return finish_num;
        }

        public void setFinish_num(int finish_num) {
            this.finish_num = finish_num;
        }
    }
}
