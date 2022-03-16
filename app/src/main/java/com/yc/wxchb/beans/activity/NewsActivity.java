package com.yc.wxchb.beans.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bytedance.sdk.dp.DPWidgetNewsParams;
import com.bytedance.sdk.dp.IDPNativeData;
import com.bytedance.sdk.dp.IDPNewsListener;
import com.bytedance.sdk.dp.IDPWidget;
import com.yc.wxchb.R;
import com.yc.wxchb.base.BaseActivity;
import com.yc.wxchb.beans.contact.EmptyContract;
import com.yc.wxchb.beans.present.EmptyPresenter;
import com.yc.wxchb.utils.video.DPHolder;

import java.util.List;
import java.util.Map;

import butterknife.OnClick;

public class NewsActivity extends BaseActivity<EmptyPresenter> implements EmptyContract.View {
    private IDPWidget mIDPWidgetnews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    public FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    public int getLayout() {
        return R.layout.activity_news;
    }

    @Override
    public void initEventAndData() {
        setFullScreen();
        initNewsWidget();
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    private void initNewsWidget() {
        mIDPWidgetnews = DPHolder.getInstance().buildNewsTabsWidget(DPWidgetNewsParams.obtain()
                .listener(new IDPNewsListener() {
                    @Override
                    public void onDPRefreshFinish() {
                        Log.d("ccc", "--------新闻-----onDPRefreshFinish: ");
                    }

                    @Override
                    public void onDPNewsItemClick(Map<String, Object> map) {
                        Log.d("ccc", "--------新闻-----onDPNewsItemClick: ");
                    }

                    @Override
                    public void onDPVideoPlay(Map<String, Object> map) {
                        Log.d("ccc", "--------新闻-----onDPVideoPlay: ");
                    }

                    @Override
                    public void onDPVideoPause(Map<String, Object> map) {
                        Log.d("ccc", "--------新闻-----onDPVideoPause: ");
                    }

                    @Override
                    public void onDPVideoContinue(Map<String, Object> map) {
                        Log.d("ccc", "--------新闻-----onDPVideoContinue: ");
                    }

                    @Override
                    public void onDPVideoOver(Map<String, Object> map) {
                        Log.d("ccc", "--------新闻-----onDPVideoOver: ");
                    }

                    @Override
                    public void onDPNewsDetailEnter(Map<String, Object> map) {
                        Log.d("ccc", "--------新闻-----onDPNewsDetailEnter: ");
                    }

                    @Override
                    public void onDPNewsDetailExit(Map<String, Object> map) {
                        Log.d("ccc", "--------新闻-----onDPNewsDetailExit: ");
                    }

                    @Override
                    public void onDPRequestStart(Map<String, Object> map) {
                        Log.d("ccc", "--------新闻-----onDPRequestStart: ");
                    }

                    @Override
                    public void onDPRequestSuccess(List<Map<String, Object>> list) {
                        Log.d("ccc", "--------新闻-----onDPRequestSuccess: ");
                    }

                    @Override
                    public void onDPRequestFail(int code, String msg, Map<String, Object> map) {
                        Log.d("ccc", "--------新闻-----onDPRequestFail: ");
                    }

                    @Override
                    public void onDPNewsLike(Map<String, Object> map, IDPNativeData data) {
                        Log.d("ccc", "--------新闻-----onDPNewsLike: ");
                        if (data == null) {
                            return;
                        }

                    }

                    @Override
                    public void onDPNewsFavor(Map<String, Object> map, IDPNativeData data) {
                        Log.d("ccc", "--------新闻-----onDPNewsFavor: ");
                        if (data == null) {
                            return;
                        }

                    }
                }));
        supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.fl_containss, mIDPWidgetnews.getFragment()).commit();
    }

    private void initKsNewsWidget() {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("android:support:fragments", null);
    }

    @Override
    public void onBackPressed() {
        if (mIDPWidgetnews != null && !mIDPWidgetnews.canBackPress()) {
            return;
        }
        super.onBackPressed();
    }

    @OnClick({R.id.iv_close, R.id.line_wall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.line_wall:
                RedWallActivity.redWallJump(NewsActivity.this);
                break;
        }
    }
}