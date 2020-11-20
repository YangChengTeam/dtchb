package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.SnatchDialog;
import com.yc.redevenlopes.homeModule.contact.SnatchTreasureContact;
import com.yc.redevenlopes.homeModule.present.SnatchTreasurePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 夺宝
 */
public class SnatchTreasureActivity extends BaseActivity<SnatchTreasurePresenter> implements SnatchTreasureContact.View {

    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.line_ruleDetails)
    LinearLayout lineRuleDetails;
    @BindView(R.id.line_snatchsOne)
    LinearLayout lineSnatchsOne;
    @BindView(R.id.line_snatchsTwo)
    LinearLayout lineSnatchsTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_snatch_treasure;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }


    public static void snatchTreasureJump(Context context) {
        Intent intent = new Intent(context, SnatchTreasureActivity.class);
        context.startActivity(intent);
    }

    private void showDialogs() {
        SnatchDialog snatchDialog = new SnatchDialog(this);
        View builder = snatchDialog.builder(R.layout.snatch_item);
        snatchDialog.setShow();
    }

    @OnClick({R.id.iv_back, R.id.tv_history, R.id.line_ruleDetails, R.id.line_snatchsOne, R.id.line_snatchsTwo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_history:
                SnatchTreasureHistoryActivity.snatchtreasurehistoryJump(this);
                break;
            case R.id.line_ruleDetails:
                SnatchTreasureRuleActivity.snatchTreasureRuleJump(this);
                break;
            case R.id.line_snatchsOne:
                showDialogs();
                break;
            case R.id.line_snatchsTwo:
                showDialogs();
                break;
        }
    }

}