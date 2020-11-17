package com.yc.redevenlopes.homeModule.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.redevenlopes.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by suns  on 2020/11/17 17:19.
 */
public class MemberCenterView extends FrameLayout {
    private TextView tvContent;

    public MemberCenterView(@NonNull Context context) {
        this(context, null);
    }

    public MemberCenterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MemberCenterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.member_center_view, this, true);

        ImageView ivIcon = findViewById(R.id.iv_icon);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        ImageView ivArrowRight = findViewById(R.id.iv_arrow_right);
        View viewDivider = findViewById(R.id.view_divider);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MemberCenterView);
        try {

            int resIcon = ta.getResourceId(R.styleable.MemberCenterView_icon, R.mipmap.personal_icon_wallet);
            String title = ta.getString(R.styleable.MemberCenterView_title);
            boolean isShowArrow = ta.getBoolean(R.styleable.MemberCenterView_show_arrow, true);
            boolean isShowDivider = ta.getBoolean(R.styleable.MemberCenterView_show_divider, true);

            ivIcon.setImageResource(resIcon);
            if (!TextUtils.isEmpty(title)) tvTitle.setText(title);
            if (isShowArrow) {
                ivArrowRight.setVisibility(VISIBLE);
            } else {
                ivArrowRight.setVisibility(GONE);
            }

            viewDivider.setVisibility(isShowDivider ? VISIBLE : GONE);
        } finally {
            ta.recycle();
        }

    }


    public void setContent(String content) {
        if (tvContent != null) {
            tvContent.setText(content);
        }
    }
}
