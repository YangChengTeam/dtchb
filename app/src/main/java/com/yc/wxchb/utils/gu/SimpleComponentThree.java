package com.yc.wxchb.utils.gu;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yc.wxchb.R;


public class SimpleComponentThree implements Component {
    @Override
    public View getView(LayoutInflater inflater) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.simple_componentthree, null);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

            }
        });
        return ll;
    }

    public interface SimpleOnclicklisten{
        void onclicks();
    }
    private SimpleOnclicklisten simpleOnclicklisten;
    public void setSimpleOnclicklisten(SimpleOnclicklisten simpleOnclicklisten){
        this.simpleOnclicklisten=simpleOnclicklisten;
    }

    @Override public int getAnchor() {
        return Component.ANCHOR_BOTTOM;
    }

    @Override public int getFitPosition() {
        return Component.FIT_CENTER;
    }
    @Override public int getXOffset() {
        return 15;
    }

    @Override public int getYOffset() {
        return -10;
    }
}
