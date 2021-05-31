package com.yc.redkingguess.homeModule.fragment;

import android.graphics.Rect;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.yc.redkingguess.R;
import com.yc.redkingguess.base.BaseDialogFragment;
import com.yc.redkingguess.updata.ScreenUtil;
import com.yc.redkingguess.utils.SoundPoolUtils;

import androidx.core.content.ContextCompat;

/**
 * Created by suns  on 2020/11/19 10:09.
 */
public class UserPolicyFragment extends BaseDialogFragment {


    private TextView tvKnowBtn;

    private WebView webView;
    private Rect rectF = new Rect();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_policy;
    }

    @Override
    public void initViews() {
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(false);
        String url="http://m.k1u.com/hongbao/yinsi.html";
        tvKnowBtn = rootView.findViewById(R.id.tv_know_btn);
        webView=rootView.findViewById(R.id.webView);
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setUseWideViewPort(false);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDefaultTextEncodingName("UTF-8");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.clearHistory();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                super.onProgressChanged(view, progress);
            }
        });
        webView.loadUrl(url);
        initListener();
    }




    private void initListener() {
        tvKnowBtn.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange_AB5B0F));
        tvKnowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                if (userPolicyOncliciListen!=null){
                    userPolicyOncliciListen.know();
                    dismiss();
                }
            }
        });
    }



    @Override
    public float getWidthRatio() {
        return 0.9f;
    }

    @Override
    public int getHeight() {
        return ScreenUtil.getHeight(getActivity()) * 4 / 5;
    }
    public UserPolicyOncliciListen userPolicyOncliciListen;
    public void setUserPolicyOncliciListen(UserPolicyOncliciListen userPolicyOncliciListen){
         this.userPolicyOncliciListen=userPolicyOncliciListen;
    }

    public interface UserPolicyOncliciListen{
         void know();
    }


}
