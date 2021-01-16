package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.RedRainContact;
import com.yc.redevenlopes.homeModule.present.RedRainPresenter;
import com.yc.redevenlopes.homeModule.widget.RedPacket;
import com.yc.redevenlopes.homeModule.widget.RedPacketTest;

import butterknife.BindView;
import butterknife.OnClick;

public class RedRainActivity extends BaseActivity<RedRainPresenter> implements RedRainContact.View {

    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.tv_stop)
    TextView tvStop;
    @BindView(R.id.red_packets_view1)
    RedPacketTest redPacketsView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_red_rain;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.tv_start, R.id.tv_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                startRedRain();
                break;
            case R.id.tv_stop:
                stopRedRain();
                break;
        }
    }

    /**
     * 开始下红包雨
     */
    private void startRedRain() {
        redPacketsView1.startRain();
        Log.d("ccc", "-------startRedRain: ");
        redPacketsView1.setOnRedPacketClickListener(new RedPacketTest.OnRedPacketClickListener() {
            @Override
            public void onRedPacketClickListener(RedPacket redPacket) {
                redPacketsView1.pauseRain();
//                ab.setCancelable(false);
//                ab.setTitle("红包提醒");
//                ab.setNegativeButton("继续抢红包", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        redPacketsView1.restartRain();
//                    }
//                });

//                if (redPacket.isRealRed) {
//                    ab.setMessage("恭喜你，抢到了" + redPacket.money + "元！");
//                    totalmoney += redPacket.money;
//                    money.setText("中奖金额: " + totalmoney);
//                } else {
//                    ab.setMessage("很遗憾，下次继续努力！");
//                }
                redPacketsView1.post(new Runnable() {
                    @Override
                    public void run() {
                       // ab.show();
                    }
                });
            }
        });
    }

    /**
     * 停止下红包雨
     */
    private void stopRedRain() {
        redPacketsView1.stopRainNow();
    }

    public static void redRainJump(Context context){
        Intent intent=new Intent(context,RedRainActivity.class);
        context.startActivity(intent);
    }

}