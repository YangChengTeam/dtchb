package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.SnatchDialog;
import com.yc.redevenlopes.homeModule.contact.SnatchTreasureContact;
import com.yc.redevenlopes.homeModule.present.SnatchTreasurePresenter;
/**
 *夺宝
 */
public class SnatchTreasureActivity extends BaseActivity<SnatchTreasurePresenter> implements SnatchTreasureContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_snatch_treasure;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
         getActivityComponent().inject(this);
    }


    public static void snatchTreasureJump(Context context){
        Intent intent=new Intent(context,SnatchTreasureActivity.class);
        context.startActivity(intent);
    }

    private void showDialogs(){
        SnatchDialog snatchDialog=new SnatchDialog(this);
        View builder = snatchDialog.builder(R.layout.snatch_item);
        snatchDialog.setShow();
    }
}