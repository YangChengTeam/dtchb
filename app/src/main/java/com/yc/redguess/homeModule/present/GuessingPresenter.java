package com.yc.redguess.homeModule.present;

import com.lq.lianjibusiness.base_libary.http.HttpResult;
import com.lq.lianjibusiness.base_libary.http.ResultSubscriber;
import com.lq.lianjibusiness.base_libary.http.RxUtil;
import com.lq.lianjibusiness.base_libary.ui.base.RxPresenter;
import com.lq.lianjibusiness.base_libary.utils.ToastUtil;
import com.yc.redguess.homeModule.contact.GuessingContact;
import com.yc.redguess.homeModule.module.HomeApiModule;
import com.yc.redguess.homeModule.module.bean.GuessBeans;
import com.yc.redguess.homeModule.module.bean.PostGuessNoBeans;
import com.yc.redguess.homeModule.module.bean.UpQuanNumsBeans;

import javax.inject.Inject;

/**
 * Created by suns  on 2020/10/14 18:02.
 */
public class GuessingPresenter extends RxPresenter<GuessingContact.View> implements GuessingContact.Presenter {

    private HomeApiModule apis;

    @Inject
    public GuessingPresenter(HomeApiModule apis) {
        this.apis = apis;
    }


    public void getGuessData(String group_id) {
        showWaiteDialog();
        addSubscribe(apis.getGuessData(group_id)
                .compose(RxUtil.<HttpResult<GuessBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<GuessBeans>(this) {
                    @Override
                    public void onAnalysisNext(GuessBeans data) {
                        mView.getGuessDataSuccess(data);
                    }

                    @Override
                    public void errorState(String message, String state) {
                        super.errorState(message, state);
                        mView.getGuessDataError();
                    }
                }));
    }

    public void submitGuessNo(String group_id, String info_id, String num) {
        showWaiteDialog();
        addSubscribe(apis.submitGuessNo(group_id,info_id,num)
                .compose(RxUtil.<HttpResult<PostGuessNoBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<PostGuessNoBeans>(this) {
                    @Override
                    public void onAnalysisNext(PostGuessNoBeans data) {
                        ToastUtil.showToast("提交成功");
                        mView.submitGuessNoSuccess(data);
                    }
                }));
    }
    public void updtreasure(String group_id) {
        addSubscribe(apis.updtreasure(group_id)
                .compose(RxUtil.<HttpResult<UpQuanNumsBeans>>rxSchedulerHelper())
                .subscribeWith(new ResultSubscriber<UpQuanNumsBeans>(this) {
                    @Override
                    public void onAnalysisNext(UpQuanNumsBeans data) {
                        mView.updtreasureSuccess(data);
                    }
                }));
    }
}
