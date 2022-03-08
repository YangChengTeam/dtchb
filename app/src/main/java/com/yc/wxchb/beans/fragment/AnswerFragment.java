package com.yc.wxchb.beans.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.voice.SPlayer;
import com.alex.voice.listener.PlayerListener;
import com.alex.voice.player.SMediaPlayer;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.umeng.analytics.MobclickAgent;
import com.yc.wxchb.R;
import com.yc.wxchb.application.Constant;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.base.BaseLazyFragment;
import com.yc.wxchb.beans.activity.AdHotActivity;
import com.yc.wxchb.beans.adapter.AnswerFgAdapter;
import com.yc.wxchb.beans.contact.AnswerFgContact;
import com.yc.wxchb.beans.module.beans.AnswerFanBeiBeans;
import com.yc.wxchb.beans.module.beans.AnswerFgBeans;
import com.yc.wxchb.beans.module.beans.AnswerFgQuestionBeans;
import com.yc.wxchb.beans.module.beans.HotNumsInfoBeans;
import com.yc.wxchb.beans.module.beans.QuestionRightBeans;
import com.yc.wxchb.beans.module.beans.UserInfo;
import com.yc.wxchb.beans.present.AnswerFgPresenter;
import com.yc.wxchb.dialog.PrizeDialog;
import com.yc.wxchb.dialog.RedDialogTwo;
import com.yc.wxchb.dialog.SnatchDialog;
import com.yc.wxchb.utils.AppSettingUtils;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.ClickListenName;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.CountDownUtilsThree;
import com.yc.wxchb.utils.SoundPoolUtils;
import com.yc.wxchb.utils.ToastUtilsViewsTwo;
import com.yc.wxchb.utils.VUiKit;
import com.yc.wxchb.utils.ad.GromoreAdShow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class AnswerFragment extends BaseLazyFragment<AnswerFgPresenter> implements AnswerFgContact.View {
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_answerNums)
    TextView tvAnswerNums;
    @BindView(R.id.tv_answerSureNums)
    TextView tvAnswerSureNums;
    @BindView(R.id.tv_answerTitle)
    TextView tvAnswerTitle;

    @BindView(R.id.progressbar_reward)
    ProgressBar progressbarReward;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.iv_ans_soul)
    ImageView ivAnsSoul;
    @BindView(R.id.line_soul)
    LinearLayout line_soul;

    private AnswerFgAdapter answerFgAdapter;
    private int page = 1;
    private int pagesize = 4;
    public int answerPositions;//答题的数量
    public int continueNums;//连对的数量
    public int righNums;//答对的数量
    private int videoType;// 1 答题  2 答题翻倍  3 在线红包  4 复活   5在线红包翻倍
    private int info_id;//看视频翻倍
    private boolean isAnswerSure;//答题是否正确
    private boolean isRedClick;//红包是否可以点击领取
    private CountDownUtilsThree countDownUtilsThree;
    public AnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grab_red, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = (View) super.onCreateView(inflater, container, savedInstanceState);
        return (View) rootView;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initLazyData() {
        initRecyclerView();
        initDatas();
        initRedRewardContinueDialog();
        initCountDownUtilsThree();
        mPresenter.getHotInfo(CacheDataUtils.getInstance().getUserInfo().getId()+"",((MyApplication) MyApplication.getInstance()).getAgentId(),"2");
    }

    public boolean isEnd;
    private void initCountDownUtilsThree() {
        countDownUtilsThree = new CountDownUtilsThree();
        countDownUtilsThree.setOnCountDownListen(new CountDownUtilsThree.OnCountDownListen() {
            @Override
            public void count(long mMin, long mSecond) {
                isEnd=false;
                String timesContents = getTv(mMin) + ":" + getTv(mSecond);
                setTimesData(timesContents,false);
            }

            @Override
            public void countFinsh() {
                isEnd=true;
                setTimesData("",true);
            }
        });
    }

    private String getTv(long l) {
        if (l >= 10) {
            return l + "";
        } else {
            return "0" + l;//小于10,,前面补位一个"0"
        }
    }

    private RedDialogTwo redDialogjiang;
    private RedDialogTwo redRewardDialogjiang;
    private PrizeDialog redPrizetwoDialog;

    private FrameLayout fl_banner;
    public void redRewardDialog(int type, String red_money, String other_money, String other_percent,int continue_num) {
        redDialogjiang = new RedDialogTwo(getActivity());
        View builder = redDialogjiang.builder(R.layout.redreward_dialog_item);
        TextView tv_moneys=builder.findViewById(R.id.tv_moneys);
        LinearLayout line_sure=builder.findViewById(R.id.line_sure);
        TextView tv_liandui=builder.findViewById(R.id.tv_liandui);
        fl_banner=builder.findViewById(R.id.fl_banner);
        TextView tv_next=builder.findViewById(R.id.tv_next);
        LinearLayout line_liandui=builder.findViewById(R.id.line_liandui);
        TextView tv_continue_nums=builder.findViewById(R.id.tv_continue_nums);
        tv_moneys.setText("+"+red_money+"元");
        if (type==1){
            line_liandui.setVisibility(View.GONE);
        }else {
            if (!TextUtils.isEmpty(other_money)){
                tv_moneys.setText("+"+red_money+"元");
                tv_liandui.setText(other_percent+"%("+other_money+"元)");
                tv_continue_nums.setText("x"+continue_num);
            }
            line_liandui.setVisibility(View.VISIBLE);
        }

        line_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                videoType=2;
                if (!CommonUtils.isDestory(getActivity())){
                    showjiliAd();
                }
                redDialogjiang.setDismiss();
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //======================================判断弹窗============================================================================================================================
                redDialogjiang.setDismiss();
                if (AppSettingUtils.commonYou(getActivity())){
                    if (huoli_question_user_day>=huoli_first_video){
                        if (hotShowIndexList.contains(String.valueOf(huoli_question_user_day))){
                            initTisuWithDraw();
                        }
                    }
                }
                nextAnswer();

            }
        });
        redDialogjiang.setOutCancle(false);
        if (!CommonUtils.isDestory(getActivity())){
            redDialogjiang.setShow();
            ToastUtilsViewsTwo.showCenterToastTwo(huoli_award);
        }
    }


    private ImageView iv_close;
    private RelativeLayout rela_open;
    private FrameLayout fl_open;
    public void initRedOpenDialog() {
        redDialogjiang = new RedDialogTwo(getActivity());
        View builder = redDialogjiang.builder(R.layout.redopen_dialog_item);
        rela_open=builder.findViewById(R.id.rela_open);
        iv_close=builder.findViewById(R.id.iv_close);
        fl_open=builder.findViewById(R.id.fl_ad_containeropen);
        redDialogjiang.setOutCancle(false);
    }

    public void redOpenDialog() {
        if (!CommonUtils.isDestory(getActivity())&&redDialogjiang!=null){
            redDialogjiang.setShow();
            ToastUtilsViewsTwo.showCenterToastTwo(huoli_award);
            iv_close.setOnClickListener(view ->{
                redDialogjiang.setDismiss();
            });
        }
    }

    private int exType;
    public void redPrizetwoDialog(String moneys) {
        redPrizetwoDialog = new PrizeDialog(getActivity());
        View builder = redPrizetwoDialog.builder(R.layout.redprizetwo_dialog_item);
        TextView tv_mone=builder.findViewById(R.id.tv_mone);
        tv_mone.setText("奖励您"+moneys+"元");
        if (!CommonUtils.isDestory(getActivity())){
            redPrizetwoDialog.setShow();
        }
        redPrizetwoDialog.setDismissListen(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (videoType==2){
                    //-================================================判断弹窗=============================================================
                    if (AppSettingUtils.commonYou(getActivity())){
                        if (huoli_question_user_day>=huoli_first_video){
                            if (hotShowIndexList.contains(String.valueOf(huoli_question_user_day))){
                                initTisuWithDraw();
                            }
                        }
                    }
                }
            }
        });
    }
    private SnatchDialog redDialog;


    private    TextView tv_next;
    private   LinearLayout line_sure;
    private   FrameLayout fl_redReward_container;
    private  boolean isshowtwo;
    public void initRedRewardContinueDialog() {
        redRewardDialogjiang = new RedDialogTwo(getActivity());
        View builder = redRewardDialogjiang.builder(R.layout.redrewardcontiune_dialog_item);
        fl_redReward_container = builder.findViewById(R.id.fl_ad_containerss);
        line_sure=builder.findViewById(R.id.line_sure);
        tv_next=builder.findViewById(R.id.tv_next);
        redRewardDialogjiang.setOutCancle(false);
    }

    public void redRewardContinueDialog() {
     if (redRewardDialogjiang!=null){
         line_sure.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SoundPoolUtils instance = SoundPoolUtils.getInstance();
                 instance.initSound();
                 videoType=4;
                 if (!CommonUtils.isDestory(getActivity())){
                     showjiliAd();
                 }
                 redRewardDialogjiang.setDismiss();
             }
         });
         tv_next.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 SoundPoolUtils instance = SoundPoolUtils.getInstance();
                 instance.initSound();
                 continueNums=0;
                 UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                 mPresenter.questionAdd(userInfo.getId()+"", 1, continueNums);
                 redRewardDialogjiang.setDismiss();
                 nextAnswer();
                 //======================================判断弹窗============================================================================================================================
                 if (AppSettingUtils.commonYou(getActivity())){
                     if (huoli_question_user_day>=huoli_first_video){
                         if (hotShowIndexList.contains(String.valueOf(huoli_question_user_day))){
                             initTisuWithDraw();
                         }
                     }
                 }
             }
         });
         exType=2;
         loadExpressAd(fl_redReward_container);
         redRewardDialogjiang.setOutCancle(false);
         if (!CommonUtils.isDestory(getActivity())){
             redRewardDialogjiang.setShow();
             ToastUtilsViewsTwo.showCenterToastTwo(huoli_award);
         }
      }
    }






    @OnClick({R.id.line_soul,R.id.iv_back})
    public void onViewClicked(View view) {
        SoundPoolUtils instance = SoundPoolUtils.getInstance();
        instance.initSound();
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.line_soul:
                String ansSoul = CacheDataUtils.getInstance().getAnsSoul();
                if (TextUtils.isEmpty(ansSoul)){//有声音
                    CacheDataUtils.getInstance().setAnsSoul("1");
                    ivAnsSoul.setImageResource(R.drawable.answer_lbclose);
                }else {
                    CacheDataUtils.getInstance().setAnsSoul("");
                    ivAnsSoul.setImageResource(R.drawable.answer_lbopen);
                }
                break;
        }
    }

    private int position;
    private ItemTouchHelper itemTouchHelper;
    private void initRecyclerView() {
        position = 0;;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false){
            //禁止水平滑动
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        recyclerView.setLayoutManager(linearLayoutManager);
        answerFgAdapter = new AnswerFgAdapter(null, getActivity());
        recyclerView.setNestedScrollingEnabled(false);
        answerFgAdapter.setOnClickListens(new AnswerFgAdapter.OnClickListens() {
            @Override
            public void clicks(int position, int answerPosition, boolean isSure) {
                if (ClickListenName.isFastClick()){
                    SoundPoolUtils instance = SoundPoolUtils.getInstance();
                    instance.initSound();
                    MobclickAgent.onEvent(getActivity(), "answer", "1");//参数二为当前统计的事件ID
                    answerPositions = answerPosition;
                    isAnswerSure=isSure;
                    lastIndex=answerPosition;
                    if (video_num==0){
                        video_num=4;
                    }
                    if (((MyApplication) MyApplication.getInstance()).answerNums>0&& ((MyApplication) MyApplication.getInstance()).answerNums%video_num==0){
                        videoType=1;
                        showjiliAd();
                    }else {
                        getRed();
                    }
                    ((MyApplication) MyApplication.getInstance()).answerNums+=1;
                }
            }
        });
        recyclerView.setAdapter(answerFgAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        String ansSoul = CacheDataUtils.getInstance().getAnsSoul();
        if (TextUtils.isEmpty(ansSoul)){//有声音
            ivAnsSoul.setImageResource(R.drawable.answer_lbopen);
        }else {
            ivAnsSoul.setImageResource(R.drawable.answer_lbclose);
        }
    }

    public  void getRed(){
        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
        if (isAnswerSure) {//答对
            righNums += 1;
            continueNums += 1;
            mPresenter.questionAdd(userInfo.getId()+"", 0, continueNums);
        } else {//答错
            redRewardContinueDialog();
        }
        List<AnswerFgBeans.QuestionListBean> data = answerFgAdapter.getData();
        if (answerPositions >= data.size() - 3) {
            page += 1;
            initDatas();
        }
    }

    private void nextAnswer() {
        List<AnswerFgBeans.QuestionListBean> data = answerFgAdapter.getData();
        if (answerPositions + 1 <= data.size() - 1) {
            AnswerFgBeans.QuestionListBean questionListBean = data.get(answerPositions + 1);
            VUiKit.postDelayed(600, () -> {
               // recyclerView.smoothScrollToPosition(answerPositions + 1);
                if (!CommonUtils.isDestory(getActivity())&&recyclerView!=null){
                    recyclerView.scrollToPosition(answerPositions + 1);
                    tvAnswerTitle.setText(questionListBean.getQuestion());
                    mp3Url = questionListBean.getFile();
                    clickCircleSong();
                }
            });
        }
    }


    private void initDatas() {
        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
        pagesize = 10;
        mPresenter.getAnswerList(userInfo.getId()+"", page, pagesize);
    }

    private int next_reward_num;
    private int reward_num;
    private int video_num;

    private int huoli_first_video;
    private int huoli_video_num;
    private int huoli_total;
    private int huoli_question_user_day;
    private List<String> hotShowIndexList=new ArrayList<>();
    @Override
    public void getAnswerListSuccess(AnswerFgBeans data) {
        if (!TextUtils.isEmpty(data.getCash())){
            tvMoney.setText(data.getCash());
            ((MyApplication) MyApplication.getInstance()).cash = data.getCash();
        }
        AnswerFgBeans.QuestionConfigBean question_config = data.getQuestion_config();
        if (question_config!=null&&!TextUtils.isEmpty(question_config.getReward_num())){
             reward_num =Integer.parseInt(question_config.getReward_num());
        }
        if (question_config!=null&&!TextUtils.isEmpty(question_config.getVideo_num())){
             video_num = Integer.parseInt(question_config.getVideo_num()) ;
        }
        next_reward_num = data.getNext_reward_num();
        isRedClick=false;
        List<AnswerFgBeans.QuestionListBean> question_list = data.getQuestion_list();
        AnswerFgBeans.QuesionUserBean quesion_user = data.getQuesion_user();
        if (page == 1) {
            answerFgAdapter.setNewData(question_list);
            if (quesion_user != null) {
                progressbarReward.setMax(next_reward_num * 10);
                progressbarReward.setProgress(quesion_user.getRight_num() * 10);
                righNums=quesion_user.getRight_num();
                tvAnswerSureNums.setText(quesion_user.getRight_num() + "");
                tvAnswerNums.setText(quesion_user.getAnswer_num() + "");
            }
            if (question_list != null && question_list.size() > 0) {
                tvAnswerTitle.setText(question_list.get(0).getQuestion() + "");
                mp3Url= question_list.get(0).getFile();
               clickCircleSong();
            }
        } else {
            answerFgAdapter.addData(question_list);
        }
        answerFgAdapter.notifyDataSetChanged();

    }
    private   int huoli_award;
    @Override
    public void questionAddSuccess(AnswerFgQuestionBeans data) {
        if (data != null) {
            huoli_award = data.getHuoli_award();
            huoli_question_user_day++;
            info_id=data.getInfo_id();
            tvAnswerSureNums.setText(data.getRight_num() + "");
            righNums=data.getRight_num();
            tvAnswerNums.setText(data.getAnswer_num()+"");
            if (data.getRight_num()>=next_reward_num){
                next_reward_num=next_reward_num+reward_num;
                isRedClick=true;
                startClickAnimontion();
            }else {
                stopClickAnimontion();
            }
            progressbarReward.setMax(next_reward_num * 10);
            progressbarReward.setProgress(data.getRight_num() * 10);
            String  other_money=data.getOther_money();
            if (!TextUtils.isEmpty(data.getCash())){
                ((MyApplication) MyApplication.getInstance()).cash =data.getCash();
                tvMoney.setText(data.getCash()+"元");
            }
            if (isAnswerSure){
                if (!TextUtils.isEmpty(other_money)&&!"0".equals(other_money)){//连对奖励
                    redRewardDialog(2,data.getRed_money(),data.getOther_money(),data.getOther_percent(),data.getContinue_num());
                }else {
                    redRewardDialog(1,data.getRed_money(),"","",data.getContinue_num());
                }
            }
        }
    }
   //开始动画
    private void startClickAnimontion() {
        if (!CommonUtils.isDestory(getActivity())){
          //  Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_rock);
        }
    }
    private void stopClickAnimontion(){
        if (!CommonUtils.isDestory(getActivity())){

        }
    }



    @Override
    public void getDoubleVideoSuccess(AnswerFanBeiBeans data) {
        if (!TextUtils.isEmpty(data.getCash())){
            ((MyApplication) MyApplication.getInstance()).cash = data.getCash();
        }
           tvMoney.setText(""+data.getCash()+"元");
           redPrizetwoDialog(data.getMoney());
    }

    @Override
    public void getQuestionrightSuccess(QuestionRightBeans data) {
        info_id=data.getInfo_id();
        if (!TextUtils.isEmpty(data.getCash())){
            ((MyApplication) MyApplication.getInstance()).cash = data.getCash();
        }
        tvMoney.setText(""+data.getCash()+"元");
    }


    private TextView tv_hour;
    private String timesCount;
    private LinearLayout line_times;
    public void setTimesData(String timesContents,boolean isEnd) {
        this.timesCount=timesContents;
        if (!isEnd){
            if (tv_hour!=null&& !TextUtils.isEmpty(timesCount)){
                tv_hour.setText(timesCount);
                if (line_times!=null){
                    line_times.setVisibility(View.VISIBLE);
                }
            }
        }else {
            if (line_times!=null){
                line_times.setVisibility(View.GONE);
            }
        }
    }

    public void setVideoCallBack(boolean isVideoClick) {
        if (videoType==3){

        }else if (videoType==1){
            getRed();
        }else if (videoType==2){
            nextAnswer();
            mPresenter.getDoubleVideo(CacheDataUtils.getInstance().getUserInfo().getId(),info_id);
        }else if (videoType==4){
            UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
            continueNums += 1;
            isAnswerSure=true;
            mPresenter.questionAdd(userInfo.getId()+"", 0, continueNums);
        }else if (videoType==5){
            mPresenter.getDoubleVideo(CacheDataUtils.getInstance().getUserInfo().getId(),info_id);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            tvMoney.setText(((MyApplication) MyApplication.getInstance()).cash + "元");
        }

        if (hidden) {
            SPlayer.instance().pause();
            stopMusic();
        } else {
            if (!isFirstLoad) {
                clickCircleSong();
            }
        }
    }
    private MediaPlayer mResultPlayer;
    private boolean isFirstLoad = true;
    private int lastIndex = -1;
    private String mp3Url = "";
    public void clickCircleSong() {
        String ansSoul = CacheDataUtils.getInstance().getAnsSoul();
        if (TextUtils.isEmpty(CacheDataUtils.getInstance().getSol())){
            if (TextUtils.isEmpty(ansSoul)){
                SPlayer.instance().pause();
                SPlayer.instance().reset();
                if (!SPlayer.instance().isPlaying()) {
                    //播放暂停 默认播放第一个item中内容
                    startPlayMusic();
                } else {
                    //暂停播放
                    SPlayer.instance().pause();
                }
            }
        }
    }
    /**
     * 停止音乐
     */
    private void stopMusic() {
        if (mResultPlayer != null) {
            mResultPlayer.stop();
            mResultPlayer.release();
            mResultPlayer = null;
        }
    }
    /**
     * 开始播放
     */
    public void startPlayMusic() {
        if (TextUtils.isEmpty(mp3Url)) {
            return;
        }
        SPlayer.instance()
                .playByUrl(mp3Url, new PlayerListener() {
                    @Override
                    public void LoadSuccess(SMediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }

                    @Override
                    public void Loading(SMediaPlayer mediaPlayer, int i) {

                    }

                    @Override
                    public void onCompletion(SMediaPlayer mediaPlayer) {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        lastIndex = -1;
    }

    @Override
    public void onPause() {
        super.onPause();
        SPlayer.instance().pause();
        //TODO，设置为0，主要控制音乐不再播放
        lastIndex = 0;
    }


    @Override
    public void getHotInfoSuccess(HotNumsInfoBeans data) {
        if (data!=null){
            huoli_question_user_day=data.getQuestion_num();
            HotNumsInfoBeans.DownloadBean download = data.getDownload();
            HotNumsInfoBeans.DownloadConfigBean download_config = data.getDownload_config();
            if (download!=null){
                Constant.IS_OPEN= download.getIs_open();
            }
            if (download_config!=null){
                huoli_first_video = download_config.getFirst_video();
                huoli_video_num = download_config.getVideo_num();
                huoli_total = download_config.getTotal();
                String ad_video = download_config.getAd_video();
                Constant.LEVEL = download_config.getLevel();
                Constant.LEVEL_STATE = download_config.getLevel_state();
                if (huoli_total>0){
                    for (int i = 0; i < huoli_total; i++) {
                        hotShowIndexList.add(String.valueOf(huoli_first_video+huoli_video_num*i));
                    }
                }
            }
        }
    }

//    public void preInitMp3() {
//        if (StringUtils.isEmpty(mp3Url)) {
//            return;
//        }
//        try {
//            VoiceDownloadUtil.instance()
//                    .download(mp3Url, new OnDownloadListener() {
//                        @Override
//                        public void onDownloadSuccess(File file) {
//                            if (!isNewPerson && !isAutoSeeVideo) {
//                                clickCircleSong();
//                            }
//                        }
//
//                        @Override
//                        public void onDownloading(int progress) {
//
//                        }
//
//                        @Override
//                        public void onDownloadFailed(Exception e) {
//
//                        }
//                    });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



    private SnatchDialog tisuWithDraw;
    public void initTisuWithDraw( ) {
        MobclickAgent.onEvent(getActivity(), "answer_huoyueshow", "1");//参数二为当前统计的事件ID
        tisuWithDraw = new SnatchDialog(getActivity());
        View builder = tisuWithDraw.builder(R.layout.tisuwithdraw_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv1 = builder.findViewById(R.id.tv1);
        TextView tv2 = builder.findViewById(R.id.tv2);
        TextView tv_titles = builder.findViewById(R.id.tv_titles);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        tv1.setText("你目前获取活跃值的速度太慢");
        tv2.setText("可以申请获取100倍活跃值");
        tv_sure.setText("点击获取");
        tv_titles.setText("活跃值奖励太少了！");
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                tisuWithDraw.setDismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                MobclickAgent.onEvent(getActivity(), "answer_huoyueclick", "1");//参数二为当前统计的事件ID
                AdHotActivity.adhotJump(getActivity(),"2");
                tisuWithDraw.setDismiss();
            }
        });
        VUiKit.postDelayed(600, () -> {
            if (!CommonUtils.isDestory(getActivity())) {
                tisuWithDraw.setShow();
            }
        });
    }



    //=================start===================激励视频=====================================================================================

    public void showjiliAd(){
        if (!CommonUtils.isDestory(getActivity())){
            GromoreAdShow.getInstance().showjiliAd(getActivity(),1,"tx_ad_dazhuangpan", new GromoreAdShow.OnAdShowCaback() {
                @Override
                public void onRewardedAdShow() {

                }

                @Override
                public void onRewardedAdShowFail() {

                }

                @Override
                public void onRewardClick() {

                }

                @Override
                public void onVideoComplete() {

                }

                @Override
                public void setVideoCallBacks() {

                }

                @Override
                public void onRewardedAdClosed(boolean isVideoClick,boolean isCompete) {
                    setVideoCallBack(isVideoClick);
                }

                @Override
                public void onFinshTask(String appPackage, String appName, String type) {

                }

                @Override
                public void onNoTask() {

                }
            });
        }
    }




    //=================start===================信息流=====================================================================================


    //=================start===================信息流=====================================================================================
    private NativeExpressADView nativeExpressADView;
    private NativeExpressAD nativeExpressAD;
    private boolean isPreloadVideo=false;
    private ViewGroup container;
    private void  loadExpressAd(ViewGroup container){
        this.container=container;
        int acceptedWidth = 380;
        nativeExpressAD=new NativeExpressAD(getActivity(), new ADSize(acceptedWidth, 200), Constant.TXEXPRESS, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onNoAD(com.qq.e.comm.util.AdError adError) {

            }

            @Override
            public void onADLoaded(List<NativeExpressADView> list) {

                // 释放前一个 NativeExpressADView 的资源
                if (nativeExpressADView != null) {
                    nativeExpressADView.destroy();
                }
                // 3.返回数据后，SDK 会返回可以用于展示 NativeExpressADView 列表
                nativeExpressADView = list.get(0);
                if (nativeExpressADView.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                    nativeExpressADView.setMediaListener(mediaListener);
                }
                if (container.getChildCount() > 0) {
                    container.removeAllViews();
                }

                if (nativeExpressADView.getBoundData().getAdPatternType() == AdPatternType.NATIVE_VIDEO) {
                    nativeExpressADView.setMediaListener(mediaListener);
                    if(isPreloadVideo) {
                        // 预加载视频素材，加载成功会回调mediaListener的onVideoCached方法，失败的话回调onVideoError方法errorCode为702。
                        nativeExpressADView.preloadVideo();
                    }
                } else {
                    isPreloadVideo = false;
                }
                if(!isPreloadVideo) {
                    // 广告可见才会产生曝光，否则将无法产生收益。
                    container.addView(nativeExpressADView);
                    nativeExpressADView.render();
                }

            }

            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

            }

        });

//       nativeExpressAD.setVideoOption(new VideoOption.Builder()
//               .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.WIFI) // WIFI 环境下可以自动播放视频
//               .setAutoPlayMuted(true) // 自动播放时为静音
//               .build()); //
//       nativeExpressAD.setVideoPlayPolicy(VideoOption.VideoPlayPolicy.AUTO); // 本次拉回的视频广告，从用户的角度看是自动播放的
        nativeExpressAD.loadAD(1);
    }


    private NativeExpressMediaListener mediaListener = new NativeExpressMediaListener() {
        @Override
        public void onVideoInit(NativeExpressADView nativeExpressADView) {

        }

        @Override
        public void onVideoLoading(NativeExpressADView nativeExpressADView) {

        }

        @Override
        public void onVideoCached(NativeExpressADView nativeExpressADView) {
            // 视频素材加载完成，此时展示视频广告不会有进度条。
            if(isPreloadVideo && nativeExpressADView != null) {
                if(container.getChildCount() > 0){
                    container.removeAllViews();
                }
                // 广告可见才会产生曝光，否则将无法产生收益。
                container.addView(nativeExpressADView);
                nativeExpressADView.render();
            }
        }

        @Override
        public void onVideoReady(NativeExpressADView nativeExpressADView, long l) {

        }

        @Override
        public void onVideoStart(NativeExpressADView nativeExpressADView) {

        }

        @Override
        public void onVideoPause(NativeExpressADView nativeExpressADView) {

        }

        @Override
        public void onVideoComplete(NativeExpressADView nativeExpressADView) {

        }

        @Override
        public void onVideoError(NativeExpressADView nativeExpressADView, com.qq.e.comm.util.AdError adError) {

        }


        @Override
        public void onVideoPageOpen(NativeExpressADView nativeExpressADView) {

        }

        @Override
        public void onVideoPageClose(NativeExpressADView nativeExpressADView) {

        }
    };


    //=================end===================信息流=====================================================================================



    //=================start===================banner=====================================================================================
    private void showBannwer() {
       /* if (TTMediationAdSdk.configLoadSuccess()) {
            loadBannerAd();
        } else {
            TTMediationAdSdk.registerConfigCallback(mSettingConfigCallbackbanner); //不能使用内部类，否则在ondestory中无法移除该回调
        }*/
    }
 /*   private void loadBannerAd() {
        if (CommonUtils.isDestory(getActivity())){
            return;
        }
        *//**
         * 注：每次加载banner的时候需要新建一个TTBannerViewAd，否则可能会出现广告填充问题
         * （ 例如：mTTBannerViewAd = new TTBannerViewAd(this, adUnitId);）
         *//*
        mTTBannerViewAd = new TTBannerViewAd(getActivity(), Constant.BANNER);
        mTTBannerViewAd.setRefreshTime(30);
        mTTBannerViewAd.setAllowShowCloseBtn(true);//如果广告本身允许展示关闭按钮，这里设置为true就是展示。注：目前只有mintegral支持。
        mTTBannerViewAd.setTTAdBannerListener(ttAdBannerListener);
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        int screenWidthDp = (int) UIUtils.getScreenWidthDp(getActivity().getApplicationContext());
        AdSlot adSlot = new AdSlot.Builder()
                .setAdStyleType(AdSlot.TYPE_EXPRESS_AD) // banner暂时只支持模版类型，必须手动设置为AdSlot.TYPE_EXPRESS_AD
//                .setBannerSize(TTAdSize.BANNER_300_250)
                .setBannerSize(TTAdSize.BANNER_CUSTOME) // 使用TTAdSize.BANNER_CUSTOME可以调用setImageAdSize设置大小
                .setImageAdSize(screenWidthDp, 100)
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        mTTBannerViewAd.loadAd(adSlot, new TTAdBannerLoadCallBack() {
            @Override
            public void onAdFailedToLoad(AdError adError) {
                LogUtils.showAdLog( "load首页banner-error-----adNetworkPlatformId: "  + adError.code + ", " + adError.message);
                if (fl_banner!=null){
                    fl_banner.removeAllViews();
                }
                // 获取本次waterfall加载中，加载失败的adn错误信息。
                if (mTTBannerViewAd != null)
                    LogUtils.showAdLog( "load首页banner-adLoadInfo-----adNetworkPlatformId: "  + mTTBannerViewAd.getAdLoadInfoList().toString());

            }

            @Override
            public void onAdLoaded() {
                if (fl_banner!=null){
                    fl_banner.removeAllViews();
                }
                if (mTTBannerViewAd != null) {
                    //横幅广告容器的尺寸必须至少与横幅广告一样大。如果您的容器留有内边距，实际上将会减小容器大小。如果容器无法容纳横幅广告，则横幅广告不会展示
                    View view = mTTBannerViewAd.getBannerView();
                    if (view != null&&fl_banner!=null)
                        fl_banner.addView(view);
                    LogUtils.showAdLog( "loadAd首页banner------adNetworkPlatformId: " + mTTBannerViewAd.getAdNetworkPlatformId() + "   adNetworkRitId：" + mTTBannerViewAd.getAdNetworkRitId() + "   preEcpm: " + mTTBannerViewAd.getPreEcpm());

                }
            }
        });
    }
    *//**
     * config回调
     *//*
    private TTSettingConfigCallback mSettingConfigCallbackbanner = new TTSettingConfigCallback() {
        @Override
        public void configLoad() {
            LogUtils.showAdLog("load mainactivity_激励视频ad 在config 回调中加载广告");
            loadBannerAd();
        }
    };
    TTAdBannerListener ttAdBannerListener = new TTAdBannerListener() {

        @Override
        public void onAdOpened() {

        }

        @Override
        public void onAdLeftApplication() {

        }

        @Override
        public void onAdClosed() {

        }

        @Override
        public void onAdClicked() {

        }

        @Override
        public void onAdShow() {

        }

        *//**
         * show失败回调。如果show时发现无可用广告（比如广告过期），会触发该回调。
         * 开发者应该结合自己的广告加载、展示流程，在该回调里进行重新加载。
         * @param adError showFail的具体原因
         *//*
        @Override
        public void onAdShowFail(AdError adError) {
            // 开发者应该结合自己的广告加载、展示流程，在该回调里进行重新加载
        }
    };
    //=================end===================banner=====================================================================================*/

}