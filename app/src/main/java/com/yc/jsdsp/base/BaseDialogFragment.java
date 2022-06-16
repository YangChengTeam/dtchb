package com.yc.jsdsp.base;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.yc.jsdsp.R;
import com.yc.jsdsp.updata.ScreenUtil;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * Created by suns  on 2020/11/19 09:58.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    protected final String TAG = this.getClass().getSimpleName();
    protected View rootView;

    protected Context mContext;


    @Override
    public void onAttach( Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();
    }




    @Override
    public void onPause() {
        super.onPause();
    }


    /**
     * 为了解决:mainActivity调用onSaveInstanceState以后又调用了show方法,
     * 出现的Can not perform this action after onSaveInstanceState
     * 这个异常(不应该用commit ,而是用commitAllowingStateLoss)
     * 得罪了,不会反射 ,先把你catch住吧.乖
     * @param manager
     * @param tag
     */
    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException ignore) {
            //  容错处理,不做操作
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        try {
            super.onDismiss(dialog);
        } catch (IllegalStateException ignore) {
            //  容错处理,不做操作
        }
    }

    /**
     * 注意,不要用super.dismiss(),bug 同上show()
     * super.onDismiss就没问题
     */
    public void dismissDialog() {
        if ( getActivity() != null && !getActivity().isFinishing()) {
            super.dismissAllowingStateLoss();
        }
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
    public void onDestroy() {
        super.onDestroy();

        //解除缓存 View 和当前 ViewGroup 的关联
        ((ViewGroup) (rootView.getParent())).removeView(rootView);
        Runtime.getRuntime().gc();
    }
}
