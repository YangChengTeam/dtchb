package com.yc.redguess.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.App.App;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.rewardvideo.ServerSideVerificationOptions;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAD;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAdListener;
import com.qq.e.comm.util.VideoAdValidity;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.adplatform.log.AdLog;
import com.yc.redguess.R;
import com.yc.redguess.application.MyApplication;
import com.yc.redguess.base.BaseActivity;
import com.yc.redguess.constants.Constant;
import com.yc.redguess.dialog.RedDialog;
import com.yc.redguess.dialog.SnatchDialog;
import com.yc.redguess.homeModule.adapter.AnswserAdapter;
import com.yc.redguess.homeModule.contact.AnswerContact;
import com.yc.redguess.homeModule.module.bean.AnsPostRecordBeans;
import com.yc.redguess.homeModule.module.bean.AnswerBeans;
import com.yc.redguess.homeModule.module.bean.UpQuanNumsBeans;
import com.yc.redguess.homeModule.present.AnswerPresenter;
import com.yc.redguess.homeModule.widget.ScrollWithRecyclerView;
import com.yc.redguess.homeModule.widget.ToastShowViews;
import com.yc.redguess.service.event.Event;
import com.yc.redguess.utils.AppSettingUtils;
import com.yc.redguess.utils.CacheDataUtils;
import com.yc.redguess.utils.ClickListenNameTwo;
import com.yc.redguess.utils.CommonUtils;
import com.yc.redguess.utils.DisplayUtil;
import com.yc.redguess.utils.SoundPoolUtils;
import com.yc.redguess.utils.ToastUtilsViews;
import com.yc.redguess.utils.VUiKit;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.qq.e.comm.util.VideoAdValidity.NONE_CACHE;
import static com.qq.e.comm.util.VideoAdValidity.OVERDUE;
import static com.qq.e.comm.util.VideoAdValidity.SHOWED;
import static com.qq.e.comm.util.VideoAdValidity.VALID;

/**
 * 答题任务
 */
