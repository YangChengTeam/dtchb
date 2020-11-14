package com.lq.lianjibusiness.base_libary.ui.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Map;

/**
 * Created by ccc on 2020/9/15.
 */

public class SupportActivity extends AppCompatActivity {

    private KProgressHUD show;
    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        show = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中....")
                .setCancellable(true);
    }

    /**
     * 跳转到其他的页面
     *
     * map可以允许为空
     */
    public void ToOtherActivity(Class clazz, Map<String, Object> map) {
        Intent intent = new Intent(this, clazz);
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof String) {
                    intent.putExtra(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue() instanceof Integer) {
                    intent.putExtra(entry.getKey(), (Integer) entry.getValue());
                } else if (entry.getValue() instanceof Boolean) {
                    intent.putExtra(entry.getKey(), (Boolean) entry.getValue());
                } else {
                    // TODO: 2017/11/9  暂时不予处理
                }
            }
        }
        startActivity(intent);
    }
}
