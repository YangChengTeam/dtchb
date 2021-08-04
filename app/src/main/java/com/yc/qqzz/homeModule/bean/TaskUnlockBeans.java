package com.yc.qqzz.homeModule.bean;

public class TaskUnlockBeans {
    /**
     * task_id : 1
     * num : 8
     * title : 手气红包
     * content :
     * finish_num : 0
     * status : 2
     */

    private String task_id;
    private String num;
    private String title;
    private String content;
    private int finish_num;
    private int status;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFinish_num() {
        return finish_num;
    }

    public void setFinish_num(int finish_num) {
        this.finish_num = finish_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
