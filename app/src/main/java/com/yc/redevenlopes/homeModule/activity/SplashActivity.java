package com.yc.redevenlopes.homeModule.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lq.lianjibusiness.base_libary.ui.base.SimpleActivity;
import com.yc.redevenlopes.R;

import butterknife.BindView;

/**
 * Created by suns  on 2020/11/18 17:48.
 */
public class SplashActivity extends SimpleActivity {


    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_progress)
    TextView tvProgress;


    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initEventAndData() {
        initData();
    }

    private void initData() {

        ValueAnimator objectAnimator = ObjectAnimator.ofInt(1, 100);
        objectAnimator.addUpdateListener(animation -> {

            int animatedFraction = (int) animation.getAnimatedValue();
            progressbar.setProgress(animatedFraction);
            tvProgress.setText(String.format(getString(R.string.percent), animatedFraction));
//            Log.e(TAG, "onAnimationUpdate: " + animatedFraction);
            if (animatedFraction==100){
                toMain();
            }

        });
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new DecelerateInterpolator());

        objectAnimator.start();

    }

    private void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
