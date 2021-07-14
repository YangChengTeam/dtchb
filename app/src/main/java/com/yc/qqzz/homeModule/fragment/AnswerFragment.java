package com.yc.qqzz.homeModule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseLazyFragment;
import com.yc.qqzz.dialog.PrizeDialog;
import com.yc.qqzz.dialog.RedDialogTwo;
import com.yc.qqzz.dialog.SignDialog;
import com.yc.qqzz.dialog.SnatchDialog;
import com.yc.qqzz.homeModule.activity.DayUpgradeActivity;
import com.yc.qqzz.homeModule.contact.GrabRedFgContract;
import com.yc.qqzz.homeModule.present.GrabRedFgPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class AnswerFragment extends BaseLazyFragment<GrabRedFgPresenter> implements GrabRedFgContract.View {
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_continumMoney)
    TextView tvContinumMoney;
    @BindView(R.id.tv_canWithDrawMoney)
    TextView tvCanWithDrawMoney;
    @BindView(R.id.iv_red)
    ImageView ivRed;
    @BindView(R.id.tv_redTimes)
    TextView tvRedTimes;
    @BindView(R.id.line_lineRed)
    LinearLayout lineLineRed;
    @BindView(R.id.tv_answerNums)
    TextView tvAnswerNums;
    @BindView(R.id.tv_answerSureNums)
    TextView tvAnswerSureNums;
    @BindView(R.id.tv_answerTitle)
    TextView tvAnswerTitle;
    @BindView(R.id.tv_continueAnserNums)
    TextView tvContinueAnserNums;
    @BindView(R.id.progressbar_reward)
    ProgressBar progressbarReward;
    @BindView(R.id.tv_withDraw_continueAnserNums)
    TextView tvWithDrawContinueAnserNums;
    @BindView(R.id.tv_redNums)
    TextView tvRedNums;
    @BindView(R.id.tv_redAllNums)
    TextView tvRedAllNums;

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

    }

    private SignDialog withDrawDialog;
    private RedDialogTwo redDialogjiang;
    private RedDialogTwo redRewardDialogjiang;
    private SnatchDialog redPrizeDialogjiang;
    private PrizeDialog redPrizetwoDialog;

    public void initSignDialog() {
        withDrawDialog = new SignDialog(getActivity());
        View builder = withDrawDialog.builder(R.layout.withdraw_dialog);
        TextView tv_withDraws = builder.findViewById(R.id.tv_withDraws);
        TextView tv_withDraws_num = builder.findViewById(R.id.tv_withDraws_num);
        TextView tv_withDraws_allNum = builder.findViewById(R.id.tv_withDraws_allNum);
        TextView tv_withDraws_status = builder.findViewById(R.id.tv_withDraws_status);
        TextView tv_withDrawsTwo = builder.findViewById(R.id.tv_withDrawsTwo);
        TextView tv_withDraw = builder.findViewById(R.id.tv_withDraws);
        TextView tv_withDrawsTwo_num = builder.findViewById(R.id.tv_withDrawsTwo_num);
        TextView tv_withDrawsTwo_allNum = builder.findViewById(R.id.tv_withDrawsTwo_allNum);
        TextView tv_withDrawsTwo_status = builder.findViewById(R.id.tv_withDrawsTwo_status);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawDialog.setDismiss();
            }
        });
        withDrawDialog.setShow();
    }


    public void redRewardDialog() {
        redDialogjiang = new RedDialogTwo(getActivity());
        View builder = redDialogjiang.builder(R.layout.redreward_dialog_item);


        redDialogjiang.setShow();
    }

    public void redRewardContinueDialog() {
        redRewardDialogjiang = new RedDialogTwo(getActivity());
        View builder = redRewardDialogjiang.builder(R.layout.redrewardcontiune_dialog_item);
        redRewardDialogjiang.setShow();
    }

    public void redPrizeDialog() {
        redPrizeDialogjiang = new SnatchDialog(getActivity());
        View builder = redPrizeDialogjiang.builder(R.layout.redprize_dialog_item);
        redPrizeDialogjiang.setShow();
    }


    public void redPrizetwoDialog() {
        redPrizetwoDialog = new PrizeDialog(getActivity());
        View builder = redPrizetwoDialog.builder(R.layout.redprizetwo_dialog_item);
        redPrizetwoDialog.setShow();
    }

    @OnClick({R.id.tv_withDraw, R.id.line_lineRed})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_withDraw:
                //redPrizetwoDialog();
                break;
            case R.id.line_lineRed:
                DayUpgradeActivity.DayUpgradeActivityJump(getActivity());
                break;
        }
    }
}