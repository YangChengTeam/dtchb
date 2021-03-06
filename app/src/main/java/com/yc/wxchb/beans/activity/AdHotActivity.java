package com.yc.wxchb.beans.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.LifecycleObserver;

import com.umeng.analytics.MobclickAgent;
import com.yc.wxchb.R;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.base.BaseActivity;
import com.yc.wxchb.beans.contact.AdHotContract;
import com.yc.wxchb.beans.module.beans.HotIndexBeans;
import com.yc.wxchb.beans.module.beans.HotTaskBeans;
import com.yc.wxchb.beans.present.AdHotPresenter;
import com.yc.wxchb.dialog.SignDialog;
import com.yc.wxchb.dialog.SnatchDialog;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.ClickListenName;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.CountDownUtilsThree;
import com.yc.wxchb.utils.SoundPoolUtils;
import com.yc.wxchb.utils.TimesUtils;
import com.yc.wxchb.utils.VUiKit;
import com.yc.wxchb.utils.ad.GromoreAdShow;
import com.zzhoujay.richtext.RichText;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AdHotActivity extends BaseActivity<AdHotPresenter> implements AdHotContract.View, LifecycleObserver {
    @BindView(R.id.tv_times)
    TextView tvTimes;
    @BindView(R.id.line_complaint)
    LinearLayout lineComplaint;
    @BindView(R.id.tv_peopleNums)
    TextView tvPeopleNums;
    @BindView(R.id.tv_step1)
    TextView tvStep1;
    @BindView(R.id.iv_step1)
    ImageView ivStep1;
    @BindView(R.id.tv_step2)
    TextView tvStep2;
    @BindView(R.id.iv_step2)
    ImageView ivStep2;
    @BindView(R.id.tv_step3)
    TextView tvStep3;
    @BindView(R.id.iv_step3)
    ImageView ivStep3;
    @BindView(R.id.tv_step4)
    TextView tvStep4;
    @BindView(R.id.iv_step4)
    ImageView ivStep4;
    @BindView(R.id.iv_sure)
    ImageView ivSure;

    @BindView(R.id.iv_rules)
    TextView ivRules;


    @BindView(R.id.tv_taskTitle)
    TextView tv_taskTitle;
    private CountDownUtilsThree countDownUtilsThree;
    private CountDownUtilsThree countDownUtilsFour;

    private int times;
    private InnerRecevier innerReceiver;
    private boolean isFinsh;
    public static WeakReference<AdHotActivity> instances;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
        //????????????
        innerReceiver = new InnerRecevier();
        //??????????????????
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //????????????
        registerReceiver(innerReceiver, intentFilter);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_ad_hot;
    }

    @Override
    public void initEventAndData() {
        setFullScreen();
        instances = new WeakReference<>(this);
        initCountDownUtilsThree();
        stepIndex=1;
        setStatus();
        mPresenter.getHotInfoIndex(CacheDataUtils.getInstance().getUserInfo().getId() + "", ((MyApplication) MyApplication.getInstance()).getAgentId());
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }


    public static void adhotJump(Context context) {
        Intent intent = new Intent(context, AdHotActivity.class);
        context.startActivity(intent);
    }

    private SignDialog hotTaskDialog;
    public void hotTaskDialogs(int type,String appPackages) {
        hotTaskDialog = new SignDialog(this);
        View builder = hotTaskDialog.builder(R.layout.hottask_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv1 = builder.findViewById(R.id.tv1);
        TextView tv2 = builder.findViewById(R.id.tv2);
        ImageView iv_closes = builder.findViewById(R.id.iv_close);
        tv2.setVisibility(View.VISIBLE);
        if (type==1){
            tv2.setVisibility(View.GONE);
            tv1.setText("????????????????????????????????????APP,?????????????????????!?????????????????????????????????????????????????????????????????????????????????");
            tv_sure.setText("????????????");
        }else if (type==2){
            tv1.setText("??????????????????APP");
            tv2.setText("????????????15???????????????????????????");
            tv_sure.setText("?????????");
        }else if (type==3){
            tv1.setText("?????????????????????????????????APP??????");
            tv2.setText("?????????????????????App?????????");
            tv_sure.setText("????????????");
        }
        iv_closes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                hotTaskDialog.setDismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                hotTaskDialog.setDismiss();
                if (type==2){
                    Intent intent = getPackageManager().getLaunchIntentForPackage(appPackages);
                    if (intent != null) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    long yuTimes = 15*1000;
                    isCountStart=true;
                    countDownUtilsFour.clean();
                    countDownUtilsFour.setHours(TimesUtils.getMinDiff(yuTimes), TimesUtils.getSecondDiff(yuTimes));
                }
            }
        });
        if (!CommonUtils.isDestory(this)) {
            if (!hotTaskDialog.getIsShow()){
                hotTaskDialog.setShow();
            }
        }
    }


    public void startCount(int outTimes) {
        long yuTimes = outTimes * 1000;
        if (countDownUtilsThree != null) {
            countDownUtilsThree.setHours(TimesUtils.getMinDiff(yuTimes), TimesUtils.getSecondDiff(yuTimes));
        }
    }
    private String ad_type;
    private boolean isDownCount;
    private void initCountDownUtilsThree() {
        countDownUtilsThree = new CountDownUtilsThree();
        countDownUtilsThree.setOnCountDownListen(new CountDownUtilsThree.OnCountDownListen() {
            @Override
            public void count(long mMin, long mSecond) {
                String timesContents = getTv(mMin) + ":" + getTv(mSecond);
                setTimesData(timesContents);
            }

            @Override
            public void countFinsh() {
                startCount(times);
            }
        });

        countDownUtilsFour = new CountDownUtilsThree();
        countDownUtilsFour.setOnCountDownListen(new CountDownUtilsThree.OnCountDownListen() {
            @Override
            public void count(long mMin, long mSecond) {
                isCountStart=true;
            }

            @Override
            public void countFinsh() {
                stepIndex=6;
                setStatus();
                isCountStart=false;
                if (TextUtils.isEmpty(ad_type)) {
                    ad_type="1";
                }
                mPresenter.gethotTask(CacheDataUtils.getInstance().getUserInfo().getId() + "", ((MyApplication) MyApplication.getInstance()).getAgentId(),appPackage,appName,ad_type);
            }
        });
    }


    private String timesCount;
    public void setTimesData(String timesContents) {
        this.timesCount = timesContents;
        if (tvTimes != null && !TextUtils.isEmpty(timesCount)) {
            tvTimes.setText(timesCount);
        }
    }

    private String getTv(long l) {
        if (l >= 10) {
            return l + "";
        } else {
            return "0" + l;//??????10,,??????????????????"0"
        }
    }

   private String contentRule;
    @Override
    public void getHotInfoIndexSuccess(HotIndexBeans data) {
        if (data != null) {
            int downnum = data.getDownnum();
            tvPeopleNums.setText(downnum+"");
            HotIndexBeans.DownLoaduserBean down_loaduser = data.getDown_loaduser();
            HotIndexBeans.DownloadBean download = data.getDownload();
            HotIndexBeans.DownloadConfigBean download_config = data.getDownload_config();
            if (download_config!=null){
                String ad_video = download_config.getAd_video();
                List<String> list=new ArrayList<>();
                if (!TextUtils.isEmpty(ad_video)){
                    if (ad_video.contains("1")){
                        list.add("1");
                    }
                    if (ad_video.contains("2")){
                        list.add("2");
                    }
                    if (ad_video.contains("3")){
                        list.add("3");
                    }
                }
            }

            if (download != null) {
                times = download.getTime_out();
                startCount(times);
                 contentRule = download.getContent();
                List<String> step_config = download.getStep_config();
                if (step_config!=null){
                    for (int i = 0; i < step_config.size(); i++) {
                        if (i==0){
                            tvStep1.setText(step_config.get(i));
                        }else if (i==1){
                            tvStep2.setText(step_config.get(i));
                        }else if (i==2){
                            tvStep3.setText(step_config.get(i));
                        }else if (i==3){
                            tvStep4.setText(step_config.get(i));
                        }
                    }
                }
            }

            if (down_loaduser!=null){
                if (down_loaduser.getStatus()==1){
                    VUiKit.postDelayed(400, () -> {
                        if (CommonUtils.isInstallApp(down_loaduser.getPackageX())){
                            appPackage=down_loaduser.getPackageX();
                            appName=down_loaduser.getApp_name();
                            hotTaskDialogs(2,down_loaduser.getPackageX());
                        }
                    });
                }
            }
        }
    }

    @Override
    public void gethotTaskSuccess(HotTaskBeans data) {
        if (data!=null){
            if (data.getStatus()==1){
                VUiKit.postDelayed(400, () -> {
                    hotTaskDialogs(2,appPackage);
                });
            }else {
                finshHotTaskDialogs(data.getPlay_award());
            }
        }
    }
    private SnatchDialog finshHotTaskDialog;
    public void finshHotTaskDialogs(int award) {
        finshHotTaskDialog = new SnatchDialog(this);
        View builder = finshHotTaskDialog.builder(R.layout.finshhottask_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_hotNums = builder.findViewById(R.id.tv_hotNums);
        tv_hotNums.setText(award+"");
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                finshHotTaskDialog.setDismiss();
                finish();
            }
        });
        finshHotTaskDialog.setOutCancle(false);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotActivity.adhotJump(AdHotActivity.this,"4");
                stepIndex=1;
                setStatus();
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                finshHotTaskDialog.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)) {
            finshHotTaskDialog.setShow();
        }
    }
    private int stepIndex;//1 ??????????????????   2??????????????????     3??????????????????   4???????????????      5???????????????  6???????????????
    public void setStatus(){
        if (stepIndex==1||stepIndex==2){
            ivSure.setImageDrawable(getResources().getDrawable(R.drawable.hot_ac7));
            if (stepIndex==2){
                tv_taskTitle.setText("??????????????????????????????????????????1/4???");
                ivStep1.setImageDrawable(getResources().getDrawable(R.drawable.hot_ac5));
                ivStep2.setImageDrawable(getResources().getDrawable(R.drawable.hot_icon17));
                ivStep1.setVisibility(View.VISIBLE);
                ivStep2.setVisibility(View.VISIBLE);
                ivStep3.setVisibility(View.INVISIBLE);
                ivStep4.setVisibility(View.INVISIBLE);
                tvStep1.setTextColor(getResources().getColor(R.color.green_1AAD19));
                tvStep2.setTextColor(getResources().getColor(R.color.yellow_54433A));
                tvStep3.setTextColor(getResources().getColor(R.color.yellow_54433A));
                tvStep4.setTextColor(getResources().getColor(R.color.yellow_54433A));
            }else if (stepIndex==1){
                tv_taskTitle.setText("??????????????????????????????????????????0/4???");
                tvStep1.setTextColor(getResources().getColor(R.color.yellow_F9740F));
                tvStep2.setTextColor(getResources().getColor(R.color.yellow_54433A));
                tvStep3.setTextColor(getResources().getColor(R.color.yellow_54433A));
                tvStep4.setTextColor(getResources().getColor(R.color.yellow_54433A));
                ivStep1.setVisibility(View.INVISIBLE);
                ivStep2.setVisibility(View.INVISIBLE);
                ivStep3.setVisibility(View.INVISIBLE);
                ivStep4.setVisibility(View.INVISIBLE);
            }
        }else if (stepIndex==3){
            tv_taskTitle.setText("??????????????????????????????????????????1/4???");
            ivSure.setImageDrawable(getResources().getDrawable(R.drawable.hot_icon2));
            ivStep1.setImageDrawable(getResources().getDrawable(R.drawable.hot_ac5));
            ivStep2.setImageDrawable(getResources().getDrawable(R.drawable.hot_icon12));
            ivStep1.setVisibility(View.VISIBLE);
            ivStep2.setVisibility(View.VISIBLE);
            ivStep3.setVisibility(View.INVISIBLE);
            ivStep4.setVisibility(View.INVISIBLE);
            tvStep1.setTextColor(getResources().getColor(R.color.green_1AAD19));
            tvStep2.setTextColor(getResources().getColor(R.color.yellow_F9740F));
            tvStep3.setTextColor(getResources().getColor(R.color.yellow_54433A));
            tvStep4.setTextColor(getResources().getColor(R.color.yellow_54433A));
        }else if (stepIndex==5||stepIndex==4){
            if (stepIndex==5){
                tv_taskTitle.setText("??????????????????????????????????????????3/4???");
                ivSure.setImageDrawable(getResources().getDrawable(R.drawable.hot_icon14));
                ivStep1.setImageDrawable(getResources().getDrawable(R.drawable.hot_ac5));
                ivStep2.setImageDrawable(getResources().getDrawable(R.drawable.hot_ac5));
                ivStep3.setImageDrawable(getResources().getDrawable(R.drawable.hot_ac5));
                ivStep4.setImageDrawable(getResources().getDrawable(R.drawable.hot_icon17));
                tvStep1.setTextColor(getResources().getColor(R.color.green_1AAD19));
                tvStep2.setTextColor(getResources().getColor(R.color.green_1AAD19));
                tvStep3.setTextColor(getResources().getColor(R.color.green_1AAD19));
                tvStep4.setTextColor(getResources().getColor(R.color.yellow_54433A));
                ivStep1.setVisibility(View.VISIBLE);
                ivStep2.setVisibility(View.VISIBLE);
                ivStep3.setVisibility(View.VISIBLE);
                ivStep4.setVisibility(View.VISIBLE);
            }else {
                tv_taskTitle.setText("????????????????????????????????????????????????2/4???");
                ivSure.setImageDrawable(getResources().getDrawable(R.drawable.hot_icon1));
                ivStep1.setImageDrawable(getResources().getDrawable(R.drawable.hot_ac5));
                ivStep2.setImageDrawable(getResources().getDrawable(R.drawable.hot_ac5));
                ivStep3.setImageDrawable(getResources().getDrawable(R.drawable.hot_icon17));
                ivStep1.setVisibility(View.VISIBLE);
                ivStep2.setVisibility(View.VISIBLE);
                ivStep3.setVisibility(View.VISIBLE);
                ivStep4.setVisibility(View.INVISIBLE);
                tvStep1.setTextColor(getResources().getColor(R.color.green_1AAD19));
                tvStep2.setTextColor(getResources().getColor(R.color.green_1AAD19));
                tvStep3.setTextColor(getResources().getColor(R.color.yellow_54433A));
                tvStep4.setTextColor(getResources().getColor(R.color.yellow_54433A));
            }
        }else if (stepIndex==6){
            tv_taskTitle.setText("????????????????????????????????????????????????4/4???");
            ivSure.setImageDrawable(getResources().getDrawable(R.drawable.hot_icon14));
            ivStep1.setImageDrawable(getResources().getDrawable(R.drawable.hot_ac5));
            ivStep2.setImageDrawable(getResources().getDrawable(R.drawable.hot_ac5));
            ivStep3.setImageDrawable(getResources().getDrawable(R.drawable.hot_ac5));
            ivStep4.setImageDrawable(getResources().getDrawable(R.drawable.hot_ac5));
            tvStep1.setTextColor(getResources().getColor(R.color.green_1AAD19));
            tvStep2.setTextColor(getResources().getColor(R.color.green_1AAD19));
            tvStep3.setTextColor(getResources().getColor(R.color.green_1AAD19));
            tvStep4.setTextColor(getResources().getColor(R.color.green_1AAD19));
            ivStep1.setVisibility(View.VISIBLE);
            ivStep2.setVisibility(View.VISIBLE);
            ivStep3.setVisibility(View.VISIBLE);
            ivStep4.setVisibility(View.VISIBLE);
        }
    }

    private SnatchDialog signRule;
    public void  initSignRuleDialog(String tips){
        signRule = new SnatchDialog(this);
        View builder = signRule.builder(R.layout.signrule_dialog_item);
        TextView tv_contents=builder.findViewById(R.id.tv_contents);
        TextView tv_title=builder.findViewById(R.id.tv_title);
        tv_title.setText("????????????");
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        if (!TextUtils.isEmpty(tips)){
            RichText.from(tips).into(tv_contents);
        }
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                signRule.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)){
            signRule.setShow();
        }
    }
   private boolean isShowAd;
   private boolean isCountStart;
    @OnClick({R.id.iv_sure, R.id.line_complaint,R.id.iv_close,R.id.iv_rules})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_sure:
                if (ClickListenName.isFastClick()){
                    if (stepIndex==1){
                        MobclickAgent.onEvent(this, "hot_start", "1");//?????????????????????????????????ID
                        showAd();
                    }else if (stepIndex==3){
                        MobclickAgent.onEvent(this, "hot_start_new", "1");//?????????????????????????????????ID
                        stepIndex=1;
                        showAd();
                        VUiKit.postDelayed(2000, () -> {
                            setStatus();
                        });
                    }else if (stepIndex==4){//?????????
                        MobclickAgent.onEvent(this, "hot_start_play", "1");//?????????????????????????????????ID
                        Intent intent = getPackageManager().getLaunchIntentForPackage(appPackage);
                        if (intent != null) {
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        long yuTimes = 15*1000;
                        isCountStart=true;
                        countDownUtilsFour.clean();
                        countDownUtilsFour.setHours(TimesUtils.getMinDiff(yuTimes), TimesUtils.getSecondDiff(yuTimes));
                    }else if (stepIndex==5||stepIndex==6){
                        MobclickAgent.onEvent(this, "hot_start_tixian", "1");//?????????????????????????????????ID
                        HotActivity.adhotJump(this,"4");
                        stepIndex=1;
                        setStatus();
                    }
                }
                break;
            case R.id.line_complaint:
                Intent intent=new Intent(AdHotActivity.this,ComplaintActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_rules:
                initSignRuleDialog(contentRule);
                break;
        }
    }
    private String appPackage;
    private  String appName;
    private int index;
    public void showAd(){
        if (isShowAd){
            index++;
            if (index>=1){
                isShowAd=false;
            }
            return;
        }
        index=0;
        GromoreAdShow.getInstance().showjiliAd(this, 2, "hot", new GromoreAdShow.OnAdShowCaback() {
            @Override
            public void onRewardedAdShow() {
                isShowAd=true;
                stepIndex=2;
                setStatus();
            }

            @Override
            public void onRewardedAdShowFail() {
                isShowAd=false;
            }

            @Override
            public void onRewardClick() {

            }

            @Override
            public void onVideoComplete() {
                isShowAd=false;
            }

            @Override
            public void setVideoCallBacks() {

            }

            @Override
            public void onRewardedAdClosed(boolean isVideoClick, boolean isCompeter) {
                isShowAd=false;
            }

            @Override
            public void onFinshTask(String appPackages, String appNames,String type) {
                appPackage=appPackages;
                appName=appNames;
                ad_type=type;
                mPresenter.gethotTask(CacheDataUtils.getInstance().getUserInfo().getId() + "", ((MyApplication) MyApplication.getInstance()).getAgentId(),appPackage,appName,type);
                stepIndex=4;
                setStatus();
            }

            @Override
            public void onNoTask() {
                stepIndex=3;
                setStatus();
                VUiKit.postDelayed(400, () -> {
                    hotTaskDialogs(1,"");
                });
            }
        });
    }
    class InnerRecevier extends BroadcastReceiver {

        final String SYSTEM_DIALOG_REASON_KEY = "reason";

        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                        if (countDownUtilsFour!=null){
                            countDownUtilsFour.clean();
                        }
                    }else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                        if (countDownUtilsFour!=null){
                            countDownUtilsFour.clean();
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (countDownUtilsFour!=null){
            countDownUtilsFour.clean();
            countDownUtilsFour=null;
        }
        if (countDownUtilsThree!=null){
            countDownUtilsThree.clean();
            countDownUtilsThree=null;
        }
        if (innerReceiver != null) {
            unregisterReceiver(innerReceiver);
            innerReceiver = null;
        }
        super.onDestroy();
    }
}