package com.yc.wxchb.beans.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.ads.nativ.NativeExpressMediaListener;
import com.qq.e.comm.constants.AdPatternType;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.CommonUtil;
import com.yc.wxchb.R;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.base.BaseLazyFragment;
import com.yc.wxchb.beans.activity.InvationfriendActivity;
import com.yc.wxchb.beans.activity.MainActivity;
import com.yc.wxchb.beans.activity.VideoActivity;
import com.yc.wxchb.beans.activity.WithDrawRecodeActivity;
import com.yc.wxchb.beans.adapter.WithDrawAdapter;
import com.yc.wxchb.beans.adapter.WithDrawTopdAdapter;
import com.yc.wxchb.beans.contact.WithDrawContract;
import com.yc.wxchb.beans.module.beans.FalseUserBeans;
import com.yc.wxchb.beans.module.beans.LotterBeans;
import com.yc.wxchb.beans.module.beans.LotterInfoBeans;
import com.yc.wxchb.beans.module.beans.PayInfoBeans;
import com.yc.wxchb.beans.module.beans.RedTaskBeans;
import com.yc.wxchb.beans.module.beans.UserInfo;
import com.yc.wxchb.beans.module.beans.WithDrawStatusBeans;
import com.yc.wxchb.beans.present.WithDrawPresenter;
import com.yc.wxchb.constants.Constant;
import com.yc.wxchb.dialog.PrizeDialog;
import com.yc.wxchb.dialog.SignDialog;
import com.yc.wxchb.dialog.SnatchDialog;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.SoundPoolUtils;
import com.yc.wxchb.utils.VUiKit;
import com.yc.wxchb.utils.ad.GromoreInsetAdShow;
import com.yc.wxchb.widget.MyTextSwitchView;
import com.yc.wxchb.widget.NineLuckPan;
import com.yc.wxchb.widget.ScrollWithRecyclerView;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class WithDrawFragment extends BaseLazyFragment<WithDrawPresenter> implements WithDrawContract.View {

    @BindView(R.id.textswitchView)
    MyTextSwitchView textswitchView;
    @BindView(R.id.tv_ansnums)
    TextView tvAnsnums;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_gojump)
    TextView tvGojump;
    @BindView(R.id.rela_nextAmout)
    LinearLayout relaNextAmout;
    @BindView(R.id.tv_recodeThree)
    TextView tvRecodeThree;
    @BindView(R.id.tv_cashMoney)
    TextView tvCashMoney;
    @BindView(R.id.tv_withdrawRule)
    TextView tvWithdrawRule;
    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    @BindView(R.id.line_sure)
    LinearLayout lineSure;
    @BindView(R.id.line_invation)
    LinearLayout lineInvation;
    @BindView(R.id.top_recyclerView)
    ScrollWithRecyclerView topRecyclerView;
    @BindView(R.id.tv_desTitle)
    TextView tvDesTitle;
    @BindView(R.id.tv_des)
    TextView tvDes;
    private WithDrawTopdAdapter withDrawTopdAdapter;
    private WithDrawAdapter withDrawAdapter;
    private String contents;
    private  MainActivity mActivity;
    public WithDrawFragment() {
        // Required empty public constructor
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_with_draw, container, false);
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
        mActivity = (MainActivity) getActivity();
        lunpanDialog();
        if (nineLuckPan!=null){
            nineLuckPan.setOnLuckPanListener(new NineLuckPan.OnLuckPanListener() {
                @Override
                public void onLuckStart() {

                }

                @Override
                public void onAnimEnd(int position, String msg) {
                        VUiKit.postDelayed(1200,()->{
                            if (!CommonUtils.isDestory(getActivity())){
                                if (lunpanDialogs!=null){
                                    lunpanDialogs.setDismiss();
                                }
                                mPresenter.getPayInfo(CacheDataUtils.getInstance().getUserInfo().getId());
                                if (lotterType == 1) {//1:账户余额 2:直接提现 3:提现机会
                                    redtipsDialog(lotterMoneys);
                                } else if (lotterType == 2) {
                                    withDrawSuccessDialog();
                                } else if (lotterType == 3) {
                                    mPresenter.getlotterInfo(CacheDataUtils.getInstance().getUserInfo().getId());
                                }
                            }
                        });
                }
            });
        }
        mPresenter.getlotterInfo(CacheDataUtils.getInstance().getUserInfo().getId());
        mPresenter.getPayInfo(CacheDataUtils.getInstance().getUserInfo().getId());
        mPresenter.getRedTaskData(CacheDataUtils.getInstance().getUserInfo().getId());
        mPresenter.getFalseuser("30");
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        withDrawTopdAdapter = new WithDrawTopdAdapter(null);
        topRecyclerView.setLayoutManager(linearLayoutManager);
        topRecyclerView.setAdapter(withDrawTopdAdapter);
        withDrawTopdAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.line_status:
                        List<PayInfoBeans.OutArrBean> data = adapter.getData();
                        String out_money = data.get(position).getOut_money();
                        if (!TextUtils.isEmpty(out_money) && !TextUtils.isEmpty(((MyApplication) MyApplication.getInstance()).cash)) {
                            Float cashs = Float.parseFloat(((MyApplication) MyApplication.getInstance()).cash);
                            Float out_moneys = Float.parseFloat(out_money);
                            if (out_moneys <= cashs) {
                                levelTipDialog(1);
                            } else {//余额不够
                                moneyTipDialog();
                            }
                        }
                        break;
                }
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        withDrawAdapter = new WithDrawAdapter(null);
        recyclerView.setAdapter(withDrawAdapter);
        withDrawAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                List<PayInfoBeans.CashOutBean.OutamountBean> lists = adapter.getData();
                PayInfoBeans.CashOutBean.OutamountBean outamountBean = lists.get(position);
                String money = outamountBean.getMoney();
                int total = outamountBean.getTotal();
                String hb_total = outamountBean.getHb_total();
                    if (!TextUtils.isEmpty(hb_total)) {
                        String[] split = hb_total.split(",");
                        if (split != null && split.length > 0) {
                            if (total < split.length) {
                                String vNums = split[total];
                                if (position==0){
                                    tvDesTitle.setText(money+"元提现说明");
                                    tvDes.setText("累计领取"+vNums+"个倒计时红包即可提现");
                                }else {
                                    if (!TextUtils.isEmpty(money)&&!TextUtils.isEmpty(((MyApplication) MyApplication.getInstance()).cash)){
                                        float fcash = Float.parseFloat(((MyApplication) MyApplication.getInstance()).cash);
                                        float mo = Float.parseFloat(money);
                                        if (mo>fcash){
                                            tvDesTitle.setText(money+"元提现说明");
                                            tvDes.setText("金额达到"+money+"元即可提现");
                                        }else {
                                            tvDesTitle.setText(money+"元提现说明");
                                            if (position==1){
                                                tvDes.setText("等级达到30级即可提现");
                                            }else if (position==2){
                                                tvDes.setText("等级达到40级即可提现");
                                            }else if (position==3){
                                                tvDes.setText("等级达到50级即可提现");
                                            }else if (position==4){
                                                tvDes.setText("等级达到60级即可提现");
                                            }else if (position==5){
                                                tvDes.setText("等级达到80级即可提现");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                for (int i = 0; i < lists.size(); i++) {
                    if (position == i) {
                        lists.get(i).setSelect(true);
                    } else {
                        lists.get(i).setSelect(false);
                    }
                }
                withDrawAdapter.notifyDataSetChanged();
            }
        });
    }

    private SignDialog lunpanDialogs;
    private NineLuckPan nineLuckPan;
    private ImageView iv_close;
    private ImageView ivStart;

    public void lunpanDialog() {
        lunpanDialogs = new SignDialog(getActivity());
        View builder = lunpanDialogs.builder(R.layout.lunpan_dialogs_item);
        ivStart = builder.findViewById(R.id.iv_start);
        nineLuckPan = builder.findViewById(R.id.nineluckpan);
        iv_close = builder.findViewById(R.id.iv_close);
        lunpanDialogs.setOutCancle(false);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunpanDialogs.setDismiss();
            }
        });
    }
    private boolean isShow;
    public void showLunpanDialog() {
        VUiKit.postDelayed(200, () -> {
            isShow=true;
            if (!CommonUtils.isDestory(getActivity())) {
                ivStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String appVersionCode = CommonUtils.getAppVersionCode(getActivity());
                        mPresenter.getlotter(CacheDataUtils.getInstance().getUserInfo().getId(), appVersionCode);
                    }
                });
                lunpanDialogs.setDismissListen(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        isShow=false;
                    }
                });
                if (lunpanDialogs != null) {
                    lunpanDialogs.setShow();
                }
            }
        });
    }

    private String cashMonesy;
    @OnClick({ R.id.tv_gojump, R.id.tv_recodeThree, R.id.line_sure, R.id.line_invation, R.id.tv_withdrawRule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_gojump:
                MobclickAgent.onEvent(getActivity(), "withdarw_gojump", "1");//参数二为当前统计的事件ID
                if (!isShow&&lottery_num>0){
                    showLunpanDialog();
                }else {
                    VideoActivity.videoJump(getActivity());
                }
                break;
            case R.id.tv_recodeThree:
                MobclickAgent.onEvent(getActivity(), "withdarw_recode", "1");//参数二为当前统计的事件ID
               WithDrawRecodeActivity.withDrawRecodeJump(getActivity());
                break;
            case R.id.line_sure:
                MobclickAgent.onEvent(getActivity(), "withdarw_recode", "1");//参数二为当前统计的事件ID
                List<PayInfoBeans.CashOutBean.OutamountBean> data = withDrawAdapter.getData();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).isSelect()) {
                        String money = data.get(i).getMoney();
                        int other_num = data.get(i).getOther_num();
                        if (!TextUtils.isEmpty(money) && !TextUtils.isEmpty(((MyApplication) MyApplication.getInstance()).cash)) {
                            float vmoney = Float.parseFloat(money);
                            cashMonesy=money;
                            float fcash = Float.parseFloat(((MyApplication) MyApplication.getInstance()).cash);
                            if (fcash >= vmoney) {
                                if (other_num > 0) {
                                    if (i == 0) {
                                        String hb_total = data.get(i).getHb_total();
                                        int total = data.get(i).getTotal();
                                        int num = data.get(i).getNum();
                                        if (total < num) {
                                            if (!TextUtils.isEmpty(hb_total)) {
                                                String[] split = hb_total.split(",");
                                                if (split != null && split.length > 0) {
                                                    if (total < split.length) {
                                                        String vNums = split[total];
                                                        if (!TextUtils.isEmpty(vNums)) {
                                                            if (((MyApplication) MyApplication.getInstance()).hb_Nums >= Integer.parseInt(vNums)) {
                                                                wxLogin();
                                                            } else {
                                                                levelTipDialog(2);
                                                            }
                                                        } else {
                                                            levelTipDialog(2);
                                                        }
                                                    } else {
                                                        levelTipDialog(2);
                                                    }
                                                } else {
                                                    levelTipDialog(3);
                                                }
                                            } else {
                                                levelTipDialog(3);
                                            }
                                        } else {
                                            levelTipDialog(3);
                                        }
                                    } else {
                                        levelTipDialog(2);
                                    }
                                } else {//次数不足
                                    levelTipDialog(3);
                                }
                            } else {//余额不足
                                moneyTipDialog();
                            }
                        }
                    }
                }
                break;
            case R.id.line_invation:
                MobclickAgent.onEvent(getActivity(), "withdarw_invation", "1");//参数二为当前统计的事件ID
                InvationfriendActivity.invationfriendJump(getActivity());
                break;
            case R.id.tv_withdrawRule:
                MobclickAgent.onEvent(getActivity(), "withdarw_rule", "1");//参数二为当前统计的事件ID
                initSignRuleDialog(contents);
                break;
        }
    }

    private SnatchDialog withDrawSuccessDialogs;

    public void withDrawSuccessDialog() {
        withDrawSuccessDialogs = new SnatchDialog(getActivity());
        View builder = withDrawSuccessDialogs.builder(R.layout.withdrawsuccess_dialog_item);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawSuccessDialogs.setDismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawSuccessDialogs.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(getActivity())) {
            withDrawSuccessDialogs.setShow();
        }
    }

    private SignDialog moneyTipDialogs;

    public void moneyTipDialog() {
        moneyTipDialogs = new SignDialog(getActivity());
        View builder = moneyTipDialogs.builder(R.layout.moneytips_dialog_item);
        LinearLayout line_sure = builder.findViewById(R.id.line_sure);
        TextView tv_title = builder.findViewById(R.id.tv_title);
        TextView tv_des = builder.findViewById(R.id.tv_des);
        tv_title.setText("余额不足");
        tv_des.setText("请继续获取余额");
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        line_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoActivity.videoJump(getActivity());
                moneyTipDialogs.setDismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moneyTipDialogs.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(getActivity())) {
            moneyTipDialogs.setShow();
        }
    }

    private SignDialog levelTipDialogs;
    public void levelTipDialog(int type) {
        levelTipDialogs = new SignDialog(getActivity());
        View builder = levelTipDialogs.builder(R.layout.leveltips_dialog_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_title = builder.findViewById(R.id.tv_title);
        TextView tv_des = builder.findViewById(R.id.tv_des);
        if (type == 1) {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText("等级不足");
            tv_des.setText("请提升等级后再提现");
        } else if (type == 2) {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText("任务未完成");
            tv_des.setText("请完成后后再提现");
        } else if (type == 3) {
            tv_title.setText("");
            tv_title.setVisibility(View.GONE);
            tv_des.setText("您的提现次数已用完");
        }
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type==1){
                    mActivity.setPositionFg(1);
                }else if (type==2){
                    VideoActivity.videoJump(getActivity());

                }
                levelTipDialogs.setDismiss();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelTipDialogs.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(getActivity())) {
            levelTipDialogs.setShow();
        }
    }
    private int lottery_num;
    @Override
    public void getPayInfoSuccess(PayInfoBeans data) {
        if (data!=null){
            PayInfoBeans.UserOtherBean user_other = data.getUser_other();
            if (user_other!=null){
                String cashs = user_other.getCash();
                if (!TextUtils.isEmpty(cashs)){
                    ((MyApplication) MyApplication.getInstance()).cash=cashs;
                    tvCashMoney.setText(cashs);
                }
            }
            List<PayInfoBeans.OutArrBean> out_arr = data.getOut_arr();
            PayInfoBeans.CashOutBean cash_out = data.getCash_out();
            contents = cash_out.getContent();
            if (cash_out != null) {
                List<PayInfoBeans.CashOutBean.OutamountBean> outamount = cash_out.getOutamount();
                if (outamount!=null){
                    for (int i = 0; i < outamount.size(); i++) {
                        if (i==0){
                            outamount.get(i).setSelect(true);
                            PayInfoBeans.CashOutBean.OutamountBean outamountBean = outamount.get(i);
                            int total = outamountBean.getTotal();
                            String hb_total = outamountBean.getHb_total();
                            if (!TextUtils.isEmpty(hb_total)) {
                                String[] split = hb_total.split(",");
                                if (split != null && split.length > 0) {
                                    if (total < split.length) {
                                        String vNums = split[total];
                                        tvDesTitle.setText(outamountBean.getMoney()+"元提现说明");
                                        tvDes.setText("累计领取"+vNums+"个倒计时红包即可提现");
                                    }
                                }
                            }
                        }
                    }
                }
                withDrawAdapter.setNewData(outamount);
            }
            withDrawTopdAdapter.setNewData(out_arr);
             lottery_num = data.getLottery_num();
            if (lottery_num > 0 && isShowLotter) {
                if (!isShow){
                    showLunpanDialog();
                }
            }
            isShowLotter = false;
        }
    }

    private List<LotterInfoBeans> lotterList;

    @Override
    public void getLimitedDataSuccess(List<LotterInfoBeans> data) {
        lotterList = data;
        String[] mLuckStr = new String[8];
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                mLuckStr[i] = data.get(i).getMoney() + "";
            }
        }
        nineLuckPan.setmLuckStr(mLuckStr);
    }

    private int lotterType;
    private String lotterMoneys;
    private int lotterId;
    private int lotterPositions;

    @Override
    public void getlotterSuccess(LotterBeans data) {
        int lottery_id = data.getLottery_id();
        if (lotterList != null) {
            for (int i = 0; i < lotterList.size(); i++) {
                if (lottery_id == lotterList.get(i).getId()) {
                    lotterPositions = i;
                    lotterId = lotterList.get(i).getId();
                    lotterType = lotterList.get(i).getType();
                    if (lotterType == 1) {//1:账户余额 2:直接提现 3:提现机会
                        lotterMoneys = data.getMoney();
                    } else if (lotterType == 2) {
                        lotterMoneys = data.getMoney();
                    } else if (lotterType == 3) {
                        lotterMoneys = data.getOut_money();
                    }
                    nineLuckPan.setmLuckNum(lotterPositions);
                    nineLuckPan.startAnim();
                }
            }
        }
    }

    @Override
    public void weixinCashSuccess(WithDrawStatusBeans data) {
        if (data.getStatus() == 0) {
            if (!TextUtils.isEmpty(data.getErr_msg())) {
                ToastUtil.showToast(data.getErr_msg());
            } else {
                ToastUtil.showToast("提现失败");
            }
        } else {
            if (!TextUtils.isEmpty(data.getCash())) {
                tvCashMoney.setText(data.getCash() + "");
            }
            withDrawSuccessDialog();
            UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
            mPresenter.getPayInfo(userInfo.getId());
        }
    }

    private void wxLogin() {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(getActivity()).setShareConfig(config);
        UMShareAPI.get(getActivity()).deleteOauth(getActivity(), SHARE_MEDIA.WEIXIN, null);
        UMShareAPI.get(getActivity()).release();
        UMShareAPI.get(getActivity()).getPlatformInfo(getActivity(), SHARE_MEDIA.WEIXIN, new MyAuthLoginListener());
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
                UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
                String appVersionCode = CommonUtils.getAppVersionCode(getActivity());
                mPresenter.weixinCash(userInfo.getId() + "", "wx", wx_openid, cashMonesy, appVersionCode);
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


    private SignDialog redtipsDialogs;

    public void redtipsDialog(String moneys) {
        redtipsDialogs = new SignDialog(getActivity());
        View builder = redtipsDialogs.builder(R.layout.redtipstwo_dialogs_item);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_moneys = builder.findViewById(R.id.tv_moneys);
        ImageView iv_close  = builder.findViewById(R.id.iv_close);
       FrameLayout fl_ad_container_money  = builder.findViewById(R.id.fl_ad_container_money);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redtipsDialogs.setDismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redtipsDialogs.setDismiss();
            }
        });
        if (!TextUtils.isEmpty(moneys)) {
            tv_moneys.setText(moneys);
        }
        if (!CommonUtils.isDestory(getActivity())) {
            loadExpressAd(fl_ad_container_money);
            redtipsDialogs.setShow();
            showInset();
        }
    }

    private void showInset() {
        VUiKit.postDelayed(1300,()->{
            if (!CommonUtils.isDestory(getActivity())) {
                GromoreInsetAdShow.getInstance().showInset(getActivity(), "", new GromoreInsetAdShow.OnInsetAdShowCaback() {
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

                    }
                });
            }
        });
    }

    private SnatchDialog signRule;
    public void initSignRuleDialog(String tips) {
        signRule = new SnatchDialog(getActivity());
        View builder = signRule.builder(R.layout.signrule_dialog_item);
        TextView tv_contents = builder.findViewById(R.id.tv_contents);
        TextView tvSure1 = builder.findViewById(R.id.tvSure1);
        TextView tvSure2 = builder.findViewById(R.id.tvSure2);
        RelativeLayout rela_sure = builder.findViewById(R.id.rela_sure);
        tvSure1.setVisibility(View.VISIBLE);
        tvSure2.setVisibility(View.GONE);
        TextView tv_title = builder.findViewById(R.id.tv_title);
        tv_title.setText("提现说明");
        if (!TextUtils.isEmpty(tips)) {
            RichText.from(tips).into(tv_contents);
        }
        rela_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                signRule.setDismiss();
            }
        });
        if (!CommonUtils.isDestory(getActivity())) {
            signRule.setShow();
        }
    }


    @Override
    public void getFalseuserSuccess(List<FalseUserBeans> data) {
        List<String> stringList=new ArrayList<>();
        if (data!=null){
            for (int i = 0; i < data.size(); i++) {
                int random = CommonUtils.getRandom(1, 6);
                String randomss="";
                if (random==1){
                    randomss="0.3元";
                }else if (random==2){
                    randomss="10元";
                }else if (random==3){
                    randomss="50元";
                }else if (random==4){
                    randomss="100元";
                }else if (random==5){
                    randomss="50元";
                }else if (random==6){
                    randomss="10元";
                }
                String tips="恭喜玩家"+data.get(i).getNickname()+"提现"+randomss;
                stringList.add(tips);
            }
        }
        textswitchView.setBanner(stringList);
    }

    @Override
    public void getRedTaskDataSuccess(RedTaskBeans data) {
        if (data!=null){
            List<RedTaskBeans.HbOnlineTaskBean> hb_online_task = data.getHb_online_task();
            if (hb_online_task!=null){
                for (int i = 0; i < hb_online_task.size(); i++) {
                    int status = hb_online_task.get(i).getStatus();
                    if (status==0){
                        RedTaskBeans.HbOnlineTaskBean hbOnlineTaskBean = hb_online_task.get(i);
                        int other_num = hbOnlineTaskBean.getOther_num();
                        tvAnsnums.setText(other_num+"个");
                        progressbar.setMax(hbOnlineTaskBean.getNum()* 10);
                        progressbar.setProgress(hbOnlineTaskBean.getFinish_num() * 10);
                        return;
                    }
                }
            }
        }
    }

    private boolean isShowLotter;
    public void setOnRefresh() {
        isShowLotter = true;
        if (!isShow&&lottery_num>0){
            showLunpanDialog();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.getRedTaskData(CacheDataUtils.getInstance().getUserInfo().getId());
            mPresenter.getPayInfo(CacheDataUtils.getInstance().getUserInfo().getId());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // QQ授权回调需要配置这里
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }
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
}