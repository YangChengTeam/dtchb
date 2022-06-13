package com.yc.rrdsprj.utils;


import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.text.TextUtils;

import com.yc.rrdsprj.R;
import com.yc.rrdsprj.application.MyApplication;


public class SoundPoolUtils {
    private static SoundPoolUtils instance;
    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID

    private SoundPool sptwo;//声明一个SoundPool
    private int musictwo;//定义一个整型用load（）；来设置suondID
    public static SoundPoolUtils getInstance() {
        if (instance == null) {
            synchronized (SoundPoolUtils.class) {
                if (instance == null) {
                    instance = new SoundPoolUtils();
                }
            }
        }
        return instance;
    }


    public void initSound(){
        if (TextUtils.isEmpty(CacheDataUtils.getInstance().getSol())){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (sp==null){
                    sp = new SoundPool.Builder()
                            .setMaxStreams(10)
                            .build();
                }
            } else {
                if (sp==null){
                    sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                }
            }
            if (music<=0){
                music= sp.load(MyApplication.getInstance().getApplicationContext(), R.raw.clickzq, 1);// 1
                sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        sp.play(music, 1, 1, 0, 0, 1);
                    }
                });
            }else {
                sp.play(music, 1, 1, 0, 0, 1);
            }
        }
    }


    public void initSoundTwo(){
        if (TextUtils.isEmpty(CacheDataUtils.getInstance().getSol())){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (sptwo==null){
                    sptwo = new SoundPool.Builder()
                            .setMaxStreams(10)
                            .build();
                }
            } else {
                if (sptwo==null){
                    sptwo = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
                }
            }
            if (musictwo<=0){
                musictwo= sptwo.load(MyApplication.getInstance().getApplicationContext(), R.raw.lotzq, 1);// 1
                sptwo.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        sptwo.play(musictwo, 0.7f, 0.7f, 0, 0, 1);
                    }
                });
            }else {
                sptwo.play(musictwo, 0.7f, 0.7f, 0, 0, 1);
            }
        }
    }

}
