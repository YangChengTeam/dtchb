package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.GuessDialog;
import com.yc.redevenlopes.homeModule.contact.GuessingContact;
import com.yc.redevenlopes.homeModule.module.bean.GuessBeans;
import com.yc.redevenlopes.homeModule.present.GuessingPresenter;
import com.yc.redevenlopes.homeModule.widget.BarChartView;
import com.yc.redevenlopes.homeModule.widget.MultiScrollNumber;
import com.yc.redevenlopes.utils.CacheDataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 竞猜
 */
public class GuessingActivity extends BaseActivity<GuessingPresenter> implements GuessingContact.View {

    @BindView(R.id.tv_sumber)
    TextView tvSumber;
    @BindView(R.id.barchartView)
    BarChartView barchartView;
    @BindView(R.id.scroll_number)
    MultiScrollNumber scrollNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_guessing;
    }

    @Override
    public void initEventAndData() {
        List<Integer> data = new ArrayList<Integer>();
        List<String> monthList = new ArrayList<String>();
        data.add(5000);
        data.add(6000);
        data.add(8000);
        data.add(9000);
        data.add(3000);
        monthList.add("0");
        monthList.add("2000");
        monthList.add("4000");
        monthList.add("6000");
        monthList.add("8000");
        monthList.add("9999");
        barchartView.setMonthList(monthList);
        barchartView.setData(data);
        barchartView.setOnDraw(true);
        barchartView.start();
         scrollNumber.setTextSize(64);
        scrollNumber.setNumber(20.48);
        scrollNumber.setInterpolator(new DecelerateInterpolator());
        scrollNumber.setScrollVelocity(100);
        initData();

    }

    private void initData() {
        mPresenter.getGuessData(CacheDataUtils.getInstance().getUserInfo().getGroup_id()+"");


    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void GuessingJump(Context context) {
        Intent intent = new Intent(context, GuessingActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_back, R.id.tv_history, R.id.tv_prizeDetails, R.id.tv_sumber})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_history:
                GuessingResultActivity.GuessingResultJump(this);
                break;
            case R.id.tv_prizeDetails:
                GuessingDetailsActivity.guessingDetailsJump(this);
                break;
            case R.id.tv_sumber:
                showGuessDialog();
                break;
        }
    }

    private void showGuessDialog() {
        GuessDialog guessDialog = new GuessDialog(this);
        View builder = guessDialog.builder(R.layout.guess_item_two);
        TextView tv_title = builder.findViewById(R.id.tv_title);
        TextView tv_des = builder.findViewById(R.id.tv_des);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_cancle = builder.findViewById(R.id.tv_cancle);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessDialog.setDismiss();
            }
        });
        guessDialog.setShow();
    }


    @Override
    public void getGuessDataSuccess(GuessBeans data) {

    }
}