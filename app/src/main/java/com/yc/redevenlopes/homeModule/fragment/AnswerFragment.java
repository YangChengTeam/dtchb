package com.yc.redevenlopes.homeModule.fragment;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.yc.redevenlopes.R;
import com.yc.redevenlopes.base.BaseLazyFragment;
import com.yc.redevenlopes.homeModule.activity.AnswerDetailsActivity;
import com.yc.redevenlopes.homeModule.adapter.AnswerFgAdapter;
import com.yc.redevenlopes.homeModule.adapter.FrequencyFgAdapter;
import com.yc.redevenlopes.homeModule.contact.AnswerFgContact;
import com.yc.redevenlopes.homeModule.module.bean.AnswerQuestionListBeans;
import com.yc.redevenlopes.homeModule.present.AnswerFgPresenter;
import com.yc.redevenlopes.homeModule.widget.CirCountDownView;
import com.yc.redevenlopes.utils.VUiKit;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AnswerFragment extends BaseLazyFragment<AnswerFgPresenter> implements AnswerFgContact.View {

    @BindView(R.id.circountdownView)
    CirCountDownView circountdownView;
    @BindView(R.id.tv_queryTitle)
    TextView tvQueryTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private AnswerDetailsActivity activity;
    private int position;
    private int correctAns = 0;
    private AnswerQuestionListBeans answerQuestionListBeans;
    private AnswerFgAdapter  answerFgAdapter;
    private String answerType;//1 没有回答  2 回答正确  3 回答错误

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.answer_fragment, container, false);
    }

    public static AnswerFragment newInstance(int position) {
        AnswerFragment answerFragment = new AnswerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        answerFragment.setArguments(bundle);
        return answerFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = (View) super.onCreateView(inflater, container, savedInstanceState);
        return (View) rootView;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected void initLazyData() {
        position = getArguments().getInt("position");
        activity = (AnswerDetailsActivity) getActivity();
        answerQuestionListBeans = activity.data.get(position);
        setViews();
        if (circountdownView!=null){
            initCir();
        }
    }

    public void initCir(){
        circountdownView.setAddCountDownListener(new CirCountDownView.OnCountDownFinishListener() {
            @Override
            public void countDownFinished() {
                List<AnswerQuestionListBeans.OptionsBean> data = answerFgAdapter.getData();
                data.get(correctAns).setStatus(1);
                answerFgAdapter.notifyItemChanged(correctAns);
                answerType="3";
                setsFg(answerType);
            }
        });
    }

    private void setViews() {
        List<AnswerQuestionListBeans.OptionsBean> options = answerQuestionListBeans.getOptions();
        if (options != null) {
            for (int i = 0; i < options.size(); i++) {
                if (options.get(i).getKey().equals(answerQuestionListBeans.getAnswer())){
                    correctAns=i;
                }
                options.get(i).setStatus(0);
            }
        }
        tvQueryTitle.setText(answerQuestionListBeans.getTitle());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        answerFgAdapter=new AnswerFgAdapter(options);
        recyclerView.setAdapter(answerFgAdapter);
        answerFgAdapter.setDuration(700);
        answerFgAdapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view, "alpha", 0, 1f),
                        ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0)
                };
            }
        });

        answerFgAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<AnswerQuestionListBeans.OptionsBean> lists = answerFgAdapter.getData();
                if (correctAns==position){
                    lists.get(position).setStatus(1);
                    answerFgAdapter.notifyItemChanged(position);
                    setStopVa();
                    answerType="2";
                    setsFg(answerType);
                }else {
                    lists.get(correctAns).setStatus(1);
                    lists.get(position).setStatus(2);
                    answerFgAdapter.notifyItemChanged(correctAns);
                    answerFgAdapter.notifyItemChanged(position);
                    setStopVa();
                    answerType="3";
                    setsFg(answerType);
                }
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setsFg(String answerType) {
        VUiKit.postDelayed(1500, () -> {
            activity.setStepType(position ,answerType);
        });
    }

    public void setStartVa() {
        VUiKit.postDelayed(900, () -> {
            if (circountdownView != null) {
                circountdownView.startCountDown();
                if (recyclerView!=null){
//                    Animation animation= AnimationUtils.loadAnimation(getActivity(), R.anim.recyclerview_item);
//                    //得到一个LayoutAnimationController对象；
//                    LayoutAnimationController lac=new LayoutAnimationController(animation);
//                    //设置控件显示的顺序；
//                    lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
//                    //设置控件显示间隔时间；
//                    lac.setDelay(0.2f);
//                    //为ListView设置LayoutAnimationController属性；
//                    recyclerView.setLayoutAnimation(lac);
                }
            }
        });
    }

    public boolean getisPaused() {
       return circountdownView.getisPaused();
    }

    public void setStopVa() {
        if (circountdownView != null) {
            circountdownView.stopCount();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (circountdownView != null) {
            circountdownView.stopCount();
            circountdownView=null;
        }
    }
}