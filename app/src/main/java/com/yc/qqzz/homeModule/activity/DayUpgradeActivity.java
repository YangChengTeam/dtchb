package com.yc.qqzz.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseActivity;
import com.yc.qqzz.dialog.BottomListDialog;
import com.yc.qqzz.dialog.SignDialog;
import com.yc.qqzz.dialog.SnatchDialog;
import com.yc.qqzz.homeModule.adapter.UpgradeTaskitemAdapter;
import com.yc.qqzz.homeModule.contact.DayUpgradeContract;
import com.yc.qqzz.homeModule.present.DayUpgradePresenter;
import com.yc.qqzz.widget.MyTextSwitchView;
import com.yc.qqzz.widget.NineLuckPan;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DayUpgradeActivity extends BaseActivity<DayUpgradePresenter> implements DayUpgradeContract.View {
    @BindView(R.id.textswitchView)
    MyTextSwitchView textswitchView;
    @BindView(R.id.nineluckpan)
    NineLuckPan nineluckpan;
    @BindView(R.id.line_memberss)
    LinearLayout lineMemberss;
    @BindView(R.id.iv_ac)
    ImageView ivAc;
    private NineLuckPan nineLuckPan;
    private List<String> strImg;
    private UpgradeTaskitemAdapter upgradeTaskitemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_day_upgrade2;
    }

    @Override
    public void initEventAndData() {
        strImg=new ArrayList<>();
        strImg.add("哈哈哈哈哈哈哈哈哈");
        strImg.add("凡人修仙传哈哈哈");
        strImg.add("斗罗大陆");
        strImg.add("恭喜中奖");
        textswitchView.setBanner(strImg);
        nineLuckPan = findViewById(R.id.nineluckpan);
        nineLuckPan.setPosition(5);
        nineLuckPan.setmLuckNum(6);
        nineLuckPan.setOnLuckPanListener(new NineLuckPan.OnLuckPanListener() {
            @Override
            public void onLuckStart() {

            }

            @Override
            public void onAnimEnd(int position, String msg) {

            }
        });
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void DayUpgradeActivityJump(Context context) {
        Intent intent = new Intent(context, DayUpgradeActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.line_answer, R.id.line_withdraw, R.id.line_snatchTreasure,R.id.line_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_answer:
                break;
            case R.id.line_withdraw:
                break;
            case R.id.line_snatchTreasure:
                break;
            case R.id.line_start:
                nineLuckPan.startAnim();
                break;
        }
    }
    private SnatchDialog upgradeDialog;
    private SignDialog withDrawDialog;
    private BottomListDialog upgradeTaskDialog;
    public void upgradeDialog() {
        upgradeDialog = new SnatchDialog(this);
        View builder = upgradeDialog.builder(R.layout.upgrade_item);
         ImageView iv_close=builder.findViewById(R.id.iv_close);
         iv_close.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 upgradeDialog.setShow();
             }
         });
        upgradeDialog.setShow();
    }
    public void withDrawDialog() {
        withDrawDialog = new SignDialog(this);
        View builder = withDrawDialog.builder(R.layout.withdraw_item_dialog);
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawDialog.setShow();
            }
        });
        withDrawDialog.setShow();
    }

    public void upgradeTaskDialog() {
        upgradeTaskDialog = new BottomListDialog(this);
        View builder = withDrawDialog.builder(R.layout.upgrade_task_dialog);
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        TextView tv_taskNums=builder.findViewById(R.id.tv_taskNums);
        RecyclerView recyclerView=builder.findViewById(R.id.recyclerView);
        upgradeTaskitemAdapter=new UpgradeTaskitemAdapter(null);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DayUpgradeActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(upgradeTaskitemAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);


        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawDialog.setShow();
            }
        });
        withDrawDialog.setShow();
    }
}