package com.yc.jsdsp.beans.module.beans;

import java.util.List;

public class AnswerFgBeans {

    /**
     * next_reward_num : 20
     * quesion_user : {"id":8,"user_id":10050,"answer_num":9,"right_num":1,"continue_num":0,"danmu":0}
     * question_config : {"min_money":"0.01","max_money":"0.2","video_num":"4","video_double":"2","reward_num":"20","reward_money":"0.3","continuity":[{"question_num":"5","addition_percent":"10"}]}
     * question_list : [{"id":1398,"sort":1398,"question":"红色霓虹灯里填充的哪种气体？","right_key":3,"option":"氮#氧#氖","difficult_degree":1,"file":"http://music-yy.imguf.com/subject/1398.mp3"},{"id":1319,"sort":1319,"question":"《楚辞》中有\u201c兰膏照烛\u201d一句。这里点灯用的\u201c膏\u201d指的是什么？","right_key":3,"option":"植物油#煤油#动物油","difficult_degree":1,"file":"http://music-yy.imguf.com/subject/1319.mp3"},{"id":618,"sort":618,"question":"洗鱼后手上有腥味用下列哪种东西可以往除","right_key":2,"option":"奶粉#牙膏","difficult_degree":1,"file":"http://music-yy.imguf.com/subject/618.mp3"},{"id":669,"sort":669,"question":"测试平衡能力最好的方法是？","right_key":4,"option":"金鸡独立#单脚闭眼站立#马步蹲桩#燕式平衡","difficult_degree":1,"file":"http://music-yy.imguf.com/subject/669.mp3"},{"id":623,"sort":1999,"question":"水杉是因为什么被称作活恐龙\u201d？","right_key":2,"option":"树高冠大#与恐龙同代","difficult_degree":1,"file":"http://music-yy.imguf.com/subject/623.mp3"},{"id":523,"sort":523,"question":"\"皇帝\"作为国家元首的正式称号，始于：","right_key":4,"option":"夏姬#商纣#周武#秦始皇","difficult_degree":1,"file":"http://music-yy.imguf.com/subject/523.mp3"},{"id":587,"sort":587,"question":"被蜜蜂蜇伤后局部可用5%~20%的什么湿敷？","right_key":2,"option":"糖水#小苏打水","difficult_degree":1,"file":"http://music-yy.imguf.com/subject/587.mp3"},{"id":323,"sort":323,"question":"一般而言， 山羊皮革和绵羊皮革哪个好？","right_key":1,"option":"山羊皮革#绵羊皮革#差不多好","difficult_degree":1,"file":"http://music-yy.imguf.com/subject/323.mp3"},{"id":1342,"sort":1342,"question":"第一届世界杯足球赛是在哪个国家举行？","right_key":3,"option":"法国#意大利#乌拉圭","difficult_degree":1,"file":"http://music-yy.imguf.com/subject/1342.mp3"},{"id":403,"sort":1999,"question":"\u201c紫禁城\u201d是古代的皇宫。","right_key":2,"option":"错误#正确","difficult_degree":1,"file":"http://music-yy.imguf.com/subject/403.mp3"}]
     */
    private String balance_money;
    private int next_reward_num;
    private QuesionUserBean quesion_user;
    private QuestionConfigBean question_config;
    private List<QuestionListBean> question_list;
    private int question_user_day;
    private QuestionHuoliBean question_huoli;

    public String getBalance_money() {
        return balance_money;
    }

    public void setBalance_money(String balance_money) {
        this.balance_money = balance_money;
    }

    public int getQuestion_user_day() {
        return question_user_day;
    }

    public void setQuestion_user_day(int question_user_day) {
        this.question_user_day = question_user_day;
    }

    public QuestionHuoliBean getQuestion_huoli() {
        return question_huoli;
    }

    public void setQuestion_huoli(QuestionHuoliBean question_huoli) {
        this.question_huoli = question_huoli;
    }

    public String getCash() {
        return balance_money;
    }

    public void setCash(String cash) {
        this.balance_money = cash;
    }

    public int getNext_reward_num() {
        return next_reward_num;
    }

    public void setNext_reward_num(int next_reward_num) {
        this.next_reward_num = next_reward_num;
    }

    public QuesionUserBean getQuesion_user() {
        return quesion_user;
    }

    public void setQuesion_user(QuesionUserBean quesion_user) {
        this.quesion_user = quesion_user;
    }

    public QuestionConfigBean getQuestion_config() {
        return question_config;
    }

    public void setQuestion_config(QuestionConfigBean question_config) {
        this.question_config = question_config;
    }

    public List<QuestionListBean> getQuestion_list() {
        return question_list;
    }

    public void setQuestion_list(List<QuestionListBean> question_list) {
        this.question_list = question_list;
    }

