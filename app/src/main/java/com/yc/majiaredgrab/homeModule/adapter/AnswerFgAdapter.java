package com.yc.majiaredgrab.homeModule.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.homeModule.module.bean.AnswerQuestionListBeans;
import java.util.List;

public class AnswerFgAdapter extends BaseQuickAdapter<AnswerQuestionListBeans.OptionsBean, BaseViewHolder> {
    private boolean isSimple;
    public AnswerFgAdapter( @Nullable List<AnswerQuestionListBeans.OptionsBean> data) {
        super(R.layout.answerfg_items, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AnswerQuestionListBeans.OptionsBean item) {
        if (isSimple){
            if (item.isCorrect()){
                ((ImageView) helper.getView(R.id.iv_shou)).setVisibility(View.VISIBLE);
            }else {
                ((ImageView) helper.getView(R.id.iv_shou)).setVisibility(View.GONE);
            }
        }else {
            ((ImageView) helper.getView(R.id.iv_shou)).setVisibility(View.GONE);
        }
           if (item.getStatus()==0){
               ((RelativeLayout) helper.getView(R.id.rela_item)).setBackground(mContext.getResources().getDrawable(R.drawable.tv_bg_gray5));
               ((TextView) helper.getView(R.id.tv_title)).setTextColor(mContext.getResources().getColor(R.color.A1_333333));
               ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.GONE);
             } else if (item.getStatus()==1){
               ImageView view = (ImageView) helper.getView(R.id.iv_shou);
               if (view.getVisibility()==View.VISIBLE){
                   view.setVisibility(View.GONE);
               }
               ((RelativeLayout) helper.getView(R.id.rela_item)).setBackground(mContext.getResources().getDrawable(R.drawable.tv_bg_green2));
               ((TextView) helper.getView(R.id.tv_title)).setTextColor(mContext.getResources().getColor(R.color.A1_47B34E));
               ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.VISIBLE);
               ((ImageView) helper.getView(R.id.iv_select)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_correct));
             }else if (item.getStatus()==2){
               ((RelativeLayout) helper.getView(R.id.rela_item)).setBackground(mContext.getResources().getDrawable(R.drawable.line_bg_red2));
               ((TextView) helper.getView(R.id.tv_title)).setTextColor(mContext.getResources().getColor(R.color.A1_D90000));
               ((ImageView) helper.getView(R.id.iv_select)).setVisibility(View.VISIBLE);
               ((ImageView) helper.getView(R.id.iv_select)).setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_error));
           }

           helper.addOnClickListener(R.id.rela_item);
         ((TextView) helper.getView(R.id.tv_title)).setText(item.getName());
    }

    public void setIsSimple(boolean isSimple) {
         this.isSimple=isSimple;
    }
}
