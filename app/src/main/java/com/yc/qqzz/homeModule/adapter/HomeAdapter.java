package com.yc.qqzz.homeModule.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.constants.Constant;
import com.yc.qqzz.homeModule.module.bean.HomeAllBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeBeanszq;
import com.yc.qqzz.homeModule.module.bean.HomeRedMessagezq;
import com.yc.qqzz.homeModule.module.bean.Info0Beanzq;
import com.yc.qqzz.homeModule.module.bean.Info1Beanzq;
import com.yc.qqzz.utils.CacheDataUtils;

import java.util.List;

public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeBeanszq, BaseViewHolder> {
    public HomeAdapter(List<HomeBeanszq> data) {
        super(data);
        addItemType(Constant.TYPE_ONE, R.layout.home_itemzq);
        addItemType(Constant.TYPE_TWO, R.layout.home_item_twozq);
        addItemType(Constant.TYPE_THREE, R.layout.home_item_threezq);
        addItemType(Constant.TYPE_FOUR, R.layout.home_item_fourzq);
        addItemType(Constant.TYPE_FIVE, R.layout.home_item_twozq);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBeanszq item) {
        switch (item.getItemType()) {
            case Constant.TYPE_ONE:
                Info0Beanzq info0Beanzq = item.getInfo0Beanzq();
                if (!TextUtils.isEmpty(info0Beanzq.getAdd_date())){
                    ((TextView) helper.getView(R.id.tv_times)).setVisibility(View.VISIBLE);
                    ((TextView) helper.getView(R.id.tv_times)).setText(info0Beanzq.getAdd_date());
                }else {
                    ((TextView) helper.getView(R.id.tv_times)).setVisibility(View.GONE);
                }
                int rand_level = info0Beanzq.getRand_level();
                if (rand_level==0){
                    rand_level= 13;
                }
                String names="把橘子当饭吃";
                if (!TextUtils.isEmpty(info0Beanzq.getNickname())){
                    names= info0Beanzq.getNickname();
                }
                String insertedNumStr="恭喜"+names+"等级升为"+rand_level+"级";
                SpannableString spannableString = new SpannableString(insertedNumStr);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#DA7420")), 2, names.length()+2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#DA7420")), insertedNumStr.length()-1-String.valueOf(rand_level).length(), insertedNumStr.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ((TextView) helper.getView(R.id.tv_contents)).setText(spannableString);
                break;
            case Constant.TYPE_TWO:
                HomeRedMessagezq homeRedMessage = item.getHomeRedMessage();
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
                HomeAllBeanszq homeAllBeans = item.getHomeAllBeans();
                ((TextView) helper.getView(R.id.tv_groupName)).setText("欢迎来到红包"+ CacheDataUtils.getInstance().getUserInfo().getGroup_id() +"群");
                ((TextView) helper.getView(R.id.groupNums)).setText(homeAllBeans.getGroup_num()+"人");
                ((TextView) helper.getView(R.id.tv_yesterdayMoney)).setText(homeAllBeans.getAll_money()+"元");
                ((TextView) helper.getView(R.id.tv_money)).setText(homeAllBeans.getMember_money()+"元");
                ((TextView) helper.getView(R.id.tv_loginNums)).setText(homeAllBeans.getUser_other().getLogin_day()+"天");
                ((TextView) helper.getView(R.id.tv_redNums)).setText(homeAllBeans.getHongbao_num()+"个");
                break;
            case Constant.TYPE_FOUR:
                HomeAllBeanszq homeAllBeanss = item.getHomeAllBeans();
                ((TextView) helper.getView(R.id.groupRank)).setText(homeAllBeanss.getUser_other().getLevel()+"级");
                helper.addOnClickListener(R.id.line_member);
                String insertedNumSt="完成每日等级任务即可升级20级会员可以享受会员收益，详情查看会员";
                SpannableString insertedNumStrs = new SpannableString(insertedNumSt);
                insertedNumStrs.setSpan(new ForegroundColorSpan(Color.parseColor("#DA7420")), insertedNumSt.length()-2, insertedNumSt.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ((TextView) helper.getView(R.id.tv_des)).setText(insertedNumStrs);
                helper.addOnClickListener(R.id.line_member);
                break;
            case Constant.TYPE_FIVE:// 滚动红包
                Info1Beanzq info1Beanzq = item.getInfo1Beanzq();
                if (!TextUtils.isEmpty(info1Beanzq.getAdd_date())){
                    ((TextView) helper.getView(R.id.tv_times)).setVisibility(View.VISIBLE);
                    ((TextView) helper.getView(R.id.tv_times)).setText(info1Beanzq.getAdd_date());
                }else {
                    ((TextView) helper.getView(R.id.tv_times)).setVisibility(View.GONE);
                }
                if (info1Beanzq.getStatus()==1){
                    ((ImageView) helper.getView(R.id.iv1)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_receive_triangle2));
                    ((ImageView) helper.getView(R.id.iv_red)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.bg_receive_red_envelope));
                    ((LinearLayout) helper.getView(R.id.line_open)).setBackground(mContext.getResources().getDrawable(R.drawable.line_bg_red3));
                }else {
                    ((ImageView) helper.getView(R.id.iv1)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_diialog_orange));
                    ((ImageView) helper.getView(R.id.iv_red)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_redenvelope));
                    ((LinearLayout) helper.getView(R.id.line_open)).setBackground(mContext.getResources().getDrawable(R.drawable.line_bg_red));
                }
                ((TextView) helper.getView(R.id.tv_redTypeName)).setText(info1Beanzq.getTypename()+ info1Beanzq.getMoney()+"元");
                ((TextView) helper.getView(R.id.tv_redTypeDes)).setText(info1Beanzq.getNum()+"个");
                ((TextView) helper.getView(R.id.tv_redType)).setText(info1Beanzq.getTypename());
                if ("3".equals(info1Beanzq.getType())){
                    ((TextView) helper.getView(R.id.tv_redType)).setText(info1Beanzq.getTypename());
                }
                helper.addOnClickListener(R.id.line_open);
                break;
       }
    }



}
