package com.yc.wxchb.beans.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.wxchb.R;
import com.yc.wxchb.base.BaseActivity;
import com.yc.wxchb.beans.adapter.RedWallDrawAdapter;
import com.yc.wxchb.beans.contact.RedWallContract;
import com.yc.wxchb.beans.module.beans.HotWithDrawBeans;
import com.yc.wxchb.beans.present.RedWallPresenter;
import com.yc.wxchb.utils.SoundPoolUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RedWallActivity extends BaseActivity<RedWallPresenter> implements RedWallContract.View {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_getRed)
    TextView tvGetRed;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv_gold)
    TextView tvGold;
    @BindView(R.id.tv_moneys)
    TextView tvMoneys;
    @BindView(R.id.tv_exchangeTips)
    TextView tvExchangeTips;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    private RedWallDrawAdapter redWallDrawAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_red_wall;
    }

    @Override
    public void initEventAndData() {
        init();
    }

    private void init() {
        GridLayoutManager hotgridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(hotgridLayoutManager);
        redWallDrawAdapter = new RedWallDrawAdapter(null);
        recyclerView.setAdapter(redWallDrawAdapter);
        redWallDrawAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                redWallDrawAdapter.notifyDataSetChanged();
                SoundPoolUtils instance = SoundPoolUtils.getInstance();
                instance.initSound();
                List<HotWithDrawBeans.HuoliBean.ConfigJsonBean> lists = redWallDrawAdapter.getData();
                for (int i = 0; i < lists.size(); i++) {
                    if (position == i) {
                        lists.get(i).setSelect(true);
                    } else {
                        lists.get(i).setSelect(false);
                    }
                }
                redWallDrawAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({R.id.iv_close, R.id.tv_getRed, R.id.tv_exchangeTips, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_getRed:
                break;
            case R.id.tv_exchangeTips:
                break;
            case R.id.tv_sure:
                break;
        }
    }
}