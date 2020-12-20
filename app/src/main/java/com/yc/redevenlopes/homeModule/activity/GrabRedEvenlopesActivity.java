package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.LevelDialog;
import com.yc.redevenlopes.dialog.RedDialog;
import com.yc.redevenlopes.dialog.SnatchDialog;
import com.yc.redevenlopes.homeModule.contact.GrabRedEvenlopesContact;
import com.yc.redevenlopes.homeModule.present.GrabRedEvenlopesPresenter;
import com.yc.redevenlopes.utils.CommonUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class GrabRedEvenlopesActivity extends BaseActivity<GrabRedEvenlopesPresenter> implements GrabRedEvenlopesContact.View {

    @BindView(R.id.tv_lookRh)
    TextView tvLookRh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_grab_red_evenlopes;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }


    public void showRedDialog() {
        LevelDialog redDialogs = new LevelDialog(this);
        View builder = redDialogs.builder(R.layout.level_reward_item);

        redDialogs.setOutCancle(false);
        if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
            redDialogs.setShow();
        }
    }

    //type  1 2幸运抽红包领金币  2幸运抽红包领金币翻倍  3幸运抽红包领金币不翻倍 4没有抽中
    public void showRedDialogTwo(int type) {
        SnatchDialog redDialogs = new SnatchDialog(this);
        View builder = redDialogs.builder(R.layout.level_reward_money);
        ImageView iv_top=builder.findViewById(R.id.iv_top);
        TextView tv2=builder.findViewById(R.id.tv2);
        TextView tv_noPrize=builder.findViewById(R.id.tv_noPrize);
        LinearLayout line_money=builder.findViewById(R.id.line_money);
        TextView tv_money=builder.findViewById(R.id.tv_money);
        RelativeLayout rela_one=builder.findViewById(R.id.rela_one);
        RelativeLayout rela_one_one=builder.findViewById(R.id.rela_one_one);
        TextView tv_iwantCheat=builder.findViewById(R.id.tv_iwantCheat);
        TextView tv_levelNums=builder.findViewById(R.id.tv_levelNums);
        TextView tv_sureOne=builder.findViewById(R.id.tv_sureOne);
        TextView tv_sureTwo=builder.findViewById(R.id.tv_sureTwo);
        TextView tv_sureThree=builder.findViewById(R.id.tv_sureThree);
        RelativeLayout rela_two=builder.findViewById(R.id.rela_two);

        if (type==1){
            iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain));
            tv2.setVisibility(View.VISIBLE);
            line_money.setVisibility(View.VISIBLE);
            tv_noPrize.setVisibility(View.GONE);
            rela_one.setVisibility(View.GONE);
            tv_sureTwo.setVisibility(View.GONE);
            tv_sureOne.setVisibility(View.VISIBLE);
            tv_sureThree.setVisibility(View.GONE);
        }else if (type==2){
            tv_iwantCheat.setVisibility(View.GONE);
            iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain));
            tv2.setVisibility(View.VISIBLE);
            line_money.setVisibility(View.VISIBLE);
            tv_noPrize.setVisibility(View.GONE);
            rela_one_one.setVisibility(View.VISIBLE);
            rela_one.setVisibility(View.VISIBLE);
            tv_sureTwo.setVisibility(View.GONE);
            tv_sureOne.setVisibility(View.GONE);
            tv_sureThree.setVisibility(View.VISIBLE);
        }else if (type==3){
            tv_iwantCheat.setVisibility(View.GONE);
            iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain));
            tv2.setVisibility(View.VISIBLE);
            line_money.setVisibility(View.VISIBLE);
            tv_noPrize.setVisibility(View.GONE);
            rela_one_one.setVisibility(View.VISIBLE);
            rela_one.setVisibility(View.GONE);
            tv_sureTwo.setVisibility(View.GONE);
            tv_sureOne.setVisibility(View.GONE);
            tv_sureThree.setVisibility(View.VISIBLE);
        }else if (type==4){
            iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain_no));
            iv_top.setScaleType(ImageView.ScaleType.FIT_XY);
            tv_iwantCheat.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.GONE);
            line_money.setVisibility(View.GONE);
            tv_noPrize.setVisibility(View.VISIBLE);
            rela_one_one.setVisibility(View.GONE);
            rela_one.setVisibility(View.VISIBLE);
            tv_sureTwo.setVisibility(View.VISIBLE);
            tv_sureOne.setVisibility(View.GONE);
            tv_sureThree.setVisibility(View.GONE);
        }


        if (!CommonUtils.isDestory(GrabRedEvenlopesActivity.this)) {
            redDialogs.setShow();
        }
    }

    @OnClick({R.id.iv_back, R.id.tv_lookRh, R.id.iv_lookVideo, R.id.iv_turn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_lookRh:
                showRedDialogTwo(2);
                break;
            case R.id.iv_lookVideo:
                showRedDialogTwo(3);
                break;
            case R.id.iv_turn:
                showRedDialogTwo(4);
             //   showRedDialog();
                break;
        }
    }

    public static void GrabRedJump(Context context){
        Intent intent=new Intent(context,GrabRedEvenlopesActivity.class);
        context.startActivity(intent);
    }
}