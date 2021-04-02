package com.yc.redguess.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAD;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAdListener;
import com.qq.e.comm.util.VideoAdValidity;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.redguess.R;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.constants.Constant;
import com.yc.redguess.dialog.GuessDialog;
import com.yc.redguess.homeModule.adapter.CommonPagerAdapter;
import com.yc.redguess.homeModule.contact.AnswerDetailsContact;
import com.yc.redguess.homeModule.fragment.AnswerFragment;
import com.yc.redguess.homeModule.module.bean.AnsPostRecordBeans;
import com.yc.redguess.homeModule.module.bean.AnswerQuestionListBeans;
import com.yc.redguess.homeModule.present.AnswerDetailsPresenter;
import com.yc.redguess.homeModule.widget.AnswerIndexView;
import com.yc.redguess.homeModule.widget.NoScrollViewPager;
import com.yc.redguess.homeModule.widget.ToastShowViews;
import com.yc.redguess.service.event.Event;
import com.yc.redguess.utils.AppSettingUtils;
import com.yc.redguess.utils.CacheDataUtils;
import com.yc.redguess.utils.CommonUtils;
import com.yc.redguess.utils.SoundPoolUtils;
import com.yc.redguess.utils.ToastUtilsViews;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        loadTx();
        initDatas(answerId);
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
        if (mRewardVideoAD != null) {
            mRewardVideoAD.destroy();
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
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        showVideo();
                    }else {
                        showTx();
                    }
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

    private String isLoadAdSuccess="0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
    private void showVideo() {
        isLoadAdSuccess="1";
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
                    ToastShowViews.cancleToast();
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
                if ("1".equals(isLoadAdSuccess)){
                    isLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("1".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                        showTx();
                    }else {
                        if (!CommonUtils.isDestory(AnswerDetailsActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    }
                }
            }

            @Override
            public void onComplete() {
                if (!CommonUtils.isDestory(AnswerDetailsActivity.this)){
                    ToastShowViews.cancleToast();
                }
              //  mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
            }

            @Override
            public void onPresent() {
                isLoadAdSuccess="3";
                if (!CommonUtils.isDestory(AnswerDetailsActivity.this)){
                    ToastShowViews.showMyToast();
                }
            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {
                isLoadAdSuccess="3";
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVideo();
    }


    public void showTx(){
        if (mRewardVideoAD == null || !mIsLoaded) {
            // showToast("广告未拉取成功！");
            loadTxTwo();
            if ("1".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                if (!CommonUtils.isDestory(AnswerDetailsActivity.this)) {
                    ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                }
            }else {
                showVideo();
            }
        }else {
            VideoAdValidity validity = mRewardVideoAD.checkValidity();
            switch (validity) {
                case SHOWED:
                case OVERDUE:
                    loadTxTwo();
                    if ("1".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                        if (!CommonUtils.isDestory(AnswerDetailsActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    }else {
                        showVideo();
                    }
                    return;
                // 在视频缓存成功后展示，以省去用户的等待时间，提升用户体验
                case NONE_CACHE:
                    //  showToast("广告素材未缓存成功！");
//            return;
                case VALID:
                    // 在视频缓存成功后展示，以省去用户的等待时间，提升用户体验
                    isTxLoadAdSuccess="1";
                    mRewardVideoAD
                            .showAD(AnswerDetailsActivity.this);
                    // 展示广告
                    break;
            }
        }

    }
    public void loadTxTwo(){
        mIsLoaded=false;
        loadTx();
    }
    private ExpressRewardVideoAD mRewardVideoAD;
    private boolean mIsLoaded;
    private boolean mIsCached;
    private String isTxLoadAdSuccess="0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
    public void loadTx(){
        mRewardVideoAD = new ExpressRewardVideoAD(this, Constant.TXRVIDEO, new ExpressRewardVideoAdListener() {
            @Override
            public void onAdLoaded() {
                mIsLoaded = true;
                isTxLoadAdSuccess="3";
            }

            @Override
            public void onVideoCached() {
                // 在视频缓存完成之后再进行广告展示，以保证用户体验
                mIsCached = true;

            }

            @Override
            public void onShow() {
                isTxLoadAdSuccess="3";
                AppSettingUtils.showTxShow("tx_ad_shuzijingcai");
                if (!CommonUtils.isDestory(AnswerDetailsActivity.this)){
                    ToastShowViews.showMyToast();
                }
            }

            @Override
            public void onExpose() {
                Log.i("ccc", "onExpose: ");
            }

            /**
             * 模板激励视频触发激励
             *
             * @param map 若选择了服务端验证，可以通过 ServerSideVerificationOptions#TRANS_ID 键从 map 中获取此次交易的 id；若未选择服务端验证，则不需关注 map 参数。
             */
            @Override
            public void onReward(Map<String, Object> map) {
                //  Object o = map.get(ServerSideVerificationOptions.TRANS_ID); // 获取服务端验证的唯一 ID
                //   Log.i("ccc", "onReward " + o);
            }

            @Override
            public void onClick() {
                AppSettingUtils.showTxClick("tx_ad_shuzijingcai");
            }

            @Override
            public void onVideoComplete() {
                if (mRewardVideoAD.hasShown()){
                    loadTxTwo();
                }
                if (!CommonUtils.isDestory(AnswerDetailsActivity.this)){
                    ToastShowViews.cancleToast();
                }
            }

            @Override
            public void onClose() {
                if (mRewardVideoAD.hasShown()){
                    loadTxTwo();
                }
                if (!CommonUtils.isDestory(AnswerDetailsActivity.this)){
                    ToastShowViews.cancleToast();
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
            public void onError(com.qq.e.comm.util.AdError adError) {
                if ("1".equals(isTxLoadAdSuccess)){
                    isTxLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("2".equals(AppSettingUtils.getVideoTypeTwo())){//先头条
                        showVideo();
                    }else {
                        if (!CommonUtils.isDestory(AnswerDetailsActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    }
                }
            }
        });
        // 设置播放时静音状态
        // mRewardVideoAD.setVolumeOn(volumeOn);
        // 拉取广告
        mRewardVideoAD.loadAD();
        // 展示广告
    }


}