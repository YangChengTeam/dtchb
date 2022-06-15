package com.yc.jsdps.beans.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.jsdps.R;
import com.yc.jsdps.base.BaseActivity;
import com.yc.jsdps.beans.adapter.WithDrawRecodeAdapter;
import com.yc.jsdps.beans.contact.WithDrawRecodeContract;
import com.yc.jsdps.beans.module.beans.CashRecordBeans;
import com.yc.jsdps.beans.module.beans.UserInfo;
import com.yc.jsdps.beans.present.WithDrawRecodePresenter;
import com.yc.jsdps.utils.CacheDataUtils;


import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawRecodeActivity extends BaseActivity<WithDrawRecodePresenter> implements WithDrawRecodeContract.View {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private WithDrawRecodeAdapter withDrawRecodeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_with_draw_recode;
    }

    public static void withDrawRecodeJump(Context context){
        Intent intent=new Intent(context,WithDrawRecodeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initEventAndData() {
        toolbarTitle.setText("提现记录");
        initRecyclerView();
        UserInfo userInfo = CacheDataUtils.getInstance().getUserInfo();
        mPresenter.getCashrecord(userInfo.getId(),"1","30");
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

   private void initRecyclerView(){
       LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
       withDrawRecodeAdapter=new WithDrawRecodeAdapter(null);
       recyclerView.setAdapter(withDrawRecodeAdapter);
       withDrawRecodeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
           @Override
           public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

           }
       });
       recyclerView.setLayoutManager(linearLayoutManager);
   }


    @OnClick({R.id.toolbar_back})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }

    @Override
    public void getCashrecordSuccess(List<CashRecordBeans> data) {
           withDrawRecodeAdapter.setNewData(data);
           withDrawRecodeAdapter.notifyDataSetChanged();
           if (data == null || data.size() == 0) {
               View empty = LayoutInflater.from(this).inflate(R.layout.empty_viewfour, null, false);
               withDrawRecodeAdapter.setEmptyView(empty);
           }
    }
}