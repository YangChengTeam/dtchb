package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.SnatchTreasureRuleContact;
import com.yc.redevenlopes.homeModule.present.SnatchTreasureRulePresenter;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;

/**
 * 夺宝结果
 */
public class SnatchTreasureRuleActivity extends BaseActivity<SnatchTreasureRulePresenter> implements SnatchTreasureRuleContact.View {

    @BindView(R.id.tv_contents)
    TextView tvContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_snatch_treasure_rule;
    }

    @Override
    public void initEventAndData() {
        setTitle("夺宝奇兵法则");
        String strContents = getIntent().getStringExtra("strContents");
        if (!TextUtils.isEmpty(strContents)) {
            RichText.fromHtml(strContents).into(tvContents);
        }
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void snatchTreasureRuleJump(Context context, String strContents) {
        Intent intent = new Intent(context, SnatchTreasureRuleActivity.class);
        intent.putExtra("strContents", strContents);
        context.startActivity(intent);
    }
}