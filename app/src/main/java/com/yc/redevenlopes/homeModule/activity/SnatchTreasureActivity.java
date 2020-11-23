package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.SnatchDialog;
import com.yc.redevenlopes.homeModule.contact.SnatchTreasureContact;
import com.yc.redevenlopes.homeModule.module.bean.SnatchDetailsBeans;
import com.yc.redevenlopes.homeModule.module.bean.SnatchPostBeans;
import com.yc.redevenlopes.homeModule.present.SnatchTreasurePresenter;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.CountDownUtils;
import com.yc.redevenlopes.utils.CountDownUtilsTwo;
import com.yc.redevenlopes.utils.TimesUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 夺宝
 */
public class SnatchTreasureActivity extends BaseActivity<SnatchTreasurePresenter> implements SnatchTreasureContact.View {

    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.line_ruleDetails)
    LinearLayout lineRuleDetails;
    @BindView(R.id.line_snatchsOne)
    LinearLayout lineSnatchsOne;
    @BindView(R.id.line_snatchsTwo)
    LinearLayout lineSnatchsTwo;
    @BindView(R.id.tv_prizePeriods)
    TextView tvPrizePeriods;
    @BindView(R.id.tv_snatchCurrNums)
    TextView tvSnatchCurrNums;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_firstSantchTimes)
    TextView tvFirstSantchTimes;
    @BindView(R.id.tv_SysCurrTimesTimes)
    TextView tvSysCurrTimesTimes;
    @BindView(R.id.tv_yuOpenPrizeTimes)
    TextView tvYuOpenPrizeTimes;
    @BindView(R.id.tv_mySnatchNums)
    TextView tvMySnatchNums;
    @BindView(R.id.tv_quanNums)
    TextView tvQuanNums;
    @BindView(R.id.tv_lianxuQuanNums)
    TextView tvLianxuQuanNums;
    @BindView(R.id.tv_onceNumsQuan)
    TextView tvOnceNumsQuan;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_snatchsTwoTitle)
    TextView tvSnatchsTwoTitle;
    @BindView(R.id.iv_quan2)
    ImageView ivQuan2;
    @BindView(R.id.tv_snatchsOneTitle)
    TextView tvSnatchsOneTitle;
    @BindView(R.id.iv_quan1)
    ImageView ivQuan1;
    private SnatchDetailsBeans snatchDetailsBeans;
    private int snatchNums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_snatch_treasure;
    }

    @Override
    public void initEventAndData() {
        mPresenter.getSnatchinfoDetails(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");
        progressbar.setMax(1000);

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }


    public static void snatchTreasureJump(Context context) {
        Intent intent = new Intent(context, SnatchTreasureActivity.class);
        context.startActivity(intent);
    }

    private void showDialogs(String user_nums) {
        SnatchDialog snatchDialog = new SnatchDialog(this);
        View builder = snatchDialog.builder(R.layout.snatch_item);
        TextView tv_snatchNo = builder.findViewById(R.id.tv_prizeNums);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        if (!TextUtils.isEmpty(user_nums)) {
            user_nums.replaceAll(",", "  ");
            tv_snatchNo.setText(user_nums);
        }
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snatchDialog.setDismiss();
            }
        });
        snatchDialog.setShow();
    }

    @OnClick({R.id.iv_back, R.id.tv_history, R.id.line_ruleDetails, R.id.line_snatchsOne, R.id.line_snatchsTwo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_history:
                SnatchTreasureHistoryActivity.snatchtreasurehistoryJump(this);
                break;
            case R.id.line_ruleDetails:
                SnatchTreasureRuleActivity.snatchTreasureRuleJump(this);
                break;
            case R.id.line_snatchsOne:
                if (snatchNums >=5) {
                    mPresenter.getSnatchPost(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "5", snatchDetailsBeans.getId() + "");
                }
                break;
            case R.id.line_snatchsTwo:
                if (snatchNums > 0) {
                    mPresenter.getSnatchPost(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", "1", snatchDetailsBeans.getId() + "");
                }
                break;
        }
    }

    private CountDownUtils countDownUtils;
    private CountDownUtilsTwo sysCountDownUtils;

    @Override
    public void getSnatchinfoDetailsSuccess(SnatchDetailsBeans data) {
        this.snatchDetailsBeans = data;
        snatchNums=data.getUser_other().getTreasure_num();
        tvPrizePeriods.setText("第" + data.getAdd_num() + "期");
        tvSnatchCurrNums.setText(data.getNum() + "/1000");
        progressbar.setProgress(data.getNum());
        tvMoney.setText(data.getMoney() + "元");
        tvFirstSantchTimes.setText(data.getStart());
        long currTimes = System.currentTimeMillis();
        long yuTimes = data.getEnd_time() - currTimes;
        if (!TextUtils.isEmpty(data.getUser_num())) {
            String user_num = data.getUser_num();
            user_num.replaceAll(",", "  ");
            tvMySnatchNums.setText(user_num);
        }
        if (data.getUser_other() != null) {
            tvQuanNums.setText(data.getUser_other().getTreasure_num() + "");
            setViewsStatus(data.getUser_other().getTreasure_num());
        }

        countDownUtils = new CountDownUtils();
        countDownUtils.setOnCountDownListen(new CountDownUtils.OnCountDownListen() {
            @Override
            public void count(long mHour, long mMin, long mSecond) {
                tvYuOpenPrizeTimes.setText(getTv(mHour) + ":" + getTv(mMin) + ":" + getTv(mSecond));
            }

            @Override
            public void countFinsh() {

            }
        });
        countDownUtils.setHours(TimesUtils.getHour(yuTimes), TimesUtils.getMinute(yuTimes), TimesUtils.getSecond(yuTimes));

        sysCountDownUtils = new CountDownUtilsTwo();
        sysCountDownUtils.setOnCountDownListen(new CountDownUtilsTwo.OnCountDownListen() {
            @Override
            public void count(long mHour, long mMin, long mSecond) {
                tvSysCurrTimesTimes.setText(getTv(mHour) + ":" + getTv(mMin) + ":" + getTv(mSecond));
            }
        });
        sysCountDownUtils.setHours(TimesUtils.getHour(currTimes), TimesUtils.getMinute(currTimes), TimesUtils.getSecond(currTimes));
    }

    @Override
    public void getSnatchPostSuccess(SnatchPostBeans data) {
        snatchNums=data.getTreasure_num();
        if (data.getNew_level() > 0) {//升级了

        }
        showDialogs(data.getUser_num());
    }

    private void setViewsStatus(int nums) {
        if (nums == 0) {
            lineSnatchsTwo.setBackground(getResources().getDrawable(R.drawable.tv_bg_gray2));
            tvSnatchsTwoTitle.setTextColor(getResources().getColor(R.color.A1_656565));
            tvOnceNumsQuan.setTextColor(getResources().getColor(R.color.A1_656565));
            ivQuan2.setImageDrawable(getResources().getDrawable(R.drawable.quan1));
        } else {
            lineSnatchsTwo.setBackground(getResources().getDrawable(R.drawable.line_bg_yellow4));
            tvSnatchsTwoTitle.setTextColor(getResources().getColor(R.color.white));
            tvOnceNumsQuan.setTextColor(getResources().getColor(R.color.white));
            ivQuan2.setImageDrawable(getResources().getDrawable(R.drawable.quan2));
        }

        if (nums>=5){
            lineSnatchsOne.setBackground(getResources().getDrawable(R.drawable.tv_bg_gray2));
            tvSnatchsOneTitle.setTextColor(getResources().getColor(R.color.A1_656565));
            tvLianxuQuanNums.setTextColor(getResources().getColor(R.color.A1_656565));
            ivQuan1.setImageDrawable(getResources().getDrawable(R.drawable.quan1));
        }else {
            lineSnatchsOne.setBackground(getResources().getDrawable(R.drawable.line_bg_yellow4));
            tvSnatchsOneTitle.setTextColor(getResources().getColor(R.color.white));
            tvLianxuQuanNums.setTextColor(getResources().getColor(R.color.white));
            ivQuan1.setImageDrawable(getResources().getDrawable(R.drawable.quan2));
        }

    }

    private String getTv(long l) {
        if (l >= 10) {
            return l + "";
        } else {
            return "0" + l;//小于10,,前面补位一个"0"
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownUtils != null) {
            countDownUtils.clean();
        }
        if (sysCountDownUtils != null) {
            sysCountDownUtils.clean();
        }
    }
}