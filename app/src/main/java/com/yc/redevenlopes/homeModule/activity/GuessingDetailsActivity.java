package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.GuessingDetailsContact;
import com.yc.redevenlopes.homeModule.present.GuessingDetailsPresenter;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;

/**
 * 竞猜详情
 */
public class GuessingDetailsActivity extends BaseActivity<GuessingDetailsPresenter> implements GuessingDetailsContact.View {

    @BindView(R.id.tv_contents)
    TextView tvContents;
    @BindView(R.id.line1)
    LinearLayout line1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_guessing_details;
    }

    @Override
    public void initEventAndData() {
        setTitle("竞猜规则");
        String contents = getIntent().getStringExtra("contents");
        if (!TextUtils.isEmpty(contents)) {
            RichText.fromHtml(contents).into(tvContents);
        }else {
            tvContents.setVisibility(View.GONE);
            line1.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void guessingDetailsJump(Context context, String contents) {
        Intent intent = new Intent(context, GuessingDetailsActivity.class);
        intent.putExtra("contents", contents);
        context.startActivity(intent);
    }
}