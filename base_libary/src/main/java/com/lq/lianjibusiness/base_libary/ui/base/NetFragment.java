package com.lq.lianjibusiness.base_libary.ui.base;

import android.graphics.Rect;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.lq.lianjibusiness.base_libary.R;
import com.lq.lianjibusiness.base_libary.utils.DensityUtils;
import com.lq.lianjibusiness.base_libary.utils.NetWorkUtils;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;

;

/**
 * Created by ccc on 2020/9/15.
 */

public abstract class NetFragment extends Fragment implements View.OnClickListener {

    private FrameLayout netContent;
    private View content;
    private RelativeLayout rlNetMistake;
    private TextView tvNetMistakeTip, tvErrorTip;
    private ImageView ivError;

    private boolean mistake;
    private boolean mistakeLoad ;

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frgment_net_base, null);
        content = initView(inflater, container, savedInstanceState);
        findViews(view);
        if (content != null) {
            netContent.addView(content);
        }
        mistake = isMistakeShow();
        initClick();
        setNetWorkState();
        return view;
    }

    private void findViews(View view) {
        netContent = view.findViewById(R.id.fl_net_content);
        rlNetMistake = view.findViewById(R.id.rl_net_mistake);
        tvNetMistakeTip = view.findViewById(R.id.tv_net_mistake_tip);
        tvErrorTip = view.findViewById(R.id.tv_net_mistake);
        ivError = view.findViewById(R.id.iv_base_error);
    }

    private void initClick() {
        tvNetMistakeTip.setOnClickListener(this);
    }

    private void setNetWorkState() {
        boolean networkConnected = NetWorkUtils.isNetworkConnected();
        if (!networkConnected){
            ToastUtil.showToast("请检查网络");
        }
        if (!networkConnected && mistake) {
            if (content != null) {
                netMistake();
            }
        }
    }

    private void netMistake() {
        content.setVisibility(View.INVISIBLE);
        rlNetMistake.setVisibility(View.VISIBLE);
        tvErrorTip.setText(getResources().getString(R.string.net_mistake));
        ivError.setBackgroundResource(R.drawable.net_error);
    }


    private void serverMistake() {
        content.setVisibility(View.INVISIBLE);
        rlNetMistake.setVisibility(View.VISIBLE);
        tvErrorTip.setText(getResources().getString(R.string.services_error));
        ivError.setBackgroundResource(R.drawable.net_error);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == tvNetMistakeTip.getId()) {
            if (NetWorkUtils.isNetworkConnected()) {
                mistakeLoad = true;
                mistakeLoadData();
            }
        }
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDialog();
    }

    public void addInputListener() {
        final View decorView = getActivity().getWindow().getDecorView();
        globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int mainInvisibleHeight = decorView.getRootView().getHeight() - rect.bottom;
                int screenHeight = decorView.getRootView().getHeight();
                if (mainInvisibleHeight > screenHeight / 4) {
                    onInput(true, mainInvisibleHeight, netContent);
                } else {
                    onInput(false, 0, netContent);
                }
            }
        };
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    public void onInput(boolean input, int mainInvisibleHeight, FrameLayout flContent) {
        if (input) {
            flContent.scrollTo(0, DensityUtils.dp2px(60));
        } else {
            flContent.scrollTo(0, 0);
        }
    }

    private KProgressHUD show;
    private boolean isNeedDialog;

    private void initDialog() {
        isNeedDialog = isNeedDialog();
        if (isNeedDialog && getActivity() != null) {
            show = KProgressHUD.create(getActivity())
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true);
        }
    }

    public boolean isNeedDialog() {
        return true;
    }

    public void showWaiteDialog() {
        try {
            if (show != null && !show.isShowing()) {
                show.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeWaiteDialog() {
        try {
            if (show != null && show.isShowing()) {
                show.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示404页面
     */
    public void showNetError() {
        if (content != null && isMistakeShow()) {
            netMistake();
        }
    }

    public void showServicesError() {
        if (content != null && isMistakeShow()) {
            serverMistake();
        }
    }

    /**
     * 显示正常页面
     */
    public void showNetPage() {
        if (mistakeLoad && content != null) {
            mistakeLoad = false;
            content.setVisibility(View.VISIBLE);
            rlNetMistake.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 如果不需要显示404界面,重写返回false
     *
     * @return
     */
    public boolean isMistakeShow() {
        return false;
    }

    /**
     * 服务器提示错误
     *
     * @param error
     */
    public void showError(String error) {

    }

    /**
     * 404错误时候点击重新请求网络的方法
     */
    protected abstract void mistakeLoadData();

    /**
     * 初始化布局，请不要把耗时操作放在这个方法里，这个方法用来提供一个
     * 基本的布局而非一个完整的布局，以免ViewPager预加载消耗大量的资源。
     */
    protected abstract View initView(LayoutInflater inflater,
                                      ViewGroup container,
                                      Bundle savedInstanceState);

    @Override
    public void onDestroyView() {
        if (globalLayoutListener != null) {
            getActivity().getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(globalLayoutListener);
            globalLayoutListener = null;
        }
        super.onDestroyView();
    }


}
