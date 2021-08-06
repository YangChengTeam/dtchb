package com.yc.qqzz.homeModule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yc.qqzz.R;
import com.yc.qqzz.base.BaseLazyFragment;
import com.yc.qqzz.homeModule.activity.HelpQuestionActivity;
import com.yc.qqzz.homeModule.activity.InvationfriendActivity;
import com.yc.qqzz.homeModule.contact.WithDrawFgContract;
import com.yc.qqzz.homeModule.present.WithDrawFgPresenter;
import com.yc.qqzz.service.event.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class WithDrawFragment extends BaseLazyFragment<WithDrawFgPresenter> implements WithDrawFgContract.View {

    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.tv_task)
    TextView tvTask;
    @BindView(R.id.tv_withDraw)
    TextView tvWithDraw;
    @BindView(R.id.fl_contains)
    FrameLayout flContains;
    @BindView(R.id.tv_allMoneys)
    TextView tvAllMoneys;
    @BindView(R.id.tv_memberMoneys)
    TextView tvMemberMoneys;
    private Fragment currfragment;
    public FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private TaskFragment taskFragment;
    private WithDrawitemFragment withDrawitemFragment;

    public WithDrawFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_with_draw, container, false);
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
        EventBus.getDefault().register(this);
        taskFragment = new TaskFragment();
        currfragment = taskFragment;
        supportFragmentManager = getChildFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.fl_contains, taskFragment).commit();
    }

    @OnClick({R.id.tv_task, R.id.tv_withDraw, R.id.iv_gotoInvatation,R.id.line_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_gotoInvatation:
                InvationfriendActivity.invationfriendJump(getActivity());
                break;
            case R.id.tv_task:
                fragmentTransaction = supportFragmentManager.beginTransaction();
                fragmentTransaction.hide(currfragment).show(taskFragment).commit();
                currfragment = taskFragment;
                setTab(0);
                break;
            case R.id.tv_withDraw:
                fragmentTransaction = supportFragmentManager.beginTransaction();
                if (withDrawitemFragment == null) {
                    withDrawitemFragment = new WithDrawitemFragment();
                    fragmentTransaction.hide(currfragment).add(R.id.fl_contains, withDrawitemFragment).commit();
                } else {
                    fragmentTransaction.hide(currfragment).show(withDrawitemFragment).commit();
                }
                currfragment = withDrawitemFragment;
                setTab(1);
                break;
            case R.id.line_help:
                HelpQuestionActivity.helpJump(getActivity());
                break;
        }
    }

    private void setTab(int position) {
        if (position == 0) {
            view1.setVisibility(View.VISIBLE);
            view1.setBackgroundResource(R.drawable.line_bg_white4);
            view2.setVisibility(View.INVISIBLE);
            tvTask.setBackgroundResource(0);
            tvWithDraw.setBackgroundResource(R.drawable.line_bg_yellow5);
        } else {
            tvWithDraw.setBackgroundResource(0);
            tvTask.setBackgroundResource(R.drawable.line_bg_yellow5);
            view2.setBackgroundResource(R.drawable.line_bg_white4);
            view1.setVisibility(View.INVISIBLE);
            view2.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onHomePage(Event event) {
        if (event instanceof Event.CashEvent) {
            Event.CashEvent cashEvent = (Event.CashEvent) event;
            String allMoneys = cashEvent.getAllMoneys();
            String moneys = cashEvent.getMoneys();
            tvAllMoneys.setText(allMoneys);
            tvMemberMoneys.setText(moneys);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void setVideoCallBack(boolean isVideoClick, int typePosition) {
           if (typePosition==2){
               if (taskFragment!=null){
                   taskFragment.setVideoCallBacks(isVideoClick);
               }
           }else {
               if (taskFragment!=null){
                   withDrawitemFragment.setVideoCallBacks(isVideoClick);
               }
           }
    }

    public void setPosition(int i) {
//        fragmentTransaction = supportFragmentManager.beginTransaction();
//        if (withDrawitemFragment == null) {
//            withDrawitemFragment = new WithDrawitemFragment();
//            fragmentTransaction.hide(currfragment).add(R.id.fl_contains, withDrawitemFragment).commit();
//        } else {
//            fragmentTransaction.hide(currfragment).show(withDrawitemFragment).commit();
//        }
//        currfragment = withDrawitemFragment;
//        setTab(1);
    }
}