public class AnswerActivity extends BaseActivity<AnswerPresenter> implements AnswerContact.View {
    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    private AnswserAdapter answserAdapter;
    private int index;
    private FrameLayout fl_ad_containe;
    private int videoType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_answer;
    }

    @Override
    public void initEventAndData() {
        fl_ad_containe=findViewById(R.id.fl_ad_containe);
        setTitle("答题任务");
        initRecyclerView();
        mPresenter.getAnswerQuestionList(CacheDataUtils.getInstance().getUserInfo().getGroup_id()+"");
        loadVideo();
        loadTx();
        showExpress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadVideo();
    }

    private void showExpress() {
        loadExpressVideo();
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        isShow= adPlatformSDK.showExpressAd();
    }

    private boolean isShow;
    private void loadExpressVideo() {
        int screenWidth = CommonUtils.getScreenWidth(this);
        int w = (int) (screenWidth);
        int h = w * 2 / 3;
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        int dpw = DisplayUtil.px2dip(AnswerActivity.this, w);
        int dph = DisplayUtil.px2dip(AnswerActivity.this, h);
        adPlatformSDK.loadExpressAd(this, "ad_expredd_answer", dpw, dph, new AdCallback() {
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
//                if (!isShow) {
//                    adPlatformSDK.showExpressAd();
//                }
            }
        }, fl_ad_containe);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void answerJump(Context context) {
        Intent intent = new Intent(context, AnswerActivity.class);
        context.startActivity(intent);
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        answserAdapter = new AnswserAdapter(null);
        recyclerView.setAdapter(answserAdapter);
        answserAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (ClickListenNameTwo.isFastClick()) {
                    SoundPoolUtils instance = SoundPoolUtils.getInstance();
                    instance.initSound();
                    List<AnswerBeans> lists = adapter.getData();
                    if ( lists.get(position).getIs_continue()==1){
                        index=position;
                        String tips="";
                        if (!TextUtils.isEmpty(lists.get(position).getMoney())){
                            if ("0.10".equals(lists.get(position).getMoney())||"0.20".equals(lists.get(position).getMoney())){
                                tips="简单难度只需要完成答题就能升级哦！";
                            }else if ("0.30".equals(lists.get(position).getMoney())){
                                tips="中等难度只需要完成答题就能升级哦！";
                            }else  if ("0.50".equals(lists.get(position).getMoney())||"1.00".equals(lists.get(position).getMoney())){
                                tips="困难难度需要正确答对才能升级哦！";
                            }
                        }

                        showRedDialog(lists.get(position).getMoney(),lists.get(position).getQuestion_num(),tips);
                    }
                }
            }
        });
    }

    private RedDialog redDialog;
    public void showRedDialog(String money,int questionNums,String tips) {
        redDialog = new RedDialog(this);
        View builder = redDialog.builder(R.layout.red_answer_dialog_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        ImageView iv_open = builder.findViewById(R.id.iv_open);
        TextView tv_answerDes=builder.findViewById(R.id.tv_answerDes);
        TextView tv_tips=builder.findViewById(R.id.tv_tips);
        TextView tv_videoClick=builder.findViewById(R.id.tv_videoClick);
        LinearLayout line_videoClick=builder.findViewById(R.id.line_videoClick);


        tv_answerDes.setVisibility(View.VISIBLE);
        tv_answerDes.setText("答完"+questionNums+"题，即可获得升级奖励");
        iv_open.setImageDrawable(getResources().getDrawable(R.drawable.red_ans));
        tv_tips.setText(tips);
       // loadVideoss(frameLayout);
        if (((MyApplication) MyApplication.getInstance()).levels>1){
            tv_tips.setVisibility(View.GONE);
            line_videoClick.setVisibility(View.VISIBLE);
        }else {
            tv_tips.setVisibility(View.VISIBLE);
            line_videoClick.setVisibility(View.GONE);
        }
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                if (!CommonUtils.isDestory(AnswerActivity.this)){
                    redDialog.setDismiss();
                }
            }
        });

        tv_videoClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoType=2;
                if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                    showVideo();
                }else {
                    showTx();
                }
            }
        });

        iv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answerVideo = CacheDataUtils.getInstance().getAnswerVideo();
                if (TextUtils.isEmpty(answerVideo)){//第一次不看视频
                    List<AnswerBeans> lists = answserAdapter.getData();
                    AnswerBeans answerBeans = lists.get(index);
                    answerBeans.setIs_continue(0);
                    answserAdapter.notifyItemChanged(index);
                    CacheDataUtils.getInstance().setAnswerVideo();
                    AnswerDetailsActivity.AnswerDetailsJump(AnswerActivity.this,answerBeans.getId()+"",answerBeans.getTotal(),answerBeans.getMoney(),answerBeans.getId()+"");
                    if (redDialog!=null){
                        redDialog.setDismiss();
                    }
                }else {
                    videoType=1;
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        showVideo();
                    }else {
                        showTx();
                    }
                }
            }
        });
        VUiKit.postDelayed(2000, () -> {
            iv_close.setVisibility(View.VISIBLE);
        });
        if (!CommonUtils.isDestory(AnswerActivity.this)){
            redDialog.setShow();
        }
    }
    private void video() {
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        adPlatformSDK.showExpressAd();
    }

    private void loadVideoss(FrameLayout frameLayout) {
        int screenWidth = CommonUtils.getScreenWidth(this);
        int w = (int) (screenWidth) * 8 / 10;
        int h = w * 2 / 3;
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        int dpw = DisplayUtil.px2dip(AnswerActivity.this, w);
        int dph = DisplayUtil.px2dip(AnswerActivity.this, h);
        adPlatformSDK.loadExpressAd(this, "ad_lingqucg", dpw, dph, new AdCallback() {
            @Override
            public void onDismissed() {

            }

            @Override
            public void onNoAd(AdError adError) {
                Log.d("ccc", "----xxx------------onNoAd: "+adError.getCode()+"---"+adError.getMessage());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onPresent() {
                Log.d("ccc", "-----xxx-----------onPresent: ");
            }

            @Override
            public void onClick() {

            }

            @Override
            public void onLoaded() {
                Log.d("ccc", "------xxx----------onLoaded: ");
                video();
            }
        }, frameLayout);
    }



    @Override
    protected void onDestroy() {
        if (redDialog!=null){
            redDialog.setDismiss();
        }
        if (mRewardVideoAD != null) {
            mRewardVideoAD.destroy();
        }
        if (snatchDialogs!=null){
            snatchDialogs.setDismiss();
        }
        super.onDestroy();
    }

    @Override
    public void getAnswerQuestionListSuccess(List<AnswerBeans> data) {
         answserAdapter.setNewData(data);
         answserAdapter.notifyDataSetChanged();
    }
    private int upTreasure=0;
    @Override
    public void updtreasureSuccess(UpQuanNumsBeans data) {
        if (data!=null){
            upTreasure=data.getRand_num();
        }
    }

    @Override
    public void postAnserRecordSuccess(AnsPostRecordBeans data) {
        ToastUtil.showToast("一键答题成功！您可以继续答题的哦！");
    }

    @Override
    public void postAnserRecordError() {
        ToastUtil.showToast("领取红包失败，建议咨询下客服哦！");
    }

    private void showVideo() {
        isVideoClick=false;
        isLoadAdSuccess="1";
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.setUserId(CacheDataUtils.getInstance().getUserInfo().getId()+"");
        adPlatformSDK.showRewardVideoAd();
        loadVideo();
    }
    private boolean isVideoClick;
    private String isLoadAdSuccess="0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
    private String isTxLoadAdSuccess="0";//0 默认状态  1：点击状态  2：拉去广告失败  3：拉去广告成功
    private void loadVideo(){
        final AdPlatformSDK adPlatformSDK = AdPlatformSDK.getInstance(this);
        adPlatformSDK.loadRewardVideoVerticalAd(this, "ad_wenda",new AdCallback() {
            @Override
            public void onDismissed() {
                if (upTreasure>0){
                    if (!CommonUtils.isDestory(AnswerActivity.this)) {
                        ToastUtilsViews.showCenterToast("1", "");
                    }
                }
                List<AnswerBeans> lists = answserAdapter.getData();
                if (index<lists.size()){
                    AnswerBeans answerBeans = lists.get(index);
                    if (videoType==2){
                          if (isVideoClick){
                              answerBeans.setIs_continue(0);
                              answserAdapter.notifyItemChanged(index);
                              mPresenter.postAnserRecord(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", String.valueOf(answerBeans.getId()), "0");
                          }else {
                              showjiesuoTaskError("你未点击视频广告内容，任务完成失败");
                          }
                    }else {
                        answerBeans.setIs_continue(0);
                        answserAdapter.notifyItemChanged(index);
                        AnswerDetailsActivity.AnswerDetailsJump(AnswerActivity.this,answerBeans.getId()+"",answerBeans.getTotal(),answerBeans.getMoney(),answerBeans.getId()+"");
                    }
                }
                if (redDialog!=null){
                    redDialog.setDismiss();
                }
                if (!CommonUtils.isDestory(AnswerActivity.this)){
                    ToastShowViews.cancleToast();
                }
            }

            @Override
            public void onNoAd(AdError adError) {
                if ("1".equals(isLoadAdSuccess)){
                    isLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        showTx();
                    }else {
                        if (!CommonUtils.isDestory(AnswerActivity.this)) {
                            ToastUtil.showToast("如果视频广告无法观看，可能是网络不好的原因加载广告失败，请检查下网络是否正常,或者试试重启APP哦");
                        }
                    }
                }
            }

            @Override
            public void onComplete() {
                if (!CommonUtils.isDestory(AnswerActivity.this)){
                    ToastShowViews.cancleToast();
                }
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
            }

            @Override
            public void onPresent() {
                isLoadAdSuccess="3";
                if (!CommonUtils.isDestory(AnswerActivity.this)){
                    ToastShowViews.showMyToast();
                }
            }

            @Override
            public void onClick() {
                isVideoClick=true;
            }

            @Override
            public void onLoaded() {
                isLoadAdSuccess="3";
            }
        });
    }
    private SnatchDialog snatchDialogs;
    private void showjiesuoTaskError(String str) {
         snatchDialogs = new SnatchDialog(this);
        View builder = snatchDialogs.builder(R.layout.jiesuotaskerror_item);
        TextView tv_des=builder.findViewById(R.id.tv_des);
        if (!TextUtils.isEmpty(str)){
            tv_des.setText(str);
        }
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snatchDialogs.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(AnswerActivity.this)) {
            snatchDialogs.setShow();
        }
    }

    public void showTx(){
        if (mRewardVideoAD == null || !mIsLoaded) {
           // showToast("广告未拉取成功！");
            loadTxTwo();
            if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                if (!CommonUtils.isDestory(AnswerActivity.this)) {
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
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        if (!CommonUtils.isDestory(AnswerActivity.this)) {
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
                    isVideoClick=false;
                    isTxLoadAdSuccess="1";
                    // 在视频缓存成功后展示，以省去用户的等待时间，提升用户体验
                    mRewardVideoAD
                            .showAD(AnswerActivity.this);
                    // 展示广告
                    break;
            }
        }
    }

    public void loadTxTwo(){
        if (mRewardVideoAD!=null){
            mIsLoaded=false;
            mRewardVideoAD.loadAD();
        }else {
            loadTx();
        }
    }
    private ExpressRewardVideoAD mRewardVideoAD;
    private boolean mIsLoaded;
    private boolean mIsCached;
    public void loadTx(){
        String posId="1081870061070830";
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
                AppSettingUtils.showTxShow("tx_ad_wenda");
                if (!CommonUtils.isDestory(AnswerActivity.this)){
                    ToastShowViews.showMyToast();
                }
            }

            @Override
            public void onExpose() {

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
                AppSettingUtils.showTxClick("tx_ad_wenda");
                isVideoClick=true;
            }

            @Override
            public void onVideoComplete() {
                if (!CommonUtils.isDestory(AnswerActivity.this)){
                    ToastShowViews.cancleToast();
                }
                mPresenter.updtreasure(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "");//更新券
            }

            @Override
            public void onClose() {
                if (upTreasure>0){
                    if (!CommonUtils.isDestory(AnswerActivity.this)) {
                        ToastUtilsViews.showCenterToast("1", "");
                    }
                }
                List<AnswerBeans> lists = answserAdapter.getData();
                if (index<lists.size()){
                    AnswerBeans answerBeans = lists.get(index);
                    if (videoType==2){
                        if (isVideoClick){
                            answerBeans.setIs_continue(0);
                            answserAdapter.notifyItemChanged(index);
                            mPresenter.postAnserRecord(CacheDataUtils.getInstance().getUserInfo().getGroup_id() + "", String.valueOf(answerBeans.getId()), "0");
                        }else {
                            showjiesuoTaskError("你未点击视频广告内容，任务完成失败");
                        }
                    }else {
                        answerBeans.setIs_continue(0);
                        answserAdapter.notifyItemChanged(index);
                        AnswerDetailsActivity.AnswerDetailsJump(AnswerActivity.this,answerBeans.getId()+"",answerBeans.getTotal(),answerBeans.getMoney(),answerBeans.getId()+"");
                    }
                }
                if (redDialog!=null){
                    redDialog.setDismiss();
                }
                if (!CommonUtils.isDestory(AnswerActivity.this)){
                    ToastShowViews.cancleToast();
                }
            }

            @Override
            public void onError(com.qq.e.comm.util.AdError adError) {
                if ("1".equals(isTxLoadAdSuccess)){
                    isTxLoadAdSuccess="2";
                    //失败了播放腾讯的
                    if ("1".equals(AppSettingUtils.getVideoType())){//先头条
                        showVideo();
                    }else {
                        if (!CommonUtils.isDestory(AnswerActivity.this)) {
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