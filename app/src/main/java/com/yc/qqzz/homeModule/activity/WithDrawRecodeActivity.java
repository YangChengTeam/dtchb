package com.yc.qqzz.homeModule.activity;

import android.content.Context;
import android.content.Intent;
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
import com.yc.qqzz.homeModule.module.bean.CashRecordBeans;
import com.yc.qqzz.homeModule.module.bean.UserInfozq;
import com.yc.qqzz.homeModule.present.WithDrawRecodePresenter;
import com.yc.qqzz.utils.CacheDataUtils;

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
        UserInfozq userInfo = CacheDataUtils.getInstance().getUserInfo();
        mPresenter.getCashrecord(userInfo.getImei(),userInfo.getGroup_id(),"1","30");
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


    @OnClick({R.id.toolbar_back,R.id.line_answer,R.id.line_withdraw,R.id.line_mine,R.id.line_memberss})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.line_answer:
                intent=new Intent(WithDrawRecodeActivity.this,MainActivity.class);
                intent.putExtra("position","1");
                startActivity(intent);
                break;
            case R.id.line_withdraw:
                intent=new Intent(WithDrawRecodeActivity.this,MainActivity.class);
                intent.putExtra("position","2");
                startActivity(intent);
                break;
            case R.id.line_mine:
                intent=new Intent(WithDrawRecodeActivity.this,MainActivity.class);
                intent.putExtra("position","3");
                startActivity(intent);
                break;
            case R.id.line_memberss:
                intent=new Intent(WithDrawRecodeActivity.this,MainActivity.class);
                intent.putExtra("position","0");
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getCashrecordSuccess(List<CashRecordBeans> data) {
       if (data!=null){
           withDrawRecodeAdapter.setNewData(data);
           withDrawRecodeAdapter.notifyDataSetChanged();
       }
    }
}