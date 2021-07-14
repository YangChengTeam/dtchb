package com.yc.qqzz.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseActivity;
import com.yc.qqzz.homeModule.adapter.RobRedEvenlopesAdapter;
import com.yc.qqzz.homeModule.contact.RodRedEvenlopesContact;
import com.yc.qqzz.homeModule.module.bean.EmptyBeans;
import com.yc.qqzz.homeModule.module.bean.RedDetailsBeans;
import com.yc.qqzz.homeModule.present.RodRedEvenlopesPresenter;
import com.yc.qqzz.utils.CacheDataUtils;
import com.yc.qqzz.utils.CommonUtils;
import com.yc.qqzz.utils.DisplayUtil;
import com.yc.qqzz.utils.SoundPoolUtils;
import com.yc.qqzz.widget.gu.Guide;
import java.lang.ref.WeakReference;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 抢红包
 */
public class RobRedEvenlopesActivity extends BaseActivity<RodRedEvenlopesPresenter> implements RodRedEvenlopesContact.View {

    @BindView(R.id.tv_redType)
    TextView tvRedType;
    @BindView(R.id.tv_redMoney)
    TextView tvRedMoney;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_hbNums)
    TextView tvHbNums;
    @BindView(R.id.line_hbNums)
    LinearLayout lineHbNums;
    @BindView(R.id.viewss)
    View view;
    @BindView(R.id.line_money)
    LinearLayout lineMoney;
    @BindView(R.id.tv_close)
    TextView tvClose;
    @BindView(R.id.line_close)
    LinearLayout lineClose;

    private RobRedEvenlopesAdapter robRedEvenlopesAdapter;
    private String type;
    private String balance_money;
    private String money;
    private String typeName;
    private String id;
    private String hongbaoMoneyType;
    private FrameLayout fl_ad_containe;
    public static WeakReference<RobRedEvenlopesActivity> instances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_rob_red_evenlopes;
    }

    @Override
    public void initEventAndData() {
        fl_ad_containe = findViewById(R.id.fl_ad_containe);
        instances = new WeakReference<>(this);
        type = getIntent().getStringExtra("type");
        typeName = getIntent().getStringExtra("typeName");
        money = getIntent().getStringExtra("money");
        balance_money = getIntent().getStringExtra("balance_money");
        id = getIntent().getStringExtra("id");
        hongbaoMoneyType = getIntent().getStringExtra("hongbaoMoneyType");
        if (TextUtils.isEmpty(hongbaoMoneyType) && !TextUtils.isEmpty(money)) {
            float v = Float.parseFloat(money);
        }
        loadInsertView(null);
        if (!TextUtils.isEmpty(typeName)) {
            tvRedType.setText(typeName);
        }
        if (!TextUtils.isEmpty(money) && !"0".equals(money) && !"0.00".equals(money)) {
            tvRedMoney.setText(money);
            lineMoney.setVisibility(View.VISIBLE);
        } else {
            lineMoney.setVisibility(View.GONE);
        }
        view.setVisibility(View.GONE);
        lineHbNums.setVisibility(View.GONE);
        setFullScreen();
        initRecyclerVeiw();
        initData();
    }

    private void video() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        adPlatformSDK.showExpressAd();
    }

    private void loadVideo() {
        int screenWidth = CommonUtils.getScreenWidth(this);
        int w = (int) (screenWidth) * 9 / 10;
        int h = w * 2 / 3;
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        int dpw = DisplayUtil.px2dip(RobRedEvenlopesActivity.this, w);
        int dph = DisplayUtil.px2dip(RobRedEvenlopesActivity.this, h);
        adPlatformSDK.loadExpressAd(this, "ad_lingqucg", dpw, dph, new AdCallback() {
            @Override
            public void onDismissed() {

            }

            @Override
            public void onNoAd(AdError adError) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPresent() {

            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {
                video();
            }
        }, fl_ad_containe);
    }

    private void initData() {
        if (!TextUtils.isEmpty(id)) {
            recyclerView.setVisibility(View.VISIBLE);
            mPresenter.getRedEvenlopesDetails(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", id);
        } else {
            loadVideo();
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_withdraw, R.id.tv_close})
    public void onViewClicked(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_withdraw:

                break;
            case R.id.tv_close:
                finish();
                break;
        }
    }

    private void initRecyclerVeiw() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        robRedEvenlopesAdapter = new RobRedEvenlopesAdapter(null);
        recyclerView.setAdapter(robRedEvenlopesAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public static void robRedEvenlopesJump(Context context, String type, String typeName, String balance_money, String money, String id, String hongbaoMoneyType) {
        Intent intent = new Intent(context, RobRedEvenlopesActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("typeName", typeName);
        intent.putExtra("money", money);
        intent.putExtra("balance_money", balance_money);
        intent.putExtra("id", id);
        intent.putExtra("hongbaoMoneyType", hongbaoMoneyType);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getRedEvenlopesDetailsSuccess(RedDetailsBeans data) {
        List<RedDetailsBeans.ListBean> list = data.getList();
        robRedEvenlopesAdapter.setTatol(data.getTotal(), list.size());
        robRedEvenlopesAdapter.setNewData(list);
        robRedEvenlopesAdapter.notifyDataSetChanged();
        if (!TextUtils.isEmpty(data.getGet_info().getMoney())) {
            tvRedMoney.setText(data.getGet_info().getMoney());
        }

        if (!TextUtils.isEmpty(data.getGet_info().getMoney()) && !"0".equals(data.getGet_info().getMoney()) && !"0.00".equals(data.getGet_info().getMoney())) {
            tvRedMoney.setText(data.getGet_info().getMoney());
            lineMoney.setVisibility(View.VISIBLE);
        } else {
            lineMoney.setVisibility(View.GONE);
        }

        if (list != null && list.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            if ("1".equals(type)) {//首页
                lineHbNums.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(balance_money)) {
                    if (list.size() == data.getTotal()) {
                        tvHbNums.setText("已领取" + data.getList().size() + "/" + data.getTotal() + "个，共" + data.getSum_money() + "/" + balance_money + "元" + "  ，" + CommonUtils.getRandom(24, 31) + "分钟被抢完");
                    } else {
                        tvHbNums.setText("已领取" + data.getList().size() + "/" + data.getTotal() + "个，共" + data.getSum_money() + "/" + balance_money + "元");
                    }
                } else {
                    if (list.size() == data.getTotal()) {
                        tvHbNums.setText(list.size() + "个红包，共" + data.getSum_money() + "元" + "  ，" + CommonUtils.getRandom(24, 31) + "分钟被抢完");
                    } else {
                        tvHbNums.setText(list.size() + "个红包，共" + data.getSum_money() + "元");
                    }
                }
            } else if ("2".equals(type)) {
                view.setVisibility(View.GONE);
                lineHbNums.setVisibility(View.GONE);
            } else if ("3".equals(type)) {
                view.setVisibility(View.GONE);
                lineHbNums.setVisibility(View.GONE);
            }
        } else {
            recyclerView.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            lineHbNums.setVisibility(View.GONE);
        }
//        String newGuHongbao = CacheDataUtils.getInstance().getNewGuHongbao();
//        if (TextUtils.isEmpty(newGuHongbao)) {
//            lineClose.post(new Runnable() {
//                @Override
//                public void run() {
//                    showGuideView(lineClose);
//                }
//            });
//        }
    }

    @Override
    public void getRegUserLogSuccess(EmptyBeans data) {
      //  MemberActivity.memberJump(RobRedEvenlopesActivity.this);
        finish();
    }

    private Guide guide;

    public void showGuideView(View view) {
//        GuideBuilder builder = new GuideBuilder();
//        builder.setTargetView(view)
//                .setAlpha(180)
//                .setHighTargetCorner(20)
//                .setOutsideTouchable(false)
//                .setAutoDismiss(false)
//                .setHighTargetPadding(10);
//        builder.setOnTarListener(new GuideBuilder.OnTarLintens() {
//            @Override
//            public void onTarLinten() {
//                MobclickAgent.onEvent(RobRedEvenlopesActivity.this, "yindaoclose");//参数二为当前统计的事件ID
//                if (guide != null) {
//                    guide.dismiss();
//                }
//            }
//        });
//
//        builder.setOnVisibilityChangedListener(new GuideBuilder.OnVisibilityChangedListener() {
//            @Override
//            public void onShown() {
//
//            }
//
//            @Override
//            public void onDismiss() {
//               // CacheDataUtils.getInstance().setNewGuHongbao("newsHongbao");
//                mPresenter.getRegUserLog(CacheDataUtils.getInstance().getUserInfo().getId(),"7");
//            }
//        });
//        builder.addComponent(new SimpleComponentTwo(-15));
//        guide = builder.createGuide();
//        guide.show(RobRedEvenlopesActivity.this);
    }

    @Override
    protected void onDestroy() {
        if (guide != null) {
            guide.clear();
            guide = null;
        }
        super.onDestroy();
    }

    private void showInsertVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setAdPosition("chapingmember");
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        if(adPlatformSDK.showInsertAd()){
            loadInsertView(null);
        } else {
            loadInsertView( new Runnable() {
                @Override
                public void run() {
                    adPlatformSDK.showInsertAd();
                }
            });
        }
    }

    private void loadInsertView(Runnable runnable){
        int screenWidth = CommonUtils.getScreenWidth(this);
        int w = (int) (screenWidth)*9/10;
        int h = screenWidth;
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        int dpw = DisplayUtil.px2dip(RobRedEvenlopesActivity.this, w);
        int dph = DisplayUtil.px2dip(RobRedEvenlopesActivity.this, h);
        adPlatformSDK.loadInsertAd(this, "chapingmember", dpw, dph, new AdCallback() {
            @Override
            public void onDismissed() {

            }

            @Override
            public void onNoAd(AdError adError) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPresent() {

            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {
                if(runnable != null){
                    runnable.run();
                }
            }
        });
    }
}