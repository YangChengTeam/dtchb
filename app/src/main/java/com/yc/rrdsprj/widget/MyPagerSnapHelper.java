package com.yc.rrdsprj.widget;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;


import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyPagerSnapHelper extends PagerSnapHelper {
    private static final int MAX_SCROLL_ON_FLING_DURATION = 2000; // ms
    static final float MILLISECONDS_PER_INCH = 200f;
    RecyclerView mRecyclerView;

//    @Override
//    public void attachToRecyclerView(@Nullable RecyclerView recyclerView){
//        this.mRecyclerView=recyclerView;
//        Log.d("ccc", "-------------3333attachToRecyclerView: ");
//    }

    @Override
    public LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager layoutManager) {
        Log.d("ccc", "----8888-------calculateSpeedPerPixel: "+MAX_SCROLL_ON_FLING_DURATION);
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return null;
        }
        return new LinearSmoothScroller(mRecyclerView.getContext()) {
            @Override
            protected void onTargetFound(View targetView, RecyclerView.State state, Action action) {
                Log.d("ccc", "----222-------calculateSpeedPerPixel: "+MAX_SCROLL_ON_FLING_DURATION);
                int[] snapDistances = calculateDistanceToFinalSnap(mRecyclerView.getLayoutManager(),
                        targetView);
                final int dx = snapDistances[0];
                final int dy = snapDistances[1];
                final int time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)));
                if (time > 0) {
                    action.update(dx, dy, time, mDecelerateInterpolator);
                }
            }

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                Log.d("ccc", "----000-------calculateSpeedPerPixel: "+MILLISECONDS_PER_INCH);
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
            }

            @Override
            protected int calculateTimeForScrolling(int dx) {
                Log.d("ccc", "----111-------calculateSpeedPerPixel: "+MAX_SCROLL_ON_FLING_DURATION);
                return Math.min(MAX_SCROLL_ON_FLING_DURATION, super.calculateTimeForScrolling(dx));
            }
        };
    }


}
