package com.yc.redevenlopes.homeModule.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.constants.Constant;
import com.yc.redevenlopes.dialog.RedDialog;
import com.yc.redevenlopes.homeModule.adapter.HomeAdapter;
import com.yc.redevenlopes.homeModule.contact.MainContact;
import com.yc.redevenlopes.homeModule.module.bean.HomeAllBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeBeans;
import com.yc.redevenlopes.homeModule.module.bean.HomeRedMessage;
import com.yc.redevenlopes.homeModule.module.bean.OpenRedEvenlopes;
import com.yc.redevenlopes.homeModule.module.bean.OtherBeans;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.homeModule.present.MainPresenter;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.CommonUtils;
import com.yc.redevenlopes.utils.DisplayUtil;
import com.yc.redevenlopes.utils.VUiKit;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContact.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.line_snatchTreasure)
    LinearLayout lineSnatchTreasure;
    @BindView(R.id.line_duobao)
    LinearLayout lineDuobao;
    @BindView(R.id.line_lay)
    LinearLayout lineLay;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.iv_ac)
    ImageView ivAc;
    private HomeAdapter homeAdapter;
    private OtherBeans otherBeans;
    private String hongbao_id;
    private String redTypeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initEventAndData() {
        initViews();
        initRecyclerView();
        initData();
        initTimes();
    }

    private void initViews() {
        if (CacheDataUtils.getInstance().isLogin()) {
            UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
            tvTitle.setText("红包" + userInfo.getGroup_id() + "群");
            tvMoney.setText(userInfo.getCash_out_money() + "元");
        }
    }

    private void initData() {
        if (CacheDataUtils.getInstance().isLogin()) {
            UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
            mPresenter.getHomeData(userInfo.getGroup_id() + "");
            mPresenter.getOtherInfo(userInfo.getGroup_id() + "", userInfo.getId() + "");
        }
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        homeAdapter = new HomeAdapter(null);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(homeAdapter);
        for (int i = 0; i < 3; i++) {
            HomeBeans homeBeans = new HomeBeans();
            homeBeans.setItemType(Constant.TYPE_TWO);
            homeAdapter.addData(homeBeans);
        }
        for (int i = 0; i < 4; i++) {
            HomeBeans homeAllBeans = new HomeBeans();
            homeAllBeans.setItemType(Constant.TYPE_THREE);
            homeAdapter.addData(homeAllBeans);
        }
        recyclerView.scrollToPosition(0);
        homeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.line_open:
                        List<HomeBeans> lists = adapter.getData();
                        HomeBeans homeBeans = lists.get(position);
                        if (homeBeans.getItemType() == Constant.TYPE_TWO) {
                            HomeRedMessage homeRedMessage = homeBeans.getHomeRedMessage();
                            redTypeName = homeRedMessage.getTypename();
                            mPresenter.getRedEvenlopsInfo(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", homeRedMessage.getId() + "");
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.line_members, R.id.line_activitys, R.id.line_snatchTreasure, R.id.line_withdraw, R.id.iv_avatar, R.id.iv_red})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_members:
                MemberActivity.memberJump(this);
                break;
            case R.id.line_activitys:
                showPopupWindow();
                break;
            case R.id.line_snatchTreasure:
                SnatchTreasureActivity.snatchTreasureJump(this);
                break;
            case R.id.line_withdraw:
                WithdrawActivity.WithdrawJump(this);
                break;
            case R.id.iv_avatar:
                MemberCenterActivity.memberCenterJump(this);
                break;
            case R.id.iv_red:
                showRedDialogTwo();
                break;
        }
    }


    private Disposable disposableTwo;

    public void initTimes() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableTwo = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (!TextUtils.isEmpty(hongbao_id)) {  //红包消息轮询
                            mPresenter.getHomeMessageRedData(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", hongbao_id);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void showRedDialog(OpenRedEvenlopes data) {
        RedDialog redDialog = new RedDialog(this);
        View builder = redDialog.builder(R.layout.red_dialog_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_type = builder.findViewById(R.id.tv_typeName);
        TextView tv_money = builder.findViewById(R.id.tv_money);
        ImageView iv_open = builder.findViewById(R.id.iv_open);
        if (TextUtils.isEmpty(redTypeName)) {
            tv_type.setText(redTypeName);
        }
        tv_money.setText(data.getBalance_money());
        if (data.getStatus() == 0) {
            iv_open.setImageDrawable(getResources().getDrawable(R.drawable.icon_open));
        }
        iv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RobRedEvenlopesActivity.robRedEvenlopesJump(MainActivity.this,"1","手气红包","100");
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

    private void showRedDialogTwo() {

    }

    public void openVideo(int type) {
//        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
//        if (type == 0) {
//            adPlatformSDK.showInsertAd(this,900, 600, this);
//        } else if (type == 1) {
//            adPlatformSDK.showExpressAd(this,this, (FrameLayout) findViewById(R.id.fl_ad_container));
//        } else if (type == 2) {
//            adPlatformSDK.showRewardVideoHorizontalAd(this,this);
//        } else if (type == 3) {
//            adPlatformSDK.showFullScreenVideoVerticalAd(this, this);
//        } else if (type == 4) {
//            adPlatformSDK.showBannerAd(this, 300, 100, this,  (FrameLayout) findViewById(R.id.fl_ad_container));
//        }
    }


    private void showPopupWindow() {
        ivAc.setImageDrawable(getResources().getDrawable(R.drawable.bottom_activity2));
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_home_item, null);
        ConstraintLayout line_answer = inflate.findViewById(R.id.cons_answer);
        ConstraintLayout line_guessr = inflate.findViewById(R.id.cons_guess);
        ConstraintLayout line_turn = inflate.findViewById(R.id.cons_turn);
        line_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerActivity.answerJump(MainActivity.this);
                popupWindow.dismiss();
            }
        });
        line_guessr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuessingActivity.GuessingJump(MainActivity.this);
                popupWindow.dismiss();
            }
        });
        line_turn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TurnTableActivity.TurnTableJump(MainActivity.this);
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(inflate);
        popupWindow.setFocusable(true);
        // 设置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        // 外部点击事件
        popupWindow.setOutsideTouchable(true);
        int screenHeight = CommonUtils.getScreenHeight(this);
        int screenWidth = CommonUtils.getScreenWidth(this);
        int statusBarHeight = CommonUtils.getStatusBarHeight(this);
        int he = DisplayUtil.dip2px(this, 114);
        int wh = screenWidth / 8;
        popupWindow.showAtLocation(lineLay, Gravity.TOP, -wh, screenHeight - he - statusBarHeight);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivAc.setImageDrawable(getResources().getDrawable(R.drawable.bottom_activity));
            }
        });
    }

    @Override
    public void getHomeDataSuccess(HomeAllBeans data) {
        if (data != null) {
            HomeBeans homeBeans = new HomeBeans();
            homeBeans.setItemType(Constant.TYPE_THREE);
            homeBeans.setHomeAllBeans(data);
            homeAdapter.addData(homeBeans);
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void getOtherInfoSuccess(OtherBeans data) {
        this.otherBeans = data;
        tvRank.setText("LV." + data.getLevel() + "");
    }

    @Override
    public void getHomeMessageRedDataInfo(List<HomeRedMessage> data) {
        for (int i = 0; i < data.size(); i++) {
            HomeBeans homeBeans = new HomeBeans();
            homeBeans.setItemType(Constant.TYPE_TWO);
            homeBeans.setHomeRedMessage(data.get(i));
            homeAdapter.addData(homeBeans);
        }
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void getRedEvenlopsInfoSuccess(OpenRedEvenlopes data) {
        showRedDialog(data);
    }
}