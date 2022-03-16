package com.yc.wxchb.beans.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.lq.lianjibusiness.base_libary.App.GoagalInfo;
import com.lq.lianjibusiness.base_libary.http.ResultRefreshSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.SimpleActivity;
import com.lq.lianjibusiness.base_libary.utils.DeviceUtils;
import com.lq.lianjibusiness.base_libary.utils.PhoneCommonUtils;
import com.qq.e.comm.util.AdError;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.yc.wxchb.R;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.beans.module.HomeApiModule;
import com.yc.wxchb.beans.module.beans.AdCodeBeans;
import com.yc.wxchb.beans.module.beans.AdInfoBeans;
import com.yc.wxchb.beans.module.beans.AdTypeBeans;
import com.yc.wxchb.beans.module.beans.UpgradeInfozq;
import com.yc.wxchb.constants.Constant;
import com.yc.wxchb.dialog.UpdateDialog;
import com.yc.wxchb.dialog.YonghuxieyiDialog;
import com.yc.wxchb.updata.DownloadManager;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.MacUtils;
import com.yc.wxchb.utils.PermissionHelper;
import com.yc.wxchb.utils.UpDataVersion;
import com.yc.wxchb.utils.ad.InitAdCallback;
import com.yc.wxchb.utils.ad.TTAdManagerHolder;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by suns  on 2020/11/18 17:48.
 */
public class SplashActivity extends SimpleActivity {

    @BindView(R.id.frame_item)
    FrameLayout frameItem;
    LinearLayout lineView;

    private PermissionHelper mPermissionHelper;
    public CompositeDisposable mDisposables;
    public HomeApiModule apis;

    private boolean isFirst;
    private String weixinloginType;
    @Override
    public int getLayout() {
        return R.layout.activity_splashzq;
    }

    @Override
    protected void initEventAndData() {
        lineView = findViewById(R.id.line_view);
        apis = new HomeApiModule();
        mDisposables = new CompositeDisposable();
        initPermissions();
    }

    private void initPermissions() {
        mPermissionHelper = new PermissionHelper();
        mPermissionHelper.checkAndRequestPermission(this, new PermissionHelper.OnRequestPermissionsCallback() {
            @Override
            public void onRequestPermissionSuccess() {
                DownloadManager.init(new WeakReference<>(SplashActivity.this));
                s();
            }

            @Override
            public void onRequestPermissionError() {
                s();
            }
        });
    }


