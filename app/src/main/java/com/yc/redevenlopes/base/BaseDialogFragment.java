package com.yc.redevenlopes.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.updata.ScreenUtil;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Created by suns  on 2020/11/19 09:58.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    protected final String TAG = this.getClass().getSimpleName();
    protected View rootView;

    protected Context mContext;


    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();

        WindowManager.LayoutParams windowParams = window.getAttributes();
        //这里设置透明度
        windowParams.dimAmount = 0.5f;
        windowParams.width = (int) (ScreenUtil.getWidth(mContext) * getWidthRatio());
        windowParams.height = getHeight();

        window.setAttributes(windowParams);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//这一句很关键
        window.setWindowAnimations(getAnimation());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

            rootView = inflater.inflate(getLayoutId(), container, false);
//            window.setLayout((int) (RxDeviceTool.getScreenWidth(getActivity()) * getWidth()), getHeight());//这2行,和上面的一样,注意顺序就行;

        }
        getDialog().setCancelable(getCancelable());
        getDialog().setCanceledOnTouchOutside(getCancelable());

        initViews();

        return rootView;
    }


    protected abstract int getLayoutId();


    public abstract void initViews();

    public float getWidthRatio() {
        return 0.8f;
    }


    public int getHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }

    public int getAnimation() {
        return R.style.dispose_anim;
    }


    public boolean getCancelable() {
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //解除缓存 View 和当前 ViewGroup 的关联
        ((ViewGroup) (rootView.getParent())).removeView(rootView);
        Runtime.getRuntime().gc();
    }
}
