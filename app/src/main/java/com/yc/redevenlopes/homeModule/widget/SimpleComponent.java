package com.yc.redevenlopes.homeModule.widget;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.homeModule.widget.gu.Component;

public class SimpleComponent implements Component {
    @Override
    public View getView(LayoutInflater inflater) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.simple_component, null);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Log.d("ccc", "------ll--ss---------onClick: ");
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
