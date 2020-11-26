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

    public Info1Bean info1Bean;//5
    public HomeAllBeans homeAllBeans; //3
    public Info0Bean info0Bean;//1
    public HomeRedMessage homeRedMessage;//2

    public Info1Bean getInfo1Bean() {
        return info1Bean;
    }

    public void setInfo1Bean(Info1Bean info1Bean) {
        this.info1Bean = info1Bean;
    }

    public HomeAllBeans getHomeAllBeans() {
        return homeAllBeans;
    }

    public void setHomeAllBeans(HomeAllBeans homeAllBeans) {
        this.homeAllBeans = homeAllBeans;
    }

    public Info0Bean getInfo0Bean() {
        return info0Bean;
    }

    public void setInfo0Bean(Info0Bean info0Bean) {
        this.info0Bean = info0Bean;
    }

    public HomeRedMessage getHomeRedMessage() {
        return homeRedMessage;
    }

    public void setHomeRedMessage(HomeRedMessage homeRedMessage) {
        this.homeRedMessage = homeRedMessage;
    }
}
