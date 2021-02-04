package com.yc.redguess.homeModule.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.yc.redguess.R;
import com.yc.redguess.utils.DisplayUtil;
import java.util.List;

public class AnswerIndexView extends LinearLayout {
    private Context context;
    public AnswerIndexView(Context context) {
        this(context,null);
    }

    public AnswerIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AnswerIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
        this.context=context;
    }

    public void  setDatas(int total){
        for (int i = 0; i <total; i++) {
            View  view=new View(context);
            view.setBackground(context.getResources().getDrawable(R.drawable.tv_bg_gray6));
            addView(view);
            LinearLayout.LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.width= DisplayUtil.dip2px(context,16);
            layoutParams.height= DisplayUtil.dip2px(context,16);
            layoutParams.rightMargin=DisplayUtil.dip2px(context,15);
            view.setLayoutParams(layoutParams);
        }
    }

    public void setIndex(List<String> viewStatusList){
            for (int i = 0; i < viewStatusList.size(); i++) {
                View childAt = getChildAt(i);
                if ("2".equals(viewStatusList.get(i))){
                    childAt.setBackground(context.getResources().getDrawable(R.drawable.line_bg_yellow7));
                }else if ("3".equals(viewStatusList.get(i))){
                    childAt.setBackground(context.getResources().getDrawable(R.drawable.tv_bg_red));
                }else {
                    childAt.setBackground(context.getResources().getDrawable(R.drawable.tv_bg_gray6));
                }
            }
            invalidate();

    }


}
