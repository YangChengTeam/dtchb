package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.RedDialog;
import com.yc.redevenlopes.homeModule.adapter.AnswserAdapter;
import com.yc.redevenlopes.homeModule.contact.AnswerContact;
import com.yc.redevenlopes.homeModule.module.bean.AnswerBeans;
import com.yc.redevenlopes.homeModule.present.AnswerPresenter;
import com.yc.redevenlopes.homeModule.widget.ScrollWithRecyclerView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

/**
 * 答题任务
 */
public class AnswerActivity extends BaseActivity<AnswerPresenter> implements AnswerContact.View {
    @BindView(R.id.recyclerView)
    ScrollWithRecyclerView recyclerView;
    private AnswserAdapter answserAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_answer;
    }

    @Override
    public void initEventAndData() {
        setRightTitle("答题任务");
        initRecyclerView();
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public static void answerJump(Context context) {
        Intent intent = new Intent(context, AnswerActivity.class);
        context.startActivity(intent);
    }

    private void initRecyclerView() {
        List<AnswerBeans> lists=new ArrayList();
        for (int i = 0; i < 10; i++) {
            AnswerBeans answerBeans=new AnswerBeans();
            lists.add(answerBeans);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        answserAdapter = new AnswserAdapter(lists);
        recyclerView.setAdapter(answserAdapter);
        answserAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showRedDialog();
            }
        });
    }
    public void showRedDialog() {
        RedDialog redDialog = new RedDialog(this);
        View builder = redDialog.builder(R.layout.red_dialog_item);
        ImageView iv_close=builder.findViewById(R.id.iv_close);
        ImageView iv_open=builder.findViewById(R.id.iv_open);
        TextView tv_ans=builder.findViewById(R.id.tv_ans);
        tv_ans.setVisibility(View.VISIBLE);
        iv_open.setImageDrawable(getResources().getDrawable(R.drawable.red_ans));
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redDialog.setDismiss();
            }
        });
        iv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerDetailsActivity.AnswerDetailsJump(AnswerActivity.this);

            }
        });
        redDialog.setShow();
    }

}