package com.yc.wxchb.beans.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yc.wxchb.R;
import com.yc.wxchb.base.BaseLazyFragment;
import com.yc.wxchb.beans.adapter.TaskAdapter;
import com.yc.wxchb.beans.contact.TaskContract;
import com.yc.wxchb.beans.present.TaskPresenter;

import butterknife.BindView;

public class TaskFragment extends BaseLazyFragment<TaskPresenter> implements TaskContract {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private TaskAdapter taskAdapter;

    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    protected android.view.View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public android.view.View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        android.view.View rootView = (android.view.View) super.onCreateView(inflater, container, savedInstanceState);
        return (android.view.View) rootView;
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
          initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        taskAdapter=new TaskAdapter(null);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskAdapter);
    }


}