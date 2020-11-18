package com.yc.redevenlopes.homeModule.activity;


import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.AnswerContact;
import com.yc.redevenlopes.homeModule.present.AnswerPresenter;

import butterknife.BindView;

/**
 * 答题任务
 */
public class AnswerActivity extends BaseActivity<AnswerPresenter> implements AnswerContact.View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_answer;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }
}