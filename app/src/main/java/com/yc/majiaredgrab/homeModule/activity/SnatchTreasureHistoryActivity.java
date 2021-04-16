package com.yc.majiaredgrab.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.yc.majiaredgrab.R;
import com.yc.majiaredgrab.base.BaseActivity;
import com.yc.majiaredgrab.homeModule.adapter.CommonPagerAdapter;
import com.yc.majiaredgrab.homeModule.contact.SnatchTreasureHistoryContact;
import com.yc.majiaredgrab.homeModule.fragment.FrequencyFragment;
import com.yc.majiaredgrab.homeModule.present.SnatchTreasureHistoryPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SnatchTreasureHistoryActivity extends BaseActivity<SnatchTreasureHistoryPresenter> implements SnatchTreasureHistoryContact.View {
    @BindView(R.id.myFrequency)
    TextView myFrequency;
    @BindView(R.id.nearFrequency)
    TextView nearFrequency;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<String> list_title; //tab名称列表
    private List<Fragment> listData;
    private CommonPagerAdapter mAdapter_attractions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_snatch_treasure_history;
    }

    @Override
    public void initEventAndData() {
        setTitle("开奖历史");
        initViewPager();
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    private void initViewPager() {
        list_title = new ArrayList<>();
        listData = new ArrayList<>();
        list_title.add("我的场次");
        list_title.add("最近场次");
        for (int i = 0; i < list_title.size(); i++) {
            FrequencyFragment frequencyFragment=FrequencyFragment.newInstance(String.valueOf(i));
            listData.add(frequencyFragment);
        }
        mAdapter_attractions = new CommonPagerAdapter(getSupportFragmentManager(), listData, list_title);
        viewPager.setAdapter(mAdapter_attractions);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.myFrequency, R.id.nearFrequency})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myFrequency:
                myFrequency.setTextColor(getResources().getColor(R.color.A1_333333));
                nearFrequency.setTextColor(getResources().getColor(R.color.A1_999999));
                myFrequency.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                nearFrequency.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                viewPager.setCurrentItem(0);
                break;
            case R.id.nearFrequency:
                myFrequency.setTextColor(getResources().getColor(R.color.A1_999999));
                nearFrequency.setTextColor(getResources().getColor(R.color.A1_333333));
                myFrequency.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                nearFrequency.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                viewPager.setCurrentItem(1);
                break;
        }
    }

    public static void snatchtreasurehistoryJump(Context context){
        Intent intent=new Intent(context,SnatchTreasureHistoryActivity.class);
        context.startActivity(intent);
    }
}