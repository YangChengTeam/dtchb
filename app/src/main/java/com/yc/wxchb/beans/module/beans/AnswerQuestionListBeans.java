package com.yc.wxchb.beans.module.beans;

import java.util.List;

public class AnswerQuestionListBeans {

    /**
     * id : 1
     * title :
     * options : [{"key":"A","name":"上游"},{"key":"B","name":"中游"},{"key":"C","name":"下游"}]
     * answer : B
     * ids : 0,1
     */

    private int id;
    private String title;
    private String answer;
    private String ids;

    private List<OptionsBean> options;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<OptionsBean> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsBean> options) {
        this.options = options;
    }

    public static class OptionsBean {
        /**
         * key : A
         * name : 上游
         */
        private int status;// 0 无状态  1对  2错
        private String key;
        private String name;
        private boolean isCorrect;

        public boolean isCorrect() {
            return isCorrect;
        }

        public void setCorrect(boolean correct) {
            isCorrect = correct;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
