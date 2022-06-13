package com.yc.rrdsprj.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;


public class BCRefreshHeader extends ClassicsHeader {
    public static String REFRESH_HEADER_PULLING = "下拉可以加载";//"下拉可以刷新";
    public static String REFRESH_HEADER_LOADING = "正在加载...";//"正在加载...";
    public static String REFRESH_HEADER_RELEASE = "释放加载";
    public static String REFRESH_HEADER_FINISH = "加载完成";//"刷新完成";
    public static String REFRESH_HEADER_FAILED = "加载失败";//"刷新失败";

  //  private TextView mTitleText;

    public BCRefreshHeader(Context context) {
        this(context, null);
    }

    public BCRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
      //  View view = LayoutInflater.from(context).inflate(R.layout.m_refresh_header, this);
      //  mTitleText = view.findViewById(R.id.tv_refresh);
    }


    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (success) {
            mTitleText.setText(REFRESH_HEADER_FINISH);
        } else {
            mTitleText.setText(REFRESH_HEADER_FAILED);
        }
        return 50; //延迟500毫秒之后再弹回
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        final View arrowView = mArrowView;
        final View updateView = mLastUpdateText;
        switch (newState) {
            case None:
                updateView.setVisibility(mEnableLastTime ? VISIBLE : GONE);
            case PullDownToRefresh:
                mTitleText.setText(REFRESH_HEADER_PULLING);
                arrowView.setVisibility(VISIBLE);
                arrowView.animate().rotation(0);
                break;
            case Refreshing:
            case RefreshReleased:
                mTitleText.setText(REFRESH_HEADER_LOADING);
                arrowView.setVisibility(GONE);
                break;
            case ReleaseToRefresh:
                mTitleText.setText(REFRESH_HEADER_RELEASE);
                arrowView.animate().rotation(180);
                break;
            case ReleaseToTwoLevel:
                mTitleText.setText(mTextSecondary);
                arrowView.animate().rotation(0);
                break;
            case Loading:
                arrowView.setVisibility(GONE);
                updateView.setVisibility(mEnableLastTime ? INVISIBLE : GONE);
                mTitleText.setText(mTextLoading);
                break;
        }
    }


}
