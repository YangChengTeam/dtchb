package com.lq.lianjibusiness.base_libary.ui.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lq.lianjibusiness.base_libary.R;
import com.lq.lianjibusiness.base_libary.utils.DensityUtils;
import com.lq.lianjibusiness.base_libary.utils.LoadViewDialog;
import com.lq.lianjibusiness.base_libary.utils.NetWorkUtils;
import com.lq.lianjibusiness.base_libary.utils.StatusBarUtil;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;

import java.lang.reflect.Field;


/**
 * Created by ccc on 2020/9/15.
 */

public abstract class NetActivity extends AppCompatActivity implements View.OnClickListener {

    public final String TAG = this.getClass().getSimpleName();

    private TextView tvTitle, tvNetMistake, tvRightTitle, tvErrorTip, ivCloseMall, tv_mall_back;
    private RelativeLayout rlNetMistake, rlTitle, rlTitleContent, rlBack;
    private FrameLayout flContent;
    private ImageButton ivBack;
    private ImageView ivError;
    private View topView;

    private boolean mistake;
    private boolean mistakeLoad, titleMistake, isNeedNewTittle;
    private View content;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener;
    private Handler handler = new Handler();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        //setAndroidState();
        setContentView(R.layout.activity_base_net);
        mistake = isMistakeShow();
        findViews();
        initView();
    }

    private void findViews() {
        tvTitle = findViewById(R.id.tv_title);
        tvNetMistake = findViewById(R.id.tv_net_mistake_tip);
        tvRightTitle = findViewById(R.id.tv_right_title);
        tvErrorTip = findViewById(R.id.tv_net_mistake);
        ivError = findViewById(R.id.iv_base_error);
        tv_mall_back = findViewById(R.id.tv_mall_back);
        rlNetMistake = findViewById(R.id.rl_net_mistake);
        rlTitleContent = findViewById(R.id.rl_title_content);
        rlTitle = findViewById(R.id.rl_title);
        ivCloseMall = findViewById(R.id.iv_close_mall);
        flContent = findViewById(R.id.fl_content);
        rlBack = findViewById(R.id.rl_back);
        topView = findViewById(R.id.view);
        ivBack = findViewById(R.id.ib_mall_back);
    }

    private void initView() {
        if (isNeedNewTitle(isNeedNewTittle)) {
            rlTitle.setVisibility(View.GONE);
            topView.setVisibility(View.GONE);
        }
        initContent();
        initTopView();
        initClick();
        initDialog();
        initDialogTwo();
        setNetWorkState();
    }

    /**
     * ??????????????????title?????????????????????true
     *
     * @return
     */
    public boolean isNeedNewTitle(boolean isNeedNewTittle) {
        this.isNeedNewTittle = isNeedNewTittle;
        return isNeedNewTittle;
    }

    private void initTopView() {
        int i = StatusBarUtil.StatusBarLightMode(this);
        if (i == 0) {
            topView.setBackgroundResource(R.drawable.top_view_bg);
        }
        topView.setVisibility(View.GONE);
    }

    private void initContent() {
        content = getLayoutInflater().inflate(getLayout(), null);
        flContent.addView(content);
    }

    private void setNetWorkState() {
        boolean networkConnected = NetWorkUtils.isNetworkConnected();
        if (!networkConnected){
            ToastUtil.showToast("???????????????");
        }
        if (!networkConnected && mistake) {
            if (content != null) {
                netMistake();
            }
        }
    }

    private void netMistake() {
        titleMistake = true;
        content.setVisibility(View.INVISIBLE);
        rlNetMistake.setVisibility(View.VISIBLE);
        tvErrorTip.setText(getResources().getString(R.string.net_mistake));
        ivError.setBackgroundResource(R.drawable.net_error);
        tvRightTitle.setVisibility(View.INVISIBLE);
        tvTitle.setVisibility(View.INVISIBLE);
    }

    private void serverMistake() {
        titleMistake = true;
        content.setVisibility(View.INVISIBLE);
        rlNetMistake.setVisibility(View.VISIBLE);
        tvErrorTip.setText(getResources().getString(R.string.services_error));
        ivError.setBackgroundResource(R.drawable.net_error);
        tvRightTitle.setVisibility(View.INVISIBLE);
        tvTitle.setVisibility(View.INVISIBLE);
    }

    private void initClick() {
        rlBack.setOnClickListener(this);
        tvRightTitle.setOnClickListener(this);
        tvNetMistake.setOnClickListener(this);
        ivCloseMall.setOnClickListener(this);
        tv_mall_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == rlBack.getId()) {
            onIbBackClick();
        } else if (v.getId() == tvRightTitle.getId()) {
            onRightTitleClick();
        } else if (v.getId() == tvNetMistake.getId()) {
            if (NetWorkUtils.isNetworkConnected()) {
                mistakeLoad = true;
                mistakeLoadData();
            }
        } else if (v.getId() == ivCloseMall.getId()) {
            onMallCloseClick();
        } else if (v.getId() == tv_mall_back.getId()) {
            onIbBackClick();
        }
    }
    private LoadViewDialog loadViewDialog;
    private KProgressHUD show;
    private boolean isNeedDialog;

    private void initDialog() {
        isNeedDialog = isNeedDialog();
        if (isNeedDialog) {
            show = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true);
        }
    }


    private void initDialogTwo() {
        isNeedDialog = isNeedDialog();
        if (isNeedDialog) {
            loadViewDialog=new LoadViewDialog(this);
        }
        loadViewDialog.dismiss();
    }

