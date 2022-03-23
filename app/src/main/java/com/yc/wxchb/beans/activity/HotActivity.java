package com.yc.wxchb.beans.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import com.yc.wxchb.R;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.base.BaseActivity;
import com.yc.wxchb.beans.adapter.HotAdapter;
import com.yc.wxchb.beans.contact.HotContract;
import com.yc.wxchb.beans.module.beans.HotWithDrawBeans;
import com.yc.wxchb.beans.module.beans.MoneysBeans;
import com.yc.wxchb.beans.module.beans.QuesTionsHotBeans;
import com.yc.wxchb.beans.module.beans.UserInfo;
import com.yc.wxchb.beans.present.HotPresenter;
import com.yc.wxchb.dialog.SignDialog;
import com.yc.wxchb.dialog.SnatchDialog;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.ClickListenName;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.SoundPoolUtils;
import com.yc.wxchb.utils.VUiKit;
import com.yc.wxchb.utils.ad.GromoreAdShow;
import com.yc.wxchb.widget.ScrollWithRecyclerView;
import com.zzhoujay.richtext.RichText;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class HotActivity extends BaseActivity<HotPresenter> implements HotContract.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.hotNums)
    TextView hotNums;
    @BindView(R.id.tv_contents)
    TextView tvContents;
    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_moneyss)
    TextView tvMoneyss;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.tv_startTask)
    TextView tv_startTask;
    @BindView(R.id.line_hot)
    LinearLayout lineHot;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    private HotAdapter hotAdapter;
    private String huoli_cash;
    private int huoli_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_hot;
    }
    private    String type;
    private boolean isFirst;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initEventAndData() {
        isFirst=true;
        initRecyclerView();
        type = getIntent().getStringExtra("type");
        if (!TextUtils.isEmpty(type)&&"4".equals(type)){
            lineHot.setVisibility(View.VISIBLE);
        }else {
            lineHot.setVisibility(View.GONE);
        }

        mPresenter.getHotIndex(CacheDataUtils.getInstance().getUserInfo().getId() + "");
        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
        tvName.setText(userInfo.getNickname());
        if (!TextUtils.isEmpty(CacheDataUtils.getInstance().getUserInfo().getFace())) {
            Glide.with(this)
                    .load(CacheDataUtils.getInstance().getUserInfo().getFace())
                    .apply(new RequestOptions().bitmapTransform(new CircleCrop()))
                    .into(ivAvatar);
        }
        if (!TextUtils.isEmpty(type)&&"4".equals(type)){
            lineHot.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(NestedScrollView.FOCUS_DOWN);
                }
            });
        }
    }
    public void initRecyclerView(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        hotAdapter = new HotAdapter(null);
        recyclerView.setAdapter(hotAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<HotWithDrawBeans.HuoliBean.ConfigJsonBean> lists = adapter.getData();
                HotWithDrawBeans.HuoliBean.ConfigJsonBean outamountBean = lists.get(position);
                if (outamountBean.getStatus() == 1) {
                    SoundPoolUtils instance = SoundPoolUtils.getInstance();
                    instance.initSound();
                    for (int i = 0; i < lists.size(); i++) {
                        if (position == i) {
                            lists.get(i).setSelect(true);
                        } else {
                            lists.get(i).setSelect(false);
                        }
                    }
                    hotAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }
    private String tixianMoneys;
    @OnClick({R.id.iv_back, R.id.tv_sure,R.id.tv_startTask})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_startTask:
                if (ClickListenName.isFastClick()){
                    MobclickAgent.onEvent(this, "hot_adclick", "1");//参数二为当前统计的事件ID
                    showJiliAd();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_sure:
                List<HotWithDrawBeans.HuoliBean.ConfigJsonBean> data = hotAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    HotWithDrawBeans.HuoliBean.ConfigJsonBean configJsonBean = data.get(i);
                    if (configJsonBean.isSelect()) {
                        String num = configJsonBean.getNum();
                        String money = configJsonBean.getMoney();
                        int finish_num = configJsonBean.getFinish_num();
                        float flMoneys = Float.parseFloat(money);
                        float flHuoli_cash = Float.parseFloat(huoli_cash);
                        int i1 = Integer.parseInt(num);
                        tixianMoneys = money;
                        if (flHuoli_cash >= flMoneys) {
                            if (i1 > 0) {
                                wxLogin();
                            } else {
                                hotNumsDialogs(2);
                            }
                        } else {//活跃值不够
                            hotNumsDialogs(1);
                        }
                    }
                }

                break;
        }
    }



    public static void adhotJump(Context context,String type) {
        Intent intent = new Intent(context, HotActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    private SnatchDialog hotNumsDialog;
    public void hotNumsDialogs(int tpye) {
        hotNumsDialog = new SnatchDialog(this);
        View builder = hotNumsDialog.builder(R.layout.hotums_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv1 = builder.findViewById(R.id.tv1);
        TextView tv2 = builder.findViewById(R.id.tv2);
        if (tpye==1){
           tv1.setText("活跃值不足");
           tv2.setText("请继续获取活跃值！");
        }else if (tpye==2){
            tv1.setText("今日提现次数已用完");
            tv2.setText("请明日再来提现哦！");
        }
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                hotNumsDialog.setDismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                hotNumsDialog.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)) {
            hotNumsDialog.setShow();
        }
    }


    private SnatchDialog withDrawSuccessDialog;
    public void withDrawSuccessDialogs(int type) {
        withDrawSuccessDialog = new SnatchDialog(this);
        View builder = withDrawSuccessDialog.builder(R.layout.withdrawsuccess_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv1 = builder.findViewById(R.id.tv1);
        TextView tv2 = builder.findViewById(R.id.tv2);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        if (type==2){
            tv1.setText("点击下方开始任务");
            tv2.setText("可获得活跃值直接提现哦！");
        }else {
            tv1.setText("提现申请成功");
            tv2.setText("请稍后在微信查看是否到账！");
        }
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                withDrawSuccessDialog.setDismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                withDrawSuccessDialog.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)) {
            withDrawSuccessDialog.setShow();
        }
    }

    @Override
    public void getHotIndexSuccess(HotWithDrawBeans data) {
        if (data != null) {
            HotWithDrawBeans.HuoliBean huoli = data.getHuoli();
            huoli_cash = data.getHuoli_cash();
            huoli_num = data.getHuoli_num();
            hotNums.setText(huoli_num + "");
            tvMoneyss.setText("≈" + huoli_cash + "元");
            if (huoli != null) {
                String content = huoli.getContent();
                if (!TextUtils.isEmpty(content)) {
                    RichText.from(content).into(tvContents);
                }
            }
            List<HotWithDrawBeans.HuoliBean.ConfigJsonBean> config_json = huoli.getConfig_json();
            if (config_json != null) {
                for (int i = 0; i < config_json.size(); i++) {
                    if (i == 0) {
                        config_json.get(i).setSelect(true);
                    } else {
                        config_json.get(i).setSelect(false);
                    }
                }
                hotAdapter.setNewData(config_json);
                hotAdapter.notifyDataSetChanged();
            }
            HotWithDrawBeans.QuestionHuoliBean question_huoli = data.getQuestion_huoli();
            if (question_huoli!=null){
                String shuoming = question_huoli.getShuoming();
                if (!TextUtils.isEmpty(shuoming)){
                    RichText.from(shuoming).into(tvTips);
                }
            }
            if (isFirst){
                if (!TextUtils.isEmpty(type)&&"4".equals(type)){
                    VUiKit.postDelayed(400, () -> {
                        withDrawSuccessDialogs(2);
                    });
                }
            }
            isFirst=false;
        }
    }


    @Override
    public void hottixianSuccess(MoneysBeans data) {
        withDrawSuccessDialogs(1);
        mPresenter.getHotIndex(CacheDataUtils.getInstance().getUserInfo().getId() + "");
    }

    @Override
    public void getQuestionHotSuccess(QuesTionsHotBeans data) {
        if (data!=null){
            isFirst=false;
            mPresenter.getHotIndex(CacheDataUtils.getInstance().getUserInfo().getId() + "");
            finshHotTaskDialogs(data.getHuoli());
        }
    }

    private SignDialog hotTaskDialog;
    public void hotTaskDialogs() {
        hotTaskDialog = new SignDialog(this);
        View builder = hotTaskDialog.builder(R.layout.hottask_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv1 = builder.findViewById(R.id.tv1);
        TextView tv2 = builder.findViewById(R.id.tv2);
        ImageView iv_closes = builder.findViewById(R.id.iv_close);
        tv1.setText("请点击视频中的广告");
        tv2.setText("即可增加活跃值!");
        tv_sure.setText("我知道了");
        iv_closes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                hotTaskDialog.setDismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                hotTaskDialog.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(this)) {
            if (!hotTaskDialog.getIsShow()){
                hotTaskDialog.setShow();
            }
        }
    }

    private SnatchDialog finshHotTaskDialog;
    public void finshHotTaskDialogs(int award) {
        finshHotTaskDialog = new SnatchDialog(this);
        View builder = finshHotTaskDialog.builder(R.layout.finshhottask_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_hotNums = builder.findViewById(R.id.tv_hotNums);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        tv_hotNums.setText(award+"");
        tv_sure.setText("我知道了");
        finshHotTaskDialog.setOutCancle(false);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                finshHotTaskDialog.setDismiss();
                finish();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                finshHotTaskDialog.setDismiss();
                finish();
            }
        });
        if (!CommonUtils.isDestory(this)) {
            finshHotTaskDialog.setShow();
        }
    }


    public class MyAuthLoginListener implements UMAuthListener {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            String unionid = map.get("unionid");
            String wx_openid = map.get("openid");
            String name = map.get("name");
            String profile_image_url = map.get("profile_image_url");
            if (!TextUtils.isEmpty(wx_openid)) {
                String appVersionCode = CommonUtils.getAppVersionCode(HotActivity.this);
                mPresenter.hottixian(CacheDataUtils.getInstance().getUserInfo().getId() + "", wx_openid, tixianMoneys, appVersionCode);
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            ToastUtil.showToast("授权失败:" + throwable.getMessage() + i + "------getLocalizedMessage()" + throwable.getLocalizedMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            ToastUtil.showToast("授权取消");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // QQ授权回调需要配置这里
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void wxLogin() {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
        UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.WEIXIN, null);
        UMShareAPI.get(this).release();
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, new MyAuthLoginListener());
    }

     public void showJiliAd(){
         GromoreAdShow.getInstance().showjiliAd(this, 3, "", new GromoreAdShow.OnAdShowCaback() {
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
             public void onRewardedAdClosed(boolean isVideoClick, boolean isCompeter) {
                 if (isVideoClick){
                       mPresenter.getQuestionHot(CacheDataUtils.getInstance().getUserInfo().getId()+"", ((MyApplication) MyApplication.getInstance()).getAgentId());
                 }else {
                     hotTaskDialogs();
                 }

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