package com.yc.jsdsp.beans.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.jsdsp.R;
import com.yc.jsdsp.base.BaseActivity;
import com.yc.jsdsp.beans.adapter.ComplainAdapter;
import com.yc.jsdsp.beans.contact.ComplaintContract;
import com.yc.jsdsp.beans.module.beans.ComplainBeans;
import com.yc.jsdsp.beans.present.ComplaintPresenter;
import com.yc.jsdsp.utils.CacheDataUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ComplaintActivity extends BaseActivity<ComplaintPresenter> implements ComplaintContract.View {

    public static WeakReference<ComplaintActivity> instance;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ComplainAdapter complainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_complaint;
    }

    @Override
    public void initEventAndData() {
        instance = new WeakReference<>(this);
        mPresenter.getComplainList(CacheDataUtils.getInstance().getUserInfo().getId()+"");
        initRecyclerViews();
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.iv_close1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close1:
                finish();
                break;
        }
    }

    public void initRecyclerViews(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        complainAdapter=new ComplainAdapter(null);
        recyclerView.setAdapter(complainAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        complainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<ComplainBeans> data = adapter.getData();
                ComplainBeans complainBeans = data.get(position);
                ComplaintEdActivity.comPlainEdJump(ComplaintActivity.this,complainBeans.getId()+"",complainBeans.getTitle());
            }
        });
    }

    @Override
    public void getComplainListSuccess(List<ComplainBeans> data) {
        if (data!=null){
            complainAdapter.setNewData(data);
            complainAdapter.notifyDataSetChanged();
        }

    }
}