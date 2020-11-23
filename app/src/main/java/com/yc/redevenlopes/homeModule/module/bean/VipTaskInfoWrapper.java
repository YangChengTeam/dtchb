package com.yc.redevenlopes.homeModule.module.bean;

import java.util.List;

/**
 * Created by suns  on 2020/11/16 18:14.
 */
public class VipTaskInfoWrapper {

    public double all_money;//": "365798.00",   昨日平台收益
    public double member_money;//   会员收益
    public long uplevel_time;//    升级日期,当此参数值不为0时，则需要显示倒计时。
    public List<VipTaskInfo> task_info;
    public UserAccountInfo user_other;//当前用户的账户信息


}
