package com.yc.redevenlopes.homeModule.module.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class HomeBeans implements MultiItemEntity {
    private int itemViewType; // 1    2红包
    public void setItemType(int itemViewType) {
        this.itemViewType = itemViewType;
    }
    @Override
    public int getItemType() {
        return itemViewType;
    }

    public HomeRedMessage homeRedMessage;

    public HomeRedMessage getHomeRedMessage() {
        return homeRedMessage;
    }

    public void setHomeRedMessage(HomeRedMessage homeRedMessage) {
        this.homeRedMessage = homeRedMessage;
    }

    public HomeAllBeans homeAllBeans;

    public HomeAllBeans getHomeAllBeans() {
        return homeAllBeans;
    }

    public void setHomeAllBeans(HomeAllBeans homeAllBeans) {
        this.homeAllBeans = homeAllBeans;
    }
}
