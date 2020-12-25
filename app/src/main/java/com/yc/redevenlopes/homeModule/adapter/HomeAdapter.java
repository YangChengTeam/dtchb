package com.yc.redevenlopes.homeModule.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.constants.Constant;
import com.yc.redevenlopes.homeModule.module.bean.HomeAllBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeRedMessage;
import com.yc.redevenlopes.homeModule.module.bean.Info0Bean;
import com.yc.redevenlopes.homeModule.module.bean.Info1Bean;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.CommonUtils;
import com.yc.redevenlopes.utils.DisplayUtil;

import java.util.List;

public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeBeans, BaseViewHolder> {
    public HomeAdapter(List<HomeBeans> data) {
        super(data);
        addItemType(Constant.TYPE_ONE, R.layout.home_item);
        addItemType(Constant.TYPE_TWO, R.layout.home_item_two);
        addItemType(Constant.TYPE_THREE, R.layout.home_item_three);
        addItemType(Constant.TYPE_FOUR, R.layout.home_item_four);
        addItemType(Constant.TYPE_FIVE, R.layout.home_item_two);
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
                String random = String.valueOf(CommonUtils.getRandom(1,20));
                String insertedNumStr="恭喜"+info0Bean.getNickname()+"等级升为"+random+"级";
                SpannableString spannableString = new SpannableString(insertedNumStr);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#DA7420")), 2, info0Bean.getNickname().length()+2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#DA7420")), insertedNumStr.length()-1-random.length(), insertedNumStr.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
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
       }
    }
}
