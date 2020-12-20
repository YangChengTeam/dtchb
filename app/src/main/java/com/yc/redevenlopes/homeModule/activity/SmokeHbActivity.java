package com.yc.redevenlopes.homeModule.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.adapter.SmokeAdapter;
import com.yc.redevenlopes.homeModule.contact.SmokeHbContact;
import com.yc.redevenlopes.homeModule.module.bean.SmokeBeans;
import com.yc.redevenlopes.homeModule.present.SmokeHbPresenter;
import com.yc.redevenlopes.homeModule.widget.ScrollWithRecyclerView;
import com.yc.redevenlopes.homeModule.widget.SpaceItemDecoration;
import com.yc.redevenlopes.utils.CommonUtils;
import com.yc.redevenlopes.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SmokeHbActivity extends BaseActivity<SmokeHbPresenter> implements SmokeHbContact.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_redNums)
    TextView tvRedNums;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    private SmokeAdapter smokeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_smoke_hb;
    }

    @Override
    public void initEventAndData() {
        setFullScreen();
        init();

    }

    private void init() {
        List<SmokeBeans> lists=new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            SmokeBeans smokeBeans=new SmokeBeans();
            lists.add(smokeBeans);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SmokeHbActivity.this, 3, GridLayoutManager.VERTICAL, false);
        smokeAdapter=new SmokeAdapter(lists);
        recyclerView.setLayoutManager(gridLayoutManager);
        int screenWidth = CommonUtils.getScreenWidth(this); //屏幕宽度
        int itemWidth = DisplayUtil.dip2px(this, 80); //每个item的宽度
        int pa = DisplayUtil.dip2px(this, 64); //每个item的宽度
        recyclerView.addItemDecoration(new SpaceItemDecoration((screenWidth -pa- itemWidth* 3)/6));
        recyclerView.setAdapter(smokeAdapter);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_select:

                break;
        }
    }
    public static void smokehbJump(Context context){
        Intent intent=new Intent(context,SmokeHbActivity.class);
        context.startActivity(intent);
    }
}