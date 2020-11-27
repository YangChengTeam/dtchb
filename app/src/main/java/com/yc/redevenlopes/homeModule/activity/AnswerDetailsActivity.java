package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.GuessDialog;
import com.yc.redevenlopes.homeModule.adapter.CommonPagerAdapter;
import com.yc.redevenlopes.homeModule.contact.AnswerDetailsContact;
import com.yc.redevenlopes.homeModule.fragment.AnswerFragment;
import com.yc.redevenlopes.homeModule.module.bean.AnsPostRecordBeans;
import com.yc.redevenlopes.homeModule.module.bean.AnswerQuestionListBeans;
import com.yc.redevenlopes.homeModule.present.AnswerDetailsPresenter;
import com.yc.redevenlopes.homeModule.widget.AnswerIndexView;
import com.yc.redevenlopes.homeModule.widget.NoScrollViewPager;
import com.yc.redevenlopes.service.event.Event;
import com.yc.redevenlopes.utils.CacheDataUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 答题详情
 */
public class AnswerDetailsActivity extends BaseActivity<AnswerDetailsPresenter> implements AnswerDetailsContact.View {

    @BindView(R.id.line_start)
    LinearLayout lineStart;
    @BindView(R.id.line_ans)
    LinearLayout lineAns;
    @BindView(R.id.rela_ansFinshResurrection)
    RelativeLayout relaAnsFinshResurrection;
    @BindView(R.id.rela_ansFinshBack)
    RelativeLayout relaAnsFinshBack;
    @BindView(R.id.tv_startNums)
    TextView tvStartNums;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;

