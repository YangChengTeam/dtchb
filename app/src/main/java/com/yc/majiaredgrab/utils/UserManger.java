package com.yc.majiaredgrab.utils;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.utils.DeviceUtils;
import com.yc.majiaredgrab.homeModule.module.HomeApiModule;
import com.yc.majiaredgrab.homeModule.module.bean.UserInfo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by suns  on 2020/11/19 18:12.
 */
public class UserManger {


    public static void reglog(String agent_id){
        String imei;
        if (CacheDataUtils.getInstance().isLogin()){
            imei=CacheDataUtils.getInstance().getUserInfo().getImei();
        }else {
            imei=DeviceUtils.getImei();
        }
       new HomeApiModule().reglog(imei,agent_id).compose(RxUtil.rxSchedulerHelper()).subscribe(new Subscriber<HttpResult<UserInfo>>() {
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
