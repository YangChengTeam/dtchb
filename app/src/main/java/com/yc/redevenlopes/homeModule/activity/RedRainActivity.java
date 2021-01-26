package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.RedRainContact;
import com.yc.redevenlopes.homeModule.present.RedRainPresenter;
import com.yc.redevenlopes.homeModule.widget.meteorshower.MeteorShowerSurface;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.CommonUtils;
import com.yc.redevenlopes.utils.DisplayUtil;

import butterknife.BindView;


public class RedRainActivity extends BaseActivity<RedRainPresenter> implements RedRainContact.View {


    @BindView(R.id.meteor_surface)
    MeteorShowerSurface meteorSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_red_rain;
    }

    @Override
    public void initEventAndData() {
        setTitle("红包雨");
        initRedRain();
    }
    private void initRedRain(){
        meteorSurface.post(new Runnable() {
            @Override
            public void run() {
                meteorSurface.setDuration(20*1000).setRedCount(90).start();
            }
        });

        meteorSurface.setCountOnCountListen(new MeteorShowerSurface.CountOnCountListen() {
            @Override
            public void getCount(int count) {
                   if (count==4){
                       showInsertVideo();
                   }
            }

            @Override
            public void getTimes(int time) {

            }
        });
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }


    public static void redRainJump(Context context) {
        Intent intent = new Intent(context, RedRainActivity.class);
        context.startActivity(intent);
    }


    private void loadInsertView(Runnable runnable) {
        int screenWidth = CommonUtils.getScreenWidth(this);
        int screenHeight = CommonUtils.getScreenHeight(this);
        int w = (int) (screenWidth) * 8/ 10;
        int h = w*3/2;
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        int dpw = DisplayUtil.px2dip(RedRainActivity.this, w);
        int dph = DisplayUtil.px2dip(RedRainActivity.this, h);
        adPlatformSDK.loadInsertAd(this, "chaping", dpw, dph, new AdCallback() {
            @Override
            public void onDismissed() {

            }

            @Override
            public void onNoAd(AdError adError) {
                Log.d("ccc", "-----------loadInsertView------onNoAd: " + adError.getCode() + "--" + adError.getMessage());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPresent() {

            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {
                if (runnable != null) {
                    Log.d("ccc", "-----------loadInsertView------runnable: ");
                    runnable.run();
                }
            }
        });
    }

    private void showInsertVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setAdPosition("chaping");
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        if (adPlatformSDK.showInsertAd()) {
            loadInsertView(null);
        } else {
            loadInsertView(new Runnable() {
                @Override
                public void run() {
                    adPlatformSDK.showInsertAd();
                }
            });
        }
    }
}