package com.yc.jsdps.beans.adapter;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.jsdps.R;
import com.yc.jsdps.beans.module.beans.AnswerFgBeans;

import java.util.ArrayList;
import java.util.List;



public class AnswerFgAdapter extends BaseQuickAdapter<AnswerFgBeans.QuestionListBean, BaseViewHolder> {
    private Context context;
    public AnswerFgAdapter(@Nullable List<AnswerFgBeans.QuestionListBean> data, Context context) {
        super(R.layout.answerfg_item, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AnswerFgBeans.QuestionListBean item) {
        String option = item.getOption();
        List<String> lists=new ArrayList<>();
        if (!TextUtils.isEmpty(option)){
            String[] split = option.split("#");
            if (split!=null){
                for (int i = 0; i < split.length; i++) {
                    lists.add(split[i]);
                }
            }
        }
        int right_key = item.getRight_key();
        RecyclerView recyclerView = helper.getView(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        AnswerFgitemAdapter answerFgitemAdapter=new AnswerFgitemAdapter(lists,right_key,helper.getAdapterPosition());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(answerFgitemAdapter);
        answerFgitemAdapter.setAnswerOnclickListens(new AnswerFgitemAdapter.AnswerOnclickListens() {
            @Override
            public void answer(int position) {
                 if (onClickListens!=null){
                    if (position+1==right_key){//答对了
                        onClickListens.clicks(position,helper.getPosition(),true);
                    }else {
                        onClickListens.clicks(position,helper.getPosition(),false);
                    }
                }
            }
        });
    }
    private  OnClickListens onClickListens;
    public interface OnClickListens{
        void clicks(int position, int answerPosition, boolean isSure);
    }
    public void setOnClickListens(OnClickListens onClickListens){
        this.onClickListens=onClickListens;
    }
}
