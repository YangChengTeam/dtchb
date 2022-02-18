package com.yc.wxchb.beans.module.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotIndexBeans {

    /**
     * hb_num : 0
     * downnum : 57
     * download : {"id":1,"is_open":1,"content":"","time_out":0,"num":1,"step_config":["1","2","3"]}
     * download_config : {"id":2,"agent_pid":173,"agent_id":1,"first_video":1,"video_num":2,"total":3,"ad_video":"1","down_award":2,"play_award":3,"level":1,"level_state":0,"is_new_user":0}
     * down_loaduser : {"id":1,"user_id":29311,"app_name":"test","package":"test","status":1,"add_time":0,"upd_time":0,"add_date":20211230}
     */

    private int hb_num;
    private int downnum;
    private DownloadBean download;
    private DownloadConfigBean download_config;
    private DownLoaduserBean download_user;

    public int getHb_num() {
        return hb_num;
    }

    public void setHb_num(int hb_num) {
        this.hb_num = hb_num;
    }

    public int getDownnum() {
        return downnum;
    }

    public void setDownnum(int downnum) {
        this.downnum = downnum;
    }

    public DownloadBean getDownload() {
        return download;
    }

    public void setDownload(DownloadBean download) {
        this.download = download;
    }

    public DownloadConfigBean getDownload_config() {
        return download_config;
    }

    public void setDownload_config(DownloadConfigBean download_config) {
        this.download_config = download_config;
    }

    public DownLoaduserBean getDown_loaduser() {
        return download_user;
    }

    public void setDown_loaduser(DownLoaduserBean down_loaduser) {
        this.download_user = down_loaduser;
    }

    public static class DownloadBean {
        /**
         * id : 1
         * is_open : 1
         * content :
         * time_out : 0
         * num : 1
         * step_config : ["1","2","3"]
         */

        private int id;
        private int is_open;
        private String content;
        private int time_out;
        private int num;
        private List<String> step_config;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_open() {
            return is_open;
        }

        public void setIs_open(int is_open) {
            this.is_open = is_open;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getTime_out() {
            return time_out;
        }

        public void setTime_out(int time_out) {
            this.time_out = time_out;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public List<String> getStep_config() {
            return step_config;
        }

        public void setStep_config(List<String> step_config) {
            this.step_config = step_config;
        }
    }

    public static class DownloadConfigBean {
        /**
         * id : 2
         * agent_pid : 173
         * agent_id : 1
         * first_video : 1
         * video_num : 2
         * total : 3
         * ad_video : 1
         * down_award : 2
         * play_award : 3
         * level : 1
         * level_state : 0
         * is_new_user : 0
         */

        private int id;
        private int agent_pid;
        private int agent_id;
        private int first_video;
        private int video_num;
        private int total;
        private String ad_video;
        private int down_award;
        private int play_award;
        private int level;
        private int level_state;
        private int is_new_user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAgent_pid() {
            return agent_pid;
        }

        public void setAgent_pid(int agent_pid) {
            this.agent_pid = agent_pid;
        }

        public int getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(int agent_id) {
            this.agent_id = agent_id;
        }

        public int getFirst_video() {
            return first_video;
        }

        public void setFirst_video(int first_video) {
            this.first_video = first_video;
        }

        public int getVideo_num() {
            return video_num;
        }

        public void setVideo_num(int video_num) {
            this.video_num = video_num;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getAd_video() {
            return ad_video;
        }

        public void setAd_video(String ad_video) {
            this.ad_video = ad_video;
        }

        public int getDown_award() {
            return down_award;
        }

        public void setDown_award(int down_award) {
            this.down_award = down_award;
        }

        public int getPlay_award() {
            return play_award;
        }

        public void setPlay_award(int play_award) {
            this.play_award = play_award;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getLevel_state() {
            return level_state;
        }

        public void setLevel_state(int level_state) {
            this.level_state = level_state;
        }

        public int getIs_new_user() {
            return is_new_user;
        }

        public void setIs_new_user(int is_new_user) {
            this.is_new_user = is_new_user;
        }
    }

    public static class DownLoaduserBean {
        /**
         * id : 1
         * user_id : 29311
         * app_name : test
         * package : test
         * status : 1
         * add_time : 0
         * upd_time : 0
         * add_date : 20211230
         */

        private int id;
        private int user_id;
        private String app_name;
        @SerializedName("package")
        private String packageX;
        private int status;
        private int add_time;
        private int upd_time;
        private int add_date;

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

        public String getApp_name() {
            return app_name;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public int getUpd_time() {
            return upd_time;
        }

        public void setUpd_time(int upd_time) {
            this.upd_time = upd_time;
        }

        public int getAdd_date() {
            return add_date;
        }

        public void setAdd_date(int add_date) {
            this.add_date = add_date;
        }
    }
}