//    public void closeWaiteDialogTwo() {
//        try {
//            if (loadViewDialog != null && loadViewDialog.isShowing()) {
//                loadViewDialog.dismiss();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public void showWaiteDialogTwo() {
//        try {
//            if (titleMistake) {
//                titleMistake = false;
//                return;
//            }
//            if (this.isFinishing())
//                return;
//            if (loadViewDialog != null && !loadViewDialog.isShowing()) {
//                loadViewDialog.show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public boolean isNeedDialog() {
        return true;
    }

    public void showWaiteDialog() {
        try {
            if (titleMistake) {
                titleMistake = false;
                return;
            }
            if (this.isFinishing())
                return;
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

    private void setAndroidState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT); //????????????
            } catch (Exception e) {
            }
        }
    }

    /**
     * ??????????????????404??????,????????????true,?????????p???????????????????????????
     * <p>
     * ??????mistakeLoadData
     *
     * @return
     */
    public boolean isMistakeShow() {
        return false;
    }

    /**
     * ????????????title
     *
     * @param stringId
     */
    public void setTitleRes(int stringId) {
        tvTitle.setText(stringId);
    }

    /**
     * ????????????title
     *
     * @param title
     */
    public void setTitle(String title) {
        tvTitle.setText(title);
    }


    /**
     * ???????????????????????????
     *
     * @param imgId
     */
    public void setLeftImgRes(Drawable imgId) {
        ivBack.setImageDrawable(imgId);
    }

    /**
     * ????????????
     */
    public void needMallClose(String text) {
        rlBack.setVisibility(View.GONE);
        ivCloseMall.setVisibility(View.INVISIBLE);
        tv_mall_back.setVisibility(View.VISIBLE);
        tv_mall_back.setText(text);
    }

    public void notNeedMallClose() {
        rlBack.setVisibility(View.VISIBLE);
        ivCloseMall.setVisibility(View.INVISIBLE);
        tv_mall_back.setVisibility(View.GONE);
    }

    public void setBackVisble(int visble) {
        rlBack.setVisibility(visble);
    }

    /**
     * ????????????title
     *
     * @param stringId
     */
    public void setTitleString(String stringId) {
        tvTitle.setText(stringId);
    }

    /**
     * ???????????????????????????
     */
    public void noTitle() {
        rlTitle.setVisibility(View.GONE);
    }

    /**
     * ?????????????????????????????????
     */
    public void noState() {
        rlTitleContent.setVisibility(View.GONE);
    }

    /**
     * ???????????????????????????
     *
     * @param stringId
     */
    public void setRightTitle(int stringId) {
        tvRightTitle.setText(stringId);
        if (!titleMistake) {
            tvRightTitle.setVisibility(View.VISIBLE);
        }
    }

    /**
     * ???????????????????????????
     *
     * @param text
     */
    public void setRightTitle(String text) {
        tvRightTitle.setText(text);
        if (!titleMistake) {
            tvRightTitle.setVisibility(View.VISIBLE);
        }
    }

    /**
     * ???????????????????????????
     *
     * @param colorRes
     */
    public void setRightTitleColor(int colorRes) {
        tvRightTitle.setTextColor(colorRes);

    }

    public void setTvRightVisibility(int visibility) {
        tvRightTitle.setVisibility(visibility);
    }

    public void onRightTitleClick() {
        //?????????,????????????????????????????????????
    }

    /**
     * ?????????????????????
     */
    public void onIbBackClick() {
        finish();
    }

    /**
     * ??????????????????????????????
     */
    public void onMallCloseClick() {

    }

    /**
     * ?????????????????????
     *
     * @param error
     */
    public void showError(String error,String status) {
//        DialogUtils.dialogTip(error, this);
        if(TextUtils.equals(status, "-9")){
            ToastUtil.showToast("??????????????????");
        }else{
            if (!TextUtils.isEmpty(error)){
                ToastUtil.showToast(error);
            }
        }
    }

    /**
     * ??????404??????
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
     * ??????????????????
     */
    public void showNetPage() {
        if (mistakeLoad && content != null) {
            mistakeLoad = false;
            content.setVisibility(View.VISIBLE);
            rlNetMistake.setVisibility(View.INVISIBLE);
            tvRightTitle.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.VISIBLE);
            titleMistake = false;
        }
    }

    private int keyboardScrollH = 60;

    public void setKeyboardScrollH(int keyboardScrollH) {
        this.keyboardScrollH = keyboardScrollH;
    }

    public void addInputListener() {
        final View decorView = getWindow().getDecorView();
        globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int mainInvisibleHeight = decorView.getRootView().getHeight() - rect.bottom;
                int screenHeight = decorView.getRootView().getHeight();
                if (mainInvisibleHeight > screenHeight / 4) {
                    onInput(true, mainInvisibleHeight, flContent);
                } else {
                    handler.postDelayed(onInput, 200);
                }
            }
        };
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    Runnable onInput = new Runnable() {
        @Override
        public void run() {
            onInput(false, 0, flContent);
        }
    };

    public void onInput(boolean input, int mainInvisibleHeight, FrameLayout flContent) {
        if (input) {
            handler.removeCallbacks(onInput);
            flContent.scrollTo(0, DensityUtils.dp2px(keyboardScrollH));
        } else {
            flContent.scrollTo(0, 0);
        }
    }

    @Override
    protected void onDestroy() {
        if (globalLayoutListener != null) {
            getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(globalLayoutListener);
            globalLayoutListener = null;
        }
        super.onDestroy();
    }

    /**
     * 404?????????????????????????????????????????????
     */
    protected abstract void mistakeLoadData();

    /**
     * @return ????????????????????????layout???id
     */
    public abstract int getLayout();
}
