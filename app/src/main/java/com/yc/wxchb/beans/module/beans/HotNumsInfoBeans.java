package com.yc.wxchb.beans.module.beans;

import java.util.List;

public class HotNumsInfoBeans {

    /**
     * hb_num : 0
     * download : {"id":1,"is_open":1,"content":"","time_out":0,"num":1,"step_config":["1","2","3"]}
     * download_config : {"id":2,"agent_pid":173,"agent_id":1,"first_video":1,"video_num":2,"total":3,"ad_video":"1","down_award":2,"play_award":3,"level":1,"level_state":0,"is_new_user":0}
     */
    private int question_num;
    private int hb_num;
    private DownloadBean download;
    private DownloadConfigBean download_config;

    public int getQuestion_num() {
        return question_num;
    }

    public void setQuestion_num(int question_num) {
        this.question_num = question_num;
    }
    public int getHb_num() {
        return hb_num;
    }

    public void setHb_num(int hb_num) {
        this.hb_num = hb_num;
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
}
