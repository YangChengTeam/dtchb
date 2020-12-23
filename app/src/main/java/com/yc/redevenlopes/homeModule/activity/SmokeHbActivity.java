package com.yc.redevenlopes.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.LevelDialog;
import com.yc.redevenlopes.dialog.SnatchDialog;
import com.yc.redevenlopes.homeModule.adapter.SmokeAdapter;
import com.yc.redevenlopes.homeModule.contact.SmokeHbContact;
import com.yc.redevenlopes.homeModule.module.bean.SmokeBeans;
import com.yc.redevenlopes.homeModule.module.bean.SmokeHbBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;
import com.yc.redevenlopes.homeModule.present.SmokeHbPresenter;
import com.yc.redevenlopes.homeModule.widget.Rotate3dAnimation;
import com.yc.redevenlopes.homeModule.widget.ScrollWithRecyclerView;
import com.yc.redevenlopes.homeModule.widget.SpaceItemDecoration;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.CommonUtils;
import com.yc.redevenlopes.utils.CountDownUtils;
import com.yc.redevenlopes.utils.DisplayUtil;
import com.yc.redevenlopes.utils.TimesUtils;
import com.yc.redevenlopes.utils.VUiKit;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SmokeHbActivity extends BaseActivity<SmokeHbPresenter> implements SmokeHbContact.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_redNumsTwo)
    TextView tvRedNums;
    @BindView(R.id.tv_selectTwo)
    TextView tvSelect;
    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    @BindView(R.id.tv_redNumsOne)
    TextView tvRedNumsOne;
    @BindView(R.id.tv_selectOne)
    TextView tvSelectOne;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_min)
    TextView tvMin;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.line_count)
    LinearLayout lineCount;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    private SmokeAdapter smokeAdapter;
    private String redId;
    private int index;
    private int type;//看视频的类型  1 看视频不翻倍 2 看视频翻倍
    private int getRedNums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_smoke_hb;
    }

    @Override
    public void initEventAndData() {
        setFullScreen();
        init();
        initRedDialogTwo();
        initRedDialogOne();
        loadVideo();
        mPresenter.getLuckyRed(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id());

    }

    private ImageView imageView;
    private CountDownUtils countDownUtils;

    private void init() {
        countDownUtils = new CountDownUtils();
        countDownUtils.setOnCountDownListen(new CountDownUtils.OnCountDownListen() {
            @Override
            public void count(long mHour, long mMin, long mSecond) {
                tvHour.setText(getTv(mHour));
                tvMin.setText(getTv(mMin));
                tvSecond.setText(getTv(mSecond));
            }

            @Override
            public void countFinsh() {
                mPresenter.getLuckyRed(CacheDataUtils.getInstance().getUserInfo().getImei(), CacheDataUtils.getInstance().getUserInfo().getGroup_id());
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(SmokeHbActivity.this, 3, GridLayoutManager.VERTICAL, false);
        smokeAdapter = new SmokeAdapter(null);
        smokeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<SmokeHbBeans.ListBean> data = adapter.getData();
                imageView = (ImageView) view;
                if (data.get(position).getStatus() == 0) {
                    index = position;
                    redId = String.valueOf(data.get(position).getId());
                    type = 1;
                    Log.d("ccc", "--------getRedNums: "+getRedNums);
                    if (getRedNums == 7) {
                        showVideo();
                    } else {
                        initOpenAnim(imageView);
                    }
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        int screenWidth = CommonUtils.getScreenWidth(this); //屏幕宽度
        int itemWidth = DisplayUtil.dip2px(this, 80); //每个item的宽度
        int pa = DisplayUtil.dip2px(this, 64); //每个item的宽度
        recyclerView.addItemDecoration(new SpaceItemDecoration((screenWidth - pa - itemWidth * 3) / 6));
        recyclerView.setAdapter(smokeAdapter);
    }

    private String getTv(long l) {
        if (l >= 10) {
            return l + "";
        } else {
            return "0" + l;//小于10,,前面补位一个"0"
        }
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_selectOne, R.id.tv_selectTwo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_selectOne:

                break;
            case R.id.tv_selectTwo:

                break;
        }
    }


    private LevelDialog redDialogsone;
    private FrameLayout fl_content_one;
    private RelativeLayout rela_fanbei;
    private ImageView iv_close;
    private TextView tv_money;
    private boolean isshowOne;

    private void initRedDialogOne() {
        redDialogsone = new LevelDialog(this);
        View builder = redDialogsone.builder(R.layout.level_reward_item);
        fl_content_one = builder.findViewById(R.id.fl_content_one);
        rela_fanbei = builder.findViewById(R.id.rela_fanbei);
        tv_money = builder.findViewById(R.id.tv_money);
        iv_close = builder.findViewById(R.id.iv_close);
        redDialogsone.setOutCancle(false);
        loadExone();
    }

    private void loadExone() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadExpressAd(this, "ad_ex_lucky_two", 300, 200, new AdCallback() {
            @Override
            public void onDismissed() {

            }

            @Override
            public void onNoAd(AdError adError) {
                Log.d("ccc", "------loadExone------onNoAd: " + adError.getCode());
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
                if (!isshowOne) {
                    adPlatformSDK.showExpressAd();
                }
            }
        }, fl_content_one);
    }

    public void showRedDialogOne(String money) {
        if (!CommonUtils.isDestory(SmokeHbActivity.this)) {
            if (tv_money != null) {
                tv_money.setText(money);
                rela_fanbei.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        type = 2;
                        showVideo();
                        if (redDialogsone != null) {
                            redDialogsone.setDismiss();
                        }
                    }
                });
                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (redDialogsone != null) {
                            redDialogsone.setDismiss();
                        }
                    }
                });
            }
            loadExone();
            final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
            adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
            isshowOne = adPlatformSDK.showExpressAd();
            redDialogsone.setShow();
        }
    }


    private SnatchDialog redDialogsTwo;
    ImageView iv_top;
    TextView tv2;
    TextView tv_noPrize;
    LinearLayout line_money;
    TextView tv_money_two;
    RelativeLayout rela_one;
    RelativeLayout rela_one_one;
    TextView tv_iwantCheat;
    TextView tv_levelNums;
    TextView tv_sureOne;
    TextView tv_sureTwo;
    TextView tv_sureThree;
    RelativeLayout rela_two;
    FrameLayout fl_content_Two;
    private boolean isshowTwo;

    private void initRedDialogTwo() {
        redDialogsTwo = new SnatchDialog(this);
        View builder = redDialogsTwo.builder(R.layout.level_reward_money);
        iv_top = builder.findViewById(R.id.iv_top);
        tv2 = builder.findViewById(R.id.tv2);
        fl_content_Two = builder.findViewById(R.id.fl_content);
        tv_noPrize = builder.findViewById(R.id.tv_noPrize);
        line_money = builder.findViewById(R.id.line_money);
        tv_money_two = builder.findViewById(R.id.tv_money);
        rela_one = builder.findViewById(R.id.rela_one);
        rela_one_one = builder.findViewById(R.id.rela_one_one);
        tv_iwantCheat = builder.findViewById(R.id.tv_iwantCheat);
        tv_levelNums = builder.findViewById(R.id.tv_levelNums);
        tv_sureOne = builder.findViewById(R.id.tv_sureOne);
        tv_sureTwo = builder.findViewById(R.id.tv_sureTwo);
        tv_sureThree = builder.findViewById(R.id.tv_sureThree);
        rela_two = builder.findViewById(R.id.rela_two);
        redDialogsTwo.setOutCancle(false);
        loadExTwo();
    }

    //type  1 2幸运抽红包领金币  2幸运抽红包领金币翻倍  3幸运抽红包领金币不翻倍 4没有抽中
    public void showRedDialogTwo(int type, String money) {
        if (redDialogsTwo != null) {
            if (tv_money_two != null) {
                if (type == 1) {
                    iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain));
                    tv2.setVisibility(View.VISIBLE);
                    line_money.setVisibility(View.VISIBLE);
                    tv_noPrize.setVisibility(View.GONE);
                    rela_one.setVisibility(View.GONE);
                    tv_sureTwo.setVisibility(View.GONE);
                    tv_sureOne.setVisibility(View.VISIBLE);
                    tv_sureThree.setVisibility(View.GONE);
                } else if (type == 2) {
                    tv_iwantCheat.setVisibility(View.GONE);
                    iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain));
                    tv2.setVisibility(View.VISIBLE);
                    line_money.setVisibility(View.VISIBLE);
                    tv_noPrize.setVisibility(View.GONE);
                    rela_one_one.setVisibility(View.VISIBLE);
                    rela_one.setVisibility(View.VISIBLE);
                    tv_sureTwo.setVisibility(View.GONE);
                    tv_sureOne.setVisibility(View.GONE);
                    tv_sureThree.setVisibility(View.VISIBLE);
                } else if (type == 3) {
                    tv_iwantCheat.setVisibility(View.GONE);
                    iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain));
                    tv2.setVisibility(View.VISIBLE);
                    line_money.setVisibility(View.VISIBLE);
                    tv_noPrize.setVisibility(View.GONE);
                    rela_one_one.setVisibility(View.VISIBLE);
                    rela_one.setVisibility(View.GONE);
                    tv_sureTwo.setVisibility(View.GONE);
                    tv_sureOne.setVisibility(View.GONE);
                    tv_sureThree.setVisibility(View.VISIBLE);
                } else if (type == 4) {
                    iv_top.setImageDrawable(getResources().getDrawable(R.drawable.bg_obtain_no));
                    iv_top.setScaleType(ImageView.ScaleType.FIT_XY);
                    tv_iwantCheat.setVisibility(View.VISIBLE);
                    tv2.setVisibility(View.GONE);
                    line_money.setVisibility(View.GONE);
                    tv_noPrize.setVisibility(View.VISIBLE);
                    rela_one_one.setVisibility(View.GONE);
                    rela_one.setVisibility(View.VISIBLE);
                    tv_sureTwo.setVisibility(View.VISIBLE);
                    tv_sureOne.setVisibility(View.GONE);
                    tv_sureThree.setVisibility(View.GONE);
                }
                tv_money_two.setText(money);
                tv_sureOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (redDialogsTwo != null) {
                            redDialogsTwo.setDismiss();
                        }
                    }
                });
                tv_sureTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (redDialogsTwo != null) {
                            redDialogsTwo.setDismiss();
                        }
                    }
                });
                tv_sureThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (redDialogsTwo != null) {
                            redDialogsTwo.setDismiss();
                        }
                    }
                });
            }
            loadExTwo();
            final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
            adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
            isshowTwo = adPlatformSDK.showExpressAd();
            if (!CommonUtils.isDestory(SmokeHbActivity.this)) {
                redDialogsTwo.setShow();
            }
        }
    }

    private void loadExTwo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadExpressAd(this, "ad_ex_lucky", 300, 200, new AdCallback() {
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
                if (!isshowTwo) {
                    adPlatformSDK.showExpressAd();
                }
            }
        }, fl_content_Two);
    }


    private void loadVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadRewardVideoVerticalAd(this, "ad_luckys", new AdCallback() {
            @Override
            public void onDismissed() {
                UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                if (type == 1) {
                    VUiKit.postDelayed(400, () -> {
                        initOpenAnim(null);
                    });
                } else if (type == 2) {
                    mPresenter.getLuckyMoney(userInfo.getImei(), userInfo.getGroup_id(), "1", redId);
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                Log.d("ccc", "--------loadVideo---------onNoAd: "+adError.getCode()+"--"+adError.getMessage());
            }

            @Override
            public void onComplete() {
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
            }

            @Override
            public void onPresent() {

            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {

            }
        });
    }


    private void showVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        loadVideo();
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        adPlatformSDK.showRewardVideoAd();
    }


    public static void smokehbJump(Context context) {
        Intent intent = new Intent(context, SmokeHbActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void getLuckyRedSuccess(SmokeHbBeans data) {
        List<SmokeHbBeans.ListBean> list = data.getList();
        if (list != null && list.size() > 0) {
            getRedNums = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getStatus() == 1) {
                    getRedNums = getRedNums+1;
                }
            }
            ivTop.setImageDrawable(getResources().getDrawable(R.drawable.bg_index));
            lineCount.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            smokeAdapter.setNewData(list);
            smokeAdapter.notifyDataSetChanged();
        } else {
            ivTop.setImageDrawable(getResources().getDrawable(R.drawable.bg_timing));
            lineCount.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            long next_time = data.getNext_time();
            long sys_time = data.getSys_time();
            if (next_time != 0 && sys_time != 0 && next_time > sys_time) {
                long yuTimes = (next_time - sys_time) * 1000;
                countDownUtils.setHours(TimesUtils.getHourDiff(yuTimes), TimesUtils.getMinDiff(yuTimes), TimesUtils.getSecondDiff(yuTimes));
            }
        }
    }

    @Override
    public void updtreasureSuccess(UpQuanNumsBeans data) {

    }

    @Override
    public void getLuckyMoneySuccess(SmokeBeans data) {
        tvRedNums.setText("今日剩余红包：" + data.getLucky_num());
        tvRedNumsOne.setText("今日剩余红包：" + data.getLucky_num());
        if (type == 1) {
            List<SmokeHbBeans.ListBean> lists = smokeAdapter.getData();
            lists.get(index).setStatus(1);
            getRedNums=getRedNums+1;
            smokeAdapter.notifyDataSetChanged();
            showRedDialogOne(data.getMoney());
        } else if (type == 2) {
            showRedDialogTwo(1, data.getDouble_money());
        }
    }

    /**
     * 卡牌文本介绍打开效果：注意旋转角度
     */
    private int duration = 800;
    private Rotate3dAnimation openAnimation;

    private void initOpenAnim(View view) {
        float centerX = 0;
        float centerY = 0;
        if (view!=null){
            // 计算中心点
            centerX = view.getWidth() / 2.0f;
            centerY = view.getHeight() / 2.0f;
        }else {
            if (imageView != null) {
                centerX = imageView.getWidth() / 2.0f;
                centerY = imageView.getHeight() / 2.0f;
            }
        }
        //从0到90度，顺时针旋转视图，此时reverse参数为true，达到90度时动画结束时视图变得不可见，
        openAnimation = new Rotate3dAnimation(0, 540, centerX, centerY, centerY, true);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setInterpolator(new LinearInterpolator());
        float finalCenterX = centerX;
        float finalCenterY = centerY;
        float finalCenterY1 = centerY;
        openAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // mLogoIv.setVisibility(View.GONE);
                // mDescTv.setVisibility(View.VISIBLE);
                //   从270到360度，顺时针旋转视图，此时reverse参数为false，达到360度动画结束时视图变得可见
                Rotate3dAnimation rotateAnimation = new Rotate3dAnimation(540, 0, finalCenterX, finalCenterY, finalCenterY1, false);
                rotateAnimation.setDuration(duration);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                        mPresenter.getLuckyMoney(userInfo.getImei(), userInfo.getGroup_id(), "0", redId);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                if (view != null) {
                    view.startAnimation(rotateAnimation);
                } else {
                    if (imageView != null) {
                        imageView.startAnimation(rotateAnimation);
                    }
                }
            }
        });
        if (view != null) {
            view.startAnimation(openAnimation);
        } else {
            if (imageView != null) {
                imageView.startAnimation(openAnimation);
            }
        }
    }

}