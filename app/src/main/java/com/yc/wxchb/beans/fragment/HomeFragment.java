package com.yc.wxchb.beans.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.yc.wxchb.R;
import com.yc.wxchb.application.MyApplication;
import com.yc.wxchb.base.BaseLazyFragment;
import com.yc.wxchb.beans.activity.AnswerActivity;
import com.yc.wxchb.beans.activity.HotActivity;
import com.yc.wxchb.beans.activity.InvationfriendActivity;
import com.yc.wxchb.beans.activity.MainActivity;
import com.yc.wxchb.beans.activity.RedWallActivity;
import com.yc.wxchb.beans.activity.VideoActivity;
import com.yc.wxchb.beans.contact.HomefgContract;
import com.yc.wxchb.beans.module.beans.OtherBeans;
import com.yc.wxchb.beans.present.HomefgPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseLazyFragment<HomefgPresenter> implements HomefgContract.View {

    @BindView(R.id.iv_invations)
    ImageView ivInvations;
    @BindView(R.id.tv_titles)
    TextView tvTitles;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.line_moneyJunp)
    LinearLayout lineMoneyJunp;
    @BindView(R.id.line_red)
    LinearLayout lineRed;
    @BindView(R.id.line_lineAnswer)
    LinearLayout lineLineAnswer;
    @BindView(R.id.line_lineredwall)
    LinearLayout lineLineredwall;
    @BindView(R.id.iv_hot)
    ImageView ivHot;
    private  MainActivity mactivity;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
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
         mactivity = (MainActivity) getActivity();
    }

    @OnClick({R.id.line_moneyJunp, R.id.line_red, R.id.line_lineAnswer, R.id.line_lineredwall,R.id.iv_invations,R.id.iv_hot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_moneyJunp:
                mactivity.setPositionFg(2);
                break;
            case R.id.line_red:
                VideoActivity.videoJump(getActivity());
                break;
            case R.id.line_lineAnswer:
                AnswerActivity.answerJump(getActivity());
                break;
            case R.id.line_lineredwall:
                RedWallActivity.redWallJump(getActivity());
                break;
            case R.id.iv_invations:
                InvationfriendActivity.invationfriendJump(getActivity());
                break;
            case R.id.iv_hot:
                HotActivity.adhotJump(getActivity(),"1");
                break;
        }
    }

    public void setRefresh(OtherBeans otherBeans) {
        if (tvMoney!=null&&otherBeans!=null){
            tvMoney.setText(otherBeans.getCash());
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