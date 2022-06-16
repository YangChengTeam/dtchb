package com.yc.jsdsp.beans.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.jsdsp.R;

import java.util.List;



public class AnswerFgitemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private int right_key;
    private int index;
    public AnswerFgitemAdapter(@Nullable List<String> data, int right_key, int index) {
        super(R.layout.answerfg_item_item, data);
        this.right_key=right_key;
        this.index=index;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ((TextView) helper.getView(R.id.tv_contents)).setText(item);
        if ((index+1)%5==0){
            ((ImageView) helper.getView(R.id.iv_shou)).setVisibility(View.INVISIBLE);
        }else {
            if (right_key==helper.getAdapterPosition()+1){
                ((ImageView) helper.getView(R.id.iv_shou)).setVisibility(View.VISIBLE);
            }else {
                ((ImageView) helper.getView(R.id.iv_shou)).setVisibility(View.INVISIBLE);
            }
        }
        ((RelativeLayout) helper.getView(R.id.line_answer_item)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (answerOnclickListens!=null){
                  answerOnclickListens.answer(helper.getAdapterPosition());
              }
            }
        });
    }

    public void setAnswerOnclickListens(AnswerOnclickListens answerOnclickListens){
          this.answerOnclickListens=answerOnclickListens;
    }
    private AnswerOnclickListens answerOnclickListens;
    public interface AnswerOnclickListens{
          void answer(int position);
    }
}
