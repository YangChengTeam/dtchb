package com.yc.redguess.homeModule.widget.meteorshower;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.yc.redguess.R;
import com.yc.redguess.utils.DisplayUtil;

import java.util.Random;

/**
 * 红包雨精灵
 * create by yao.cui at 2016/12/1
 */
public class MeteorSprite extends LineAnimSprite {

    private int[] mImgIds = new int[]{
        R.drawable.hongb0,
        R.drawable.hongb1,
        R.drawable.hongb2,
        R.drawable.hongb3,
        R.drawable.hongb4
    };
    private Random mRandom = new Random();

    public MeteorSprite(Context context, int pWidth, int pHeihgt) {
        super(context,pWidth,pHeihgt);
        clickable = true;

        int index = mRandom.nextInt(mImgIds.length);
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), mImgIds[index]);
        width = DisplayUtil.dip2px(context,60)+ mRandom.nextInt(DisplayUtil.dip2px(context,30));
        srcBmp = scaleBmp(bmp,width,true);
        bmp.recycle();
        height = srcBmp.getHeight();

        point = newPosition(true,0,0);
    }

    @Override
    protected void updatePosition() {
        super.updatePosition();
        //精灵滑出屏幕标记可完成，使其被回收
        if (point[0] + srcBmp.getWidth()< 0){
            isOver = true;
        }
    }

    @Override
    public void reset() {
        super.reset();
        point = newPosition(true,0,0);
        time = 0;
    }
}
