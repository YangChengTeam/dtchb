package com.yc.redevenlopes.homeModule.activity;



import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.GrabRedEvenlopesContact;
import com.yc.redevenlopes.homeModule.present.GrabRedEvenlopesPresenter;

public class GrabRedEvenlopesActivity extends BaseActivity<GrabRedEvenlopesPresenter> implements GrabRedEvenlopesContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_grab_red_evenlopes;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
           getActivityComponent().inject(this);
    }
}