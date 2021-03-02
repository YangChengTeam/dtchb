package com.yc.redguess.homeModule.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redguess.R;
import com.yc.redguess.constants.Constant;
import com.yc.redguess.homeModule.module.bean.HomeAllBeans;
import com.yc.redguess.homeModule.module.bean.HomeBeans;
import com.yc.redguess.homeModule.module.bean.HomeRedMessage;
import com.yc.redguess.homeModule.module.bean.Info0Bean;
import com.yc.redguess.homeModule.module.bean.Info1Bean;
import com.yc.redguess.homeModule.module.bean.VipTaskInfoHomes;
import com.yc.redguess.utils.CacheDataUtils;
import com.yc.redguess.utils.CommonUtils;

import java.util.List;

public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeBeans, BaseViewHolder> {
    public HomeAdapter(List<HomeBeans> data) {
        super(data);
        addItemType(Constant.TYPE_ONE, R.layout.home_item);
        addItemType(Constant.TYPE_TWO, R.layout.home_item_two);
        addItemType(Constant.TYPE_THREE, R.layout.home_item_three);
        addItemType(Constant.TYPE_FOUR, R.layout.home_item_four);
        addItemType(Constant.TYPE_FIVE, R.layout.home_item_two);
        addItemType(Constant.TYPE_SIX, R.layout.home_item_six);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBeans item) {
        switch (item.getItemType()) {
            case Constant.TYPE_ONE:
                Info0Bean info0Bean = item.getInfo0Bean();
                if (!TextUtils.isEmpty(info0Bean.getAdd_date())){
                    ((TextView) helper.getView(R.id.tv_times)).setVisibility(View.VISIBLE);
                    ((TextView) helper.getView(R.id.tv_times)).setText(info0Bean.getAdd_date());
                }else {
                    ((TextView) helper.getView(R.id.tv_times)).setVisibility(View.GONE);
                }
                int rand_level = info0Bean.getRand_level();
                if (rand_level==0){
                    rand_level= CommonUtils.getRandom(4,20);
                }
                String names="把橘子当饭吃";
                if (!TextUtils.isEmpty(info0Bean.getNickname())){
                    names=info0Bean.getNickname();
                }
                String insertedNumStr="恭喜"+names+"等级升为"+rand_level+"级";
                SpannableString spannableString = new SpannableString(insertedNumStr);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#DA7420")), 2, names.length()+2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#DA7420")), insertedNumStr.length()-1-String.valueOf(rand_level).length(), insertedNumStr.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ((TextView) helper.getView(R.id.tv_contents)).setText(spannableString);
                break;
            case Constant.TYPE_TWO:
                HomeRedMessage homeRedMessage = item.getHomeRedMessage();
                if (!TextUtils.isEmpty(homeRedMessage.getAdd_time())){
                    ((TextView) helper.getView(R.id.tv_times)).setVisibility(View.VISIBLE);
                    ((TextView) helper.getView(R.id.tv_times)).setText(homeRedMessage.getAdd_time());
                }else {
                    ((TextView) helper.getView(R.id.tv_times)).setVisibility(View.GONE);
                }
                if (homeRedMessage.getStatus()==1){
                    ((ImageView) helper.getView(R.id.iv1)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_receive_triangle2));
                    ((ImageView) helper.getView(R.id.iv_red)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.bg_receive_red_envelope));
                    ((LinearLayout) helper.getView(R.id.line_open)).setBackground(mContext.getResources().getDrawable(R.drawable.line_bg_red3));
                }else {
                    ((ImageView) helper.getView(R.id.iv1)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_diialog_orange));
                    ((ImageView) helper.getView(R.id.iv_red)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_redenvelope));
                    ((LinearLayout) helper.getView(R.id.line_open)).setBackground(mContext.getResources().getDrawable(R.drawable.line_bg_red));
                }
                ((TextView) helper.getView(R.id.tv_redTypeName)).setText(homeRedMessage.getTypename()+homeRedMessage.getBalance_money()+"元");
                ((TextView) helper.getView(R.id.tv_redTypeDes)).setText(homeRedMessage.getNum()+"个");
                ((TextView) helper.getView(R.id.tv_redType)).setText(homeRedMessage.getTypename());
                if ("3".equals(homeRedMessage.getType())){
                    ((TextView) helper.getView(R.id.tv_redType)).setText(homeRedMessage.getTypename());
                }
                helper.addOnClickListener(R.id.line_open);
                break;
            case Constant.TYPE_THREE:
                HomeAllBeans homeAllBeans = item.getHomeAllBeans();
                ((TextView) helper.getView(R.id.tv_groupName)).setText("欢迎来到红包"+ CacheDataUtils.getInstance().getUserInfo().getGroup_id() +"群");
                ((TextView) helper.getView(R.id.groupNums)).setText(homeAllBeans.getGroup_num()+"人");
                ((TextView) helper.getView(R.id.tv_yesterdayMoney)).setText(homeAllBeans.getAll_money()+"元");
                ((TextView) helper.getView(R.id.tv_money)).setText(homeAllBeans.getMember_money()+"元");
                ((TextView) helper.getView(R.id.tv_loginNums)).setText(homeAllBeans.getUser_other().getLogin_day()+"天");
                ((TextView) helper.getView(R.id.tv_redNums)).setText(homeAllBeans.getHongbao_num()+"个");
                break;
            case Constant.TYPE_FOUR:
                HomeAllBeans homeAllBeanss = item.getHomeAllBeans();
                ((TextView) helper.getView(R.id.groupRank)).setText(homeAllBeanss.getUser_other().getLevel()+"级");
                helper.addOnClickListener(R.id.line_member);
                String insertedNumSt="完成每日等级任务即可升级20级会员可以享受会员收益，详情查看会员";
                SpannableString insertedNumStrs = new SpannableString(insertedNumSt);
                insertedNumStrs.setSpan(new ForegroundColorSpan(Color.parseColor("#DA7420")), insertedNumSt.length()-2, insertedNumSt.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ((TextView) helper.getView(R.id.tv_des)).setText(insertedNumStrs);
                helper.addOnClickListener(R.id.line_member);
                break;
            case Constant.TYPE_FIVE:// 滚动红包
                Info1Bean info1Bean = item.getInfo1Bean();
                if (!TextUtils.isEmpty(info1Bean.getAdd_date())){
                    ((TextView) helper.getView(R.id.tv_times)).setVisibility(View.VISIBLE);
                    ((TextView) helper.getView(R.id.tv_times)).setText(info1Bean.getAdd_date());
                }else {
                    ((TextView) helper.getView(R.id.tv_times)).setVisibility(View.GONE);
                }
                if (info1Bean.getStatus()==1){
                    ((ImageView) helper.getView(R.id.iv1)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_receive_triangle2));
                    ((ImageView) helper.getView(R.id.iv_red)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.bg_receive_red_envelope));
                    ((LinearLayout) helper.getView(R.id.line_open)).setBackground(mContext.getResources().getDrawable(R.drawable.line_bg_red3));
                }else {
                    ((ImageView) helper.getView(R.id.iv1)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_diialog_orange));
                    ((ImageView) helper.getView(R.id.iv_red)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_redenvelope));
                    ((LinearLayout) helper.getView(R.id.line_open)).setBackground(mContext.getResources().getDrawable(R.drawable.line_bg_red));
                }
                ((TextView) helper.getView(R.id.tv_redTypeName)).setText(info1Bean.getTypename()+info1Bean.getMoney()+"元");
                ((TextView) helper.getView(R.id.tv_redTypeDes)).setText(info1Bean.getNum()+"个");
                ((TextView) helper.getView(R.id.tv_redType)).setText(info1Bean.getTypename());
                if ("3".equals(info1Bean.getType())){
                    ((TextView) helper.getView(R.id.tv_redType)).setText(info1Bean.getTypename());
                }
                helper.addOnClickListener(R.id.line_open);
                break;
            case Constant.TYPE_SIX:// 滚动红包
                VipTaskInfoHomes vipTaskInfoHomes = item.getVipTaskInfoHomes();
                String task_id = vipTaskInfoHomes.getTask_id();
                if ("7".equals(task_id)){//签到
                    ((TextView) helper.getView(R.id.tv_des)).setText("在线等，领取50元签到奖励！");
                    ((ImageView) helper.getView(R.id.iv_top)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.bg_01home));
                }else if ("3".equals(task_id)){//转盘
                    ((TextView) helper.getView(R.id.tv_des)).setText("转盘抽奖，苹果手机等你拿！");
                    ((ImageView) helper.getView(R.id.iv_top)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.bg_2home));
                }else if ("2".equals(task_id)){//答题
                    ((TextView) helper.getView(R.id.tv_des)).setText("离提现，差一个答题的距离！");
                    ((ImageView) helper.getView(R.id.iv_top)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.bg_3home));
                }else if ("4".equals(task_id)){//夺宝
                    ((TextView) helper.getView(R.id.tv_des)).setText("在线夺宝，领取10元奖励！");
                    ((ImageView) helper.getView(R.id.iv_top)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.bg_4home));
                }else if ("5".equals(task_id)){//竞猜
                    ((TextView) helper.getView(R.id.tv_des)).setText("完成数字竞猜，就能提现啦！");
                    ((ImageView) helper.getView(R.id.iv_top)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.bg_5home));
                }
                if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getUserInfo().getFace())){
                    Glide.with(mContext)
                            .load(CacheDataUtils.getInstance().getUserInfo().getFace())
                            .apply(new RequestOptions().bitmapTransform(new CircleCrop()))
                            .into(((ImageView) helper.getView(R.id.iv_avatar)));
                }
                helper.addOnClickListener(R.id.line_itemSix);
                break;
       }
    }



}
