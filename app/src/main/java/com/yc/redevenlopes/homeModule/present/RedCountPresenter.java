package com.yc.redevenlopes.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.yc.redevenlopes.homeModule.contact.AnswerContact;
import com.yc.redevenlopes.homeModule.contact.RedCountContact;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.AnswerBeans;
import com.yc.redevenlopes.homeModule.module.bean.UpQuanNumsBeans;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class RedCountPresenter extends RxPresenter<RedCountContact.View> implements RedCountContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public RedCountPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


}
