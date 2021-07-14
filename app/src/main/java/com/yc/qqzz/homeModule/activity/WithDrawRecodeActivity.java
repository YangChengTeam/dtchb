package com.yc.qqzz.homeModule.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseActivity;
import com.yc.qqzz.homeModule.adapter.WithDrawRecodeAdapter;
import com.yc.qqzz.homeModule.contact.WithDrawRecodeContract;
import com.yc.qqzz.homeModule.present.WithDrawRecodePresenter;

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

    @Override
    public void initEventAndData() {
        toolbarTitle.setText("提现记录");
        initRecyclerView();
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
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
        }
    }
}