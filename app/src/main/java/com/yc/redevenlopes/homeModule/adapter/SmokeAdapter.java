package com.yc.redevenlopes.homeModule.adapter;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.homeModule.module.bean.SmokeBeans;
import com.yc.redevenlopes.homeModule.widget.Rotate3dAnimation;

import java.util.List;

public class SmokeAdapter extends BaseQuickAdapter<SmokeBeans, BaseViewHolder> {
    public SmokeAdapter( @Nullable List<SmokeBeans> data) {
        super(R.layout.smoke_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SmokeBeans item) {
        ImageView view = (ImageView) helper.getView(R.id.iv_top);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initOpenAnim(view);
            }
        });

    }

    /**
     * 卡牌文本介绍打开效果：注意旋转角度
     */
    private void initOpenAnim(View view) {
        // 计算中心点
        final float centerX = view.getWidth() / 2.0f;
        final float centerY = view.getHeight() / 2.0f;
        //从0到90度，顺时针旋转视图，此时reverse参数为true，达到90度时动画结束时视图变得不可见，
        openAnimation = new Rotate3dAnimation(0, 540, centerX, centerY, centerY, true);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new LinearInterpolator());
        openAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
               // mLogoIv.setVisibility(View.GONE);
               // mDescTv.setVisibility(View.VISIBLE);

             //   从270到360度，顺时针旋转视图，此时reverse参数为false，达到360度动画结束时视图变得可见
                Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(540, 0, centerX, centerY, centerY, false);
                rotateAnimation.setDuration(duration);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                view.startAnimation(rotateAnimation);
            }
        });
        view.startAnimation(openAnimation);
    }

    private int duration = 1200;
    private Rotate3dAnimation openAnimation;
    private Rotate3dAnimation closeAnimation;




}
