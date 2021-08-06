package com.yc.qqzz.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.lq.lianjibusiness.base_libary.ui.base.SimpleActivity;
import com.yc.qqzz.R;


import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends SimpleActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rela_yinsi)
    RelativeLayout relaYinsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_about;
    }

    protected void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.transparent));
        } else if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public static void aboutJump(Context context){
         Intent intent=new Intent(context,AboutActivity.class);
         context.startActivity(intent);
    }

    @Override
    protected void initEventAndData() {
        setTranslucentStatus();

    }

    @OnClick({R.id.iv_back, R.id.rela_yinsi,R.id.rela_yonghuyinsi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rela_yinsi:
                Intent intent1 = new Intent(AboutActivity.this, WebViewActivity.class);
                intent1.putExtra("url", "http://m.k1u.com/gdgw/qqzz.html");
                intent1.putExtra("title", "用户隐私协议");
                startActivity(intent1);
                break;
            case R.id.rela_yonghuyinsi:
                Intent intent = new Intent(AboutActivity.this, WebViewActivity.class);
                intent.putExtra("url", "http://m.k1u.com/xinshen/qqzz.html");
                intent.putExtra("title", "用户协议");
                startActivity(intent);
                break;
        }
    }
}