package com.yc.majiaredgrab.homeModule.module.bean;

/**
 * Created by suns  on 2020/11/16 18:14.
 */
public class VipTaskInfo {

    public int id;//
    public int task_id;//            等级奖励ID
    public int level;//": 20,        可领取的等级
    public double money;//": "50.00",   领取金额
    public int status;//": 0         0:表示不能领取 1:表示能领取 2:表示已领取

    public int num;// 任务所需完成次数
    public String title;//":"手气红包", 任务名称
    public String content;//":"", 任务描述
    public int finish_num;//":0 已完成次数


}
