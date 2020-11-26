package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.adapter.DisposeMoneyAdapter;
import com.yc.redevenlopes.homeModule.adapter.DisposeNoticeAdapter;
import com.yc.redevenlopes.homeModule.contact.WithdrawConstact;
import com.yc.redevenlopes.homeModule.fragment.DisposeTintFragment;
import com.yc.redevenlopes.homeModule.module.bean.DisposeInfo;
import com.yc.redevenlopes.homeModule.present.WithdrawPresenter;
import com.yc.redevenlopes.homeModule.widget.TextViewSwitcher;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提现
 */
public class WithdrawActivity extends BaseActivity<WithdrawPresenter> implements WithdrawConstact.View {

    @BindView(R.id.tv_wallet_num)
    TextView tvWalletNum;
    @BindView(R.id.recyclerView_money)
    RecyclerView recyclerViewMoney;
    @BindView(R.id.textViewSwitcher)
    TextViewSwitcher textViewSwitcher;

    private DisposeMoneyAdapter disposeMoneyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_withdraw;
    }

    @Override
    public void initEventAndData() {
        setTitle("钱包详情");
        initView();
        initRecyclerView();
        initData();
    }

    private void initData() {
        List<DisposeInfo> disposeInfoList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            disposeInfoList.add(new DisposeInfo());
        }
        disposeMoneyAdapter.setNewData(disposeInfoList);
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerViewMoney.setLayoutManager(gridLayoutManager);

        disposeMoneyAdapter = new DisposeMoneyAdapter(null);
        recyclerViewMoney.setAdapter(disposeMoneyAdapter);

    }

    private void initView() {

        List<String> notices = new ArrayList<>();

        notices.add("玩家“张小波”成功提现200元");
        notices.add("玩家“默默的小鸟”成功提现200元");
        notices.add("玩家“走天涯”成功提现300元");
        notices.add("玩家“张小波”成功提现200元");
        notices.add("玩家“默默的小鸟”成功提现200元");
        notices.add("玩家“走天涯”成功提现300元");
//        textBannerView.setDatas(notices);
        DisposeNoticeAdapter disposeNoticeAdapter = new DisposeNoticeAdapter(notices);
        textViewSwitcher.setAdapter(disposeNoticeAdapter);
    }


    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }


    public static void WithdrawJump(Context context) {
        Intent intent = new Intent(context, WithdrawActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.tv_dispose_record, R.id.ll_wx_pay})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_dispose_record:
                startActivity(new Intent(WithdrawActivity.this, WithdrawRecordActivity.class));
                break;
            case R.id.ll_wx_pay:
                DisposeTintFragment disposeTintFragment = new DisposeTintFragment();
                disposeTintFragment.show(getSupportFragmentManager(), "");
                break;
        }
    }

    public void withDrawBindWx(){
//        UMShareConfig config = new UMShareConfig();
//        config.isNeedAuthOnGetUserInfo(true);
//        UMShareAPI.get(this).setShareConfig(config);
//        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new MyAuthLoginListener());
    }

}