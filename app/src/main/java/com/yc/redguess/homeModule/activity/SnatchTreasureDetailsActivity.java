package com.yc.redguess.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.homeModule.contact.SnatchTreasureDeatilsContact;
import com.yc.redguess.homeModule.module.bean.SnatchTreasureDetailssBeans;
import com.yc.redguess.homeModule.present.SnatchTreasureDetailsPresenter;
import com.yc.redguess.utils.CacheDataUtils;
import com.yc.redguess.utils.SoundPoolUtils;
import com.yc.redguess.utils.TimesUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 夺宝详情
 */
public class SnatchTreasureDetailsActivity extends BaseActivity<SnatchTreasureDetailsPresenter> implements SnatchTreasureDeatilsContact.View {

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
    @BindView(R.id.tv_prizePeriods)
    TextView tvPrizePeriods;
    @BindView(R.id.tv_formula)
    TextView tvFormula;
    private String id;

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
        id = getIntent().getStringExtra("id");
        setTitle("夺宝详情");
        mPresenter.getSnatchDetailss(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", id);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.tv_mySnatch, R.id.tv_sure})
    public void onViewClicked(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        switch (view.getId()) {
            case R.id.tv_mySnatch:
                break;
            case R.id.tv_sure:
                finish();
                break;
        }
    }

    public static void snatchTreasureDetailsJump(Context context, String id) {
        Intent intent = new Intent(context, SnatchTreasureDetailsActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public void getSnatchDetailssSuccess(SnatchTreasureDetailssBeans data) {
        tvPrizePeriods.setText(data.getAdd_date() + data.getAdd_num() + "");
        tvPrizeMoney.setText(data.getMoney() + "元");
        tvFirstPrizeTimes.setText(TimesUtils.getStrTime(String.valueOf(data.getEnd_time()*1000)));
        tvSnatchNumber.setText(data.getPrize_num() + "");
        tvFormula.setText(data.getStart()+"x"+data.getEnd()+"="+data.getResult_num()+"");
        tvPrizeName.setText(data.getPrize_user().getNickname());
        tvSnatchNums.setText(data.getTotal()+"");
        if (!TextUtils.isEmpty(data.getUser_num())){
            String userNums = data.getUser_num().replaceAll(",", "  ");
            tvMySnatch.setText(userNums);
        }
        Glide.with(this)
                .load(data.getPrize_user().getFace())
                .apply(new RequestOptions().bitmapTransform(new CircleCrop()))
                .into(ivPrizeAvatar);
    }
}