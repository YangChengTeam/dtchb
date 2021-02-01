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
import com.yc.redevenlopes.homeModule.widget.ToastShowViews;
import com.yc.redevenlopes.service.event.Event;
import com.yc.redevenlopes.utils.CacheDataUtils;
import com.yc.redevenlopes.utils.CommonUtils;
import com.yc.redevenlopes.utils.SoundPoolUtils;
import com.yc.redevenlopes.utils.ToastUtilsViews;
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
    private String isGetRed;//是否领取了红包
    public String money;
    private String id;
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
        money = getIntent().getStringExtra("money");
        id=getIntent().getStringExtra("id");
        type = 1;
        setViews();
        initDatas(answerId);
        loadVideo();
    }


    private void initDatas(String answerId) {
        mPresenter.getDetailsQuestionList(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", answerId);
    }

    private void initViewpager(List<AnswerQuestionListBeans> data) {
        if (data!=null&&data.size()>0){
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
            downTimer.start();
        }
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public void setStepType(int index, String answerType) {
        indexs = index;
        if (index == listData.size() - 1) {//答题结束
            type = 3;
            ansType = 1;
            setViews();
            mPresenter.postAnserRecord(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", answerId, "0");
        } else if(index < listData.size() - 1){
            if (total == 1) {
                if (viewStatusList!=null&&answerIndexView!=null){
                    if ("3".equals(answerType)) {
                        viewStatusList.set(index, "3");
                        answerIndexView.setIndex(viewStatusList);
                    } else {//回答正确，下一题
                        viewStatusList.set(index, "2");
                        answerIndexView.setIndex(viewStatusList);
                    }
                }
                setPager(indexs);
            } else {
                if ("3".equals(answerType)) {//回答错误 //需要复活
                    if (viewStatusList!=null&&answerIndexView!=null){
                        viewStatusList.set(index, "3");
                        answerIndexView.setIndex(viewStatusList);
                    }
                    ansType = 2;
                    mPresenter.postAnserRecord(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", answerId, "1");
                } else {//回答正确，下一题
                    if (viewStatusList!=null&&answerIndexView!=null){
                        viewStatusList.set(index, "2");
                        answerIndexView.setIndex(viewStatusList);
                    }
                    setPager(indexs);
                }
            }
        }else if (index>listData.size() - 1){
            type = 3;
            ansType = 1;
            setViews();
            mPresenter.postAnserRecord(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", answerId, "0");
        }
    }

    private void setPager(int index)  {
        if (listData!=null){
            int currIndex = index + 1;
            if (currIndex<=listData.size()-1){
                viewpager.setCurrentItem(currIndex,false);
                AnswerFragment fragment = (AnswerFragment) listData.get(currIndex);
                if (fragment != null) {
                    fragment.setStartVa();
                }
            }else {
                type = 3;
                ansType = 1;
                setViews();
                mPresenter.postAnserRecord(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", answerId, "0");
            }
        }
    }

    private void setViews() {
        if (type == 1) {
            lineStart.setVisibility(View.VISIBLE);
            lineAns.setVisibility(View.GONE);
            relaAnsFinshBack.setVisibility(View.GONE);
            relaAnsFinshResurrection.setVisibility(View.GONE);
        } else if (type == 2) {
            lineStart.setVisibility(View.GONE);
            lineAns.setVisibility(View.VISIBLE);
            relaAnsFinshBack.setVisibility(View.GONE);
            relaAnsFinshResurrection.setVisibility(View.GONE);
            if (listData!=null&&listData.size()>0){
                viewpager.setCurrentItem(0,false);
                AnswerFragment fragment = (AnswerFragment) listData.get(0);
                fragment.setStartVa();
            }
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
            guessDialog=null;
        }

        if (answerIndexView!=null){
            answerIndexView= null;
        }
    }

    public static void AnswerDetailsJump(Context context, String answerId, int total,String money,String id) {
        Intent intent = new Intent(context, AnswerDetailsActivity.class);
        intent.putExtra("answerId", answerId);
        intent.putExtra("total", total);
        intent.putExtra("money", money);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @OnClick({R.id.line_back, R.id.tv_ansfinshresu_back, R.id.line_ansfinshResurrec, R.id.tv_ansfinshBack})
    public void onViewClicked(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
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
                if (indexs<=listData.size()-1){
                    AnswerFragment fragment = (AnswerFragment) listData.get(indexs);
                    fragment.setStopVa();
                    showVideo();
                }
                break;
            case R.id.tv_ansfinshBack://跳转到领取红包详情
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
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                guessDialog.setDismiss();
                finish();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
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
        }else {
            RobRedEvenlopesActivity.robRedEvenlopesJump(AnswerDetailsActivity.this, "3", "答题红包", "", money,"","");
            finish();
        }
    }


    private void showVideo() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId()+"");
        loadVideo();
        adPlatformSDK.showRewardVideoAd();
    }
    private void loadVideo(){
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadRewardVideoVerticalAd(this,"ad_fuhuo", new AdCallback() {
            @Override
            public void onDismissed() {
                if (!CommonUtils.isDestory(AnswerDetailsActivity.this)){
                    ToastShowViews.getInstance().cancleToast();
                }
                if (!CommonUtils.isDestory(AnswerDetailsActivity.this)){
                    ToastUtilsViews.showCenterToast("1","");
                }
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
                if (!CommonUtils.isDestory(AnswerDetailsActivity.this)){
                    ToastShowViews.getInstance().cancleToast();
                }
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
            }

            @Override
            public void onPresent() {
                if (!CommonUtils.isDestory(AnswerDetailsActivity.this)){
                    ToastShowViews.getInstance().showMyToast();
                }
            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {

            }
        });
    }


}