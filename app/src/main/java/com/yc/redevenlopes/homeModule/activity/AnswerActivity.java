package com.yc.redevenlopes.homeModule.activity;



import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.AnswerContact;
import com.yc.redevenlopes.homeModule.present.AnswerPresenter;
/**
 *答题任务
 */
public class AnswerActivity extends BaseActivity<AnswerPresenter> implements AnswerContact.View {

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