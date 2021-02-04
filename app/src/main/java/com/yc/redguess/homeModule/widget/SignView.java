package com.yc.redguess.homeModule.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yc.redguess.R;

public class SignView extends LinearLayout {
    private String money;
    private ProgressBar progressBar;
    private TextView tv_money;

    public SignView(Context context) {
        this(context,null);
    }

    public SignView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View inflate = inflate(context, R.layout.signview, this);
        progressBar=inflate.findViewById(R.id.progressbar);
        tv_money=inflate.findViewById(R.id.tv_money);
    }

    public void  setProgressBar(int progress,String money){
        if (progressBar!=null){
            progressBar.setProgress(progress);
        }
        if (tv_money!=null&&!TextUtils.isEmpty(money)){
            this.money=money;
            tv_money.setText("领取"+money);
        }
        invalidate();
    }
}
