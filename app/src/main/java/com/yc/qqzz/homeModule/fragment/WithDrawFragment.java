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
import com.yc.qqzz.homeModule.contact.WithDrawFgContract;
import com.yc.qqzz.homeModule.present.WithDrawFgPresenter;

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
        taskFragment = new TaskFragment();
        currfragment = taskFragment;
        supportFragmentManager = getChildFragmentManager();
        supportFragmentManager.beginTransaction().add(R.id.fl_contains, taskFragment).commit();
    }

    @OnClick({R.id.tv_task, R.id.tv_withDraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
        }
    }

    private void setTab(int position) {
      if (position==0){
           view1.setVisibility(View.VISIBLE);
           view1.setBackgroundResource(R.drawable.line_bg_white4);
           view2.setVisibility(View.INVISIBLE);
           tvTask.setBackgroundResource(0);
           tvWithDraw.setBackgroundResource(R.drawable.line_bg_yellow5);
      }else {
          tvWithDraw.setBackgroundResource(0);
          tvTask.setBackgroundResource(R.drawable.line_bg_yellow5);
          view2.setBackgroundResource(R.drawable.line_bg_white4);
          view1.setVisibility(View.INVISIBLE);
          view2.setVisibility(View.VISIBLE);
      }

    }
}