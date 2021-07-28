package com.yc.qqzz.homeModule.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.qqzz.R;
import com.yc.qqzz.homeModule.bean.AnswerFgBeans;

import java.util.List;

public class AnswerFgitemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public AnswerFgitemAdapter(@Nullable List<String> data) {
        super(R.layout.answerfg_item_item, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ((TextView) helper.getView(R.id.tv_contents)).setText(item);
        ((LinearLayout) helper.getView(R.id.line_answer_item)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ccc", "-------ccc---------onClick: ");
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
