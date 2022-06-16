package com.yc.jsdsp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yc.jsdsp.R;
import com.yc.jsdsp.utils.CacheDataUtils;


/**
 * Created by suns  on 2020/11/17 17:19.
 */
public class MemberCenterViewSolzq extends FrameLayout {
    private TextView tvContent;

    private ToggleButton toggleButton;

    public MemberCenterViewSolzq(@NonNull Context context) {
        this(context, null);
    }

    public MemberCenterViewSolzq(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MemberCenterViewSolzq(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.member_center_view, this, true);

        ImageView ivIcon = findViewById(R.id.iv_icon);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        ImageView ivArrowRight = findViewById(R.id.iv_arrow_right);
        View viewDivider = findViewById(R.id.view_divider);
        LinearLayout linearLayout = findViewById(R.id.ll_content_container);
        toggleButton = findViewById(R.id.toggleButton);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MemberCenterViewzq);
        try {

            int resIcon = ta.getResourceId(R.styleable.MemberCenterViewzq_icon, R.mipmap.personal_icon_wallet);
            String title = ta.getString(R.styleable.MemberCenterViewzq_title);
            boolean isShowArrow = ta.getBoolean(R.styleable.MemberCenterViewzq_show_arrow, true);
            boolean isShowDivider = ta.getBoolean(R.styleable.MemberCenterViewzq_show_divider, true);
            boolean isShowButton = ta.getBoolean(R.styleable.MemberCenterViewzq_show_button, false);

            ivIcon.setImageResource(resIcon);
            if (!TextUtils.isEmpty(title)) tvTitle.setText(title);
            if (isShowArrow) {
                ivArrowRight.setVisibility(VISIBLE);
            } else {
                ivArrowRight.setVisibility(GONE);
            }
            toggleButton.setVisibility(VISIBLE);
            linearLayout.setVisibility(GONE);
            if (TextUtils.isEmpty(CacheDataUtils.getInstance().getSol())){
                toggleButton.setChecked(true);
            }else {
                toggleButton.setChecked(false);
            }

            viewDivider.setVisibility(isShowDivider ? VISIBLE : GONE);
            toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        CacheDataUtils.getInstance().setSol("");
                    }else {
                        CacheDataUtils.getInstance().setSol("1");
                    }
                }
            });
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
