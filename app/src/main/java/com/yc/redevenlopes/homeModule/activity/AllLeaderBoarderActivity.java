package com.yc.redevenlopes.homeModule.activity;



import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.AllLeaderBoarderContact;
import com.yc.redevenlopes.homeModule.present.AllLeaderBoarderPresenter;
/**
 *全服排行榜
 */
public class AllLeaderBoarderActivity extends BaseActivity<AllLeaderBoarderPresenter> implements AllLeaderBoarderContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_all_leader_boarder;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }
}