package com.yc.redevenlopes.homeModule.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.umeng.analytics.MobclickAgent;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.updata.ScreenUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Created by suns  on 2020/11/18 16:19.
 */
public class DisposeTintFragment extends DialogFragment {
    protected View rootView;

    protected BaseActivity mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (BaseActivity) context;
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();

        WindowManager.LayoutParams windowParams = window.getAttributes();
        //这里设置透明度
        windowParams.dimAmount = 0.5f;
        windowParams.width = (int) (ScreenUtil.getWidth(mContext) * 0.8);
        windowParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        window.setAttributes(windowParams);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//这一句很关键
        window.setWindowAnimations(R.style.dispose_anim);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Window window = getDialog().getWindow();

        if (rootView == null) {
            getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

            rootView = inflater.inflate(getLayoutId(), container, false);
//            window.setLayout((int) (RxDeviceTool.getScreenWidth(getActivity()) * getWidth()), getHeight());//这2行,和上面的一样,注意顺序就行;

        }
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);

        initViews();

        return rootView;
    }


    protected int getLayoutId() {
        return R.layout.fragment_dispose_tint;
    }


    public void initViews() {
        ImageView ivClose = rootView.findViewById(R.id.iv_close);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

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
