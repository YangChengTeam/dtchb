package com.yc.wxchb.beans.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.umeng.analytics.MobclickAgent;
import com.yc.wxchb.R;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.base.BaseActivity;
import com.yc.wxchb.beans.contact.MainContract;
import com.yc.wxchb.beans.fragment.ExitTintFragment;
import com.yc.wxchb.beans.fragment.HomeFragment;
import com.yc.wxchb.beans.fragment.MineFragment;
import com.yc.wxchb.beans.fragment.TaskFragment;
import com.yc.wxchb.beans.fragment.WithDrawFragment;
import com.yc.wxchb.beans.module.beans.OtherBeans;
import com.yc.wxchb.beans.present.MainPresenter;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.SoundPoolUtils;
import com.yc.wxchb.utils.VUiKit;
import com.yc.wxchb.utils.ad.GromoreAdShow;
import com.yc.wxchb.utils.ad.GromoreInsetAdShow;
import com.yc.wxchb.utils.adgromore.GromoreAdShowTwo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.fl_contains)
    FrameLayout flContains;
    @BindView(R.id.rb_moneys)
    RadioButton rbMoneys;
    @BindView(R.id.rb_updata)
    RadioButton rbUpdata;
    @BindView(R.id.rb_withdraw)
    RadioButton rbWithdraw;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;

    public List<View> tab_lay = new ArrayList<>();
    //    @BindView(R.id.viewpager)
