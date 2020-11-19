package com.yc.redevenlopes.homeModule.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.redevenlopes.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by suns  on 2020/11/18 14:18.
 */
public class EmptyView extends FrameLayout {
    private ImageView ivNoData;
    private TextView tvNoData;

    public EmptyView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.empty_view, this, true);
        ivNoData = findViewById(R.id.iv_no_data);
        tvNoData = findViewById(R.id.tv_no_data);
        ivNoData.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick();
                }
            }
        });
    }

    public void setEmptyIcon(int resId) {
        if (ivNoData != null) {
            ivNoData.setImageResource(resId);
        }
    }

    public void setEmptyText(String text) {
        if (tvNoData != null) {
            tvNoData.setText(text);
        }
    }



    private OnTryClickListener clickListener;

    public void setClickListener(OnTryClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface OnTryClickListener {
        void onClick();
    }
}
