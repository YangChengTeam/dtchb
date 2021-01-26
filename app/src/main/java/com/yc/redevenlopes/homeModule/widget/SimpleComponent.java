package com.yc.redevenlopes.homeModule.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.binioter.guideview.Component;
import com.yc.redevenlopes.R;

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
        return 0;
    }

    @Override public int getYOffset() {
        return 10;
    }
}
