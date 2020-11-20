package com.yc.redevenlopes.homeModule.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseActivity;
import com.yc.redevenlopes.dialog.GuessDialog;
import com.yc.redevenlopes.homeModule.adapter.CommonPagerAdapter;
import com.yc.redevenlopes.homeModule.contact.AnswerDetailsContact;
import com.yc.redevenlopes.homeModule.fragment.AnswerFragment;
import com.yc.redevenlopes.homeModule.present.AnswerDetailsPresenter;
import com.yc.redevenlopes.homeModule.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 答题详情
 */
public class AnswerDetailsActivity extends BaseActivity<AnswerDetailsPresenter> implements AnswerDetailsContact.View {

    @BindView(R.id.line_start)
    LinearLayout lineStart;
    @BindView(R.id.line_ans)
    LinearLayout lineAns;
    @BindView(R.id.rela_ansFinshResurrection)
    RelativeLayout relaAnsFinshResurrection;
    @BindView(R.id.rela_ansFinshBack)
    RelativeLayout relaAnsFinshBack;
    @BindView(R.id.tv_startNums)
    TextView tvStartNums;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewpager;
    @BindView(R.id.view_query1)
    View viewQuery1;
    @BindView(R.id.view_query2)
    View viewQuery2;
    @BindView(R.id.view_query3)
    View viewQuery3;
    @BindView(R.id.view_query4)
    View viewQuery4;
    @BindView(R.id.view_query5)
    View viewQuery5;
    private int type;//1 开始 2 答题  2答题结束复活 4答题结束返回
    private int stepType;//
    private List<Fragment> listData;
    private List<String> list_title;
    private CommonPagerAdapter pagerAdapter;
    private CountDownTimer downTimer = new CountDownTimer(5 * 1000, 1000) {
        @Override
        public void onTick(long time) {
            tvStartNums.setText((time / 1000) + "");
        }

        @Override
        public void onFinish() {
            type = 2;
            setViews();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isNeedNewTitle(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_answer_details;
    }

    @Override
    public void initEventAndData() {
        type = 1;
        setViews();
        initViewpager();
    }

    private void initViewpager() {
        list_title = new ArrayList<>();
        listData = new ArrayList<>();
        list_title.add("问题1");
        list_title.add("问题2");
        list_title.add("问题3");
        list_title.add("问题4");
        list_title.add("问题5");
        for (int i = 0; i < list_title.size(); i++) {
            AnswerFragment answerFragment = AnswerFragment.newInstance(i);
            listData.add(answerFragment);
        }
        pagerAdapter = new CommonPagerAdapter(getSupportFragmentManager(), listData, list_title);
        viewpager.setAdapter(pagerAdapter);
        viewpager.setOffscreenPageLimit(1);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    public void setStepType(int index) {
        if (index < 5) {
            stepType = index;
            viewpager.setCurrentItem(stepType);
        } else {
            type = 3;
            setViews();
        }
    }

    private void setViews() {
        if (type == 1) {
            lineStart.setVisibility(View.VISIBLE);
            lineAns.setVisibility(View.GONE);
            relaAnsFinshBack.setVisibility(View.GONE);
            relaAnsFinshResurrection.setVisibility(View.GONE);
            downTimer.start();
        } else if (type == 2) {
            lineStart.setVisibility(View.GONE);
            lineAns.setVisibility(View.VISIBLE);
            relaAnsFinshBack.setVisibility(View.GONE);
            relaAnsFinshResurrection.setVisibility(View.GONE);
            setStepType(0);
        } else if (type == 3) {
            lineStart.setVisibility(View.GONE);
            lineAns.setVisibility(View.GONE);
            relaAnsFinshBack.setVisibility(View.VISIBLE);
            relaAnsFinshResurrection.setVisibility(View.GONE);
        } else if (type == 4) {
            lineStart.setVisibility(View.GONE);
            lineAns.setVisibility(View.GONE);
            relaAnsFinshBack.setVisibility(View.GONE);
            relaAnsFinshResurrection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (downTimer != null) {
            downTimer.cancel();
            downTimer = null;
        }
    }

    public static void AnswerDetailsJump(Context context) {
        Intent intent = new Intent(context, AnswerDetailsActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.line_back, R.id.tv_ansfinshresu_back, R.id.line_ansfinshResurrec, R.id.tv_ansfinshBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_back:
                if (type==1||type==2){
                    showGuessDialog();
                }else {
                    finish();
                }
                break;
            case R.id.tv_ansfinshresu_back://放弃

                break;
            case R.id.line_ansfinshResurrec://复活

                break;
            case R.id.tv_ansfinshBack:
                finish();
                break;
        }
    }

    private void showGuessDialog() {
        GuessDialog guessDialog = new GuessDialog(this);
        View builder = guessDialog.builder(R.layout.guess_item);
        TextView tv_title = builder.findViewById(R.id.tv_title);
        TextView tv_des = builder.findViewById(R.id.tv_des);
        TextView tv_sure = builder.findViewById(R.id.tv_sure);
        TextView tv_cancle = builder.findViewById(R.id.tv_cancle);
        ImageView iv_close = builder.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessDialog.setDismiss();
            }
        });
        guessDialog.setShow();
    }
}