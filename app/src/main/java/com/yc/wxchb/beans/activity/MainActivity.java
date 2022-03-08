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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.umeng.analytics.MobclickAgent;
import com.yc.wxchb.R;
import com.yc.wxchb.base.BaseActivity;
import com.yc.wxchb.beans.contact.MainContract;
import com.yc.wxchb.beans.fragment.ExitTintFragment;
import com.yc.wxchb.beans.fragment.HomeFragment;
import com.yc.wxchb.beans.fragment.MineFragment;
import com.yc.wxchb.beans.fragment.TaskFragment;
import com.yc.wxchb.beans.fragment.WithDrawFragment;
import com.yc.wxchb.beans.present.MainPresenter;
import com.yc.wxchb.utils.SoundPoolUtils;
import com.yc.wxchb.utils.VUiKit;

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
        positon = 0;
        homeFragment = new HomeFragment();
        currfragment = homeFragment;
        supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.fl_contains, homeFragment).commit();
        tab_lay.add(rbMoneys);
        tab_lay.add(rbUpdata);
        tab_lay.add(rbWithdraw);
        tab_lay.add(rbMine);
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
            //showIn();
        }
        switch (view.getId()) {
            case R.id.rb_moneys:
                MobclickAgent.onEvent(this, "homemoney", "1");//参数二为当前统计的事件ID
                fragmentTransaction = supportFragmentManager.beginTransaction();
                positon = 0;
                fragmentTransaction.hide(currfragment).show(homeFragment).commit();
                currfragment = homeFragment;
                setRadiobutton(0);
                break;
            case R.id.rb_updata:
                MobclickAgent.onEvent(this, "shengji", "1");//参数二为当前统计的事件ID
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
                MobclickAgent.onEvent(this, "withdraw", "1");//参数二为当前统计的事件ID
                positon = 2;
                fragmentTransaction = supportFragmentManager.beginTransaction();
                if (withDrawFragment == null) {
                    withDrawFragment = new WithDrawFragment();
                    fragmentTransaction.hide(currfragment).add(R.id.fl_contains, withDrawFragment).commit();
                } else {
                    fragmentTransaction.hide(currfragment).show(withDrawFragment).commit();
                }
                currfragment = withDrawFragment;
                setRadiobutton(2);
                break;
            case R.id.rb_mine:
                MobclickAgent.onEvent(this, "mine", "1");//参数二为当前统计的事件ID
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
}