package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.TurnTableContact;
import com.yc.redevenlopes.homeModule.present.TurnTablePresenter;
import com.yc.redevenlopes.homeModule.widget.LuckPanLayout;
import com.yc.redevenlopes.homeModule.widget.RotatePan;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 转盘
 */
public class TurnTableActivity extends BaseActivity<TurnTablePresenter> implements TurnTableContact.View ,LuckPanLayout.AnimationEndListener{

    @BindView(R.id.rotatePan)
    RotatePan rotatePan;
    @BindView(R.id.go)
    ImageView go;
    @BindView(R.id.luckpan_layout)
    LuckPanLayout luckpanLayout;
    private String[] strs = {"0.01元","0.02元","0.05元","3元","50元","mate40元"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_turn_table;
    }

    @Override
    public void initEventAndData() {
        luckpanLayout.setAnimationEndListener(this);
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @OnClick({ R.id.go})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go:
                luckpanLayout.rotate(-1,100);
                break;
        }
    }

    @Override
    public void endAnimation(int position) {
        Toast.makeText(this,"Position = "+position+","+strs[position],Toast.LENGTH_SHORT).show();
    }

    public static void TurnTableJump(Context context){
        Intent intent=new Intent(context,TurnTableActivity.class);
        context.startActivity(intent);
    }
}