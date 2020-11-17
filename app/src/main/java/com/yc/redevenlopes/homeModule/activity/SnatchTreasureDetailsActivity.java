package com.yc.redevenlopes.homeModule.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.SnatchTreasureDeatilsContact;
import com.yc.redevenlopes.homeModule.present.SnatchTreasurePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 夺宝详情
 */
public class SnatchTreasureDetailsActivity extends BaseActivity<SnatchTreasurePresenter> implements SnatchTreasureDeatilsContact.View {

    @BindView(R.id.tv_prizeMoney)
    TextView tvPrizeMoney;
    @BindView(R.id.tv_firstPrizeTimes)
    TextView tvFirstPrizeTimes;
    @BindView(R.id.tv_openPrizeTimes)
    TextView tvOpenPrizeTimes;
    @BindView(R.id.tv_snatchNumber)
    TextView tvSnatchNumber;
    @BindView(R.id.iv_prizeAvatar)
    ImageView ivPrizeAvatar;
    @BindView(R.id.tv_prizeName)
    TextView tvPrizeName;
    @BindView(R.id.tv_snatchNums)
    TextView tvSnatchNums;
    @BindView(R.id.tv_mySnatch)
    TextView tvMySnatch;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_snatch_treasure_details;
    }

    @Override
    public void initEventAndData() {
         setRightTitle("夺宝详情");
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.tv_mySnatch, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_mySnatch:
                break;
            case R.id.tv_sure:
                break;
        }
    }
}