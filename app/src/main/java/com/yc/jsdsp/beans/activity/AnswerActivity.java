package com.yc.jsdsp.beans.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yc.jsdsp.R;
import com.yc.jsdsp.base.BaseActivity;
import com.yc.jsdsp.beans.contact.AnswerContact;
import com.yc.jsdsp.beans.fragment.AnswerFragment;
import com.yc.jsdsp.beans.present.AnswerPresenter;

import butterknife.BindView;


public class AnswerActivity extends BaseActivity<AnswerPresenter> implements AnswerContact.View {
    @BindView(R.id.fl_containss)
    FrameLayout flContains;
    public FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private AnswerFragment answerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_answer;
    }

    @Override
    public void initEventAndData() {
        setFullScreen();
        answerFragment=new AnswerFragment();
        supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.fl_containss, answerFragment).commit();
    }

    @Override
    public void initInject() {
          getActivityComponent().inject(this);
    }
    public static void answerJump(Context context){
        Intent intent=new Intent(context,AnswerActivity.class);
        context.startActivity(intent);
    }
}