    @BindView(R.id.iv_giveUp)
    ImageView ivGiveUp;
    @BindView(R.id.tv_giveUp)
    TextView tvGiveUp;
    @BindView(R.id.answerIndexView)
    AnswerIndexView answerIndexView;
    @BindView(R.id.tv_tatolQuestion)
    TextView tvTatolQuestion;
    private int type;//1 开始 2 答题  2答题结束复活 4答题结束返回
    private List<Fragment> listData;
    private List<String> list_title;
    private CommonPagerAdapter pagerAdapter;
    public List<AnswerQuestionListBeans> data = new ArrayList<>();
    private List<String> viewStatusList = new ArrayList<>();
    private int indexs;//当前答题进度
    private int total;
    private String answerId;
    private int ansType = 1;
    private CountDownTimer downTimer = new CountDownTimer(5 * 1000, 1000) {
        @Override
        public void onTick(long time) {
            tvStartNums.setText((time / 1000) + "");
        }

        @Override
        public void onFinish() {
            type = 2;
            setViews();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_answer_details;
    }

    @Override
    public void initEventAndData() {
        answerId = getIntent().getStringExtra("answerId");
        total = getIntent().getIntExtra("total", 1);
        type = 1;
        setViews();
        initDatas(answerId);
    }


    private void initDatas(String answerId) {
        mPresenter.getDetailsQuestionList(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", answerId);
    }

    private void initViewpager(List<AnswerQuestionListBeans> data) {
        list_title = new ArrayList<>();
        listData = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            list_title.add("问题" + i);
            AnswerFragment answerFragment = AnswerFragment.newInstance(i);
            listData.add(answerFragment);
            viewStatusList.add("1");
        }
        answerIndexView.setDatas(data.size());
        pagerAdapter = new CommonPagerAdapter(getSupportFragmentManager(), listData, list_title);
        viewpager.setAdapter(pagerAdapter);
        viewpager.setOffscreenPageLimit(0);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public void setStepType(int index, String answerType) {
        indexs = index;
        if (index == data.size() - 1) {//答题结束
            type = 3;
            ansType = 1;
            setViews();
            mPresenter.postAnserRecord(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", answerId, "0");
        } else {
            if (total == 1) {
                if ("3".equals(answerType)) {
                    viewStatusList.set(index, "3");
                    answerIndexView.setIndex(viewStatusList);
                } else {//回答正确，下一题
                    viewStatusList.set(index, "2");
                    answerIndexView.setIndex(viewStatusList);
                }
                setPager(indexs);
            } else {
                if ("3".equals(answerType)) {//回答错误 //需要复活
                    viewStatusList.set(index, "3");
                    answerIndexView.setIndex(viewStatusList);
                    ansType = 2;
                    mPresenter.postAnserRecord(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", answerId, "1");
                } else {//回答正确，下一题
                    viewStatusList.set(index, "2");
                    answerIndexView.setIndex(viewStatusList);
                    setPager(indexs);
                }
            }
        }
    }

    private void setPager(int index) {
        AnswerFragment fragmentss = (AnswerFragment) listData.get(index);
        boolean b = fragmentss.getisPaused();
        int currIndex = index + 1;
        viewpager.setCurrentItem(currIndex);
        AnswerFragment fragment = (AnswerFragment) listData.get(currIndex);
        if (fragment != null) {
            fragment.setStartVa();
        }
    }

    private void setViews() {
        if (type == 1) {
            lineStart.setVisibility(View.VISIBLE);
            lineAns.setVisibility(View.GONE);
            relaAnsFinshBack.setVisibility(View.GONE);
            relaAnsFinshResurrection.setVisibility(View.GONE);
            downTimer.start();
        } else if (type == 2) {
            lineStart.setVisibility(View.GONE);
            lineAns.setVisibility(View.VISIBLE);
            relaAnsFinshBack.setVisibility(View.GONE);
            relaAnsFinshResurrection.setVisibility(View.GONE);
            viewpager.setCurrentItem(0);
            AnswerFragment fragment = (AnswerFragment) listData.get(0);
            fragment.setStartVa();
        } else if (type == 3) {
            tvGiveUp.setVisibility(View.GONE);
            ivGiveUp.setImageDrawable(getResources().getDrawable(R.drawable.icon_back));
            lineStart.setVisibility(View.GONE);
            lineAns.setVisibility(View.GONE);
            relaAnsFinshBack.setVisibility(View.VISIBLE);
            relaAnsFinshResurrection.setVisibility(View.GONE);
        } else if (type == 4) {
            tvGiveUp.setVisibility(View.GONE);
            ivGiveUp.setImageDrawable(getResources().getDrawable(R.drawable.icon_back));
            lineStart.setVisibility(View.GONE);
            lineAns.setVisibility(View.GONE);
            relaAnsFinshBack.setVisibility(View.GONE);
            relaAnsFinshResurrection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (downTimer != null) {
            downTimer.cancel();
            downTimer = null;
        }
        if (guessDialog != null) {
            guessDialog.setDismiss();
        }
    }

    public static void AnswerDetailsJump(Context context, String answerId, int total) {
        Intent intent = new Intent(context, AnswerDetailsActivity.class);
        intent.putExtra("answerId", answerId);
        intent.putExtra("total", total);
        context.startActivity(intent);
    }

    @OnClick({R.id.line_back, R.id.tv_ansfinshresu_back, R.id.line_ansfinshResurrec, R.id.tv_ansfinshBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_back:
                if (type == 1 || type == 2) {
                    showGuessDialog();
                } else {
                    finish();
                }
                break;
            case R.id.tv_ansfinshresu_back://放弃
                finish();
                break;
            case R.id.line_ansfinshResurrec://复活
                AnswerFragment fragment = (AnswerFragment) listData.get(indexs);
                fragment.setStopVa();
                showVideo();
                break;
            case R.id.tv_ansfinshBack:
                finish();
                break;
        }
    }

    private GuessDialog guessDialog;

    private void showGuessDialog() {
        guessDialog = new GuessDialog(this);
        View builder = guessDialog.builder(R.layout.guess_item);
        TextView tv_title = builder.findViewById(R.id.tv_title);
        TextView tv_des = builder.findViewById(R.id.tv_des);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_cancle = builder.findViewById(R.id.tv_cancle);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessDialog.setDismiss();
                finish();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessDialog.setDismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessDialog.setDismiss();
            }
        });
        guessDialog.setShow();
    }

    @Override
    public void getDetailsQuestionListSuccess(List<AnswerQuestionListBeans> datass) {
        data.clear();
        data.addAll(datass);
        tvTatolQuestion.setText("答完"+datass.size()+"题，即可获得红包");
        initViewpager(datass);
    }

    @Override
    public void postAnserRecordSuccess(AnsPostRecordBeans data) {
        if (data.getNew_level() > 0) {
            EventBus.getDefault().post(new Event.CashEvent());
        }
        if (ansType == 2) {
            int is_continue = data.getIs_continue();
            if (is_continue == 0) {//没有复活机会
                type = 3;
                tvGiveUp.setVisibility(View.GONE);
                ivGiveUp.setImageDrawable(getResources().getDrawable(R.drawable.icon_back));
                lineStart.setVisibility(View.GONE);
                lineAns.setVisibility(View.GONE);
                relaAnsFinshBack.setVisibility(View.VISIBLE);
                relaAnsFinshResurrection.setVisibility(View.GONE);
                type = 3;
                setViews();
            } else {
                type = 4;
                setViews();
            }
        }
    }

    private void showVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.showRewardVideoVerticalAd(this, new AdCallback() {
            @Override
            public void onDismissed() {
                type = 2;
                lineStart.setVisibility(View.GONE);
                lineAns.setVisibility(View.VISIBLE);
                relaAnsFinshBack.setVisibility(View.GONE);
                relaAnsFinshResurrection.setVisibility(View.GONE);
                setPager(indexs);
            }

            @Override
            public void onNoAd(AdError adError) {

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
        });
    }


}