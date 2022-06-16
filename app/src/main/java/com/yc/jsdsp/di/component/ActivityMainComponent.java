package com.yc.jsdsp.di.component;

import android.app.Activity;

import com.yc.jsdsp.beans.activity.AdHotActivity;
import com.yc.jsdsp.beans.activity.AnswerActivity;
import com.yc.jsdsp.beans.activity.ComplaintActivity;
import com.yc.jsdsp.beans.activity.ComplaintEdActivity;
import com.yc.jsdsp.beans.activity.ExpressActivity;
import com.yc.jsdsp.beans.activity.HelpQuestionActivity;
import com.yc.jsdsp.beans.activity.HotActivity;
import com.yc.jsdsp.beans.activity.InvationfriendActivity;
import com.yc.jsdsp.beans.activity.LoginActivity;
import com.yc.jsdsp.beans.activity.MainActivity;
import com.yc.jsdsp.beans.activity.MoneyTaskActivity;
import com.yc.jsdsp.beans.activity.NewsActivity;
import com.yc.jsdsp.beans.activity.RedWallActivity;
import com.yc.jsdsp.beans.activity.TiaoJinMainActivity;
import com.yc.jsdsp.beans.activity.VideoActivity;
import com.yc.jsdsp.beans.activity.WebViewActivity;
import com.yc.jsdsp.beans.activity.WithDrawRecodeActivity;
import com.yc.jsdsp.di.ActivityScope;
import com.yc.jsdsp.di.module.ActivityMainModule;

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
