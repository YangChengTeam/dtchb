package com.yc.qqzz.homeModule.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAD;
import com.qq.e.ads.rewardvideo2.ExpressRewardVideoAdListener;
import com.qq.e.comm.util.VideoAdValidity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yc.adplatform.AdPlatformSDK;
import com.yc.adplatform.ad.core.AdCallback;
import com.yc.adplatform.ad.core.AdError;
import com.yc.qqzz.R;
import com.yc.qqzz.application.MyApplication;
import com.yc.qqzz.base.BaseLazyFragment;
import com.yc.qqzz.constants.Constant;
import com.yc.qqzz.dialog.SnatchDialog;
import com.yc.qqzz.homeModule.activity.InvationfriendActivity;
import com.yc.qqzz.homeModule.activity.MainActivity;
import com.yc.qqzz.homeModule.activity.WithDrawRecodeActivity;
import com.yc.qqzz.homeModule.adapter.WithDrawAdapter;
import com.yc.qqzz.homeModule.bean.WithDrawHomeBeans;
import com.yc.qqzz.homeModule.bean.WxCashBeans;
import com.yc.qqzz.homeModule.contact.WithDrawitemContract;
import com.yc.qqzz.homeModule.module.bean.UserInfozq;
import com.yc.qqzz.homeModule.present.WithDrawitemPresenter;
import com.yc.qqzz.utils.AppSettingUtils;
import com.yc.qqzz.utils.CacheDataUtils;
import com.yc.qqzz.utils.CommonUtils;
import com.yc.qqzz.utils.SoundPoolUtils;
import com.yc.qqzz.widget.ScrollWithRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


public class WithDrawitemFragment extends BaseLazyFragment<WithDrawitemPresenter> implements WithDrawitemContract.View {


    @BindView(R.id.rela_avatar)
    RelativeLayout relaAvatar;
    @BindView(R.id.progressbar_reward)
    ProgressBar progressbarReward;
    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    @BindView(R.id.line_dayCashTitle)
    LinearLayout lineDayCashTitle;
    @BindView(R.id.tv_dayCashStatus)
    TextView tvDayCashStatus;
    @BindView(R.id.tv_dayCashMoneys)
    TextView tvDayCashMoneys;
    @BindView(R.id.iv_dayCashSelect)
    ImageView ivDayCashSelect;
    @BindView(R.id.rela_dayCash_item)
    RelativeLayout relaDayCashItem;
    @BindView(R.id.line_dayCash)
    LinearLayout lineDayCash;
    @BindView(R.id.tv_cashMoney)
    TextView tvCashMoney;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    private WithDrawAdapter withDrawAdapter;
    private boolean dayMoneysIsSelect;
    private int videoType;// 1
    private int userLevel;
    private String cashMoney;
    private String dayMoneys;
    private MainActivity activity;
    private WithDrawHomeBeans.UserOtherBean user_other;

