package com.yc.redkingguess.homeModule.module.bean;

import java.util.List;

/**
 * Created by suns  on 2020/11/16 18:14.
 */
public class VipTaskInfoWrapper {

    public double all_money;//": "365798.00",   昨日平台收益
    public double member_money;//   会员收益
    public long uplevel_time;//    升级日期,当此参数值不为0时，则需要显示倒计时。
    public List<VipTaskInfo> task_info;
    public List<TaskUnlock> other_info;
    public UserAccountInfo user_other;//当前用户的账户信息
    public BonusesInfoBeans bonuses_info;//当前用户的账户信息

    public BonusesInfoBeans getBonuses_info() {
        return bonuses_info;
    }

    public void setBonuses_info(BonusesInfoBeans bonuses_info) {
        this.bonuses_info = bonuses_info;
    }

    private int unlock;
    public List<TaskUnlock> getOther_info() {
        return other_info;
    }

    public void setOther_info(List<TaskUnlock> other_info) {
        this.other_info = other_info;
    }

    public int getUnlock() {
        return unlock;
    }

    public void setUnlock(int unlock) {
        this.unlock = unlock;
    }



    public double red_money;//红包金额

    public double getAll_money() {
        return all_money;
    }

    public void setAll_money(double all_money) {
        this.all_money = all_money;
    }

    public double getMember_money() {
        return member_money;
    }

    public void setMember_money(double member_money) {
        this.member_money = member_money;
    }

    public long getUplevel_time() {
        return uplevel_time;
    }

    public void setUplevel_time(long uplevel_time) {
        this.uplevel_time = uplevel_time;
    }

    public List<VipTaskInfo> getTask_info() {
        return task_info;
    }

    public void setTask_info(List<VipTaskInfo> task_info) {
        this.task_info = task_info;
    }

    public UserAccountInfo getUser_other() {
        return user_other;
    }

    public void setUser_other(UserAccountInfo user_other) {
        this.user_other = user_other;
    }

    public double getRed_money() {
        return red_money;
    }

    public void setRed_money(double red_money) {
        this.red_money = red_money;
    }
}
