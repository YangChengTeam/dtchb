package com.yc.redevenlopes.homeModule.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.RedDialog;
import com.yc.redevenlopes.homeModule.adapter.HomeAdapter;
import com.yc.redevenlopes.homeModule.contact.MainContact;
import com.yc.redevenlopes.homeModule.present.MainPresenter;
import com.yc.redevenlopes.utils.CommonUtils;
import com.yc.redevenlopes.utils.DisplayUtil;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContact.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.line_snatchTreasure)
    LinearLayout lineSnatchTreasure;
    @BindView(R.id.line_duobao)
    LinearLayout lineDuobao;
    @BindView(R.id.line_lay)
    LinearLayout lineLay;
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initEventAndData() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        homeAdapter = new HomeAdapter(null);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.line_members, R.id.line_activitys, R.id.line_snatchTreasure, R.id.line_withdraw, R.id.iv_avatar, R.id.iv_red})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_members:
                MemberActivity.memberJump(this);
                break;
            case R.id.line_activitys:
                showPopupWindow();
                break;
            case R.id.line_snatchTreasure:
                SnatchTreasureActivity.snatchTreasureJump(this);
                break;
            case R.id.line_withdraw:
                WithdrawActivity.WithdrawJump(this);
                break;
            case R.id.iv_avatar:
                MemberCenterActivity.memberCenterJump(this);
                break;
            case R.id.iv_red:
                showRedDialog();
                break;
        }
    }

    public void showRedDialog() {
        RedDialog redDialog = new RedDialog(this);
        View builder = redDialog.builder(R.layout.red_dialog_item);
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redDialog.setDismiss();
            }
        });
        redDialog.setShow();
    }

    public void openVideo(int type) {
//        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
//        if (type == 0) {
//            adPlatformSDK.showInsertAd(this,900, 600, this);
//        } else if (type == 1) {
//            adPlatformSDK.showExpressAd(this,this, (FrameLayout) findViewById(R.id.fl_ad_container));
//        } else if (type == 2) {
//            adPlatformSDK.showRewardVideoHorizontalAd(this,this);
//        } else if (type == 3) {
//            adPlatformSDK.showFullScreenVideoVerticalAd(this, this);
//        } else if (type == 4) {
//            adPlatformSDK.showBannerAd(this, 300, 100, this,  (FrameLayout) findViewById(R.id.fl_ad_container));
//        }
    }


    private void showPopupWindow() {
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_home_item, null);
        ConstraintLayout line_answer = inflate.findViewById(R.id.cons_answer);
        ConstraintLayout line_guessr = inflate.findViewById(R.id.cons_guess);
        ConstraintLayout line_turn = inflate.findViewById(R.id.cons_turn);
        line_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerActivity.answerJump(MainActivity.this);
                popupWindow.dismiss();
            }
        });
        line_guessr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuessingActivity.GuessingJump(MainActivity.this);
                popupWindow.dismiss();
            }
        });
        line_turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TurnTableActivity.TurnTableJump(MainActivity.this);
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(inflate);
        popupWindow.setFocusable(true);
        // 设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        // 外部点击事件
        popupWindow.setOutsideTouchable(true);
        int screenHeight = CommonUtils.getScreenHeight(this);
        int screenWidth = CommonUtils.getScreenWidth(this);
        int statusBarHeight = CommonUtils.getStatusBarHeight(this);
        int he = DisplayUtil.dip2px(this, 114);
        int wh = screenWidth / 8;
        popupWindow.showAtLocation(lineLay, Gravity.TOP, -wh, screenHeight - he-statusBarHeight);
    }

}