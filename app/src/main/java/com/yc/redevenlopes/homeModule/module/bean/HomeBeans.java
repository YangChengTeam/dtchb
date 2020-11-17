package com.yc.redevenlopes.homeModule.module.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class HomeBeans implements MultiItemEntity {
    private int itemViewType;
    public void setItemType(int itemViewType) {
        this.itemViewType = itemViewType;
    }

    @Override
    public int getItemType() {
        return itemViewType;
    }
}
