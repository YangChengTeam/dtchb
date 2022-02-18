package com.yc.wxchb.beans.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yc.wxchb.R;
import com.yc.wxchb.base.BaseLazyFragment;
import com.yc.wxchb.beans.contact.WithDrawContract;
import com.yc.wxchb.beans.present.WithDrawPresenter;
import com.yc.wxchb.dialog.SignDialog;
import com.yc.wxchb.dialog.SnatchDialog;
import com.yc.wxchb.utils.CommonUtils;
import com.yc.wxchb.widget.MyTextSwitchView;
import com.yc.wxchb.widget.ScrollWithRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;


public class WithDrawFragment extends BaseLazyFragment<WithDrawPresenter> implements WithDrawContract.View {

    @BindView(R.id.textswitchView)
    MyTextSwitchView textswitchView;
    @BindView(R.id.tv_amount_moneys)
    TextView tvAmountMoneys;
    @BindView(R.id.tv_amount_sure)
    TextView tvAmountSure;
    @BindView(R.id.rela_amount)
    RelativeLayout relaAmount;
    @BindView(R.id.tv_ansnums)
    TextView tvAnsnums;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_gojump)
    TextView tvGojump;
    @BindView(R.id.rela_nextAmout)
    RelativeLayout relaNextAmout;
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

    }

    private SnatchDialog lunpanDialogs;

    public void lunpanDialog() {
        lunpanDialogs = new SnatchDialog(getActivity());
        View builder = lunpanDialogs.builder(R.layout.lunpan_dialogs_item);
        if (!CommonUtils.isDestory(getActivity())) {
            lunpanDialogs.setShow();
        }
    }

    @OnClick({R.id.tv_amount_sure, R.id.tv_gojump, R.id.tv_recodeThree, R.id.line_sure, R.id.line_invation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_amount_sure:
                break;
            case R.id.tv_gojump:
                break;
            case R.id.tv_recodeThree:
                break;
            case R.id.line_sure:
                break;
            case R.id.line_invation:
                lunpanDialog();
                break;
        }
    }
}