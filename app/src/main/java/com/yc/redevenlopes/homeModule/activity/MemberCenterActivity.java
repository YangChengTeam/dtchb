package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.MemberCenterContact;
import com.yc.redevenlopes.homeModule.present.MemberCenterPresenter;
/**
 * 会员中心
 */
public class MemberCenterActivity extends BaseActivity<MemberCenterPresenter> implements MemberCenterContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_center);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_member_center;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
          getActivityComponent().inject(this);
    }
    public static void memberCenterJump(Context context){
        Intent intent=new Intent(context,MemberCenterActivity.class);
        context.startActivity(intent);
    }
}