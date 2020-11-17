package com.yc.redevenlopes.homeModule.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.RedDialog;
import com.yc.redevenlopes.homeModule.adapter.HomeAdapter;
import com.yc.redevenlopes.homeModule.contact.MainContact;
import com.yc.redevenlopes.homeModule.present.MainPresenter;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContact.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
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
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        homeAdapter=new HomeAdapter(null);
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
        redDialog.builder(R.layout.red_dialog_item);
        redDialog.setShow();
    }
    public void openVideo(int type){
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


    private void showPopupWindow(){
        PopupWindow popupWindow=new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.layout_home_item, null));
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
       // popupWindow.showAsDropDown();
    }

}