//    ViewPager viewpager;
    private List<String> titleList;
    private HomeFragment homeFragment;
    private TaskFragment taskFragment;
    private WithDrawFragment withDrawFragment;
    private MineFragment mineFragment;
    private Fragment currfragment;
    public FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private boolean isFirst;
    private int positon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initEventAndData() {
        setFullScreen();
        GromoreAdShow.getInstance().setContextsInit(this);
        GromoreInsetAdShow.getInstance().setContextsInit(this);
        positon = 0;
        homeFragment = new HomeFragment();
        currfragment = homeFragment;
        supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.fl_contains, homeFragment).commit();
        tab_lay.add(rbMoneys);
        tab_lay.add(rbUpdata);
        tab_lay.add(rbWithdraw);
        tab_lay.add(rbMine);
        mPresenter.getOtherInfo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", CacheDataUtils.getInstance().getUserInfo().getId() + "");
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String positionss = intent.getStringExtra("position");
        if (!TextUtils.isEmpty(positionss)) {
            switch (positionss) {
                case "0":
                    setPositionFg(0);
                    break;
                case "1":
                    setPositionFg(1);
                    break;
                case "2":
                    setPositionFg(2);
                    break;
                case "3":
                    setPositionFg(3);
                    break;
            }
        }
    }

    public int home_membersst = 1;//
    @OnClick({R.id.rb_moneys, R.id.rb_updata, R.id.rb_withdraw, R.id.rb_mine})
    public void onViewClicked(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        home_membersst++;
        if (home_membersst % 2 == 0) {
           showIn();
        }
        switch (view.getId()) {
            case R.id.rb_moneys:
                MobclickAgent.onEvent(this, "tab_money", "1");//参数二为当前统计的事件ID
                fragmentTransaction = supportFragmentManager.beginTransaction();
                positon = 0;
                fragmentTransaction.hide(currfragment).show(homeFragment).commit();
                currfragment = homeFragment;
                setRadiobutton(0);
                break;
            case R.id.rb_updata:
                MobclickAgent.onEvent(this, "tab_task", "1");//参数二为当前统计的事件ID
                positon = 1;
                fragmentTransaction = supportFragmentManager.beginTransaction();
                if (taskFragment == null) {
                    taskFragment = new TaskFragment();
                    fragmentTransaction.hide(currfragment).add(R.id.fl_contains, taskFragment).commit();
                } else {
                    fragmentTransaction.hide(currfragment).show(taskFragment).commit();
                }
                currfragment = taskFragment;
                setRadiobutton(1);
                break;
            case R.id.rb_withdraw:
                MobclickAgent.onEvent(this, "tab_withdraw", "1");//参数二为当前统计的事件ID
                positon = 2;
                fragmentTransaction = supportFragmentManager.beginTransaction();
                if (withDrawFragment == null) {
                    withDrawFragment = new WithDrawFragment();
                    fragmentTransaction.hide(currfragment).add(R.id.fl_contains, withDrawFragment).commit();
                } else {
                    fragmentTransaction.hide(currfragment).show(withDrawFragment).commit();
                }
                withDrawFragment.setOnRefresh();
                currfragment = withDrawFragment;
                setRadiobutton(2);
                break;
            case R.id.rb_mine:
                MobclickAgent.onEvent(this, "tab_withdraw", "1");//参数二为当前统计的事件ID
                positon = 3;
                fragmentTransaction = supportFragmentManager.beginTransaction();
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    fragmentTransaction.hide(currfragment).add(R.id.fl_contains, mineFragment).commit();
                } else {
                    fragmentTransaction.hide(currfragment).show(mineFragment).commit();
                }
                currfragment = mineFragment;
                setRadiobutton(3);
                mineFragment.setRefresh();
                break;
        }
    }

    private boolean isInset;
    private int index;
    public void showIn() {
        VUiKit.postDelayed(1700, () -> {
            //腾讯播放插屏
            if (isInset){
                index++;
                if (index>=1){
                    isInset=false;
                }
                return;
            }
            index=0;
            GromoreInsetAdShow.getInstance().showInset(this, "home_inset", new GromoreInsetAdShow.OnInsetAdShowCaback() {
                @Override
                public void onRewardedAdShow() {
                    isInset=true;
                }

                @Override
                public void onRewardedAdShowFail() {
                    isInset=false;
                }

                @Override
                public void onRewardClick() {

                }

                @Override
                public void onVideoComplete() {

                }

                @Override
                public void setVideoCallBacks() {

                }

                @Override
                public void onRewardedAdClosed(boolean isVideoClick, boolean isCompeter) {
                    isInset=false;
                }
            });
//            String insetTypeThree = AppSettingUtils.getInsetTypeThree();
//            if ("1".equals(insetTypeThree)){
//                requestInterstitialAd();
//            }else {
//                showTxInsertAd();
//            }
        });
    }

    public void setPositionFg(int i) {
        if (i == 0) {
            fragmentTransaction = supportFragmentManager.beginTransaction();
            positon = 0;
            fragmentTransaction.hide(currfragment).show(homeFragment).commit();
            currfragment = homeFragment;
            setRadiobutton(0);
        } else if (i == 1) {
            positon = 1;
            fragmentTransaction = supportFragmentManager.beginTransaction();
            if (taskFragment == null) {
                taskFragment = new TaskFragment();
                fragmentTransaction.hide(currfragment).add(R.id.fl_contains, taskFragment).commit();
            } else {
                fragmentTransaction.hide(currfragment).show(taskFragment).commit();
            }
            currfragment = taskFragment;
            setRadiobutton(1);
        } else if (i == 2) {
            positon = 2;
            fragmentTransaction = supportFragmentManager.beginTransaction();
            if (withDrawFragment == null) {
                withDrawFragment = new WithDrawFragment();
                fragmentTransaction.hide(currfragment).add(R.id.fl_contains, withDrawFragment).commit();
            } else {
                fragmentTransaction.hide(currfragment).show(withDrawFragment).commit();
            }
            currfragment = withDrawFragment;
            withDrawFragment.setOnRefresh();
            setRadiobutton(2);
        } else if (i == 3) {
            positon = 3;
            fragmentTransaction = supportFragmentManager.beginTransaction();
            if (mineFragment == null) {
                mineFragment = new MineFragment();
                fragmentTransaction.hide(currfragment).add(R.id.fl_contains, mineFragment).commit();
            } else {
                fragmentTransaction.hide(currfragment).show(mineFragment).commit();
            }
            currfragment = mineFragment;
            setRadiobutton(3);
            mineFragment.setRefresh();
        }
    }
    private void setRadiobutton(int index) {
        for (int i = 0; i < tab_lay.size(); i++) {
            if (i == index) {
                ((RadioButton) tab_lay.get(i)).setChecked(true);
            } else {
                ((RadioButton) tab_lay.get(i)).setChecked(false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getOtherInfo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", CacheDataUtils.getInstance().getUserInfo().getId() + "");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ExitTintFragment exitTintFragment = new ExitTintFragment();
            exitTintFragment.show(getSupportFragmentManager(), "");
            exitTintFragment.setExitListener(new ExitTintFragment.OnExitListener() {
                @Override
                public void onExit() {
                    System.exit(0);
                }

                @Override
                public void onSure() {

                }
            });
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
    //=================start===================激励视频=====================================================================================
    private int typePosition; //0 首页 1 任务 2提现 3我的页面
    private String ad_positions;
    public void showjiliAd(int typePositios, String ad_positions) {
        this.typePosition = typePositios;
        this.ad_positions = ad_positions;
        if (CommonUtils.isDestory(this)){
            return;
        }
        GromoreAdShow.getInstance().showjiliAd(this,1,ad_positions, new GromoreAdShow.OnAdShowCaback() {
            @Override
            public void onRewardedAdShow() {

            }

            @Override
            public void onRewardedAdShowFail() {

            }

            @Override
            public void onRewardClick() {

            }

            @Override
            public void onVideoComplete() {

            }

            @Override
            public void setVideoCallBacks() {

            }

            @Override
            public void onRewardedAdClosed(boolean isVideoClick,boolean isCompete) {
                if (typePosition == 0) {
                    /*if (homeFragment != null) {
                        homeFragment.setVideoCallBack(isVideoClick);
                    }*/
                } else if (typePosition == 1) {
                    if (taskFragment != null) {
                        taskFragment.setVideoCallBacks(isVideoClick);
                    }
                } else if (typePosition == 2) {
                    if (withDrawFragment != null) {
                       // withDrawFragment.setVideoCallBacks(isVideoClick);
                    }
                } else if (typePosition == 3) {
                    if (mineFragment != null) {
                        mineFragment.setVideoCallBack(isVideoClick);
                    }
                }
            }

            @Override
            public void onFinshTask(String appPackage, String appName, String type) {

            }

            @Override
            public void onNoTask() {

            }
        });
    }
    public OtherBeans otherBeans;
    @Override
    public void getOtherInfoSuccess(OtherBeans data) {
        if (data!=null){
            ((MyApplication) MyApplication.getInstance()).hb_Nums=data.getHb_num();
            if (!TextUtils.isEmpty(data.getCash())){
                ((MyApplication) MyApplication.getInstance()).cash=data.getCash();
            }
            this.otherBeans=data;
            if (homeFragment!=null){
                homeFragment.setRefresh(otherBeans);
            }
            if (taskFragment!=null){
                taskFragment.setRefresh(otherBeans);
            }
            if (mineFragment!=null){
                mineFragment.setRefreshs(otherBeans);
            }
        }
    }
    //=================end===================激励视频=====================================================================================
}