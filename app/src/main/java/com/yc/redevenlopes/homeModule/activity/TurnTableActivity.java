package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.RedDialog;
import com.yc.redevenlopes.homeModule.contact.TurnTableContact;
import com.yc.redevenlopes.homeModule.module.bean.TurnGoPrizeBeans;
import com.yc.redevenlopes.homeModule.module.bean.TurnTablePrizeInfoBeans;
import com.yc.redevenlopes.homeModule.present.TurnTablePresenter;
import com.yc.redevenlopes.homeModule.widget.LuckPanLayout;
import com.yc.redevenlopes.homeModule.widget.RotatePan;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.VUiKit;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 转盘
 */
public class TurnTableActivity extends BaseActivity<TurnTablePresenter> implements TurnTableContact.View, LuckPanLayout.AnimationEndListener {

    @BindView(R.id.rotatePan)
    RotatePan rotatePan;
    @BindView(R.id.go)
    ImageView go;
    @BindView(R.id.luckpan_layout)
    LuckPanLayout luckpanLayout;
    @BindView(R.id.tv_prizeNums)
    TextView tvPrizeNums;
    @BindView(R.id.tv_go)
    TextView tvGo;
    private String[] strs = {"0.01元", "0.02元", "0.05元", "3元", "50元", "mate40元"};
    private List<TurnTablePrizeInfoBeans.PrizeInfoBean> prize_info;
    private TurnGoPrizeBeans turnGoPrizeBeans;
    private int prizeNums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_turn_table;
    }

    @Override
    public void initEventAndData() {
        luckpanLayout.setAnimationEndListener(this);
        mPresenter.getPrizeInfoData(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.tv_go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_go:
                if (prizeNums > 0) {
                    mPresenter.getGoPrize(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
                    luckpanLayout.rotate(-1, 100);
                } else {
                    ToastUtil.showToast("今日抽奖次数已用完");
                }
                break;
        }
    }

    @Override
    public void endAnimation(int position) {
        prizeNums--;
        setViewStatus();
        showRedDialog();
    }

    private void setViewStatus() {
        tvPrizeNums.setText(prizeNums+"");
        if (prizeNums>0){
             tvPrizeNums.setTextColor(getResources().getColor(R.color.A1_AB5B0F));
             tvGo.setTextColor(getResources().getColor(R.color.A1_AB5B0F));
             tvGo.setBackground(getResources().getDrawable(R.drawable.line_bg_yellow5));
         }else {
             tvPrizeNums.setTextColor(getResources().getColor(R.color.A1_999999));
             tvGo.setBackground(getResources().getDrawable(R.drawable.tv_bg_gray3));
             tvGo.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void showRedDialog() {
        RedDialog redDialog = new RedDialog(this);
        View builder = redDialog.builder(R.layout.red_dialog_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_type = builder.findViewById(R.id.tv_typeName);
        TextView tv_money = builder.findViewById(R.id.tv_money);
        ImageView iv_open = builder.findViewById(R.id.iv_open);
        tv_type.setText("转盘红包");
        if (turnGoPrizeBeans != null) {
            tv_money.setText(turnGoPrizeBeans.getMoney());
        }
        iv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RobRedEvenlopesActivity.robRedEvenlopesJump(TurnTableActivity.this,"2","转盘红包","100",turnGoPrizeBeans.getMoney());
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redDialog.setDismiss();
            }
        });
        VUiKit.postDelayed(2000, () -> {
            iv_close.setVisibility(View.VISIBLE);
        });
        redDialog.setShow();
    }


    public static void TurnTableJump(Context context) {
        Intent intent = new Intent(context, TurnTableActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getPrizeInfoDataSuccess(TurnTablePrizeInfoBeans data) {
        prize_info = data.getPrize_info();
        prizeNums = data.getUser_other().getPrize_num();
        setViewStatus();
        for (int i = 0; i < prize_info.size(); i++) {
            strs[i] = prize_info.get(i).getName();
        }
        rotatePan.setStr(strs);
    }

    @Override
    public void getGoPrizeSuccess(TurnGoPrizeBeans data) {
        this.turnGoPrizeBeans = data;
        int id = data.getId();
        if (prize_info != null) {
            for (int i = 0; i < prize_info.size(); i++) {
                if (id == prize_info.get(i).getId()) {
                    rotatePan.setRotate(i);
                }
            }
        }
    }
}