package com.yc.jsdsp.beans.module.beans;

public class AnswerBeans {

    /**
     * id : 9
     * question_id : 1
     * money : 0.10
     * add_date : 20201125
     * total : 1
     * question_num : 5
     * is_continue : 1
     */

    private int id;
    private int question_id;
    private String money;
    private int add_date;
    private int total;
    private int question_num;
    private int is_continue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getAdd_date() {
        return add_date;
    }

    public void setAdd_date(int add_date) {
        this.add_date = add_date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getQuestion_num() {
        return question_num;
    }

    public void setQuestion_num(int question_num) {
        this.question_num = question_num;
    }

    public int getIs_continue() {
        return is_continue;
    }

    public void setIs_continue(int is_continue) {
        this.is_continue = is_continue;
    }
}
