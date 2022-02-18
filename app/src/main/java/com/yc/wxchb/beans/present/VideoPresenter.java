package com.yc.wxchb.beans.present;


import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.wxchb.beans.contact.VideoContract;
import com.yc.wxchb.beans.module.HomeApiModule;
import javax.inject.Inject;

/**
 * Created by suns  on 2020/11/19 16:21.
 */
public class VideoPresenter extends RxPresenter<VideoContract.View> implements VideoContract.Presenter {

    private HomeApiModule apiModule;

    @Inject
    public VideoPresenter(HomeApiModule apiModule) {
        this.apiModule = apiModule;
    }

}
