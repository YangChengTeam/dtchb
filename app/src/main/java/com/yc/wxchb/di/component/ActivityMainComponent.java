package com.yc.wxchb.di.component;

import android.app.Activity;

import com.yc.wxchb.beans.activity.AdHotActivity;
import com.yc.wxchb.beans.activity.AnswerActivity;
import com.yc.wxchb.beans.activity.ComplaintActivity;
import com.yc.wxchb.beans.activity.ComplaintEdActivity;
import com.yc.wxchb.beans.activity.ExpressActivity;
import com.yc.wxchb.beans.activity.HelpQuestionActivity;
import com.yc.wxchb.beans.activity.HotActivity;
import com.yc.wxchb.beans.activity.InvationfriendActivity;
import com.yc.wxchb.beans.activity.LoginActivity;
import com.yc.wxchb.beans.activity.MainActivity;
import com.yc.wxchb.beans.activity.MoneyTaskActivity;
import com.yc.wxchb.beans.activity.NewsActivity;
import com.yc.wxchb.beans.activity.RedWallActivity;
import com.yc.wxchb.beans.activity.TiaoJinMainActivity;
import com.yc.wxchb.beans.activity.VideoActivity;
import com.yc.wxchb.beans.activity.WebViewActivity;
import com.yc.wxchb.beans.activity.WithDrawRecodeActivity;
import com.yc.wxchb.di.ActivityScope;
import com.yc.wxchb.di.module.ActivityMainModule;

import dagger.Component;


/**
 * Created by ccc
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityMainModule.class)
public interface ActivityMainComponent {

    Activity getActivity();
    void inject(MainActivity mainActivity);
    void inject(HotActivity hotActivity);
    void inject(AdHotActivity adHotActivity);
    void inject(ComplaintActivity complaintActivity);
    void inject(ComplaintEdActivity complaintEdActivity);
    void inject(MoneyTaskActivity moneyTaskActivity);
    void inject(LoginActivity loginActivity);
    void inject(InvationfriendActivity invationfriendActivity);
    void inject(HelpQuestionActivity helpQuestionActivity);
    void inject(VideoActivity videoActivity);
    void inject(WebViewActivity webViewActivity);
    void inject(RedWallActivity redWallActivity);
    void inject(AnswerActivity answerActivity);
    void inject(WithDrawRecodeActivity withDrawRecodeActivity);
    void inject(NewsActivity newsActivity);
    void inject(ExpressActivity expressActivity);
    void inject(TiaoJinMainActivity tiaoJinMainActivity);

}
