package com.yc.qqzz.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yc.qqzz.R;
import com.yc.qqzz.widget.gu.Component;


public class SimpleComponentTwo implements Component {
    @Override
    public View getView(LayoutInflater inflater) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.simple_componenttwo, null);

        ImageView iv_red=ll.findViewById(R.id.iv_red);
        iv_red.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (simpleOnclicklisten!=null){
                    simpleOnclicklisten.onclicks();
                }
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
        return Component.FIT_END;
    }

    @Override public int getXOffset() {
        return 30;
    }

    @Override public int getYOffset() {
        return -40;
    }
}
