package com.yc.qqzz.homeModule.fragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyHelperCallback extends ItemTouchHelper.Callback  {

    private int dragFlags;
    private int swipeFlags;
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
//        //判断 recyclerView的布局管理器数据
//        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {//设置能拖拽的方向
//            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN ;
//            swipeFlags =  ItemTouchHelper.DOWN; ;
//        }
//        return makeMovementFlags(dragFlags, swipeFlags);
        return 0;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        Log.d("ccc", "--------onMove: ");
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        Log.d("ccc", "--------isItemViewSwipeEnabled: ");
        return false;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        Log.d("ccc", "--------isLongPressDragEnabled: ");
        return false;
    }




}
