package com.yc.rrdsprj.beans.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fc.tjcpl.sdk.TJSDK;
import com.lq.lianjibusiness.base_libary.App.GoagalInfo;
import com.yc.rrdsprj.R;
import com.yc.rrdsprj.base.BaseActivity;
import com.yc.rrdsprj.beans.contact.EmptyContract;
import com.yc.rrdsprj.beans.present.EmptyPresenter;
import com.yc.rrdsprj.utils.CacheDataUtils;

import butterknife.BindView;

public class TiaoJinMainActivity extends BaseActivity<EmptyPresenter> implements EmptyContract.View {

    @BindView(R.id.tv_jump)
    TextView tvJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_tiao_jin_main;
    }

    public static void TiaoJinJump(Context context){
        Intent intent=new Intent(context,TiaoJinMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initEventAndData() {
        tvJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化sdk,TJSDK.init(appId,appSecret,userId)
                //appId，appKey,创建媒体后获取
                //userId：媒体app中的用户id,媒体采用自己的提现系统时，必传，支持数字、字母混合，最长64位
                //TODO Demo使用AndroidID作为userID，开发者对接时请使用自己应用的用户ID。
                TJSDK.init("2867", "a0e06ab63edf8417fab0bda499214d91", CacheDataUtils.getInstance().getUserInfo().getId() + "");
                String oid = GoagalInfo.oaid;
                Log.d("ccc", "========oid: "+oid);
                jumpSDK(oid);
            }
        });

    }

    private void jumpSDK(String oaid) {
        //进入游戏列表
        TJSDK.show(this, oaid);
        //直接进入指定id的游戏任务
        //TJSDK.showDetail(this,"",oaid);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }


    @Override
    public void onBackPressed() {
        finish();
        Process.killProcess(Process.myPid());
    }
}