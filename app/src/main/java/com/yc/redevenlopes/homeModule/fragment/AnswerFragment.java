package com.yc.redevenlopes.homeModule.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseLazyFragment;
import com.yc.redevenlopes.homeModule.activity.AnswerActivity;
import com.yc.redevenlopes.homeModule.activity.AnswerDetailsActivity;
import com.yc.redevenlopes.homeModule.contact.AnswerFgContact;
import com.yc.redevenlopes.homeModule.present.AnswerFgPresenter;
import com.yc.redevenlopes.homeModule.widget.CirCountDownView;
import com.yc.redevenlopes.utils.VUiKit;

import butterknife.BindView;
import butterknife.OnClick;

public class AnswerFragment extends BaseLazyFragment<AnswerFgPresenter> implements AnswerFgContact.View {

    @BindView(R.id.circountdownView)
    CirCountDownView circountdownView;
    @BindView(R.id.tv_queryTitle)
    TextView tvQueryTitle;
    @BindView(R.id.tv_an1)
    TextView tvAn1;
    @BindView(R.id.iv_an1)
    ImageView ivAn1;
    @BindView(R.id.rela_an1)
    RelativeLayout relaAn1;
    @BindView(R.id.tv_an2)
    TextView tvAn2;
    @BindView(R.id.iv_an2)
    ImageView ivAn2;
    @BindView(R.id.rela_an2)
    RelativeLayout relaAn2;
    @BindView(R.id.tv_an3)
    TextView tvAn3;
    @BindView(R.id.iv_an3)
    ImageView ivAn3;
    @BindView(R.id.rela_an3)
    RelativeLayout relaAn3;
    @BindView(R.id.tv_an4)
    TextView tvAn4;
    @BindView(R.id.iv_an4)
    ImageView ivAn4;
    @BindView(R.id.rela_an4)
    RelativeLayout relaAn4;
    private AnswerDetailsActivity activity;
    private int position;
    private int correctAns=1;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.answer_fragment, container, false);
    }

    public static AnswerFragment newInstance(int position) {
        AnswerFragment answerFragment = new AnswerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        answerFragment.setArguments(bundle);
        return answerFragment;
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
        position = getArguments().getInt("position");
        activity = (AnswerDetailsActivity) getActivity();
        circountdownView.setAddCountDownListener(new CirCountDownView.OnCountDownFinishListener() {
            @Override
            public void countDownFinished() {
                initSelects(correctAns);
                setsFg();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            setStartVa();
            //相当于Fragment的onResume，为true时，Fragment已经可见
        } else {
            setStopVa();
            //相当于Fragment的onPause，为false时，Fragment不可见
        }
    }

    public void setsFg(){
        VUiKit.postDelayed(1100, () -> {
            activity.setStepType(position+1);
        });
    }

    @OnClick({R.id.rela_an1, R.id.rela_an2, R.id.rela_an3, R.id.rela_an4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rela_an1:
                setStatus(0);
                setStopVa();
                setsFg();
                break;
            case R.id.rela_an2:
                setStatus(1);
                setStopVa();
                setsFg();
                break;
            case R.id.rela_an3:
                setStatus(2);
                setStopVa();
                setsFg();
                break;
            case R.id.rela_an4:
                setStatus(3);
                setStopVa();
                setsFg();
                break;
        }
    }

    public void setStatus(int select){
        if (select==0){
            if (select==correctAns){
                 relaAn1.setBackground(getActivity().getResources().getDrawable(R.drawable.tv_bg_green2));
                 tvAn1.setTextColor(getActivity().getResources().getColor(R.color.A1_47B34E));
                 ivAn1.setVisibility(View.VISIBLE);
                 ivAn1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_correct));
            }else {
                relaAn1.setBackground(getActivity().getResources().getDrawable(R.drawable.line_bg_red2));
                tvAn1.setTextColor(getActivity().getResources().getColor(R.color.A1_D90000));
                ivAn1.setVisibility(View.VISIBLE);
                ivAn1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_error));
                initSelects(correctAns);
            }
        }else if (select==1){
            if (select==correctAns){
                relaAn2.setBackground(getActivity().getResources().getDrawable(R.drawable.tv_bg_green2));
                tvAn2.setTextColor(getActivity().getResources().getColor(R.color.A1_47B34E));
                ivAn2.setVisibility(View.VISIBLE);
                ivAn2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_correct));
            }else {
                relaAn2.setBackground(getActivity().getResources().getDrawable(R.drawable.line_bg_red2));
                tvAn2.setTextColor(getActivity().getResources().getColor(R.color.A1_D90000));
                ivAn2.setVisibility(View.VISIBLE);
                ivAn2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_error));
                initSelects(correctAns);
            }
        }else if (select==2){
            if (select==correctAns){
                relaAn3.setBackground(getActivity().getResources().getDrawable(R.drawable.tv_bg_green2));
                tvAn3.setTextColor(getActivity().getResources().getColor(R.color.A1_47B34E));
                ivAn3.setVisibility(View.VISIBLE);
                ivAn3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_correct));
            }else {
                relaAn3.setBackground(getActivity().getResources().getDrawable(R.drawable.line_bg_red2));
                tvAn3.setTextColor(getActivity().getResources().getColor(R.color.A1_D90000));
                ivAn3.setVisibility(View.VISIBLE);
                ivAn3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_error));
                initSelects(correctAns);
            }
        }else if (select==3){
            if (select==correctAns){
                relaAn4.setBackground(getActivity().getResources().getDrawable(R.drawable.tv_bg_green2));
                tvAn4.setTextColor(getActivity().getResources().getColor(R.color.A1_47B34E));
                ivAn4.setVisibility(View.VISIBLE);
                ivAn4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_correct));
            }else {
                relaAn4.setBackground(getActivity().getResources().getDrawable(R.drawable.line_bg_red2));
                tvAn4.setTextColor(getActivity().getResources().getColor(R.color.A1_D90000));
                ivAn4.setVisibility(View.VISIBLE);
                ivAn4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_error));
                initSelects(correctAns);
            }
        }
    }

    private void initSelects(int correctAns) {
        if (correctAns==0){
            relaAn1.setBackground(getActivity().getResources().getDrawable(R.drawable.tv_bg_green2));
            tvAn1.setTextColor(getActivity().getResources().getColor(R.color.A1_47B34E));
            ivAn1.setVisibility(View.VISIBLE);
            ivAn1.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_correct));
        }else if (correctAns==1){
            relaAn2.setBackground(getActivity().getResources().getDrawable(R.drawable.tv_bg_green2));
            tvAn2.setTextColor(getActivity().getResources().getColor(R.color.A1_47B34E));
            ivAn2.setVisibility(View.VISIBLE);
            ivAn2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_correct));
        }else if (correctAns==2){
            relaAn3.setBackground(getActivity().getResources().getDrawable(R.drawable.tv_bg_green2));
            tvAn3.setTextColor(getActivity().getResources().getColor(R.color.A1_47B34E));
            ivAn3.setVisibility(View.VISIBLE);
            ivAn3.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_correct));
        }else if (correctAns==3){
            relaAn4.setBackground(getActivity().getResources().getDrawable(R.drawable.tv_bg_green2));
            tvAn4.setTextColor(getActivity().getResources().getColor(R.color.A1_47B34E));
            ivAn4.setVisibility(View.VISIBLE);
            ivAn4.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_correct));
        }
    }


    public void setStartVa(){
        if (circountdownView!=null){
            circountdownView.startCountDown();
        }
    }

    public void setStopVa(){
        if (circountdownView!=null){
            circountdownView.stopCount();
        }
    }
}