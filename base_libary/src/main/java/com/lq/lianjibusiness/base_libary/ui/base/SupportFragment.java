package com.lq.lianjibusiness.base_libary.ui.base;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by ccc on 2020/9/15.
 */

public class SupportFragment extends Fragment {
    private KProgressHUD show;

    public void showWaiteDialog() {
        show = KProgressHUD.create(getActivity())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("加载中....")
                .setCancellable(true)
                .show();
    }

    public void closeWaiteDialog() {
        try {
            show.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转到其他的页面
     */
    public void ToOtherActivity(Class clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }
}
