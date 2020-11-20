package com.yc.redevenlopes.utils;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.yc.redevenlopes.homeModule.module.HomeApiModule;
import com.yc.redevenlopes.homeModule.module.bean.UserInfo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by suns  on 2020/11/19 18:12.
 */
public class UserManger {


    public static void reglog(){
       new HomeApiModule().reglog().compose(RxUtil.rxSchedulerHelper()).subscribe(new Subscriber<HttpResult<UserInfo>>() {
           @Override
           public void onSubscribe(Subscription s) {

           }

           @Override
           public void onNext(HttpResult<UserInfo> userInfoHttpResult) {

           }

           @Override
           public void onError(Throwable t) {

           }

           @Override
           public void onComplete() {

           }
       });
    }
}
