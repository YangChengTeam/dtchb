package com.yc.majiaredgrab.homeModule.module.bean;

public class TaskUnlock {

    /**
     * title : 完成任务1
     * num : 2
     * other_id : 1
     * finish_num : 0
     */

    private String title;
    private int num;
    private int other_id;
    private int finish_num;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getFinish_num() {
        return finish_num;
    }

    public void setFinish_num(int finish_num) {
        this.finish_num = finish_num;
    }
}
