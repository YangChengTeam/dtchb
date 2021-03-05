package com.yc.redguess.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.constants.Constant;
import com.yc.redguess.homeModule.contact.GuessingDetailsContact;
import com.yc.redguess.homeModule.present.GuessingDetailsPresenter;
import com.yc.redguess.utils.CacheDataUtils;
import com.yc.redguess.utils.CommonUtils;
import com.yc.redguess.utils.DisplayUtil;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;

/**
 * 竞猜详情
 */
public class GuessingDetailsActivity extends BaseActivity<GuessingDetailsPresenter> implements GuessingDetailsContact.View {

    @BindView(R.id.tv_contents)
    TextView tvContents;
    @BindView(R.id.line1)
    LinearLayout line1;
    @BindView(R.id.fl_banner)
    FrameLayout flBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_guessing_details;
    }

    @Override
    public void initEventAndData() {
        setTitle("竞猜规则");
        String contents = getIntent().getStringExtra("contents");
        if (!TextUtils.isEmpty(contents)) {
            RichText.fromHtml(contents).into(tvContents);
        } else {
            tvContents.setVisibility(View.GONE);
            line1.setVisibility(View.VISIBLE);
        }
        if (flBanner!=null) {
            loadBanner(flBanner);
        }
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void guessingDetailsJump(Context context, String contents) {
        Intent intent = new Intent(context, GuessingDetailsActivity.class);
        intent.putExtra("contents", contents);
        context.startActivity(intent);
    }

    private void loadBanner(FrameLayout fl_ad_containe) {
        int screenWidth = CommonUtils.getScreenWidth(this);
        int w = (int) (screenWidth);
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        int dpw = DisplayUtil.px2dip(GuessingDetailsActivity.this, w);
        adPlatformSDK.loadBannerAd(this, "ad_banner_guess", dpw, 70, new AdCallback() {
            @Override
            public void onDismissed() {

            }

            @Override
            public void onNoAd(AdError adError) {

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
                showBanner();
            }
        }, fl_ad_containe);
    }


    private void showBanner() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        adPlatformSDK.showBannerAd();
    }
}