    public WithDrawitemFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        dayMoneysIsSelect=false;
        activity = (MainActivity) getActivity();
        initRecyclerView();
        UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
        mPresenter.getPayinfo(userInfo.getImei(), userInfo.getGroup_id());
    }



    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_with_drawitem, container, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = (View) super.onCreateView(inflater, container, savedInstanceState);
        return (View) rootView;
    }

    @OnClick({R.id.tv_go, R.id.tv_recode,R.id.tv_recodeThree, R.id.tv_sure,R.id.rela_dayCash_item,R.id.line_invation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_go:
                break;
            case R.id.tv_recodeThree:
            case R.id.tv_recode:
                WithDrawRecodeActivity.withDrawRecodeJump(getActivity());
                break;
            case R.id.tv_sure:
                videoType=1;
                activity.showjiliAd(3,"withdraw");
                break;
            case R.id.rela_dayCash_item:
                List<WithDrawHomeBeans.CashOutBean.OutamountBean> lists = withDrawAdapter.getData();
                dayMoneysIsSelect=true;
                cashMoney=dayMoneys;
                setStatus();
                for (int i = 0; i < lists.size(); i++) {
                    lists.get(i).setSelect(false);
                }
                withDrawAdapter.notifyDataSetChanged();
                break;
            case R.id.line_invation:
                InvationfriendActivity.invationfriendJump(getActivity());
                break;
        }
    }

    private void  initMoneyDia(){
        userLevel=((MyApplication) MyApplication.getInstance()).levels;
      String cashMoneys= ((MyApplication) MyApplication.getInstance()).cash;
        if (dayMoneysIsSelect){//每日提现
            setDialogs(3,"");
        }else {
            List<WithDrawHomeBeans.CashOutBean.OutamountBean> lists = withDrawAdapter.getData();
            String level = "2";
            float money = 0.01f;
            int other_num=0;
            if (user_other!=null&&!TextUtils.isEmpty(cashMoneys)){
                float userCash = Float.parseFloat(cashMoneys);
                int selectPosition=-1;
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).isSelect()) {
                        other_num = lists.get(i).getOther_num();
                        level = lists.get(i).getOut_level();
                        money = Float.parseFloat(lists.get(i).getMoney());
                        if (userCash >= money) {//可提现

                        }else {
                            setDialogs(2, level);
                            return;
                        }
                    }
                }
                for (int i = 0; i < lists.size(); i++) {
                    if (lists.get(i).isSelect()) {
                        selectPosition=i;
                        other_num = lists.get(i).getOther_num();
                        level = lists.get(i).getOut_level();
                        money = Float.parseFloat(lists.get(i).getMoney());
                    }
                    if (selectPosition==-1){
                        if (lists.get(i).getOther_num()>0){
                            String tishi="完成"+lists.get(i).getNum()+"次"+lists.get(i).getMoney()+"元提现才能提现"+money+"元哦!";
                            //  完成9次0.3元提现才能提现100元哦！
                            setDialogs(4, tishi);
                            return;
                        }
                    }
                }
                if (userLevel >= Integer.parseInt(level)) {//
                    if (other_num>0){//提现次数
                        cashMoney = String.valueOf(money);
                        setDialogs(3,"");
                    }else {
                        ToastUtil.showToast("今日提现次数已用完");
                    }
                } else {//等级不够
                    setDialogs(1, level);
                }
            }
        }
    }

    public void setDialogs(int type, String level) {
        DisposeTintFragment disposeTintFragment = new DisposeTintFragment();
        if (type == 1) {
            if (userLevel==2){
                if (userLevel==20){
                    disposeTintFragment.setViewStatus( "白金会员专属提现金额", "确定");
                }else {
                    disposeTintFragment.setViewStatus("等级不足，升级提现", "去升级");
                }
            }else {
                disposeTintFragment.setViewStatus("达到" + level + "级可提现", "去升级");
            }
        } else if ((type == 2)){
            disposeTintFragment.setViewStatus("余额不足，请达到后再来提现", "确定");
        } else if ((type == 3)){
            disposeTintFragment.setViewStatus("微信提现需要绑定微信", "确定");
        } else if ((type == 4)){//前面的还没有提完
            disposeTintFragment.setViewStatus(level, "去做任务");
        } else if ((type ==5)){//今日已提现
            disposeTintFragment.setViewStatus("今日已提现，请明日再来", "确定");
        }else if ((type ==6)){//达到5级提现，赶快去升级吧
            disposeTintFragment.setViewStatus("达到5级提现，赶快去升级吧", "确定");
        }else if ((type ==7)){//达到5级提现，赶快去升级吧

        }else if ((type ==8)){//签到专属奖励，请完成7天签到。
            disposeTintFragment.setViewStatus("签到专属奖励，请完成7天签到。", "确定");
        }else if ((type ==9)){//签到专属奖励，请完成7天签到。
            if (userLevel==20){
                disposeTintFragment.setViewStatus("白金会员专属提现金额", "确定");
            }else {
                disposeTintFragment.setViewStatus("达到20级提现，赶快去升级吧", "去升级");
            }
        }
        disposeTintFragment.setListenCash(new DisposeTintFragment.OnClickListenCash() {
            @Override
            public void sure()  {
                if (type == 1) {

                }else if (type == 3){
                    wxLogin();
                }else if (type == 4){
                  //  MemberActivity.memberJump(WithdrawActivity.this);
                }else if (type == 6||type==9){
                   // MemberActivity.memberJump(WithdrawActivity.this);
                }else if (type == 2){

                }
            }

            @Override
            public void close() {
                if (type == 1) {

                }else if (type == 3){
                    wxLogin();
                }else if (type == 4){

                }else if (type == 6||type==9){

                }else if (type == 2){

                }
            }
        });
        disposeTintFragment.show(getChildFragmentManager(), "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // QQ授权回调需要配置这里
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }

    private void wxLogin() {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(getActivity()).setShareConfig(config);
        UMShareAPI.get(getActivity()).deleteOauth(getActivity(), SHARE_MEDIA.WEIXIN,null);
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
            String  name = map.get("name");
            String  profile_image_url = map.get("profile_image_url");
            if (!TextUtils.isEmpty(wx_openid)) {
                UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
                String appVersionCode = CommonUtils.getAppVersionCode(getActivity());
                mPresenter.weixinCash(userInfo.getImei(),userInfo.getGroup_id() + "", "wx", wx_openid,cashMoney,appVersionCode);
            }
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            ToastUtil.showToast("授权失败:"+throwable.getMessage()+i+"------getLocalizedMessage()"+throwable.getLocalizedMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            ToastUtil.showToast("授权取消");
        }
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        withDrawAdapter = new WithDrawAdapter(null);
        recyclerView.setAdapter(withDrawAdapter);
        withDrawAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                List<WithDrawHomeBeans.CashOutBean.OutamountBean> lists = adapter.getData();
                dayMoneysIsSelect=false;
                setStatus();
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

    @Override
    public void getPayinfoSuccess(WithDrawHomeBeans data) {
        if (data!=null){
            String day_money = data.getDay_money();
            WithDrawHomeBeans.CashOutBean cash_out = data.getCash_out();
            user_other = data.getUser_other();
            if (!TextUtils.isEmpty(day_money)&&!"0".equals(day_money)){
                dayMoneys=day_money;
                lineDayCash.setVisibility(View.VISIBLE);
                lineDayCashTitle.setVisibility(View.VISIBLE);
            }else {
                lineDayCash.setVisibility(View.GONE);
                lineDayCashTitle.setVisibility(View.GONE);
            }
            if (cash_out!=null){
                List<WithDrawHomeBeans.CashOutBean.OutamountBean> outamount = cash_out.getOutamount();
                for (int i = 0; i < outamount.size(); i++) {
                    if (i==0){
                        dayMoneysIsSelect=false;
                        setStatus();
                        outamount.get(i).setSelect(true);
                    }else {
                        outamount.get(i).setSelect(false);
                    }
                }
                withDrawAdapter.setNewData(outamount);
                withDrawAdapter.notifyDataSetChanged();
            }
            if (data.getUser_other()!=null){
                WithDrawHomeBeans.UserOtherBean user_other = data.getUser_other();
                userLevel=user_other.getLevel();
                tvLevel.setText(userLevel+"");
                ((MyApplication) MyApplication.getInstance()).levels =user_other.getLevel();
                ((MyApplication) MyApplication.getInstance()).cash =user_other.getCash();
                tvCashMoney.setText(user_other.getCash());
            }
        }
    }

    @Override
    public void getWeixinCashSuccess(WxCashBeans data) {
        if (data.getStatus()==0){
            if (!TextUtils.isEmpty(data.getErr_msg())){
                ToastUtil.showToast(data.getErr_msg());
            }else {
                ToastUtil.showToast("提现失败");
            }
        }else {
            if (!TextUtils.isEmpty(data.getCash())){
                tvCashMoney.setText(data.getCash()+"元");
            }
            cashSuccessDialogs();
            UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
            mPresenter.getPayinfo(userInfo.getImei(), userInfo.getGroup_id());
        }
    }


    private SnatchDialog cashSuccessDialog;
    public void cashSuccessDialogs() {
        cashSuccessDialog = new SnatchDialog(getActivity());
        View builder = cashSuccessDialog.builder(R.layout.cashsuccess_item_dialog);
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashSuccessDialog.setDismiss();
            }
        });
        cashSuccessDialog.setShow();
    }

    public void setStatus(){
        if (dayMoneysIsSelect){
            relaDayCashItem.setBackgroundResource(R.drawable.line_bg_yellow12);
            tvDayCashMoneys.setTextColor(getResources().getColor(R.color.gray_FB9D3F));
            ivDayCashSelect.setVisibility(View.VISIBLE);
        }else {
            relaDayCashItem.setBackgroundResource(R.drawable.line_bg_black1);
            tvDayCashMoneys.setTextColor(getResources().getColor(R.color.A1_000000));
            ivDayCashSelect.setVisibility(View.GONE);
        }
    }


    public void setVideoCallBacks(boolean isVideoClick) {
        if (videoType==1){
            initMoneyDia();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            tvLevel.setText(((MyApplication) MyApplication.getInstance()).levels+"");
            tvCashMoney.setText(((MyApplication) MyApplication.getInstance()).cash+"元");
        }
    }
}