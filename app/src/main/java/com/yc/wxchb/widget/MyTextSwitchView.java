package com.yc.wxchb.widget;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.yc.wxchb.R;
import java.util.List;

public class MyTextSwitchView extends TextSwitcher {

    private int index = 0;//textview上下滚动下标
    private Handler handler = new Handler();
    private boolean isFlipping = false; // 是否启用预警信息轮播
    private List<String> strList;
    public MyTextSwitchView(Context context) {
        super(context,null);
    }

    public MyTextSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initBanner();
    }

    public void initBanner() {
        this.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right));
        this.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_left));
        this.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getContext());
                textView.setSingleLine();
                textView.setTextSize(14);//字号
                textView.setTextColor(Color.parseColor("#a5caf6"));
                textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                textView.setSingleLine();
                textView.setGravity(Gravity.CENTER);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.CENTER;
                textView.setLayoutParams(params);
                textView.setPadding(25, 0, 25, 0);
                return textView;
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isFlipping) {
                return;
            }
            index++;
            setText(strList.get(index % strList.size()));
            if (index == strList.size()) {
                index = 0;
            }
            startFlipping();
        }
    };
    //开启信息轮播
    public void startFlipping() {
        if (strList != null && strList.size() > 1) {
            handler.removeCallbacks(runnable);
            isFlipping = true;
            handler.postDelayed(runnable, 4700);
        }
    }

    //关闭信息轮播
    public void stopFlipping() {
        if (strList != null && strList.size() > 1) {
            isFlipping = false;
            handler.removeCallbacks(runnable);
        }
    }
    public void setBanner(List<String> composeInfos) {
        if (composeInfos == null || composeInfos.size() == 0) {
            return;
        }
        this.strList = composeInfos;

        if (!isFlipping) {
           setText(composeInfos.get(index));
            startFlipping();
        }
    }
//    private String creatDes(String composeInfo) {
//        String nickname = composeInfo.getNickname();
//        String goodsName = composeInfo.getGoodsName();
//        if (nickname.length() > 5) {
//            String s1 = nickname.substring(0, 2);
//            String s2 = nickname.substring(nickname.length() - 2);
//            nickname = s1 + "***" + s2;
//        }
//        return "恭喜 " + nickname + " 兑换道具【" + goodsName + "】";
//    }

}