    public static class QuestionHuoliBean {
        /**
         * id : 1
         * agent_pid : 173
         * agent_id : 1
         * first_video : 1
         * video_num : 1
         * total : 10
         * ad_video : 1,2,3
         * award : 100
         * award_config : ["400","250","200","150"]
         * level : 1
         * level_state : 0
         * is_new_user : 0
         * shuoming : <p><span style="text-decoration: none;">点击开始任务，观看视频并点击下载安装即可获得最高百倍的活跃值奖励！</span></p>
         */

        private int id;
        private int agent_pid;
        private int agent_id;
        private int first_video;
        private int video_num;
        private int total;
        private String ad_video;
        private int award;
        private String award_config;
        private int level;
        private int level_state;
        private int is_new_user;
        private String shuoming;

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

        public int getAward() {
            return award;
        }

        public void setAward(int award) {
            this.award = award;
        }

        public String getAward_config() {
            return award_config;
        }

        public void setAward_config(String award_config) {
            this.award_config = award_config;
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

        public String getShuoming() {
            return shuoming;
        }

        public void setShuoming(String shuoming) {
            this.shuoming = shuoming;
        }
    }

    public static class QuesionUserBean {
        /**
         * id : 8
         * user_id : 10050
         * answer_num : 9
         * right_num : 1
         * continue_num : 0
         * danmu : 0
         */

        private int id;
        private int user_id;
        private int answer_num;
        private int right_num;
        private int continue_num;
        private int danmu;

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

        public int getAnswer_num() {
            return answer_num;
        }

        public void setAnswer_num(int answer_num) {
            this.answer_num = answer_num;
        }

        public int getRight_num() {
            return right_num;
        }

        public void setRight_num(int right_num) {
            this.right_num = right_num;
        }

        public int getContinue_num() {
            return continue_num;
        }

        public void setContinue_num(int continue_num) {
            this.continue_num = continue_num;
        }

        public int getDanmu() {
            return danmu;
        }

        public void setDanmu(int danmu) {
            this.danmu = danmu;
        }
    }

    public static class QuestionConfigBean {
        /**
         * min_money : 0.01
         * max_money : 0.2
         * video_num : 4
         * video_double : 2
         * reward_num : 20
         * reward_money : 0.3
         * continuity : [{"question_num":"5","addition_percent":"10"}]
         */

        private String min_money;
        private String max_money;
        private String video_num;
        private String video_double;
        private String reward_num;
        private String reward_money;
        private List<ContinuityBean> continuity;

        public String getMin_money() {
            return min_money;
        }

        public void setMin_money(String min_money) {
            this.min_money = min_money;
        }

        public String getMax_money() {
            return max_money;
        }

        public void setMax_money(String max_money) {
            this.max_money = max_money;
        }

        public String getVideo_num() {
            return video_num;
        }

        public void setVideo_num(String video_num) {
            this.video_num = video_num;
        }

        public String getVideo_double() {
            return video_double;
        }

        public void setVideo_double(String video_double) {
            this.video_double = video_double;
        }

        public String getReward_num() {
            return reward_num;
        }

        public void setReward_num(String reward_num) {
            this.reward_num = reward_num;
        }

        public String getReward_money() {
            return reward_money;
        }

        public void setReward_money(String reward_money) {
            this.reward_money = reward_money;
        }

        public List<ContinuityBean> getContinuity() {
            return continuity;
        }

        public void setContinuity(List<ContinuityBean> continuity) {
            this.continuity = continuity;
        }

        public static class ContinuityBean {
            /**
             * question_num : 5
             * addition_percent : 10
             */

            private String question_num;
            private String addition_percent;

            public String getQuestion_num() {
                return question_num;
            }

            public void setQuestion_num(String question_num) {
                this.question_num = question_num;
            }

            public String getAddition_percent() {
                return addition_percent;
            }

            public void setAddition_percent(String addition_percent) {
                this.addition_percent = addition_percent;
            }
        }
    }

    public static class QuestionListBean {
        /**
         * id : 1398
         * sort : 1398
         * question : 红色霓虹灯里填充的哪种气体？
         * right_key : 3
         * option : 氮#氧#氖
         * difficult_degree : 1
         * file : http://music-yy.imguf.com/subject/1398.mp3
         */

        private int id;
        private int sort;
        private String question;
        private int right_key;
        private String option;
        private int difficult_degree;
        private String file;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public int getRight_key() {
            return right_key;
        }

        public void setRight_key(int right_key) {
            this.right_key = right_key;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public int getDifficult_degree() {
            return difficult_degree;
        }

        public void setDifficult_degree(int difficult_degree) {
            this.difficult_degree = difficult_degree;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }
}