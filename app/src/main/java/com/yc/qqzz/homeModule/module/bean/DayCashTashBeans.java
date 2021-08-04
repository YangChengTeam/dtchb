package com.yc.qqzz.homeModule.module.bean;

import java.io.Serializable;
import java.util.List;

public class DayCashTashBeans implements Serializable {

    /**
     * dayhb : 0
     * down_list : [{"id":1,"app_name":"APP名称","icon_url":"图片地址","package_name":"包名","down_url":"下载地址","state":1,"sort":1,"describe":"下载说明","money":"奖励金额","add_time":0,"status":0}]
     */

    private int dayhb;
    private List<DownListBean> down_list;

    public int getDayhb() {
        return dayhb;
    }

    public void setDayhb(int dayhb) {
        this.dayhb = dayhb;
    }

    public List<DownListBean> getDown_list() {
        return down_list;
    }

    public void setDown_list(List<DownListBean> down_list) {
        this.down_list = down_list;
    }

    public static class DownListBean  implements Serializable{
        /**
         * id : 1
         * app_name : APP名称
         * icon_url : 图片地址
         * package_name : 包名
         * down_url : 下载地址
         * state : 1
         * sort : 1
         * describe : 下载说明
         * money : 奖励金额
         * add_time : 0
         * status : 0
         */

        private int id;
        private String app_name;
        private String icon_url;
        private String package_name;
        private String down_url;
        private int state;
        private int sort;
        private String describe;
        private String money;
        private int add_time;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getApp_name() {
            return app_name;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public String getPackage_name() {
            return package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public String getDown_url() {
            return down_url;
        }

        public void setDown_url(String down_url) {
            this.down_url = down_url;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