    public void  s(){
        UMConfigure.init(this, "622ffedd317aa87760998865", ((MyApplication) MyApplication.getInstance()).getAgentId(), UMConfigure.DEVICE_TYPE_PHONE, "");
        // 选用合适的页面采集模式，这里以LEGACY_MANUAL为例
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.LEGACY_AUTO);
        // 支持在子进程中统计自定义事件
        // UMConfigure.setProcessEvent(true);
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMConfigure.setLogEnabled(true);
        UMShareAPI.get(getApplicationContext()).setShareConfig(config);
        // 微信设置
        PlatformConfig.setWXFileProvider("com.yc.wxchb.service.FileProvider");
        PlatformConfig.setWeixin("wx672845b0f5a6c744","88b3b6761f751d738bc71252bdca8486");
        getAdCode();
    }

    public void getAdCode(){
        String oid = GoagalInfo.oaid;
        String macAddress = MacUtils.getMacAddress();
        String imei = DeviceUtils.getImei();
        String imie2 = PhoneCommonUtils.getIMEI2();
        if (TextUtils.isEmpty(imie2)){
            imie2="";
        }
        mDisposables.add(apis.getAdCode(imei,oid,macAddress,imie2).compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResultRefreshSubscriber<AdCodeBeans>() {
                    @Override
                    public void onAnalysisNext(AdCodeBeans data) {
                        if (data!=null){
                            Constant.DIQU_PIBI=data.getIs_pb();
                            if (data.getAgent_login()!=null){
                                String share_img = data.getAgent_login().getShare_img();
                                String share_url = data.getAgent_login().getShare_url();
                              /*  Constant.video_cash = data.getAgent_login().getVideo_cash();
                                Constant.hb_ad_type = data.getAgent_login().getHb_ad_type();
                                Constant.kiaping=data.getAgent_login().getKaiping();
                                Constant.ad_follow = data.getAgent_login().getAd_follow();*/

                                if (!TextUtils.isEmpty(share_url)){
                                    Constant.SHAREURL=share_url ;
                                }
                                if (!TextUtils.isEmpty(share_img)){
                                    Constant.SHAREIMG=share_img ;
                                }
                               /* if (data.getAgent_login().getCash_status()==0){
                                    Constant.ISCASH="1" ;
                                }else {
                                    Constant.ISCASH="2" ;
                                }*/
                            }

                            List<AdCodeBeans.AdReportBean> ad_report = data.getAd_report();
                            Constant.ISJLYQ="1";
                            if (ad_report!=null&&ad_report.size()>0){
                                AdCodeBeans.AdReportBean adReportBean = ad_report.get(0);
                                if (adReportBean!=null){
                                    int state = adReportBean.getState();
                                    if (state==1){
                                        String agentId = ((MyApplication) MyApplication.getInstance()).getAgentId();
                                        String agent_ids = adReportBean.getAgent_ids();
                                        if (!TextUtils.isEmpty(agent_ids)){
                                            if (agent_ids.contains(",")){
                                                String[] split = agent_ids.split(",");
                                                for (int i = 0; i < split.length; i++) {
                                                    if (agentId.equals(split[i])){
                                                        Constant.ISJLYQ="2";
                                                    }
                                                }
                                            }else {
                                                if (agentId.equals(agent_ids)){
                                                    Constant.ISJLYQ="2";
                                                }
                                            }
                                        }
                                    }
                                }
                            }


                            List<AdInfoBeans> ad_csj = data.getAd_csj();
                            List<AdInfoBeans> ad_tx = data.getAd_tx();
                            List<AdInfoBeans> ad_ks = data.getAd_ks();
                            if (ad_csj!=null){
                                for (int i = 0; i < ad_csj.size(); i++) {
                                    if (ad_csj!=null){
                                        String ad_position = ad_csj.get(i).getPosition();
                                        if ("ad_jili".equals(ad_position)){
                                            Constant.RVIDEO=ad_csj.get(i).getCode();
                                        }
                                        if ("ad_banner".equals(ad_position)){
                                            Constant.BANNER=ad_csj.get(i).getCode();
                                        }
                                        if ("ad_express".equals(ad_position)){
                                            Constant.EXPRESS=ad_csj.get(i).getCode();
                                        }
                                        if ("ad_insert".equals(ad_position)){
                                            Constant.INSTER=ad_csj.get(i).getCode();
                                        }
                                        if ("ad_kaiping".equals(ad_position)){
                                            Constant.SPLASH=ad_csj.get(i).getCode();
                                        }
                                    }
                                }
                            }

                            if (ad_tx!=null){
                                for (int i = 0; i < ad_tx.size(); i++) {
                                    if (ad_tx!=null){
                                        String ad_position = ad_tx.get(i).getPosition();
                                        if ("ad_jili".equals(ad_position)){
                                            Constant.TXRVIDEO=ad_tx.get(i).getCode();
                                        }
                                        if ("ad_express".equals(ad_position)){
                                            Constant.TXEXPRESS=ad_tx.get(i).getCode();
                                        }
                                        if ("ad_insert".equals(ad_position)){
                                            Constant.TXINSTER=ad_tx.get(i).getCode();
                                        }
                                    }
                                }
                            }
                            if (ad_ks!=null){
                                for (int i = 0; i < ad_ks.size(); i++) {
                                    if (ad_ks!=null){
                                        String ad_position = ad_ks.get(i).getPosition();
                                        if ("ad_jili".equals(ad_position)){
                                            Constant.KSRVIDEO=ad_ks.get(i).getCode();
                                        }
                                        if ("ad_insert".equals(ad_position)){
                                            Constant.KSINSTER=ad_ks.get(i).getCode();
                                        }
                                    }
                                }
                            }

                        }

                        List<AdCodeBeans.AdGromoreBean> ad_gromore = data.getAd_gromore();
                        if (ad_gromore!=null){
                            for (int i = 0; i < ad_gromore.size(); i++) {
                                String ad_position = ad_gromore.get(i).getPosition();
                                if ("ad_jili".equals(ad_position)){
                                    Constant.GROMOREJILI=ad_gromore.get(i).getCode();
                                    Constant.GROMOREJILIFIRSTRED=ad_gromore.get(i).getHigh_code();
                                }
                            }
                            if (!TextUtils.isEmpty(data.getServer_ip())) {
                                Constant.IPCODE=data.getServer_ip();
                            }
                        }
                        if (data!=null&&data.getAd_config()!=null){
                            AdCodeBeans.AdConfigBean ad_config = data.getAd_config();
                            if (ad_config.getJili()!=null){
                                List<AdTypeBeans> typeList=new ArrayList<>();
                                AdCodeBeans.AdConfigBean.JiliBean.CsjBean csj = ad_config.getJili().getCsj();
                                AdCodeBeans.AdConfigBean.JiliBean.TxBean tx = ad_config.getJili().getTx();
                                AdCodeBeans.AdConfigBean.JiliBean.KsBean ks = ad_config.getJili().getKs();
                                if (csj!=null){
                                    AdTypeBeans beans=new AdTypeBeans();
                                    if (!TextUtils.isEmpty(csj.getNum())&&!TextUtils.isEmpty(csj.getSort())){
                                        int csjNus=Integer.parseInt(csj.getNum());
                                        int csjSort=Integer.parseInt(csj.getSort());
                                        beans.setNums(csjNus);
                                        beans.setSort(csjSort);
                                        beans.setType("1");
                                        if (csjSort!=0&&csjNus>0){
                                            typeList.add(beans);
                                        }
                                    }
                                }
                                if (tx!=null){
                                    AdTypeBeans beans=new AdTypeBeans();
                                    if (!TextUtils.isEmpty(tx.getNum())&&!TextUtils.isEmpty(tx.getSort())){
                                        int ttNus=Integer.parseInt(tx.getNum());
                                        int ttSort=Integer.parseInt(tx.getSort());
                                        beans.setNums(ttNus);
                                        beans.setSort(ttSort);
                                        beans.setType("2");
                                        if (ttSort!=0&&ttNus>0){
                                            typeList.add(beans);
                                        }
                                    }
                                }
                                if (ks!=null){
                                    AdTypeBeans beans=new AdTypeBeans();
                                    if (!TextUtils.isEmpty(ks.getNum())&&!TextUtils.isEmpty(ks.getSort())){
                                        int  ksNus=Integer.parseInt(ks.getNum());
                                        int  ksSort=Integer.parseInt(ks.getSort());
                                        beans.setNums(ksNus);
                                        beans.setSort(ksSort);
                                        beans.setType("3");
                                        if (ksNus!=0&&ksNus>0){
                                            typeList.add(beans);
                                        }
                                    }
                                }
                                if (typeList.size()>0){
                                    List<String> adList=new ArrayList<>();
                                    Collections.sort(typeList);
                                    for (int i = 0; i < typeList.size(); i++) {
                                        AdTypeBeans beans = typeList.get(i);
                                        int nums = beans.getNums();
                                        for (int j = 0; j < nums; j++) {
                                            adList.add(beans.getType());
                                        }
                                    }
                                    CacheDataUtils.getInstance().setAdType(adList);
                                }
                            }


                            if (ad_config.getChaping()!=null){
                                List<AdTypeBeans> insetList=new ArrayList<>();
                                AdCodeBeans.AdConfigBean.JiliBean.CsjBean csj = ad_config.getChaping().getCsj();
                                AdCodeBeans.AdConfigBean.JiliBean.TxBean tx = ad_config.getChaping().getTx();
                                AdCodeBeans.AdConfigBean.JiliBean.KsBean ks = ad_config.getChaping().getKs();
                                if (csj!=null){
                                    AdTypeBeans beans=new AdTypeBeans();
                                    if (!TextUtils.isEmpty(csj.getNum())&&!TextUtils.isEmpty(csj.getSort())){
                                        int csjNus=Integer.parseInt(csj.getNum());
                                        int csjSort=Integer.parseInt(csj.getSort());
                                        beans.setNums(csjNus);
                                        beans.setSort(csjSort);
                                        beans.setType("1");
                                        if (csjSort!=0&&csjNus>0){
                                            insetList.add(beans);
                                        }
                                    }
                                }
                                if (tx!=null){
                                    AdTypeBeans beans=new AdTypeBeans();
                                    if (!TextUtils.isEmpty(tx.getNum())&&!TextUtils.isEmpty(tx.getSort())){
                                        int ttNus=Integer.parseInt(tx.getNum());
                                        int ttSort=Integer.parseInt(tx.getSort());
                                        beans.setNums(ttNus);
                                        beans.setSort(ttSort);
                                        beans.setType("2");
                                        if (ttSort!=0&&ttNus>0){
                                            insetList.add(beans);
                                        }
                                    }
                                }
                                if (ks!=null){
                                    AdTypeBeans beans=new AdTypeBeans();
                                    if (!TextUtils.isEmpty(ks.getNum())&&!TextUtils.isEmpty(ks.getSort())){
                                        int  ksNus=Integer.parseInt(ks.getNum());
                                        int  ksSort=Integer.parseInt(ks.getSort());
                                        beans.setNums(ksNus);
                                        beans.setSort(ksSort);
                                        beans.setType("3");
                                        if (ksNus!=0&&ksNus>0){
                                            insetList.add(beans);
                                        }
                                    }
                                }
                                if (insetList.size()>0){
                                    List<String> adList=new ArrayList<>();
                                    Collections.sort(insetList);
                                    for (int i = 0; i < insetList.size(); i++) {
                                        AdTypeBeans beans = insetList.get(i);
                                        int nums = beans.getNums();
                                        for (int j = 0; j < nums; j++) {
                                            Log.d("ccc", "-------插屏--onAnalysisNext: "+beans.getType());
                                            adList.add(beans.getType());
                                        }
                                    }
                                    CacheDataUtils.getInstance().setAdInsetType(adList);
                                }
                            }
                        }

                        if (data!=null&&data.getDownload_ad()!=null){
                            AdCodeBeans.AdConfigBean down_config = data.getDownload_ad();
                            if (down_config.getJili()!=null){
                                List<AdTypeBeans> typeLists=new ArrayList<>();
                                AdCodeBeans.AdConfigBean.JiliBean.CsjBean csj = down_config.getJili().getCsj();
                                AdCodeBeans.AdConfigBean.JiliBean.TxBean tx = down_config.getJili().getTx();
                                AdCodeBeans.AdConfigBean.JiliBean.KsBean ks = down_config.getJili().getKs();
                                if (csj!=null){
                                    if (!TextUtils.isEmpty(csj.getAd_code())){
                                        Constant.HOTCSJRVIDEO = csj.getAd_code();
                                    }
                                    AdTypeBeans beans=new AdTypeBeans();
                                    if (!TextUtils.isEmpty(csj.getNum())&&!TextUtils.isEmpty(csj.getSort())){
                                        int csjNus=Integer.parseInt(csj.getNum());
                                        int csjSort=Integer.parseInt(csj.getSort());
                                        beans.setNums(csjNus);
                                        beans.setSort(csjSort);
                                        beans.setType("1");
                                        if (csjSort!=0&&csjNus>0){
                                            typeLists.add(beans);
                                        }
                                    }
                                }
                                if (tx!=null){
                                    if (!TextUtils.isEmpty(tx.getAd_code())){
                                        Constant.HOTXJRVIDEO = tx.getAd_code();
                                    }
                                    AdTypeBeans beans=new AdTypeBeans();
                                    if (!TextUtils.isEmpty(tx.getNum())&&!TextUtils.isEmpty(tx.getSort())){
                                        int ttNus=Integer.parseInt(tx.getNum());
                                        int ttSort=Integer.parseInt(tx.getSort());
                                        beans.setNums(ttNus);
                                        beans.setSort(ttSort);
                                        beans.setType("2");
                                        if (ttSort!=0&&ttNus>0){
                                            typeLists.add(beans);
                                        }
                                    }
                                }
                                if (ks!=null){
                                    if (!TextUtils.isEmpty(ks.getAd_code())){
                                        Constant.HOKSJRVIDEO = ks.getAd_code();
                                    }
                                    AdTypeBeans beans=new AdTypeBeans();
                                    if (!TextUtils.isEmpty(ks.getNum())&&!TextUtils.isEmpty(ks.getSort())){
                                        int  ksNus=Integer.parseInt(ks.getNum());
                                        int  ksSort=Integer.parseInt(ks.getSort());
                                        beans.setNums(ksNus);
                                        beans.setSort(ksSort);
                                        beans.setType("3");
                                        if (ksNus!=0&&ksNus>0){
                                            typeLists.add(beans);
                                        }
                                    }
                                }
                                if (typeLists.size()>0){
                                    List<String> adLists=new ArrayList<>();
                                    Collections.sort(typeLists);
                                    for (int i = 0; i < typeLists.size(); i++) {
                                        AdTypeBeans beans = typeLists.get(i);
                                        int nums = beans.getNums();
                                        for (int j = 0; j < nums; j++) {
                                            Log.d("ccc", "---------onAnalysisNext: "+beans.getType());
                                            adLists.add(beans.getType());
                                        }
                                    }
                                    CacheDataUtils.getInstance().setDownAdType(adLists);
                                }
                            }
                        }

                        initAdCode();
                    }

                    @Override
                    public void errorState(String message, String state) {
                        initAdCode();
                    }
                }));
    }

    private void initAdCode(){
        TTAdManagerHolder.init(SplashActivity.this, "5273285", Constant.APPNAME, false, new InitAdCallback() {
            @Override
            public void onSuccess() {
                initVersion();
            }

            @Override
            public void onFailure(AdError adError) {
                initAdCodeTwo();
            }
        });

    }

    private void initAdCodeTwo(){
        TTAdManagerHolder.init(SplashActivity.this, "5273285", Constant.APPNAME, false, new InitAdCallback() {
            @Override
            public void onSuccess() {
                initVersion();
            }

            @Override
            public void onFailure(AdError adError) {
                initVersion();
            }
        });
    }

    private YonghuxieyiDialog agree_dialog;
    private void showAgreementDialog(){
        agree_dialog=new YonghuxieyiDialog(this);
        View view = agree_dialog.builder(R.layout.agreement_dialog);
        TextView tv_agree=view.findViewById(R.id.tv_sure);
        TextView tv_cancle=view.findViewById(R.id.tv_cancle);
        TextView tv_agreeContents=view.findViewById(R.id.tv_contents);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        String str = "欢迎使用答题拆红包！我们非常重视您的隐私和个人信息保护，在您使用答题拆红包，请认真阅读《隐私协议》和《用户协议》,您同意并接受全部条款后方可使用答题拆红包。";
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(str);
        final int start = str.indexOf("《");//第一个出现的位置
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent1 = new Intent(SplashActivity.this, WebViewActivity.class);
                intent1.putExtra("url", "http://m.x6h.com/gdgw/dtchb.html");
                intent1.putExtra("title", "隐私协议");
                startActivity(intent1);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.A1_4EB1FF));       //设置文件颜色
                // 去掉下划线
                ds.setUnderlineText(false);
            }

        }, start, start +6, 0);

        final int end = str.lastIndexOf("《");//最后一个出现的位置
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(SplashActivity.this, WebViewActivity.class);
                intent.putExtra("url", "http://m.x6h.com/xinshen/dtchb.html");
                intent.putExtra("title", "用户协议");
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.A1_4EB1FF));       //设置文件颜色
                // 去掉下划线
                ds.setUnderlineText(false);
            }

        }, end, end + 6, 0);


        tv_agreeContents.setMovementMethod(LinkMovementMethod.getInstance());
        tv_agreeContents.setText(ssb, TextView.BufferType.SPANNABLE);
        tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheDataUtils.getInstance().setAgreement();
                initPermissions();
                agree_dialog.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(SplashActivity.this)) {
            agree_dialog.setShow();
        }
    }


    private UpdateDialog dialog;
    private void  initVersion(){
        initData();
       /* mDisposables.add(apis.upVersion(((MyApplication) MyApplication.getInstance()).getAgentId()).compose(RxUtil.rxSchedulerHelper())
                .subscribeWith(new ResultRefreshSubscriber<UpDataVersion>() {
                    @Override
                    public void onAnalysisNext(UpDataVersion data) {
                        UpgradeInfozq upgradeInfo = new UpgradeInfozq();
                        upgradeInfo.setDesc(data.getUpdate_content());
                        upgradeInfo.setDownUrl(data.getDownload_url());
                        upgradeInfo.setVersion(data.getVersion_name());
                        upgradeInfo.setVersionCode(data.getVersion_code());
                        upgradeInfo.setForce_update(data.getForce_update());
                        try {
                            PackageInfo info = getPackageManager().getPackageInfo(SplashActivity.this.getPackageName(), PackageManager.GET_ACTIVITIES);
                            if (upgradeInfo != null && upgradeInfo.getVersionCode() > info.versionCode) {
                                if (!TextUtils.isEmpty(data.getDownload_url())) {
                                    dialog = new UpdateDialog(SplashActivity.this);
                                    dialog.setInfo(upgradeInfo);
                                    dialog.show();
                                }else {
                                    initData();
                                }
                            }else {
                                initData();
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            initData();
                        }
                    }

                    @Override
                    public void errorState(String message, String state) {
                        initData();
                    }
                }));*/
    }


    public void mainJump(){
        Intent intent=new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        if (isFirst==true){
            if (apis == null) {
                apis = new HomeApiModule();
            }

            if (mDisposables == null) {
                mDisposables = new CompositeDisposable();
            }
            weixinLogin();
        }
    }
    /**
     * 检查包是否存在
     * @param packname
     * @return
     */
    private boolean isInstalled(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }
    private ValueAnimator objectAnimator;

    private void initData() {
        if (!isFirst) {
            isFirst = true;

            if (apis == null) {
                apis = new HomeApiModule();
            }

            if (mDisposables == null) {
                mDisposables = new CompositeDisposable();
            }
            weixinLogin();
        }
    }

    //微信登录
    private void weixinLogin(){
        if (CacheDataUtils.getInstance().isLogin()){
            if (Constant.kiaping==0){
                toMain();
            }else {
                showSplashAd();
            }
        }else {
            toLogin();
        }
    }
    private void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        if (dialog!=null){
            dialog.dismiss();
        }
        super.onDestroy();
        if (objectAnimator != null) {
            objectAnimator.cancel();
            objectAnimator = null;
        }
    }

    /**
     * 加载开屏广告
     */
    private void showSplashAd() {
        //step3:创建开屏广告请求参数AdSlot,具体参数含义参考文档
        AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(Constant.SPLASH)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .build();
        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        TTAdManagerHolder.get().createAdNative(this).loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            public void onError(int code, String message) {
                toMain();
            }

            @Override
            public void onTimeout() {
                toMain();
            }

            @Override
            public void onSplashAdLoad(TTSplashAd ad) {
                if (ad == null) {
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();

                ViewGroup viewGroup;
                if (frameItem != null) {
                    viewGroup = frameItem;
                } else {
                    viewGroup = getWindow().getDecorView().findViewById(android.R.id.content);
                }

                if (viewGroup != null) {
                    viewGroup.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕宽
                    viewGroup.addView(view);
                }
                //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                //ad.setNotAllowSdkCountdown();

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {

                    }

                    @Override
                    public void onAdShow(View view, int type) {

                    }

                    @Override
                    public void onAdSkip() {
                        toMain();
                    }

                    @Override
                    public void onAdTimeOver() {
                        toMain();
                    }
                });
            }
        }, 3000);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
