package com.yc.rrdsprj.beans.module.beans;

public class AnswerFgQuestionBeans {
    /**
     * right_num : 1
     * continue_num : 1
     * answer_num : 1
     * red_money : 0.2
     * other_money : 0
     * other_percent : 10
     * cash : 2.52
     * info_id : 1
     */
    private int answer_num;
    private int right_num;
    private int info_id;

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

    public int getInfo_id() {
        return info_id;
    }

    public void setInfo_id(int info_id) {
        this.info_id = info_id;
    }
}
