package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.GuessDialog;
import com.yc.redevenlopes.homeModule.contact.GuessingContact;
import com.yc.redevenlopes.homeModule.module.bean.GuessBeans;
import com.yc.redevenlopes.homeModule.module.bean.PostGuessNoBeans;
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
    @BindView(R.id.tv_guessPeriodss)
    TextView tvGuessPeriods;
    @BindView(R.id.tv_openPrizeTimes)
    TextView tvOpenPrizeTimes;
    @BindView(R.id.guessMoney)
    TextView guessMoney;
    @BindView(R.id.guessPeopleNums)
    TextView guessPeopleNums;
    @BindView(R.id.tv_prizeDetails)
    TextView tvPrizeDetails;
    @BindView(R.id.myGuessNums)
    TextView myGuessNums;
    private GuessBeans guessBeans;
    private int guess_num;

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
        initData();
    }

    private void initData() {
        mPresenter.getGuessData(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
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
                if (guess_num>0){
                    showGuessDialog();
                }else {
                    ToastUtil.showToast("您今日的竞猜次数已用完");
                }
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
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.submitGuessNo(CacheDataUtils.getInstance().getUserInfo().getGroup_id()+"",guessBeans.getInfo_id()+"","2452");
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessDialog.setDismiss();
            }
        });
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
        if (data!=null){
            guessBeans=data;
            tvGuessPeriods.setText(data.getGuessno()+"");
            guessPeopleNums.setText(data.getTotal()+"");
            guessMoney.setText(data.getMoney());
            guess_num=data.getUser_other().getGuess_num();
            String add_num = data.getAdd_num();
            if (!TextUtils.isEmpty(add_num)){
                add_num.replaceAll(",","  ");
            }
            myGuessNums.setText(add_num);
            List<String> range = data.getRange();
            List<Integer> datass = new ArrayList<Integer>();
            for (int i = 0; i < range.size(); i++) {
                datass.add(Integer.parseInt(range.get(i)));
            }
            List<String> monthList = new ArrayList<String>();
            monthList.add("0");
            monthList.add("2000");
            monthList.add("4000");
            monthList.add("6000");
            monthList.add("8000");
            monthList.add("9999");
            barchartView.setMonthList(monthList);
            barchartView.setData(datass);
            barchartView.setOnDraw(true);
            barchartView.start();
        }
    }

    @Override
    public void submitGuessNoSuccess(PostGuessNoBeans data) {
        guess_num=data.getGuess_num();
        String num = data.getNum();
        String myGuess = myGuessNums.getText().toString();
        myGuessNums.setText(myGuess+"  "+num);
    }
}