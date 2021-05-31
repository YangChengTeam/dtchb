package com.yc.redkingguess.homeModule.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yc.redkingguess.R;
import com.yc.redkingguess.homeModule.widget.gu.Component;

public class SimpleComponent implements Component {
    @Override
    public View getView(LayoutInflater inflater) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.simple_component, null);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
            }
        });
        return ll;
    }

    @Override public int getAnchor() {
        return Component.ANCHOR_BOTTOM;
    }

    @Override public int getFitPosition() {
        return Component.FIT_END;
    }

    @Override public int getXOffset() {
        return 30;
    }

    @Override public int getYOffset() {
        return -40;
    }
}