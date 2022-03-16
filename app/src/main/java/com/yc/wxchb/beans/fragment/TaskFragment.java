package com.yc.wxchb.beans.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.wxchb.R;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.base.BaseLazyFragment;
import com.yc.wxchb.beans.activity.MainActivity;
import com.yc.wxchb.beans.activity.VideoActivity;
import com.yc.wxchb.beans.adapter.LimitedAdapter;
import com.yc.wxchb.beans.adapter.TaskAdapter;
import com.yc.wxchb.beans.contact.TaskContract;
import com.yc.wxchb.beans.module.beans.LimitedBeans;
import com.yc.wxchb.beans.module.beans.LimitedRedBeans;
import com.yc.wxchb.beans.module.beans.RedTaskBeans;
import com.yc.wxchb.beans.module.beans.TaskLineBean;
import com.yc.wxchb.beans.module.beans.UserInfo;
import com.yc.wxchb.beans.present.TaskPresenter;
import com.yc.wxchb.dialog.PrizeDialog;
import com.yc.wxchb.dialog.SnatchDialog;
import com.yc.wxchb.utils.CacheDataUtils;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.utils.VUiKit;
import com.yc.wxchb.utils.adgromore.GromoreAdShowFour;
import com.yc.wxchb.utils.adgromore.GromoreAdShowTwo;
import com.yc.wxchb.widget.ScrollWithRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TaskFragment extends BaseLazyFragment<TaskPresenter> implements TaskContract.View {

    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    @BindView(R.id.tv_titles)
    TextView tvTitles;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.line_moneyJunp)
    LinearLayout lineMoneyJunp;
    @BindView(R.id.limited_recyclerView)
    ScrollWithRecyclerView limitedRecyclerView;
    private TaskAdapter taskAdapter;
    private LimitedAdapter limitedAdapter;
    private int limitedId;
    private int taskId;
    private  MainActivity mActivity;
    private int videoType;//1倒计时视频  2限量视频
    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    protected android.view.View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        android.view.View rootView = (android.view.View) super.onCreateView(inflater, container, savedInstanceState);
        return (android.view.View) rootView;
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
        mPresenter.getLimitedData(CacheDataUtils.getInstance().getUserInfo().getId());
        mPresenter.getRedTaskData(CacheDataUtils.getInstance().getUserInfo().getId());
        mActivity = (MainActivity) getActivity();
        tvMoney.setText(((MyApplication) MyApplication.getInstance()).cash);
    }
    private int type;
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        taskAdapter = new TaskAdapter(null);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.line_status:
                        List<RedTaskBeans.HbOnlineTaskBean> data = taskAdapter.getData();
                        taskId = data.get(position).getId();
                        int status = data.get(position).getStatus();
                        if (status==0){
                             videoType=1;
                           //  showAdsyuan();
                            mActivity.showjiliAd(1,"task");
                        }else if (status==1){
                            type=data.get(position).getType();
                            if (data.get(position).getType()==0){//账户余额
                                mPresenter.getTaskMoney(CacheDataUtils.getInstance().getUserInfo().getId(),taskId);
                            }else {
                                mPresenter.getTaskMoney(CacheDataUtils.getInstance().getUserInfo().getId(),taskId);
                                mActivity.setPositionFg(2);
                            }
                        }
                        break;
                }
            }
        });

        LinearLayoutManager linearLayoutManagerTwo = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        limitedAdapter = new LimitedAdapter(null);
        limitedRecyclerView.setLayoutManager(linearLayoutManagerTwo);
        limitedRecyclerView.setAdapter(limitedAdapter);
        limitedAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.line_status:
                        List<LimitedBeans> data = limitedAdapter.getData();
                        limitedId = data.get(position).getId();
                        int status = data.get(position).getStatus();
                        if (status==0){
                            showAds(data.get(position).getAd_code()+"");
                        }else if (status==1){
                            withDrawSuccessDialog();
                        }
                        break;
                }
            }
        });
    }


    public void showAdsyuan( ){
        GromoreAdShowTwo.getInstance().showjiliAd("","1",new GromoreAdShowTwo.OnAdShowCaback() {
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
                mPresenter.getTaskLine(CacheDataUtils.getInstance().getUserInfo().getId());
            }
        });
    }

    public void showAds(String codes){
        GromoreAdShowFour.getInstance().showjiliAd("", codes,new GromoreAdShowFour.OnAdShowCaback() {
            @Override
            public void onRewardedAdShow() {

            }

            @Override
            public void onRewardedAdShowFail() {
                limitipsDialog();
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
                mPresenter.getLimiteRed(CacheDataUtils.getInstance().getUserInfo().getId(),limitedId);
            }
        });
    }

    private PrizeDialog limitipsDialogs;
    public void limitipsDialog(){
        limitipsDialogs = new PrizeDialog(getActivity());
        View builder = limitipsDialogs.builder(R.layout.limitips_dialogs_item);
        if (!CommonUtils.isDestory(getActivity())){
            limitipsDialogs.setShow();
        }
    }


    @OnClick({R.id.line_moneyJunp})
    public void onViewClicked(android.view.View view) {
        switch (view.getId()) {
            case R.id.line_moneyJunp:
                mActivity.setPositionFg(2);
                break;
        }
    }

    private boolean isFirst;
    @Override
    public void getLimitedDataSuccess(List<LimitedBeans> data) {
        if (data!=null){
            if (data!=null){
                for (int i = 0; i < data.size(); i++) {
                    if (i==0){
                        int ad_code = data.get(0).getAd_code();
                        if (!isFirst){
                            isFirst=true;
                            GromoreAdShowFour.getInstance().setContexts(getActivity(),ad_code+"");
                        }
                    }
                }
                limitedAdapter.setNewData(data);
            }
        }
    }

    @Override
    public void getRedTaskDataSuccess(RedTaskBeans data) {
        if (data!=null){
            List<RedTaskBeans.HbOnlineTaskBean> hb_online_task = data.getHb_online_task();
            if (hb_online_task!=null){
                List<RedTaskBeans.HbOnlineTaskBean> lists=new ArrayList<>();
                for (int i = 0; i < hb_online_task.size(); i++) {
                    int status = hb_online_task.get(i).getStatus();
                    if (status!=2){
                        lists.add(hb_online_task.get(i));
                    }
                }
                taskAdapter.setNewData(lists);
            }
        }
    }

    @Override
    public void getLimiteRedSuccess(LimitedRedBeans data) {
        mPresenter.getLimitedData(CacheDataUtils.getInstance().getUserInfo().getId());
    }

    @Override
    public void getTaskLineSuccess(TaskLineBean data) {
        mPresenter.getRedTaskData(CacheDataUtils.getInstance().getUserInfo().getId());
        if (data!=null){
            ((MyApplication) MyApplication.getInstance()).hb_Nums=data.getHb_num();
            redtipsDialog(data.getRed_money());
            if (!TextUtils.isEmpty(data.getCash())){
                ((MyApplication) MyApplication.getInstance()).cash=data.getCash();
                tvMoney.setText(data.getCash());
            }
        }
    }

    @Override
    public void getTaskMoneySuccess(TaskLineBean data) {
        mPresenter.getRedTaskData(CacheDataUtils.getInstance().getUserInfo().getId());
        if (data!=null){
            if (type==0){//账户余额
                redtipsDialog(data.getRed_money());
                if (!TextUtils.isEmpty(data.getCash())){
                    ((MyApplication) MyApplication.getInstance()).cash=data.getCash();
                    tvMoney.setText(data.getCash());
                }
            }else {
                mActivity.setPositionFg(2);
            }
        }
    }

    public void setVideoCallBacks(boolean isVideoClick) {
        if (videoType==2){

        }else if (videoType==1){
            mPresenter.getTaskLine(CacheDataUtils.getInstance().getUserInfo().getId());
        }
    }

    private SnatchDialog withDrawSuccessDialogs;
    public void withDrawSuccessDialog(){
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
        if (!CommonUtils.isDestory(getActivity())){
            withDrawSuccessDialogs.setShow();
        }
    }
    private PrizeDialog redtipsDialogs;
    public void redtipsDialog(String moneys){
        redtipsDialogs = new PrizeDialog(getActivity());
        View builder = redtipsDialogs.builder(R.layout.redtips_dialogs_item);
        TextView tv_moneys=builder.findViewById(R.id.tv_moneys);
        if (!TextUtils.isEmpty(moneys)){
            tv_moneys.setText("+"+moneys+"元");
        }
        if (!CommonUtils.isDestory(getActivity())){
            redtipsDialogs.setShow();
            VUiKit.postDelayed(3000,()->{
                if (!CommonUtils.isDestory(getActivity())&&redtipsDialogs!=null) {
                    redtipsDialogs.setDismiss();
                }
            });
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
             tvMoney.setText(((MyApplication) MyApplication.getInstance()).cash);
        }
    }
}