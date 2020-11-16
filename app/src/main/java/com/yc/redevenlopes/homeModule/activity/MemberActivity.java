package com.yc.redevenlopes.homeModule.activity;

import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.MemberConstact;
import com.yc.redevenlopes.homeModule.present.MemberPresenter;
/**
 * 会员
 */
public class MemberActivity extends BaseActivity<MemberPresenter> implements MemberConstact.View {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_member;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
            getActivityComponent().inject(this);
    }
}