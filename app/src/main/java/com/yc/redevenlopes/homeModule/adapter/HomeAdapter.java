package com.yc.redevenlopes.homeModule.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.constants.Constant;
import com.yc.redevenlopes.homeModule.module.bean.HomeAllBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeRedMessage;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.DisplayUtil;

import java.util.List;

public class HomeAdapter extends BaseMultiItemQuickAdapter<HomeBeans, BaseViewHolder> {
    public HomeAdapter(List<HomeBeans> data) {
        super(data);
        addItemType(Constant.TYPE_ONE, R.layout.home_item);
        addItemType(Constant.TYPE_TWO, R.layout.home_item_two);
        addItemType(Constant.TYPE_THREE, R.layout.home_item_three);
        addItemType(Constant.TYPE_FOUR, R.layout.home_item_four);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBeans item) {
        int size = getData().size();
        switch (item.getItemType()) {
            case Constant.TYPE_ONE:

                break;
            case Constant.TYPE_TWO:
//                HomeRedMessage homeRedMessage = item.getHomeRedMessage();
//                ((TextView) helper.getView(R.id.tv_redTypeName)).setText(homeRedMessage.getTypename()+homeRedMessage.getBalance_money()+"元");
//                ((TextView) helper.getView(R.id.tv_redTypeDes)).setText(homeRedMessage.getNum()+"个");
//                ((TextView) helper.getView(R.id.tv_redType)).setText(homeRedMessage.getTypename());
//                if ("3".equals(homeRedMessage.getType())){
//                    ((TextView) helper.getView(R.id.tv_redType)).setText(homeRedMessage.getTypename());
//                }
//                helper.addOnClickListener(R.id.line_open);
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
                String insertedNumStr="完成每日等级任务即可升级20级会员可以享受会员收益，详情查看会员";
                SpannableString spannableString = new SpannableString(insertedNumStr);
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#DA7420")), insertedNumStr.length()-2, insertedNumStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ((TextView) helper.getView(R.id.tv_des)).setText(spannableString);
                break;
       }
    }
}
