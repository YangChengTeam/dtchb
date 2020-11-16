package com.yc.redevenlopes.homeModule.activity;



import android.os.Bundle;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.homeModule.contact.AnswerDetailsContact;
import com.yc.redevenlopes.homeModule.present.AnswerDetailsPresenter;
/**
 *答题详情
 */
public class AnswerDetailsActivity extends BaseActivity<AnswerDetailsPresenter> implements AnswerDetailsContact.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_answer_details;
    }

    @Override
    public void initEventAndData() {

    }

    @Override
    public void initInject() {
       getActivityComponent().inject(this);
    }
}