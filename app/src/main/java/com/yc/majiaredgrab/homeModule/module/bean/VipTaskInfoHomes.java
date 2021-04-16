package com.yc.majiaredgrab.homeModule.module.bean;

/**
 * Created by suns  on 2020/11/16 18:14.
 */
public class VipTaskInfoHomes {


    /**
     * task_id : 1
     * num : 5
     * sort : 1
     * title : 手气红包
     * content : null
     * status : 1
     * finish_num : 5
     */

    private String task_id;
    private String num;
    private int sort;
    private String title;
    private Object content;
    private int status;
    private int finish_num;

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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFinish_num() {
        return finish_num;
    }

    public void setFinish_num(int finish_num) {
        this.finish_num = finish_num;
    }
}
