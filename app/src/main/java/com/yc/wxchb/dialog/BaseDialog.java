package com.yc.wxchb.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yc.wxchb.R;
import com.yc.wxchb.updata.ScreenUtil;

import butterknife.ButterKnife;

public abstract class BaseDialog extends Dialog {

    public BaseDialog(Context context) {
        super(context, R.style.center_dialog);

        View view = LayoutInflater.from(context).inflate(
                getLayoutId(), null);
        ButterKnife.bind(this, view);
        setContentView(view);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ScreenUtil.getWidth(context);
        layoutParams.height = ScreenUtil.getHeight(context);
        view.setLayoutParams(layoutParams);

        setCancelable(true);
        initViews();
    }

    protected abstract int getLayoutId();

    protected abstract void initViews();

    @Override
    public void show() {
        if (!this.isShowing() && !ScreenUtil.isActivityDestory(getContext())) {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (this.isShowing() && !ScreenUtil.isActivityDestory(getContext())) {
            super.dismiss();
        }
    }


}
