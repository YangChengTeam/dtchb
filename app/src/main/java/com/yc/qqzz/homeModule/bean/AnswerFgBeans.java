package com.yc.qqzz.homeModule.bean;

import java.util.List;

public class AnswerFgBeans {

    /**
     * quesion_user : {"user_id":10019,"id":"2","answer_num":0,"right_num":0,"continue_num":0,"danmu":0}
     * question_config : [{"min_money":"0.01","max_money":"0.2","video_num":4,"video_double":2,"continuity":[{"question_num":2,"addition_percent":10}]}]
     * question_list : [{"id":628,"sort":1999,"question":"","right_key":2,"option":"苹果#海带","difficult_degree":1,"file":"http://music-yy.tn550.com/subject/628.mp3"}]
     */

    private QuesionUserBean quesion_user;
    private List<QuestionConfigBean> question_config;
    private List<QuestionListBean> question_list;

    public QuesionUserBean getQuesion_user() {
        return quesion_user;
    }

    public void setQuesion_user(QuesionUserBean quesion_user) {
        this.quesion_user = quesion_user;
    }

    public List<QuestionConfigBean> getQuestion_config() {
        return question_config;
    }

    public void setQuestion_config(List<QuestionConfigBean> question_config) {
        this.question_config = question_config;
    }

    public List<QuestionListBean> getQuestion_list() {
        return question_list;
    }

    public void setQuestion_list(List<QuestionListBean> question_list) {
        this.question_list = question_list;
    }

    public static class QuesionUserBean {
        /**
         * user_id : 10019
         * id : 2
         * answer_num : 0
         * right_num : 0
         * continue_num : 0
         * danmu : 0
         */

        private int user_id;
        private String id;
        private int answer_num;
        private int right_num;
        private int continue_num;
        private int danmu;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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
         * continuity : [{"question_num":2,"addition_percent":10}]
         */

        private String min_money;
        private String max_money;
        private int video_num;
        private int video_double;
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

        public int getVideo_num() {
            return video_num;
        }

        public void setVideo_num(int video_num) {
            this.video_num = video_num;
        }

        public int getVideo_double() {
            return video_double;
        }

        public void setVideo_double(int video_double) {
            this.video_double = video_double;
        }

        public List<ContinuityBean> getContinuity() {
            return continuity;
        }

        public void setContinuity(List<ContinuityBean> continuity) {
            this.continuity = continuity;
        }

        public static class ContinuityBean {
            /**
             * question_num : 2
             * addition_percent : 10
             */

            private int question_num;
            private int addition_percent;

            public int getQuestion_num() {
                return question_num;
            }

            public void setQuestion_num(int question_num) {
                this.question_num = question_num;
            }

            public int getAddition_percent() {
                return addition_percent;
            }

            public void setAddition_percent(int addition_percent) {
                this.addition_percent = addition_percent;
            }
        }
    }

    public static class QuestionListBean {
        /**
         * id : 628
         * sort : 1999
         * question :
         * right_key : 2
         * option : 苹果#海带
         * difficult_degree : 1
         * file : http://music-yy.tn550.com/subject/628.mp3
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
