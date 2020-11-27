package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.yc.redevenlopes.homeModule.widget.NumberPickerView;
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
    @BindView(R.id.picker1)
    NumberPickerView picker1;
    @BindView(R.id.picker2)
    NumberPickerView picker2;
    @BindView(R.id.picker3)
    NumberPickerView picker3;
    @BindView(R.id.picker4)
    NumberPickerView picker4;
    private GuessBeans guessBeans;
    private int guess_num;
    private int guessPick1=0;
    private int guessPick2=0;
    private int guessPick3=0;
    private int guessPick4=0;
    private View nodData;
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
        nodData=findViewById(R.id.view_nodata);
        initData();
        initPick();
    }

    private void initPick() {
        String[] strs = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        picker1.refreshByNewDisplayedValues(strs);
        picker2.refreshByNewDisplayedValues(strs);
        picker3.refreshByNewDisplayedValues(strs);
        picker4.refreshByNewDisplayedValues(strs);
        picker1.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                guessPick1=newVal;
            }
        });
        picker2.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                guessPick2=newVal;
            }
        });
        picker3.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                guessPick3=newVal;
            }
        });
        picker4.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                guessPick4=newVal;
            }
        });
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
                if (guessBeans!=null){
                    GuessingResultActivity.GuessingResultJump(this, guessBeans.getInfo_id() + "");
                }
                break;
            case R.id.tv_prizeDetails:
                if (guessBeans!=null){
                    String contents = "";
                    if (!TextUtils.isEmpty(guessBeans.getContent())) {
                        contents = guessBeans.getContent();
                    }
                    GuessingDetailsActivity.guessingDetailsJump(this, contents);
                }
                break;
            case R.id.tv_sumber:
                if (guess_num > 0) {
                    String guessNums=guessPick1+""+guessPick2+""+guessPick3+""+guessPick4;
                    showGuessDialog(guessNums);
                } else {
                    ToastUtil.showToast("您今日的竞猜次数已用完");
                }
                break;
        }
    }

    private GuessDialog guessDialog;
    private void showGuessDialog(String guessNums) {
        guessDialog = new GuessDialog(this);
        View builder = guessDialog.builder(R.layout.guess_item_two);
        TextView tv_title = builder.findViewById(R.id.tv_title);
        TextView tv_des = builder.findViewById(R.id.tv_des);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_cancle = builder.findViewById(R.id.tv_cancle);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        tv_des.setText(guessNums);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.submitGuessNo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", guessBeans.getInfo_id() + "", guessNums);
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
        if (data != null) {
            nodData.setVisibility(View.GONE);
            guessBeans = data;
            tvGuessPeriods.setText(data.getGuessno() + "期");
            guessPeopleNums.setText(data.getTotal() + "");
            guessMoney.setText(data.getMoney());
            guess_num = data.getUser_other().getGuess_num();
            String add_num = data.getAdd_num();
            if (!TextUtils.isEmpty(add_num)) {
                add_num.replaceAll(",", "  ");
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
        }else {
            nodData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void submitGuessNoSuccess(PostGuessNoBeans data) {
        if (guessDialog != null) {
            guessDialog.setDismiss();
        }
        guess_num = data.getGuess_num();
        String num = data.getNum();
        String myGuess = myGuessNums.getText().toString();
        myGuessNums.setText(myGuess + "  " + num);
    }

    @Override
    public void getGuessDataError() {
        nodData.setVisibility(View.VISIBLE);
    }
}